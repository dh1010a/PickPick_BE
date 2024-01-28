package com.pickpick.server.converter;

import com.pickpick.server.domain.Feed;
import com.pickpick.server.dto.FeedResponse;

public class FeedConverter {

    public static FeedResponse.CreateDTO toCreateDTO(Feed feed){
        return FeedResponse.CreateDTO.builder()
            .feedId(feed.getId())
            .build();
    }
}
