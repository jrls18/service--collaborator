package br.com.developcorporation.collaborator.rest.exception.error;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


public class ConstraintViolationException extends ValidationException {

    private static final long serialVersionUID = 1805544247561323594L;

    private final Set<ConstraintViolation<?>> constraintViolations;

    public ConstraintViolationException(String message, Set<? extends ConstraintViolation<?>> constraintViolations) {
        super(message);
        if (constraintViolations == null) {
            this.constraintViolations = null;
        } else {
            this.constraintViolations = new HashSet(constraintViolations);
        }

    }

    public ConstraintViolationException(Set<? extends ConstraintViolation<?>> constraintViolations) {
        this(constraintViolations != null ? toString(constraintViolations) : null, constraintViolations);
    }

    public Set<ConstraintViolation<?>> getConstraintViolations() {
        return this.constraintViolations;
    }

    private static String toString(Set<? extends ConstraintViolation<?>> constraintViolations) {
        return constraintViolations.stream().map(cv -> {
            return cv == null ? "null" : cv.getPropertyPath() + ": " + cv.getMessage();
        }).collect(Collectors.joining(", "));
    }
}
