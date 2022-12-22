package dev.rvz.boxvideos.core.validation.strings;

class ValidationIsBlank implements ValidationString {
    private final ValidationString validationStringNext;

    public ValidationIsBlank(ValidationString validationStringNext) {
        this.validationStringNext = validationStringNext;
    }

    @Override
    public Boolean verify(String value) {
        if (value.isBlank()) return true;
        if (validationStringNext == null) return false;

        return this.validationStringNext.verify(value);
    }
}
