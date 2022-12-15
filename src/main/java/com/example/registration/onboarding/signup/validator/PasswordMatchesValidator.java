package com.example.registration.onboarding.signup.validator;

import com.example.registration.onboarding.signup.RegistrationRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegistrationRequest> {

	@Override
	public boolean isValid(final RegistrationRequest user, final ConstraintValidatorContext context) {
		return user.getPassword().equals(user.getConfirmPassword());
	}

}
