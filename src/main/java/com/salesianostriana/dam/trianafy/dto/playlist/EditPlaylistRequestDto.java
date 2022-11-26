package com.salesianostriana.dam.trianafy.dto.playlist;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EditPlaylistRequestDto {
    private String name;

    private String description;
}
