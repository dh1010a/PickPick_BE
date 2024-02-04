package com.pickpick.server.photo.service;

import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.apiPayload.exception.handler.PhotoHandler;
import com.pickpick.server.global.apiPayload.exception.handler.MemberHandler;
import com.pickpick.server.photo.domain.Category;
import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.member.domain.Member;
import com.pickpick.server.photo.dto.PhotoRequest;
import com.pickpick.server.photo.repository.CategoryRepository;
import com.pickpick.server.photo.repository.PhotoRepository;
import com.pickpick.server.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final CategoryRepository categoryRepository;
    private final MemberRepository memberRepository;

    public Photo create(PhotoRequest.CreateDTO request) {
        Optional<Member> member = memberRepository.findById(request.getMemberId());
        if(member.isEmpty()){
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        Photo photo = Photo.builder()
            .member(member.get())
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
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isEmpty()) {
            throw new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        List<String> imgUrlList = new ArrayList<>();

        List<Photo> photoList = member.get().getPhotos();
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