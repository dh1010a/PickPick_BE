package com.pickpick.server.global.converter;

import com.pickpick.server.feed.domain.Feed;
import com.pickpick.server.feed.dto.FeedResponse;
import com.pickpick.server.feed.dto.FeedResponse.GetFeedDTO;
import com.pickpick.server.feed.dto.FeedResponse.GetFeedListDTO;
import com.pickpick.server.photo.domain.Photo;
import java.util.List;

public class FeedConverter {

    public static FeedResponse.CreateDTO toCreateDTO(Feed feed){
        return FeedResponse.CreateDTO.builder()
            .feedId(feed.getId())
            .build();
    }

    public static GetFeedListDTO toGetFeedListDTO(List<Long> feedIdList){
        return GetFeedListDTO.builder()
            .feedIdList(feedIdList)
            .build();
    }

    public static GetFeedDTO toGetFeedDTO(Feed feed){
        return GetFeedDTO.builder()
                .memberId(feed.getMember().getId())
                .albumId(feed.getAlbum().getId())
                .photoIdList(feed.getPhoto().stream().map(Photo::getId).toList())
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
