package com.salesianostriana.dam.trianafy.model;

import com.salesianostriana.dam.trianafy.repos.AddedToRepository;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class AddedTo {

    @EmbeddedId
    @ToString.Exclude
    private AddedToPK id;

    @ManyToOne
    @MapsId("playlistId")
    @ToString.Exclude
    private Playlist playlist;

    @ManyToOne()
    @MapsId("songId")
    private Song song;

    private LocalDateTime dateTime;

    private int playlistOrder;

    @PostConstruct
    private void asignarOrden() {
        playlistOrder = playlist.getAddedTo().size();
    }

    @PreRemove
    private void preRemove() {
/*        song = null;
        playlist = null;
        id = null;*/
    }
}
