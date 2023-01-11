package com.salesianostriana.dam.trianafy.repos;

import com.salesianostriana.dam.trianafy.model.Playlist;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    @EntityGraph(value = "playlist-addedTo", type = EntityGraph.EntityGraphType.LOAD)
    @Query(value = """
            select p
            from Playlist p left join fetch p.addedTo at left join fetch at.song s
            where p.id = :id
            """)
    public Optional<Playlist> findPlaylistByIdWithSongs(long id);

    @Query(value = """
            select p
            from Playlist p left join fetch p.addedTo at left join fetch at.song s left join fetch s.artist a
            where a.id = :id
            """)
    public Optional<List<Playlist>> findPlaylistByArtist(long id);
}
