package com.pickpick.server.converter;

import com.pickpick.server.apiPayload.ApiResponse;
import com.pickpick.server.domain.Photo;
import com.pickpick.server.dto.PhotoResponse;

public class PhotoConverter {

    public static PhotoResponse.CreateDTO toCreateDTO(Photo photo){
        return PhotoResponse.CreateDTO.builder()
            .id(photo.getId())
            .build();
    }

    public static PhotoResponse.CreateCategoryDTO toCreateCategoryDTO(Photo photo){
        return PhotoResponse.CreateCategoryDTO.builder()
            .id(photo.getId())
            .build();
    }
}
