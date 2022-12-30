package dev.rvz.boxvideos.core.validation.inputs;

import dev.rvz.boxvideos.core.domain.commons.exception.InfoValidationInput;

import java.util.List;

public class ValidateInputChain {
    private final List<InfoValidationInput> infoValidationInputs;

    public ValidateInputChain(List<InfoValidationInput> infoValidationInputs) {
        this.infoValidationInputs = infoValidationInputs;
    }

    public ValidateInputChain validate(String nameInput, Object input) {
        ValidateInput validateInput = configureExistsData();
        validateInput.verify(nameInput, input);

        return this;
    }

    public ValidateInputChain validateLength(ValidateData validateData) {
        for (InfoValidationInput validationInput : infoValidationInputs) {
            if (validationInput.input() == validateData.nameInput()) {
                return this;
            }
        }

        ValidateLengthInput validateLengthInput = configureValidateLength();
        validateLengthInput.verify(validateData);

        return this;
    }

    private ValidateIsNull configureExistsData() {
        return new ValidateIsNull(
                new ValidateIsBlank(null, infoValidationInputs), infoValidationInputs
        );
    }

    private ValidateLengthInput configureValidateLength() {
        return new ValidateMinLength(new ValidateMaxLength(null, infoValidationInputs), infoValidationInputs);
    }
}
