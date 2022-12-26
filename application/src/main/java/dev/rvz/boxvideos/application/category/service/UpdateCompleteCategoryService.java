package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.CreateCategoryPortIn;
import dev.rvz.boxvideos.port.in.category.UpdateCompleteCategoryPortIn;
import dev.rvz.boxvideos.port.out.category.GetCategoryByIdPortOut;

import java.util.logging.Level;
import java.util.logging.Logger;

public class UpdateCompleteCategoryService implements UpdateCompleteCategoryPortIn {
    private final Logger LOGGER = Logger.getLogger(UpdateCompleteCategoryService.class.getName());

    private final GetCategoryByIdPortOut getCategoryByIdPortOut;

    private final CreateCategoryPortIn createCategoryPortIn;

    public UpdateCompleteCategoryService(GetCategoryByIdPortOut getCategoryByIdPortOut, CreateCategoryPortIn createCategoryPortIn) {
        this.getCategoryByIdPortOut = getCategoryByIdPortOut;
        this.createCategoryPortIn = createCategoryPortIn;
    }

    @Override
    public Category update(Category category, Boolean categoryExists) {
        if (!categoryExists) {
            LOGGER.log(Level.INFO, "update - category not exists");
            Category createCategory = new Category(null, category.title(), category.color());
            return createCategoryPortIn.create(createCategory);
        }


        LOGGER.log(Level.INFO, "update exists, updating");
        return createCategoryPortIn.create(category);
    }

    @Override
    public Boolean existsCategoryById(Long id) {
        LOGGER.log(Level.INFO, "existsCategoryById - id %d".formatted(id));
        return getCategoryByIdPortOut.exitsCategoryById(id);
    }
}
