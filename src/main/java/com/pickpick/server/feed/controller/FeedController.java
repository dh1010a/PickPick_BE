package com.pickpick.server.feed.controller;

import com.pickpick.server.global.apiPayload.ApiResponse;
import com.pickpick.server.converter.FeedConverter;
import com.pickpick.server.feed.dto.FeedRequest;
import com.pickpick.server.feed.dto.FeedResponse;
import com.pickpick.server.feed.service.FeedService;
import com.pickpick.server.global.validation.annotation.ExistAlbum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    @PostMapping("/feed")
    public ApiResponse<FeedResponse.CreateDTO> create(@RequestBody @Valid FeedRequest.CreateDTO request){
        return ApiResponse.onSuccess(FeedConverter.toCreateDTO(feedService.create(request)));
    }

    @GetMapping("/feeds/{album_id}")
    public ApiResponse<FeedResponse.GetFeedDTO> getFeed(@PathVariable("album_id") @ExistAlbum Long albumId){
        return ApiResponse.onSuccess(FeedConverter.toGetFeedDTO(feedService.getFeed(albumId)));
    }

    @PostMapping("/feed/update")
    public ApiResponse<FeedResponse.UpdateFeedDTO> updateFeed(@RequestBody @Valid FeedRequest.UpdateFeedDTO request){
        return ApiResponse.onSuccess(FeedConverter.toUpdateFeedDTO(feedService.updateFeed(request)));
    }

    @DeleteMapping("/feed/delete")
    public ApiResponse<FeedResponse.DeleteFeedDTO> deleteFeed(@RequestBody @Valid FeedRequest.DeleteFeedDTO request){
        feedService.deleteFeed(request);
        return ApiResponse.onSuccess(FeedConverter.toDeleteFeedDTO());
    }
}
