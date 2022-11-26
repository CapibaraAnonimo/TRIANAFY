package com.salesianostriana.dam.trianafy.dto.playlist;

import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.stereotype.Component;

@Component
public class PlaylistSongDtoMapper {
    public PlaylistSongDto songToPlaylistSongDto(Song song) {
        return PlaylistSongDto.builder().id(song.getId()).title(song.getTitle()).artist(song.getArtist().getName()).album(song.getAlbum()).year(song.getYear()).build();
    }
}
