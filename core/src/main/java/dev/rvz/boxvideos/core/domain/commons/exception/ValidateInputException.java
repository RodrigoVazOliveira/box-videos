package dev.rvz.boxvideos.core.domain.commons.exception;

public class ValidateInputException extends RuntimeException {
    private final Iterable<InfoValidationInput> infoValidationInputs;

    public ValidateInputException(Iterable<InfoValidationInput> infoValidationInputs) {
        this.infoValidationInputs = infoValidationInputs;
    }

    public Iterable<InfoValidationInput> getInfoValidationInputs() {
        return infoValidationInputs;
    }
}
