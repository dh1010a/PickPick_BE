package com.pickpick.server.album.controller;

import com.pickpick.server.global.apiPayload.ApiResponse;
import com.pickpick.server.converter.AlbumConverter;
import com.pickpick.server.album.dto.AlbumRequest;
import com.pickpick.server.album.dto.AlbumResponse;
import com.pickpick.server.album.service.AlbumService;
import com.pickpick.server.global.security.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

    @GetMapping("/albums")
    public ApiResponse<AlbumResponse.GetAlbumDTO> getAlbum(){
        String userEmail = SecurityUtil.getLoginEmail();
        return ApiResponse.onSuccess(AlbumConverter.toGetAlbumDTO(albumService.findByEmail(userEmail)));
    }

    
    @DeleteMapping("/album/delete")
    public ApiResponse<AlbumResponse.DeleteAlbumDTO> deleteAlbum(@RequestBody @Valid AlbumRequest.DeleteAlbumDTO request){
        albumService.deleteAlbum(request);
        return ApiResponse.onSuccess(AlbumConverter.toDeleteAlbumDTO());
    }

    @PatchMapping("/album/update")
    public ApiResponse<AlbumResponse.UpdateAlbumDTO> updateAlbum(@RequestBody @Valid AlbumRequest.UpdateAlbumDTO request){
        return ApiResponse.onSuccess(AlbumConverter.toUpdateAlbumDTO(albumService.updateAlbum(request)));
    }
}