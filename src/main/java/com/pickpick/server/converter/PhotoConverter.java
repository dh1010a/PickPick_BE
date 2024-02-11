package com.pickpick.server.converter;

import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.photo.dto.PhotoResponse;
import com.pickpick.server.photo.dto.PhotoResponse.GetPhotosDTO;
import java.util.ArrayList;
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

    public static List<GetPhotosDTO> toGetPhotosDTO(List<Photo> photos){
        List<GetPhotosDTO> list = new ArrayList<>();
        for (Photo p : photos) {
            GetPhotosDTO getPhotosDTO = GetPhotosDTO.builder()
                    .photoId(p.getId())
                    .imgUrl(p.getImgUrl())
                    .build();
            list.add(getPhotosDTO);
        }
        return list;
    }

    public static PhotoResponse.UpdatePhotoDTO toUpdatePhotoDTO(Photo photo){
        return PhotoResponse.UpdatePhotoDTO.builder()
            .photoId(photo.getId())
            .build();
    }
}
