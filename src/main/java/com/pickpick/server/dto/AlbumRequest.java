package com.pickpick.server.dto;

import com.pickpick.server.domain.Photo;
import com.pickpick.server.domain.SharedAlbum;
import com.pickpick.server.domain.enums.ShareStatus;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;


public class AlbumRequest {

    @Getter
    public static class CreateDTO {

        private String name;

        private String titleImgUrl;

        private ShareStatus shareStatus;

        private List<Long> userId;
    }

}
