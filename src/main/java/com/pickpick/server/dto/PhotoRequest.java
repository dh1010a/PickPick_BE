package com.pickpick.server.dto;

import com.pickpick.server.domain.Category;
import com.pickpick.server.validation.annotation.ExistAlbum;
import com.pickpick.server.validation.annotation.ExistPhoto;
import com.pickpick.server.validation.annotation.ExistUser;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

public class PhotoRequest {

    @Getter
    public static class CreateDTO {

        @ExistUser
        private Long userId;

        @NotNull
        private String imgUrl;
    }

    @Getter
    public static class CreateCategoryDTO {

        @ExistPhoto
        private Long photoId;

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
