package com.pickpick.server.dto;

import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.validation.annotation.ExistAlbum;
import com.pickpick.server.validation.annotation.ExistUsers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;

public class AlbumRequest {

    @Getter
    public static class CreateDTO {

        @Size(min = 0, max = 15)
        private String name;

        private String titleImgUrl;

        @NotNull
        private ShareStatus shareStatus;

        @ExistUsers
        private List<Long> userId;
    }

    @Getter
    public static class DeleteAlbumDTO {

        @ExistAlbum
        private Long albumId;
    }

    @Getter
    public static class UpdateAlbumDTO {

        @ExistAlbum
        private Long albumId;

        @Size(min = 0, max = 15)
        private String title;

        @ExistUsers
        private List<Long> userId;

        private String imgUrl;
    }
}