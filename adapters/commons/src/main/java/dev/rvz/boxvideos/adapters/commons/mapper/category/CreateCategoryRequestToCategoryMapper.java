package dev.rvz.boxvideos.adapters.commons.mapper.category;

import dev.rvz.boxvideos.adapters.commons.requests.categories.CreateCategoryRequest;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryRequestToCategoryMapper implements Mapper<CreateCategoryRequest, Category> {
    @Override
    public Category to(CreateCategoryRequest to) {
        return new Category(null, to.title(), to.color());
    }
}
