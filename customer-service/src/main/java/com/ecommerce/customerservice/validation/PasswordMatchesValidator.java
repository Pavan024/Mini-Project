package com.ecommerce.customerservice.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.ecommerce.customerservice.entity.User;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public void initialize(final PasswordMatches constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
		final User user = (User) obj;
		return user.getPassword() != null;
	}

}
