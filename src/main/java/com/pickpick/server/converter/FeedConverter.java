package com.pickpick.server.converter;

import com.pickpick.server.feed.domain.Feed;
import com.pickpick.server.feed.dto.FeedResponse;
import java.util.List;

public class FeedConverter {

    public static FeedResponse.CreateDTO toCreateDTO(Feed feed){
        return FeedResponse.CreateDTO.builder()
            .feedId(feed.getId())
            .build();
    }

    public static FeedResponse.GetFeedDTO toGetFeedDTO(List<Long> feedIdList){
        return FeedResponse.GetFeedDTO.builder()
            .feedIdList(feedIdList)
            .build();
    }

    public static FeedResponse.UpdateFeedDTO toUpdateFeedDTO(Feed feed){
        return FeedResponse.UpdateFeedDTO.builder()
            .feedId(feed.getId())
            .build();
    }

    public static FeedResponse.DeleteFeedDTO toDeleteFeedDTO(){
        return FeedResponse.DeleteFeedDTO.builder()
            .result("success")
            .build();
    }
}
