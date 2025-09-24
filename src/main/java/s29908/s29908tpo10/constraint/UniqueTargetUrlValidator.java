package s29908.s29908tpo10.constraint;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import s29908.s29908tpo10.repository.ILinkRepository;

@Component
public class UniqueTargetUrlValidator implements ConstraintValidator<UniqueTargetUrl, String> {

    private final ILinkRepository repository;

    @Autowired
    public UniqueTargetUrlValidator(ILinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return repository.findByTargetURL(value).isEmpty();
    }
}
