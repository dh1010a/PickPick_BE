package com.pickpick.server.member.dto;

import lombok.Builder;
import lombok.Data;

public class MemberResponseDto {

    @Builder
    public static class IsDuplicateDTO{
        private boolean isDuplicate;
    }

    @Data
    @Builder
    public static class SignupResponseDto{
        private String email;
        private boolean isSuccess;
    }
}
