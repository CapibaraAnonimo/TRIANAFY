package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.NewArtistDto;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;

    @Operation(
            summary = "Obtener todos los artistas",
            description = "Esta petición devuelve una lista con todos los aristas"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La lista contiene artistas",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron artistas",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class))}
            )
    })
    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> findAll() {
        List<Artist> artists = artistRepository.findAll();
        if (!artists.isEmpty())
            return ResponseEntity.ok().body(artists);
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findById(@PathVariable Long id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isPresent())
            return ResponseEntity.ok().body(artist.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/artist/")
    public ResponseEntity<Artist> addArtist(@RequestBody NewArtistDto newArtistDto) {
        Artist artist;
        if (newArtistDto.getName() == null)
            return ResponseEntity.badRequest().build();

        /*if (artistRepository.findAll().stream().anyMatch(art -> art.getName().equals(newArtistDto.getName())))
            return ResponseEntity.badRequest().build();*/


        artist = Artist.builder().name(newArtistDto.getName()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(artistRepository.save(artist));
    }

    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(@RequestBody NewArtistDto newArtistDto, @PathVariable Long id) {
        Optional<Artist> artistOpt = artistRepository.findById(id);
        Artist artist;
        if (artistOpt.isEmpty())
            return ResponseEntity.notFound().build();

        artist = artistOpt.get();
        artist.setName(newArtistDto.getName());
        return ResponseEntity.ok().body(artistRepository.save(artist));
    }

    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> deleteArtist(@PathVariable Long id) {
        //TODO te toca hacer la consulta para que sea más eficiente
        //TODO arregla el find con el optional pedazo de trozo de cacho de imbécil
        songRepository.findAll().stream().filter(song -> Optional.of(song.getArtist()).)/*.forEach(art -> art.setArtist(null))*/;
        artistRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
