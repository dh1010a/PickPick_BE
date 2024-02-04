package com.pickpick.server.album.dto;

import com.pickpick.server.member.domain.enums.ShareStatus;
import com.pickpick.server.global.validation.annotation.ExistAlbum;
import com.pickpick.server.global.validation.annotation.ExistMembers;
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

        @ExistMembers
        private List<Long> memberId;
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

        @ExistMembers
        private List<Long> memberId;

        private String imgUrl;
    }
}