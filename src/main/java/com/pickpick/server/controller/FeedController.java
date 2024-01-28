package com.pickpick.server.controller;

import com.pickpick.server.apiPayload.ApiResponse;
import com.pickpick.server.converter.FeedConverter;
import com.pickpick.server.dto.FeedRequest;
import com.pickpick.server.dto.FeedResponse;
import com.pickpick.server.service.FeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
