package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.commons.exception.InfoValidationInput;
import dev.rvz.boxvideos.core.domain.commons.exception.ValidateInputException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.validation.inputs.ValidateData;
import dev.rvz.boxvideos.core.validation.inputs.ValidateInputChain;

import java.util.ArrayList;
import java.util.List;

public class ValidateInputService {
    private final Video video;

    public ValidateInputService(Video video) {
        this.video = video;
    }

    public void validateInputs() {
        List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        ValidateInputChain validateInputs = new ValidateInputChain(infoValidationInputs);
        validateInputs
                .validate("title", video.title())
                .validate("description", video.description())
                .validate("url", video.url());

        validateInputs.validateLength(new ValidateData("title", video.title(), 3, 150))
                .validateLength(new ValidateData("description", video.description(), 3, 180))
                .validateLength(new ValidateData("url", video.url(), 3, 500));

        if (!infoValidationInputs.isEmpty()) {
            throw new ValidateInputException(infoValidationInputs);
        }
    }
}
