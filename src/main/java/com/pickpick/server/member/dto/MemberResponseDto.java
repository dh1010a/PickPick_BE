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
        private boolean isDuplicate;
    }

    @Data
    @Builder
    public static class SignupResponseDto{
        private String email;
        private boolean isSuccess;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeleteDTO{
        private Long id;
    }

}
