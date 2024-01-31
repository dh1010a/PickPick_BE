package com.pickpick.server.validation.validator;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.repository.FeedRepository;
import com.pickpick.server.validation.annotation.ExistFeed;
import com.pickpick.server.validation.annotation.ExistPhoto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FeedExistValidator implements ConstraintValidator<ExistFeed, Long> {

    private final FeedRepository feedRepository;
    @Override
    public void initialize(ExistFeed constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = feedRepository.existsById(value);

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.FEED_NOT_FOUND.toString()).addConstraintViolation();

        }
        return isValid;
    }
}
