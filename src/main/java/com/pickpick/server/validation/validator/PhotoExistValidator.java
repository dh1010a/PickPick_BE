package com.pickpick.server.validation.validator;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.repository.AlbumRepository;
import com.pickpick.server.repository.PhotoRepository;
import com.pickpick.server.validation.annotation.ExistAlbum;
import com.pickpick.server.validation.annotation.ExistPhoto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotoExistValidator implements ConstraintValidator<ExistPhoto, Long> {

    private final PhotoRepository photoRepository;
    @Override
    public void initialize(ExistPhoto constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = photoRepository.existsById(value);
        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PHOTO_NOT_FOUND.toString()).addConstraintViolation();

        }
        return isValid;
    }
}