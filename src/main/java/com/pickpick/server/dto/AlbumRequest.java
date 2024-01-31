package com.pickpick.server.dto;

import com.pickpick.server.domain.Photo;
import com.pickpick.server.domain.SharedAlbum;
import com.pickpick.server.domain.enums.ShareStatus;
import com.pickpick.server.validation.annotation.ExistUsers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;


public class AlbumRequest {

    @Getter
    public static class CreateDTO {

        @Size(max = 15)
        private String name;

        @NotBlank
        private String titleImgUrl;

        @NotNull
        private ShareStatus shareStatus;

        @ExistUsers
        private List<Long> userId;
    }

}
