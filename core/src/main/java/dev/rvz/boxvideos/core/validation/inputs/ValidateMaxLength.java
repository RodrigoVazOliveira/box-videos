package dev.rvz.boxvideos.core.validation.inputs;

import dev.rvz.boxvideos.core.domain.video.exception.InfoValidationInput;

import java.util.List;

public class ValidateMaxLength implements ValidateLengthInput {

    private final ValidateLengthInput validateLengthInputNext;
    private final List<InfoValidationInput> infoValidationInputs;


    public ValidateMaxLength(ValidateLengthInput validateLengthInputNext, List<InfoValidationInput> infoValidationInputs) {
        this.validateLengthInputNext = validateLengthInputNext;
        this.infoValidationInputs = infoValidationInputs;
    }

    @Override
    public void verify(ValidateData validateData) {
        String value = validateData.value();
        Integer lengthValue = value.length();
        Integer maxLength = validateData.maxLength();

        if (lengthValue > maxLength) {
            String nameInput = validateData.nameInput();
            InfoValidationInput infoValidationInput = new InfoValidationInput(
                    nameInput,
                    "O cmapo %s deve ter no máximo %d de caracteres.".formatted(nameInput, maxLength)
            );
            infoValidationInputs.add(infoValidationInput);
        }

        if (validateLengthInputNext != null) validateLengthInputNext.verify(validateData);
    }
}
