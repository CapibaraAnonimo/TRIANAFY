package com.salesianostriana.dam.trianafy.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OnePlaylistDto {
    private Long id;

    private String name;

    private String description;

    private List<PlaylistSongDto> songs = new ArrayList<>();
}
