package com.salesianostriana.dam.trianafy.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@NamedEntityGraph
        (name = "playlist-addedTo",
                attributeNodes = {
                        @NamedAttributeNode(value = "addedTo",
                                subgraph = "addedTo-songs")
                }, subgraphs = {
                @NamedSubgraph(name = "addedTo-songs",
                        attributeNodes = {
                                @NamedAttributeNode("song")
                        })
        })
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AddedTo> addedTo = new ArrayList<>();


    public void addSong(AddedTo song) {
        addedTo.add(song);
    }

    public void deleteSong(AddedTo song) {
        addedTo.remove(song);
    }
}
