package dev.rvz.boxvideos.core.validation.strings;

class ValidationIsNull implements ValidationString {
    private final ValidationString validationStringNext;

    public ValidationIsNull(ValidationString validationStringNext) {
        this.validationStringNext = validationStringNext;
    }

    @Override
    public Boolean verify(String value) {
        if (value == null) return false;
        if (validationStringNext == null) return true;

        return this.validationStringNext.verify(value);
    }
}
