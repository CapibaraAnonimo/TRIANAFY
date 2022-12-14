package com.salesianostriana.dam.trianafy.model;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter
@Setter
@Builder
public class Artist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
