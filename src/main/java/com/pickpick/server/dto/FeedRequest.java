package com.pickpick.server.dto;

import com.pickpick.server.domain.Photo;
import java.time.LocalDate;
import java.util.List;
import lombok.Getter;

public class FeedRequest {

    @Getter
    public static class CreateDTO{

        private Long albumId;

        private List<Long> photoIdList;

        private String content;

    }

}
