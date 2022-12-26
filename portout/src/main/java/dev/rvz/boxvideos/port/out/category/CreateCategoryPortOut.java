package dev.rvz.boxvideos.port.out.category;

import dev.rvz.boxvideos.core.domain.category.model.Category;

public interface CreateCategoryPortOut {
    Category create(Category category);
}
