package dev.rvz.boxvideos.core.validation.inputs;

public record ValidateData(String nameInput,
                           String value,
                           Integer minLength,
                           Integer maxLength) {
}
