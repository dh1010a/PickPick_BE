package com.pickpick.server.member.dto;

import lombok.Builder;

public class MemberResponseDto {

    @Builder
    public static class IsDuplicateDTO{
        private boolean isDuplicate;
    }
}
