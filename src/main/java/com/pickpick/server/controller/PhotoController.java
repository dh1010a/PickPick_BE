package com.pickpick.server.controller;

import com.pickpick.server.apiPayload.ApiResponse;
import com.pickpick.server.converter.PhotoConverter;
import com.pickpick.server.dto.PhotoRequest;
import com.pickpick.server.dto.PhotoResponse;
import com.pickpick.server.service.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/photos/{login_id}")
    public ApiResponse<PhotoResponse.GetPhotosDTO> getPhotos(@PathVariable("login_id") Long userId){
        return ApiResponse.onSuccess(PhotoConverter.toGetPhotosDTO(photoService.getPhotos(userId)));
    }

    @PostMapping("/photo/update")
    public ApiResponse<PhotoResponse.UpdatePhotoDTO> updatePhoto(@RequestBody @Valid PhotoRequest.UpdatePhotoDTO request){
        return ApiResponse.onSuccess(PhotoConverter.toUpdatePhotoDTO(photoService.updatePhoto(request)));
    }
}
