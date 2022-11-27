package com.salesianostriana.dam.trianafy.dto.playlist;

import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.stereotype.Component;

@Component
public class AllPlaylistsDtoMapper {
    public AllPlaylistsDto PlaylistToAllPlaylistsDto(Playlist playlist) {
        //TODO hacer consulta para contar mejor las canciones
        return AllPlaylistsDto.builder().id(playlist.getId()).name(playlist.getName()).numberOfSongs((long) playlist.getSongs().size()).build();
    }
}
