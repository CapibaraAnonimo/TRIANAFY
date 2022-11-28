package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.song.EditSongDto;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
import com.salesianostriana.dam.trianafy.service.SongService;
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
@Tag(name = "Canciones", description = "Operaciones con canciones")
public class SongController {

    private final SongService songService;
    private final SongRepository songRepository;
    private final PlaylistService playlistService;

    @Operation(
            summary = "Obtener todas las canciones",
            description = "Esta petición devuelve una lista con todas las canciones"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La lista contiene canciones",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Song.class)), examples = @ExampleObject("""
                            [
                                {
                                    "id": 4,
                                    "title": "19 días y 500 noches",
                                    "album": "19 días y 500 noches",
                                    "year": "1999",
                                    "artist": {
                                        "id": 1,
                                        "name": "Joaquín Sabina"
                                    }
                                },
                                {
                                    "id": 5,
                                    "title": "Donde habita el olvido",
                                    "album": "19 días y 500 noches",
                                    "year": "1999",
                                    "artist": {
                                        "id": 1,
                                        "name": "Joaquín Sabina"
                                    }
                                }
                            ]
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontraron canciones",
                    content = {@Content()}
            )
    })
    @GetMapping("/song/")
    public ResponseEntity<List<Song>> findAll() {
        List<Song> songs = songService.findAll();
        if (!songs.isEmpty())
            return ResponseEntity.ok().body(songs);
        else
            return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Obtener una canción",
            description = "Esta petición devuelve la canción con el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se encontró la canción",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Song.class), examples = @ExampleObject("""
                                {
                                    "id": 5,
                                    "title": "Donde habita el olvido",
                                    "album": "19 días y 500 noches",
                                    "year": "1999",
                                    "artist": {
                                         "id": 1,
                                         "name": "Joaquín Sabina"
                                    }
                                }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la canción",
                    content = {@Content()}
            )
    })
    @GetMapping("/song/{id}")
    public ResponseEntity<Song> findById(@Parameter(description = "id del la canción a buscar") @PathVariable Long id) {
        Optional<Song> song = songService.findById(id);
        if (song.isPresent())
            return ResponseEntity.ok().body(song.get());
        else
            return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Crear una canción",
            description = "Esta petición crea una nueva canción con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se creo la canción",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Song.class), examples = @ExampleObject("""
                            {
                                "id": 5,
                                "title": "Donde habita el olvido",
                                "album": "19 días y 500 noches",
                                "year": "1999",
                                "artist": {
                                     "id": 1,
                                     "name": "Joaquín Sabina"
                                }
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para crear una nueva canción",
                    content = {@Content()}
            )
    })
    @PostMapping("/song/")
    public ResponseEntity<Song> addSong(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creación de una nueva canción", content = @Content(examples = @ExampleObject("""
            {
                "title": "Donde habita el olvido",
                "album": "19 días y 500 noches",
                "year": "1999",
                "artist": {
                     "id": 1,
                     "name": "Joaquín Sabina"
                }
            }
            """))) @RequestBody EditSongDto editSongDto) {
        Song song;
        if (editSongDto.getTitle() == null || editSongDto.getAlbum() == null || editSongDto.getYear() == null || editSongDto.getArtist() == null)
            return ResponseEntity.badRequest().build();
        song = Song.builder().title(editSongDto.getTitle()).album(editSongDto.getAlbum()).year(editSongDto.getYear()).artist(editSongDto.getArtist()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(songService.add(song));
    }

    @Operation(
            summary = "Editar una canción",
            description = "Esta petición edita una canción con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se editó la canción",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Song.class), examples = @ExampleObject("""
                            {
                                "id": 5,
                                "title": "Donde habita el olvido",
                                "album": "19 días y 500 noches",
                                "year": "1999",
                                "artist": {
                                     "id": 1,
                                     "name": "Joaquín Sabina"
                                }
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Canción no encontrada",
                    content = {@Content()}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Id invalido",
                    content = {@Content()}
            )
    })
    @PutMapping("/song/{id}")
    public ResponseEntity<Song> editSong(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la edición de una canción", content = @Content(examples = @ExampleObject("""
            {
                "title": "Donde habita el olvido",
                "album": "19 días y 500 noches",
                "year": "1999",
                "artist": {
                     "id": 1,
                     "name": "Joaquín Sabina"
                }
            }
            """))) @RequestBody EditSongDto editSongDto, @Parameter(description = "id de la canción a editar") @PathVariable Long id) {

        if (editSongDto.getTitle() == null || editSongDto.getAlbum() == null || editSongDto.getYear() == null || editSongDto.getArtist() == null)
            return ResponseEntity.badRequest().build();

        if (!songRepository.existsById(id))
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok().body(songService.findById(id).map(song -> {
            song.setTitle(editSongDto.getTitle());
            song.setAlbum(editSongDto.getAlbum());
            song.setYear(editSongDto.getYear());
            song.setArtist(editSongDto.getArtist());
            songService.edit(song);
            return song;
        }).get());
    }

    @Operation(
            summary = "Elimina una canción",
            description = "Esta petición elimina la canción que contenga el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe la canción",
                    content = {@Content()}
            )
    })
    @DeleteMapping("/song/{id}")
    public ResponseEntity<Song> deleteSong(@Parameter(description = "id de la canción a eliminar") @PathVariable Long id) {
        if (songRepository.existsById(id)) {
            playlistService.findAll().forEach(playlist -> playlist.deleteSong(songService.findById(id).get()));
            songService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}
