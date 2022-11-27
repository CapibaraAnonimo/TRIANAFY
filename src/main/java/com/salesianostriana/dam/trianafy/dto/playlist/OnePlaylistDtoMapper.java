package com.salesianostriana.dam.trianafy.dto.playlist;

import com.salesianostriana.dam.trianafy.model.Playlist;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class OnePlaylistDtoMapper {
    private final PlaylistSongDtoMapper playlistSongDtoMapper;

    public OnePlaylistDto playlistToOnePlaylistDto(Playlist playlist) {
        return OnePlaylistDto.builder().id(playlist.getId()).name(playlist.getName()).description(playlist.getDescription()).songs(playlist.getSongs().stream().map(playlistSongDtoMapper::songToPlaylistSongDto).toList()).build();
    }
}
