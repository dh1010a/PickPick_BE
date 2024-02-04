package com.pickpick.server.photo.controller;

import com.pickpick.server.global.apiPayload.ApiResponse;
import com.pickpick.server.converter.PhotoConverter;
import com.pickpick.server.photo.dto.PhotoRequest;
import com.pickpick.server.photo.dto.PhotoResponse;
import com.pickpick.server.photo.service.PhotoService;
import com.pickpick.server.global.security.util.SecurityUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping("/photo")
    public ApiResponse<PhotoResponse.CreateDTO> create(@RequestBody @Valid PhotoRequest.CreateDTO request) {
        return ApiResponse.onSuccess(PhotoConverter.toCreateDTO(photoService.create(request)));
    }

    @PostMapping("/photo/category")
    public ApiResponse<PhotoResponse.CreateCategoryDTO> createCategory(@RequestBody @Valid PhotoRequest.CreateCategoryDTO request){
        return ApiResponse.onSuccess(PhotoConverter.toCreateCategoryDTO(photoService.createCategory(request)));
    }

    @GetMapping("/photos")
    public ApiResponse<PhotoResponse.GetPhotosDTO> getPhotos(){
        String userEmail = SecurityUtil.getLoginEmail();
        return ApiResponse.onSuccess(PhotoConverter.toGetPhotosDTO(photoService.getPhotos(userEmail)));
    }

    @PostMapping("/photo/update")
    public ApiResponse<PhotoResponse.UpdatePhotoDTO> updatePhoto(@RequestBody @Valid PhotoRequest.UpdatePhotoDTO request){
        return ApiResponse.onSuccess(PhotoConverter.toUpdatePhotoDTO(photoService.updatePhoto(request)));
    }
}
