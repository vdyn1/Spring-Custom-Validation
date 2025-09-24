package s29908.s29908tpo10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPasswordValidator implements ConstraintValidator<StrongPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.trim().isEmpty()) {
            return true;
        }

        boolean valid = true;
        context.disableDefaultConstraintViolation();

        if (!password.matches(".*[a-z].*")) {
            context.buildConstraintViolationWithTemplate("{error.password.lowercase}")
                    .addConstraintViolation();
            valid = false;
        }

        if (password.replaceAll("[^A-Z]", "").length() < 2) {
            context.buildConstraintViolationWithTemplate("{error.password.uppercase}")
                    .addConstraintViolation();
            valid = false;
        }

        if (password.replaceAll("[^0-9]", "").length() < 3) {
            context.buildConstraintViolationWithTemplate("{error.password.digits}")
                    .addConstraintViolation();
            valid = false;
        }

        if (password.replaceAll("[\\w\\s]", "").length() < 4) {
            context.buildConstraintViolationWithTemplate("{error.password.special}")
                    .addConstraintViolation();
            valid = false;
        }

        if (password.length() < 10) {
            context.buildConstraintViolationWithTemplate("{error.password.length}")
                    .addConstraintViolation();
            valid = false;
        }

        return valid;
    }
}
