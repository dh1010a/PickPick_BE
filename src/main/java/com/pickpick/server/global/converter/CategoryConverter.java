package com.pickpick.server.global.converter;

import com.pickpick.server.category.dto.CategoryResponse.FindPhotosDTO;
import com.pickpick.server.photo.domain.Photo;
import java.util.ArrayList;
import java.util.List;

public class CategoryConverter {

	public static List<FindPhotosDTO> convertToFindPhotosDto(List<Photo> photos) {
		List<FindPhotosDTO> dtoList = new ArrayList<>();
		for (Photo p : photos) {
			FindPhotosDTO findPhotosDTO = FindPhotosDTO.builder()
					.photoId(p.getId())
					.imgUrl(p.getImgUrl())
					.build();
			dtoList.add(findPhotosDTO);
		}
		return dtoList;
	}
}
