package dev.rvz.boxvideos.port.in.category;

import dev.rvz.boxvideos.core.domain.category.model.Category;

public interface UpdatePartialCategoryPortIn {
    Category update(Category category);
}
