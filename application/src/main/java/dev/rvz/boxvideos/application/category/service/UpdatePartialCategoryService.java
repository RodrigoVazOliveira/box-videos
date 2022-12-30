package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.InfoValidationInput;
import dev.rvz.boxvideos.core.domain.commons.exception.ValidateInputException;
import dev.rvz.boxvideos.core.validation.inputs.ValidateData;
import dev.rvz.boxvideos.core.validation.inputs.ValidateInputChain;
import dev.rvz.boxvideos.core.validation.strings.ValidationStringChain;
import dev.rvz.boxvideos.port.in.category.CreateCategoryPortIn;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import dev.rvz.boxvideos.port.in.category.UpdatePartialCategoryPortIn;

import java.util.ArrayList;
import java.util.List;

public class UpdatePartialCategoryService implements UpdatePartialCategoryPortIn {
    private final CreateCategoryPortIn createCategoryPortIn;
    private final GetCategoryByIdPortIn getCategoryByIdPortIn;

    public UpdatePartialCategoryService(CreateCategoryPortIn createCategoryPortIn, GetCategoryByIdPortIn getCategoryByIdPortIn) {
        this.createCategoryPortIn = createCategoryPortIn;
        this.getCategoryByIdPortIn = getCategoryByIdPortIn;
    }

    @Override
    public Category update(Category category) {
        Long id = category.id();
        Category categoryOld = getCategoryByIdPortIn.getCategoryById(id);

        ValidationStringChain validationTitle = new ValidationStringChain(category.title());
        ValidationStringChain validationColor = new ValidationStringChain(category.color());

        List<InfoValidationInput> infoValidationInputs = new ArrayList<>();
        ValidateInputChain validateInputChain = new ValidateInputChain(infoValidationInputs);

        if (validationTitle.isValid() && validationColor.isValid()) {
            Category updateCategory = new Category(categoryOld.id(), category.title(), category.color());
            validateInputChain.validateLength(new ValidateData("title", category.title(), 3, 150));
            validateInputChain.validateLength(new ValidateData("color", category.color(), 3, 150));
            validateLengthInputs(infoValidationInputs);

            return createCategoryPortIn.create(updateCategory);
        } else if (validationTitle.isValid() && !validationColor.isValid()) {
            validateInputChain.validateLength(new ValidateData("title", category.title(), 3, 150));
            Category updateCategory = new Category(categoryOld.id(), category.title(), categoryOld.color());
            validateLengthInputs(infoValidationInputs);

            return createCategoryPortIn.create(updateCategory);
        } else if (!validationTitle.isValid() && validationColor.isValid()) {
            Category updateCategory = new Category(categoryOld.id(), categoryOld.title(), category.color());
            validateInputChain.validateLength(new ValidateData("color", category.color(), 3, 150));
            validateLengthInputs(infoValidationInputs);

            return createCategoryPortIn.create(updateCategory);
        }
        
        return categoryOld;
    }

    private static void validateLengthInputs(List<InfoValidationInput> infoValidationInputs) {
        if (!infoValidationInputs.isEmpty()) {
            throw new ValidateInputException(infoValidationInputs);
        }
    }
}
