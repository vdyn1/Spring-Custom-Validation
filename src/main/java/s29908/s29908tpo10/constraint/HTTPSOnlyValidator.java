package s29908.s29908tpo10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HTTPSOnlyValidator implements ConstraintValidator<HTTPSOnly, String> {

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null || url.isBlank()) return true;

        return url.startsWith("https://");
    }
}
