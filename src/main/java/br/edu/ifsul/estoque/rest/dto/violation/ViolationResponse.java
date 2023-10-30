package br.edu.ifsul.estoque.rest.dto.violation;

import jakarta.validation.ConstraintViolation;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ViolationResponse {
    private String message;
    private Collection<FieldViolation> violations;

    public ViolationResponse(String message, Collection<FieldViolation> violations) {
        this.message = message;
        this.violations = violations;
    }

    public static <T> ViolationResponse listViolations (
            Set<ConstraintViolation<T>> violations){
        List<FieldViolation> errors = violations
                .stream()
                .map(cv -> new FieldViolation(cv.getPropertyPath().toString(), cv.getMessage()))
                .collect(Collectors.toList());
        String message = "Erro de Validação";

        return new ViolationResponse(message, errors);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Collection<FieldViolation> getViolations() {
        return violations;
    }

    public void setViolations(Collection<FieldViolation> violations) {
        this.violations = violations;
    }
}
