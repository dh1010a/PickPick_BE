package com.pickpick.server.category.dto;

import java.util.List;
import lombok.Data;

public class CategoryRequest {

	@Data
	public static class findPhotoByCategoryDto {

		private List<String> categories;

	}
}
