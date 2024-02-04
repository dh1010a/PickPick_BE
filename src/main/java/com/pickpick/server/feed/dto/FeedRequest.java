package com.pickpick.server.feed.dto;

import com.pickpick.server.global.validation.annotation.ExistAlbum;
import com.pickpick.server.global.validation.annotation.ExistFeed;
import com.pickpick.server.global.validation.annotation.ExistPhotos;
import com.pickpick.server.global.validation.annotation.ExistMember;
import java.util.List;
import lombok.Getter;

public class FeedRequest {

    @Getter
    public static class CreateDTO{

        @ExistMember
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
