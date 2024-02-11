package com.pickpick.server.category.repository;

import com.pickpick.server.category.domain.Category;
import com.pickpick.server.photo.domain.Photo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);

    boolean existsByName(String name);
}
