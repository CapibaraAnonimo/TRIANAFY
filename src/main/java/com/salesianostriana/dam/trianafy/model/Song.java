package com.salesianostriana.dam.trianafy.model;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@NamedEntityGraph
        (name = "artist-song",
                attributeNodes = {
                        @NamedAttributeNode(value = "artist",
                                subgraph = "addedTo")
                }, subgraphs = @NamedSubgraph(
                        name = "addedTo",
                attributeNodes =
        ))
public class Song {

    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String album;
    @Column(name = "year_of_song")
    private String year;

    @ManyToOne()
    private Artist artist;


}
