package com.pickpick.server.dto;

import com.pickpick.server.validation.annotation.ExistAlbum;
import com.pickpick.server.validation.annotation.ExistFeed;
import com.pickpick.server.validation.annotation.ExistPhotos;
import com.pickpick.server.validation.annotation.ExistUser;
import java.util.List;
import lombok.Getter;

public class FeedRequest {

    @Getter
    public static class CreateDTO{

        @ExistUser
        private Long userId;

        @ExistAlbum
        private Long albumId;

        @ExistPhotos
        private List<Long> photoIdList;

        private String content;

    }

    @Getter
    public static class UpdateFeedDTO{
        @ExistFeed
        private Long feedId;

        private String content;
    }

    @Getter
    public static class DeleteFeedDTO{

        @ExistFeed
        private Long feedId;
    }

}
