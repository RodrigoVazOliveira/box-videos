package dev.rvz.boxvideos.core.domain.commons.exception;

public final class ResponseInputException {
    private Integer status = 400;
    private final Iterable<InfoValidationInput> inputs;

    public ResponseInputException(Iterable<InfoValidationInput> inputs) {
        this.inputs = inputs;
    }

    public Integer getStatus() {
        return status;
    }

    public Iterable<InfoValidationInput> getInputs() {
        return inputs;
    }
}
