package com.pickpick.server.global.validation.validator;

import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.photo.repository.PhotoRepository;
import com.pickpick.server.global.validation.annotation.ExistPhotos;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotosExistValidator implements ConstraintValidator<ExistPhotos, List<Long>> {

    private final PhotoRepository photoRepository;

    @Override
    public void initialize(ExistPhotos constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> values, ConstraintValidatorContext context) {
        boolean isValid = values.stream()
            .allMatch(value -> photoRepository.existsById(value));

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PHOTO_NOT_FOUND.toString()).addConstraintViolation();

        }
        return isValid;
    }
}
