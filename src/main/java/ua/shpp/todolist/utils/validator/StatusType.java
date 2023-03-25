package ua.shpp.todolist.utils.validator;

import ua.shpp.todolist.dto.Status;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = StatusValidator.class)
public @interface StatusType {
    Status value();
    String message() default "New task must have a status {value}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
