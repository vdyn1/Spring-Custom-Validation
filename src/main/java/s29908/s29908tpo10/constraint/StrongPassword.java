package s29908.s29908tpo10.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StrongPasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPassword {
    String message() default "{error.password.invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
