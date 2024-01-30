package com.pickpick.server.dto;

import com.pickpick.server.domain.Category;
import java.util.List;
import lombok.Getter;

public class PhotoRequest {

    @Getter
    public static class CreateDTO {

        private Long userId;

        private String imgUrl;
    }

    @Getter
    public static class CreateCategoryDTO {

        private Long photoId;

        private List<String> categoryList;

    }

    @Getter
    public static class UpdatePhotoDTO {
        private Long photoId;
        private String imgUrl;
    }
}
