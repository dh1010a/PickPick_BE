package com.pickpick.server.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberResponseDto {

    @Builder
    @Getter
    public static class IsDuplicateDTO{
        private Boolean isDuplicate;
    }

    @Data
    @Builder
    public static class SignupResponseDto{
        private String email;
        private Boolean isSuccess;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteDTO{
        private Long id;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UploadImgDTO {
        private String imgUrl;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IsSuccessDTO {
        private Boolean isSuccess;
    }

}
