package com.salesianostriana.dam.trianafy.model;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@Embeddable
public class AddedToPK implements Serializable {
    long playlistId;
    long songId;
}
