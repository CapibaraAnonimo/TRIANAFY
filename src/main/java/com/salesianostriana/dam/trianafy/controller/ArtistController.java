package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public
}
