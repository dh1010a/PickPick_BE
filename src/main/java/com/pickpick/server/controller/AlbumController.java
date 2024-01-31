package com.pickpick.server.controller;

import com.pickpick.server.apiPayload.ApiResponse;
import com.pickpick.server.converter.AlbumConverter;
import com.pickpick.server.dto.AlbumRequest;
import com.pickpick.server.dto.AlbumResponse;
import com.pickpick.server.service.AlbumService;
import com.pickpick.server.validation.annotation.ExistUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping("/album/init")
    public ApiResponse<AlbumResponse.CreateDTO> create(@RequestBody @Valid AlbumRequest.CreateDTO request) {
        return ApiResponse.onSuccess(AlbumConverter.toCreateDTO(albumService.create(request)));
    }

    @GetMapping("/albums/{login_id}")
    public ApiResponse<AlbumResponse.GetAlbumDTO> getAlbum(@PathVariable("login_id") @ExistUser Long userId){
        return ApiResponse.onSuccess(AlbumConverter.toGetAlbumDTO(albumService.findById(userId)));
    }

}
