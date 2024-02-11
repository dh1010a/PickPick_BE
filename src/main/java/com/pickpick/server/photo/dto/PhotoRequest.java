package com.pickpick.server.photo.dto;

import com.pickpick.server.global.validation.annotation.ExistPhoto;
import com.pickpick.server.global.validation.annotation.ExistMember;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

public class PhotoRequest {

    @Data
    public static class CreatePhotoDTO {

        private MultipartFile imgUrl;

        private List<String> categoryList;
    }

    @Data
    public static class findByCategoryDTO {
        private List<String> categoryList;
    }

    @Getter
    public static class UpdatePhotoDTO {
        @ExistPhoto
        private Long photoId;

        @NotNull
        private String imgUrl;
    }
}
