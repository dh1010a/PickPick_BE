package com.pickpick.server.category.service;

import com.pickpick.server.category.domain.Category;
import com.pickpick.server.category.repository.CategoryRepository;
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
}
