package com.pickpick.server.service;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.UserHandler;
import com.pickpick.server.domain.Category;
import com.pickpick.server.domain.Photo;
import com.pickpick.server.domain.Users;
import com.pickpick.server.dto.PhotoRequest;
import com.pickpick.server.repository.CategoryRepository;
import com.pickpick.server.repository.PhotoRepository;
import com.pickpick.server.repository.UsersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;

    public Photo create(PhotoRequest.CreateDTO request) {
        Photo photo = Photo.builder()
            .user(usersRepository.findById(request.getUserId()).orElseThrow())
            .imgUrl(request.getImgUrl())
            .build();
        return photoRepository.save(photo);
    }

    public Photo createCategory(PhotoRequest.CreateCategoryDTO request) {
        Photo photo = photoRepository.findById(request.getPhotoId()).orElseThrow();
        List<Category> categories = categoryRepository.findByPhoto(photo);

        categoryRepository.deleteAll(categories);

        for (String category : request.getCategoryList()) {
            Category newCategory = Category.builder()
                .photo(photo)
                .name(category)
                .build();
            categoryRepository.save(newCategory);
        }
        return photoRepository.save(photo);
    }

    public List<String> getPhotos(Long userId) {
        Optional<Users> user = usersRepository.findById(userId);

        if (user.isEmpty()) {
            throw new UserHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        List<String> imgUrlList = new ArrayList<>();

        List<Photo> photoList = user.get().getPhotos();
        for (Photo photo : photoList) {
            imgUrlList.add(photo.getImgUrl());
        }
        return imgUrlList;
    }

    public Photo updatePhoto(PhotoRequest.UpdatePhotoDTO request){
        Photo photo = photoRepository.findById(request.getPhotoId()).orElseThrow();
        photo.setImgUrl(request.getImgUrl());
        return photoRepository.save(photo);
    }
}