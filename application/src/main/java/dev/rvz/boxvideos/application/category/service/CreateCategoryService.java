package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.InfoValidationInput;
import dev.rvz.boxvideos.core.domain.commons.exception.ValidateInputException;
import dev.rvz.boxvideos.core.validation.inputs.ValidateData;
import dev.rvz.boxvideos.core.validation.inputs.ValidateInputChain;
import dev.rvz.boxvideos.port.in.category.CreateCategoryPortIn;
import dev.rvz.boxvideos.port.out.category.CreateCategoryPortOut;

import java.util.ArrayList;
import java.util.List;

public class CreateCategoryService implements CreateCategoryPortIn {
    private final CreateCategoryPortOut createCategoryPortOut;

    public CreateCategoryService(CreateCategoryPortOut createCategoryPortOut) {
        this.createCategoryPortOut = createCategoryPortOut;
    }

    @Override
    public Category create(Category category) {
        validateCategory(category);
        return createCategoryPortOut.create(category);
    }

    @Override
    public void validateCategory(Category category) {
        List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        ValidateInputChain validateInputChain = new ValidateInputChain(infoValidationInputs);

        ValidateData validateDataTitle = new ValidateData("title", category.title(), 3, 150);
        ValidateData validateDataColor = new ValidateData("color", category.color(), 3, 150);

        validateInputChain
                .validate("title", category.title())
                .validate("color", category.color())
                .validateLength(validateDataTitle)
                .validateLength(validateDataColor);

        if (!infoValidationInputs.isEmpty()) {
            throw new ValidateInputException(infoValidationInputs);
        }
    }
}
