package dev.rvz.boxvideos.core.validation.inputs;

import dev.rvz.boxvideos.core.domain.video.exception.InfoValidationInput;

import java.util.List;

public class ValidateIsNull implements ValidateInput {

    private final ValidateInput validateInput;
    private final List<InfoValidationInput> infoValidationInputs;

    public ValidateIsNull(ValidateInput validateInput, List<InfoValidationInput> infoValidationInputs) {
        this.validateInput = validateInput;
        this.infoValidationInputs = infoValidationInputs;
    }

    @Override
    public void verify(String nameInput, Object value) {
        if (value == null) {
            InfoValidationInput infoValidationInput = new InfoValidationInput(
                    nameInput,
                    "O campo %s está nulo. O campo %s é obrigatório!".formatted(nameInput, nameInput));
            infoValidationInputs.add(infoValidationInput);
        }

        if (validateInput != null) validateInput.verify(nameInput, value);
    }
}
