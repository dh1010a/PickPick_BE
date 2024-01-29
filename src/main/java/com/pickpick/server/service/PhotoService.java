package com.pickpick.server.service;

import com.pickpick.server.domain.Category;
import com.pickpick.server.domain.Photo;
import com.pickpick.server.dto.PhotoRequest;
import com.pickpick.server.repository.CategoryRepository;
import com.pickpick.server.repository.PhotoRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhotoService {

    private final PhotoRepository photoRepository;
    private final CategoryRepository categoryRepository;
    public Photo create(PhotoRequest.CreateDTO request){
        Photo photo = Photo.builder()
            .imgUrl(request.getImgUrl())
            .build();
        return photoRepository.save(photo);
    }

    public Photo createCategory(PhotoRequest.CreateCategoryDTO request){
        Photo photo = photoRepository.findById(request.getPhotoId()).orElseThrow();
        List<Category> categories = categoryRepository.findByPhoto(photo);

        categoryRepository.deleteAll(categories);

        for(String category : request.getCategoryList()) {
            Category newCategory = Category.builder()
                .photo(photo)
                .name(category)
                .build();
            categoryRepository.save(newCategory);
        }
        return photoRepository.save(photo);
    }
}
