package com.pickpick.server.photo.repository;

import com.pickpick.server.photo.domain.PhotoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoCategoryRepository extends JpaRepository<PhotoCategory, Long> {

}
