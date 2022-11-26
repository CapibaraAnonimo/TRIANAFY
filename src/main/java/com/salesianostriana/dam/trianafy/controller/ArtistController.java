package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.EditArtistDto;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Tag(name = "Artistas", description = "Operaciones con artistas")
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
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Artist.class)), examples = @ExampleObject("""
                            [
                                {
                                    "id": 1,
                                    "name": "Joaquín Sabina"
                                },
                                {
                                    "id": 2,
                                    "name": "Dua Lipa"
                                },
                                {
                                    "id": 3,
                                    "name": "Metallica"
                                }
                            ]
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron artistas",
                    content = {@Content()}
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

    @Operation(
            summary = "Obtener un artista",
            description = "Esta petición devuelve el artista con el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se encontró el artista",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class), examples = @ExampleObject("""
                                {
                                    "id": 1,
                                    "name": "Joaquín Sabina"
                                }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró el artistas",
                    content = {@Content()}
            )
    })
    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> findById(@Parameter(description = "id del artista a buscar") @PathVariable Long id) {
        Optional<Artist> artist = artistRepository.findById(id);
        if (artist.isPresent())
            return ResponseEntity.ok().body(artist.get());
        else
            return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Crear un artista",
            description = "Esta petición crea un nuevo artista con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se creo el artista",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class), examples = @ExampleObject("""
                            {
                                "id": 13,
                                "name": "Blind Guardian"
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para crear un nuevo artista",
                    content = {@Content()}
            )
    })
    @PostMapping("/artist/")
    public ResponseEntity<Artist> addArtist(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creación de un nuevo artista", content = @Content(examples = @ExampleObject("""
            {
                "name": "Blind Guardian"
            }
            """))) @RequestBody EditArtistDto editArtistDto) {
        Artist artist;
        if (editArtistDto.getName() == null)
            return ResponseEntity.badRequest().build();
        artist = Artist.builder().name(editArtistDto.getName()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(artistRepository.save(artist));
    }

    @Operation(
            summary = "Editar un artista",
            description = "Esta petición edita un artista con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se editó el artista",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Artist.class), examples = @ExampleObject("""
                            {
                                "id": 2,
                                "name": "Blind Guardian"
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Artista no encontrado",
                    content = {@Content()}
            )
    })
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editArtist(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la edición de un artista", content = @Content(examples = @ExampleObject("""
            {
                "name": "Blind Guardian"
            }
            """))) @RequestBody EditArtistDto editArtistDto, @Parameter(description = "id del artista a editar") @PathVariable Long id) {
        if (!artistRepository.existsById(id))
            return ResponseEntity.notFound().build();
        if (editArtistDto.getName() == null)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().body(artistRepository.findById(id).map(artist -> {
            artist.setName(editArtistDto.getName());
            artistRepository.save(artist);
            return artist;
        }).get());
    }

    @Operation(
            summary = "Elimina un artista",
            description = "Esta petición elimina el artista que contenga el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe el artista",
                    content = {@Content()}
            )
    })
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> deleteArtist(@Parameter(description = "id del artista a eliminar") @PathVariable Long id) {
        //TODO te toca hacer la consulta para que sea más eficiente
        if (artistRepository.existsById(id)) {
            songRepository.findAll().stream().filter(song -> Optional.ofNullable(song.getArtist()).isPresent()).forEach(art -> {
                if (art.getArtist().getId().equals(id))
                    art.setArtist(null);
            });
            artistRepository.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
