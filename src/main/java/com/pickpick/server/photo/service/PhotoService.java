package com.pickpick.server.photo.service;

import com.pickpick.server.category.service.CategoryService;
import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.apiPayload.exception.handler.MemberHandler;
import com.pickpick.server.global.file.FileService;
import com.pickpick.server.global.util.SecurityUtil;
import com.pickpick.server.category.domain.Category;
import com.pickpick.server.photo.domain.Photo;
import com.pickpick.server.member.domain.Member;
import com.pickpick.server.photo.domain.PhotoCategory;
import com.pickpick.server.photo.dto.PhotoRequest;
import com.pickpick.server.photo.repository.PhotoCategoryRepository;
import com.pickpick.server.photo.repository.PhotoRepository;
import com.pickpick.server.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotoService {

    private final CategoryService categoryService;
    private final PhotoCategoryRepository photoCategoryRepository;
    private final PhotoRepository photoRepository;
    private final MemberRepository memberRepository;
    private final FileService fileService;

    public Photo createPhoto(PhotoRequest.CreatePhotoDTO request) {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail()).orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Photo photo = Photo.builder()
                .member(member)
                .build();
        String savedImgUrl = fileService.savePhoto(request.getImgUrl(), member.getId());
        photo.setImgUrl(savedImgUrl);

        List<Category> categories = categoryService.createCategory(request.getCategoryList());
        List<PhotoCategory> photoCategory = photo.getPhotoCategories();

        for (Category category : categories) {
            PhotoCategory newPhotoCategory = PhotoCategory.builder()
                .photo(photo)
                .category(category)
                .build();
            photoCategoryRepository.save(newPhotoCategory);

            category.getPhotoCategories().add(newPhotoCategory);
            photoCategory.add(newPhotoCategory);
        }
        member.getPhotos().add(photo);

        return photoRepository.save(photo);
    }

    public List<Photo> getPhotos() {
        Member member = memberRepository.findByEmail(SecurityUtil.getLoginEmail())
                .orElseThrow(() -> new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return member.getPhotos();
    }


    public Photo updatePhoto(PhotoRequest.UpdatePhotoDTO request){
        Photo photo = photoRepository.findById(request.getPhotoId()).orElseThrow();
        photo.setImgUrl(request.getImgUrl());
        return photoRepository.save(photo);
    }
}