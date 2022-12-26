package dev.rvz.boxvideos.adapters.commons.mapper.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToCategoryMapper implements Mapper<CategoryEntity, Category> {
    @Override
    public Category to(CategoryEntity to) {
        return new Category(to.getId(), to.getTitle(), to.getColor());
    }
}
