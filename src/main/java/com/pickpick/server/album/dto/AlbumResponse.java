package com.pickpick.server.album.dto;

import com.pickpick.server.album.domain.Album;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
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
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AlbumDto {
        private Long id;
        private String name;
        private LocalDate createdAt;
        private String titleImgUrl;
        private List<Long> sharedAlbumIds;
        private List<Long> feedIds;
        private List<Long> memberIds;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetSharedAlbumDTO {

        private List<AlbumDto> album;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetNonSharedAlbumDTO {
        private List<AlbumDto> album;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteAlbumDTO{
        private String result;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateAlbumDTO{
        private Long albumId;
    }
}
