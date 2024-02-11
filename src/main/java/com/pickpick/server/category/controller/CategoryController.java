package com.pickpick.server.category.controller;

import com.pickpick.server.category.dto.CategoryRequest;
import com.pickpick.server.category.dto.CategoryResponse.FindPhotosDTO;
import com.pickpick.server.category.service.CategoryService;
import com.pickpick.server.global.apiPayload.ApiResponse;
import com.pickpick.server.global.converter.CategoryConverter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

	private final CategoryService categoryService;

	@GetMapping("/category")
	public ApiResponse<List<FindPhotosDTO>> findPhotoByCategory(@RequestBody CategoryRequest.findPhotoByCategoryDto request) {
		return ApiResponse.onSuccess(CategoryConverter.convertToFindPhotosDto(categoryService.findPhotoByCategory(request)));
	}
}
