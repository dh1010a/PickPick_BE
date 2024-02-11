package com.pickpick.server.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class CategoryResponse {

	@Builder
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FindPhotosDTO {

		private Long photoId;
		private String imgUrl;

	}

}
