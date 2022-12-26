package dev.rvz.boxvideos.port.out.category;

import dev.rvz.boxvideos.core.domain.category.model.Category;

public interface UpdateCompleteCategoryPortOut {
    Category update(Category category);

    Boolean existsCategoryById(Long id);
}
