package com.pickpick.server.repository;

import com.pickpick.server.domain.Album;
import com.pickpick.server.domain.SharedAlbum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedAlbumRepository extends JpaRepository<SharedAlbum, Long> {
    List<SharedAlbum> findByAlbum(Album album);
}
