package org.arjunaoverdrive.newsapp.validation.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.arjunaoverdrive.newsapp.dao.UserRepository;
import org.arjunaoverdrive.newsapp.validation.annotations.EmailAlreadyExists;

@RequiredArgsConstructor
public class EmailAlreadyExistsValidator implements ConstraintValidator<EmailAlreadyExists, String> {

    private final UserRepository userRepository;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByEmail(email);
    }
}
