package dev.rvz.boxvideos.core.validation.strings;

class ValidationIsNull implements ValidationString{
    private final ValidationString validationStringNext;

    public ValidationIsNull(ValidationString validationStringNext) {
        this.validationStringNext = validationStringNext;
    }

    @Override
    public Boolean verify(String value) {
        if (value == null) return true;
        if (validationStringNext == null) return false;

        return this.validationStringNext.verify(value);
    }
}
