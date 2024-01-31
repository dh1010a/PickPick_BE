package com.pickpick.server.validation.validator;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.repository.AlbumRepository;
import com.pickpick.server.validation.annotation.ExistAlbum;
import com.pickpick.server.validation.annotation.ExistUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AlbumExistValidator implements ConstraintValidator<ExistAlbum, Long> {

    private final AlbumRepository albumRepository;
    @Override
    public void initialize(ExistAlbum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = albumRepository.existsById(value);
        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ALBUM_NOT_FOUND.toString()).addConstraintViolation();

        }
        return isValid;
    }
}
