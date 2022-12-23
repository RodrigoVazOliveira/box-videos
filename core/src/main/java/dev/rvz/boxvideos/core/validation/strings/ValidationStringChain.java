package dev.rvz.boxvideos.core.validation.strings;

public class ValidationStringChain {

    private final String value;
    private ValidationString validationString;

    public ValidationStringChain(String value) {
        this.value = value;
    }

    public Boolean isValid() {
        configure();
        return validationString.verify(value);
    }

    private void configure() {
        validationString = new ValidationIsNull(
                new ValidationIsBlank(
                        new ValidationStringIsEmpty(null)
                )
        );
    }
}
