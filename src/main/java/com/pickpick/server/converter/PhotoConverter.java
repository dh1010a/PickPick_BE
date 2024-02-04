package com.pickpick.server.converter;

import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.photo.dto.PhotoResponse;
import java.util.List;

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

    public static PhotoResponse.GetPhotosDTO toGetPhotosDTO(List<String> imgUrlList){
        return PhotoResponse.GetPhotosDTO.builder()
            .imgUrlList(imgUrlList)
            .build();
    }

    public static PhotoResponse.UpdatePhotoDTO toUpdatePhotoDTO(Photo photo){
        return PhotoResponse.UpdatePhotoDTO.builder()
            .photoId(photo.getId())
            .build();
    }
}
