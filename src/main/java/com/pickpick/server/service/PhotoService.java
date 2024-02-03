package com.pickpick.server.service;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.apiPayload.exception.handler.PhotoHandler;
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
import javax.swing.text.html.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final CategoryRepository categoryRepository;
    private final UsersRepository usersRepository;

    public Photo create(PhotoRequest.CreateDTO request) {
        Optional<Users> user = usersRepository.findById(request.getUserId());
        if(user.isEmpty()){
            throw new UserHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        Photo photo = Photo.builder()
            .user(user.get())
            .imgUrl(request.getImgUrl())
            .build();
        return photoRepository.save(photo);
    }

    public Photo createCategory(PhotoRequest.CreateCategoryDTO request) {
        Optional<Photo> photo = photoRepository.findById(request.getPhotoId());
        if(photo.isEmpty()){
            throw new PhotoHandler(ErrorStatus.PHOTO_NOT_FOUND);
        }
        List<Category> categories = categoryRepository.findByPhoto(photo.get());

        categoryRepository.deleteAll(categories);

        for (String category : request.getCategoryList()) {
            Category newCategory = Category.builder()
                .photo(photo.get())
                .name(category)
                .build();
            categoryRepository.save(newCategory);
        }
        return photoRepository.save(photo.get());
    }

    public List<String> getPhotos(String email) {
        Optional<Users> user = usersRepository.findByEmail(email);

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