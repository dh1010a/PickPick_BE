package com.pickpick.server.validation.validator;

import com.pickpick.server.apiPayload.code.status.ErrorStatus;
import com.pickpick.server.domain.Users;
import com.pickpick.server.repository.UsersRepository;
import com.pickpick.server.validation.annotation.ExistUser;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserExistValidator implements ConstraintValidator<ExistUser, Long> {

    private final UsersRepository usersRepository;

    @Override
    public void initialize(ExistUser constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        boolean isValid = usersRepository.findById(value).isPresent();

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.MEMBER_NOT_FOUND.toString()).addConstraintViolation();

        }
        return isValid;
    }
}
