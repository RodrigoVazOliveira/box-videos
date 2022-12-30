package dev.rvz.boxvideos.adapters.commons.mapper.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryEntityMapper implements Mapper<Category, CategoryEntity> {
    @Override
    public CategoryEntity to(Category to) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(to.id());
        categoryEntity.setTitle(to.title());
        categoryEntity.setColor(to.color());

        return categoryEntity;
    }
}
