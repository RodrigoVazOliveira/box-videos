package dev.rvz.boxvideos.core.validation.inputs;

import dev.rvz.boxvideos.core.domain.video.exception.InfoValidationInput;

import java.util.List;

public class ValidateMinLength implements ValidateLengthInput {

    private final ValidateLengthInput validateLengthInputNext;
    private final List<InfoValidationInput> infoValidationInputs;


    public ValidateMinLength(ValidateLengthInput validateLengthInputNext, List<InfoValidationInput> infoValidationInputs) {
        this.validateLengthInputNext = validateLengthInputNext;
        this.infoValidationInputs = infoValidationInputs;
    }

    @Override
    public void verify(ValidateData validateData) {
        String value = validateData.value();
        Integer lengthValue = value.length();
        Integer minLength = validateData.minLength();

        if (lengthValue <= minLength) {
            String nameInput = validateData.nameInput();
            InfoValidationInput infoValidationInput = new InfoValidationInput(
                    nameInput,
                    "O cmapo %s deve ter no mínimo %d de caracteres.".formatted(nameInput, minLength)
            );
            infoValidationInputs.add(infoValidationInput);
        }

        if (validateLengthInputNext != null) validateLengthInputNext.verify(validateData);
    }
}
