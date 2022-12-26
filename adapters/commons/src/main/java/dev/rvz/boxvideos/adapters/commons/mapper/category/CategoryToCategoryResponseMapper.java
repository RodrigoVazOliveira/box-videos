package dev.rvz.boxvideos.adapters.commons.mapper.category;

import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CategoryToCategoryResponseMapper implements Mapper<Category, CategoryResponse> {
    @Override
    public CategoryResponse to(Category to) {
        return new CategoryResponse(to.id(), to.title(), to.color());
    }
}
