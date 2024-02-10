package com.pickpick.server.notification.dto;

import lombok.Getter;

@Getter
public class RequestDTO {
    private String targetToken;
    private String title;
    private String body;
}
