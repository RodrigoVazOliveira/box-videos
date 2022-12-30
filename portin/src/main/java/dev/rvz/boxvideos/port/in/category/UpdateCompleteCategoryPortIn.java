package dev.rvz.boxvideos.port.in.category;

import dev.rvz.boxvideos.core.domain.category.model.Category;

public interface UpdateCompleteCategoryPortIn {
    Category update(Category category, Boolean categoryExists);

    Boolean existsCategoryById(Long id);
}
