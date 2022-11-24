package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.NewArtistDto;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
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

        if (artistRepository.findAll().stream().anyMatch(art -> art.getName().equals(newArtistDto.getName())))
            return ResponseEntity.badRequest().build();


        artist = Artist.builder().name(newArtistDto.getName()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(artistRepository.save(artist));
    }

    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> eddit(@RequestBody NewArtistDto newArtistDto, @PathVariable long id) {
        Optional<Artist> artistOpt = artistRepository.findById(id);
        Artist artist;
        if (artistOpt.isEmpty())
            return ResponseEntity.notFound().build();

        artist = artistOpt.get();
        artist.setName(newArtistDto.getName());
        return ResponseEntity.ok().body(artistRepository.save(artist));
    }
}
