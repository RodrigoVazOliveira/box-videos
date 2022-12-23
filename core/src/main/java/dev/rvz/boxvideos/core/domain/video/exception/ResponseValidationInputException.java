package dev.rvz.boxvideos.core.domain.video.exception;

public class ResponseValidationInputException extends RuntimeException {
    private final Iterable<InfoValidationInput> infoValidationInputs;

    public ResponseValidationInputException(Iterable<InfoValidationInput> infoValidationInputs) {
        this.infoValidationInputs = infoValidationInputs;
    }
}
