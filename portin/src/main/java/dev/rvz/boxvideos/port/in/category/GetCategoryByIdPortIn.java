package dev.rvz.boxvideos.port.in.category;

import dev.rvz.boxvideos.core.domain.category.model.Category;

public interface GetCategoryByIdPortIn {
    Boolean existsCategoryById(Long id);

    Category getCategoryById(Long id);
}
