package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.dto.playlist.*;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
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
@Tag(name = "Listas de reproducción", description = "Operaciones con listas de reproducción")
public class PlaylistController {

    private final PlaylistRepository playlistRepository;

    private final AllPlaylistsDtoMapper allPlaylistsDtoMapper;
    private final OnePlaylistDtoMapper onePlaylistDtoMapper;

    @Operation(
            summary = "Obtener todas las playlists",
            description = "Esta petición devuelve una lista con todas las playlists"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "La lista contiene playlists",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = AllPlaylistsDto.class)), examples = @ExampleObject("""
                            [
                                {
                                    "id": 12,
                                    "name": "Random",
                                    "numberOfSongs": 4
                                }
                            ]
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró ninguna playlist",
                    content = {@Content()}
            )
    })
    @GetMapping("/list/")
    public ResponseEntity<List<AllPlaylistsDto>> findAll() {
        List<AllPlaylistsDto> allPlaylistsDtos = playlistRepository.findAll().stream().map(allPlaylistsDtoMapper::PlaylistToAllPlaylistsDto).toList();
        if (!allPlaylistsDtos.isEmpty())
            return ResponseEntity.ok().body(allPlaylistsDtos);
        else
            return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Obtener una playlist",
            description = "Esta petición devuelve la playlist con el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se encontró la playlist",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OnePlaylistDto.class), examples = @ExampleObject("""
                            {
                                "id": 12,
                                "name": "Random",
                                "description": "Una lista muy loca",
                                "songs": [
                                    {
                                        "id": 9,
                                        "title": "Enter Sandman",
                                        "artist": "Metallica",
                                        "album": "Metallica",
                                        "year": "1991"
                                    },
                                    {
                                        "id": 8,
                                        "title": "Love Again",
                                        "artist": "Dua Lipa",
                                        "album": "Future Nostalgia",
                                        "year": "2021"
                                    }
                                ]
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la playlist",
                    content = {@Content()}
            )
    })
    @GetMapping("/list/{id}")
    public ResponseEntity<OnePlaylistDto> findById(@Parameter(description = "id del la canción a buscar") @PathVariable Long id) {
        Optional<Playlist> playlist = playlistRepository.findById(id);
        if (playlist.isPresent())
            return ResponseEntity.ok().body(onePlaylistDtoMapper.PlaylistToOnePlaylistDto(playlist.get()));
        else
            return ResponseEntity.notFound().build();
    }

    @Operation(
            summary = "Crear una playlist",
            description = "Esta petición crea una nueva playlist con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se creo la playlist",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = NewPlaylistResponseDto.class), examples = @ExampleObject("""
                            {
                                "id": 12,
                                "name": "Random",
                                "description": "Una lista muy loca",
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos inválidos para crear una nueva playlist",
                    content = {@Content()}
            )
    })
    @PostMapping("/list/")
    public ResponseEntity<NewPlaylistResponseDto> addList(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la creación de una nueva playlist", content = @Content(examples = @ExampleObject("""
            {
                "name": "Random",
                "description": "Una lista muy loca",
            }
            """))) @RequestBody NewPlaylistRequestDto newPlaylistRequestDto) {
        Playlist playlist;
        if (newPlaylistRequestDto.getName() == null || newPlaylistRequestDto.getDescription() == null)
            return ResponseEntity.badRequest().build();

        playlist = Playlist.builder().name(newPlaylistRequestDto.getName()).description(newPlaylistRequestDto.getDescription()).build();
        playlistRepository.save(playlist);
        return ResponseEntity.status(HttpStatus.CREATED).body(NewPlaylistResponseDto.builder().id(playlist.getId()).name(playlist.getName()).description(playlist.getDescription()).build());
    }

    @Operation(
            summary = "Editar una playlist",
            description = "Esta petición edita una playlist con los datos proporcionados"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Se editó la canción",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AllPlaylistsDto.class), examples = @ExampleObject("""
                            {
                                "id": 12,
                                "name": "Random",
                                "numberOfSongs": 4
                            }
                            """))}
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Playlist no encontrada",
                    content = {@Content()}
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Datos no validos",
                    content = {@Content()}
            )
    })
    @PutMapping("/list/{id}")
    public ResponseEntity<AllPlaylistsDto> editList(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos necesarios para la edición de una canción", content = @Content(examples = @ExampleObject("""
            {
                "name": "Random",
                "description": "Una lista muy loca"
            }
            """))) @RequestBody EditPlaylistRequestDto editPlaylistRequestDto, @Parameter(description = "id de la playlist a editar") @PathVariable Long id) {
        if (editPlaylistRequestDto.getName() == null || editPlaylistRequestDto.getDescription() == null)
            return ResponseEntity.badRequest().build();

        if (!playlistRepository.existsById(id))
            return ResponseEntity.notFound().build();

        playlistRepository.save(Playlist.builder().id(id).name(editPlaylistRequestDto.getName()).description(editPlaylistRequestDto.getDescription()).songs(playlistRepository.findById(id).get().getSongs()).build());
        return ResponseEntity.ok().body(AllPlaylistsDto.builder().id(id).name(editPlaylistRequestDto.getName()).numberOfSongs((long) playlistRepository.findById(id).get().getSongs().size()).build());
    }

    @Operation(
            summary = "Elimina una playlist",
            description = "Esta petición elimina la playlist que contenga el id indicado"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "No existe la playlist",
                    content = {@Content()}
            )
    })
    @DeleteMapping("/list/{id}")
    public ResponseEntity<Playlist> deleteList(@Parameter(description = "id de la playlist a eliminar") @PathVariable Long id) {
        if (playlistRepository.existsById(id))
            playlistRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
