package ua.shpp.todolist.validator;

import ua.shpp.todolist.utils.Status;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class StatusValidator implements ConstraintValidator<StatusTypeSubset, Status> {

    private Status[] subset;
    @Override
    public void initialize(StatusTypeSubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Status status, ConstraintValidatorContext context) {
        return status == null || Arrays.asList(subset).contains(status);
    }
}
