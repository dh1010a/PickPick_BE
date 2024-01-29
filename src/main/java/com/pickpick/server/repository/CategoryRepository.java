package com.pickpick.server.repository;

import com.pickpick.server.domain.Category;
import com.pickpick.server.domain.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByPhoto(Photo photo);
}
