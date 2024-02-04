package com.pickpick.server.album.repository;

import com.pickpick.server.album.domain.Album;
import com.pickpick.server.album.domain.SharedAlbum;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SharedAlbumRepository extends JpaRepository<SharedAlbum, Long> {
    List<SharedAlbum> findByAlbum(Album album);
}
