package dev.rvz.boxvideos.core.validation.inputs;

import dev.rvz.boxvideos.core.domain.video.exception.InfoValidationInput;

import java.util.List;

public class ValidateInputChain {
    private final List<InfoValidationInput> infoValidationInputs;
    private ValidateInput validateInput;

    public ValidateInputChain(List<InfoValidationInput> infoValidationInputs) {
        this.infoValidationInputs = infoValidationInputs;
    }

    public List<InfoValidationInput> validate(String nameInput, Object input) {
        configure();
        validateInput.verify(nameInput, input);

        return infoValidationInputs;
    }

    private void configure() {
        validateInput = new ValidateIsNull(
                new ValidateIsBlank(null, infoValidationInputs), infoValidationInputs
        );
    }
}