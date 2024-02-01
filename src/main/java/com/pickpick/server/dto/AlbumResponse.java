package com.pickpick.server.dto;

import com.pickpick.server.domain.Album;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AlbumResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateDTO {
        private Long albumId;
        private LocalDate createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetAlbumDTO {

        private List<Long> shareAlbumId;

        private List<Long> nonShareAlbumId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteAlbumDTO{
        private String result;
    }
}
