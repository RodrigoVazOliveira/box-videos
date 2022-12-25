package dev.rvz.boxvideos.port.out.category;

import dev.rvz.boxvideos.core.domain.category.model.Category;

public interface GetCategoryByIdPortOut {
    Category getCategoryById(Long id);

    Boolean exitsCategoryById(Long id);
}
