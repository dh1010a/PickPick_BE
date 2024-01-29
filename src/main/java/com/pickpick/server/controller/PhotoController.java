package com.pickpick.server.controller;

import com.pickpick.server.apiPayload.ApiResponse;
import com.pickpick.server.converter.PhotoConverter;
import com.pickpick.server.dto.PhotoRequest;
import com.pickpick.server.dto.PhotoResponse;
import com.pickpick.server.service.PhotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
}
