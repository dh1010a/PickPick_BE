package com.pickpick.server.photo.dto;

import com.pickpick.server.global.validation.annotation.ExistPhoto;
import com.pickpick.server.global.validation.annotation.ExistMember;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;

public class PhotoRequest {

    @Getter
    public static class CreateDTO {

        @ExistMember
        private Long memberId;

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
