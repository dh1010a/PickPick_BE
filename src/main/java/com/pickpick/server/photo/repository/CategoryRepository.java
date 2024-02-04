package com.pickpick.server.photo.repository;

import com.pickpick.server.photo.domain.Category;
import com.pickpick.server.photo.domain.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByPhoto(Photo photo);
}
