package com.pickpick.server.dto;

import com.pickpick.server.domain.Album;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

public class AlbumResponse {

    @Builder
    @Getter
    public static class CreateDTO {
        private Long albumId;
        private LocalDate createdAt;
    }

}
