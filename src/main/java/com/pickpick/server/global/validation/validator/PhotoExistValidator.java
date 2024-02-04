package com.pickpick.server.global.validation.validator;

import com.pickpick.server.global.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.global.validation.annotation.ExistPhoto;
import com.pickpick.server.photo.repository.PhotoRepository;
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