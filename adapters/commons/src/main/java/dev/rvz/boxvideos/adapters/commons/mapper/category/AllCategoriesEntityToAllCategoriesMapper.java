package dev.rvz.boxvideos.adapters.commons.mapper.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AllCategoriesEntityToAllCategoriesMapper implements Mapper<Iterable<CategoryEntity>, Iterable<Category>> {
    @Override
    public Iterable<Category> to(Iterable<CategoryEntity> to) {
        List<Category> categories = new ArrayList<>();
        to.forEach(categoryEntity -> {
            Category category = new Category(categoryEntity.getId(), categoryEntity.getTitle(), categoryEntity.getColor());
            categories.add(category);
        });

        return categories;
    }
}
