package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import dev.rvz.boxvideos.port.out.category.GetCategoryByIdPortOut;

public class GetCategoryByIdService implements GetCategoryByIdPortIn {
    private final GetCategoryByIdPortOut getCategoryByIdPortOut;

    public GetCategoryByIdService(GetCategoryByIdPortOut getCategoryByIdPortOut) {
        this.getCategoryByIdPortOut = getCategoryByIdPortOut;
    }

    @Override
    public Category getCategoryById(Long id) {
        if (!getCategoryByIdPortOut.exitsCategoryById(id)) {
            throw new CategoryNotFoundException("categoria com id %d não existe.".formatted(id));
        }

        return getCategoryByIdPortOut.getCategoryById(id);
    }
}
