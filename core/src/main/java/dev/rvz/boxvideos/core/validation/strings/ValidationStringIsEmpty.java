package dev.rvz.boxvideos.core.validation.strings;

class ValidationStringIsEmpty implements ValidationString {

    private final ValidationString validationStringNext;

    public ValidationStringIsEmpty(ValidationString validationStringNext) {
        this.validationStringNext = validationStringNext;
    }

    @Override
    public Boolean verify(String value) {
        if (value.isEmpty()) return true;
        if (validationStringNext == null) return false;

        return this.validationStringNext.verify(value);
    }

}
