package com.pickpick.server.category.service;

import com.pickpick.server.category.domain.Category;
import com.pickpick.server.category.dto.CategoryRequest.findPhotoByCategoryDto;
import com.pickpick.server.category.repository.CategoryRepository;
import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.photo.domain.PhotoCategory;
import com.pickpick.server.photo.repository.PhotoCategoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final PhotoCategoryRepository photoCategoryRepository;

	public List<Category> createCategory(List<String> list) {
		List<Category> categories = new ArrayList<>();
		for (String x : list) {
			if (categoryRepository.existsByName(x)) {
				categories.add(categoryRepository.findByName(x));
			} else {
				Category newCategory = Category.builder()
						.name(x)
						.build();
				categories.add(newCategory);
			}
		}
		return categories;
	}

	public List<Photo> findPhotoByCategory(findPhotoByCategoryDto request) {
		List<Photo> photos = new ArrayList<>();
		for (String x : request.getCategories()) {
			if (categoryRepository.existsByName(x)) {
				Category category = categoryRepository.findByName(x);
				List<PhotoCategory> photoCategoryList = category.getPhotoCategories();
				for (PhotoCategory p : photoCategoryList) {
					if (!photos.contains(p.getPhoto())) {
						photos.add(p.getPhoto());
					}
				}

			}
		}
		return photos;
	}
}
