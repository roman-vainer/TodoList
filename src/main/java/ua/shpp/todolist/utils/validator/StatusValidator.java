package ua.shpp.todolist.utils.validator;

import ua.shpp.todolist.dto.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StatusValidator implements ConstraintValidator<StatusType, Status> {

    private Status value;
    @Override
    public void initialize(StatusType constraint) {
        this.value = constraint.value();
    }

    @Override
    public boolean isValid(Status status, ConstraintValidatorContext context) {
        return status == null || value == status;
    }
}
