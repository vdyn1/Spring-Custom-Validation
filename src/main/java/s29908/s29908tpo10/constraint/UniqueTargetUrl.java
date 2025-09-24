package s29908.s29908tpo10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UniqueTargetUrlValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueTargetUrl {
    String message() default "{error.url.duplicate}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
