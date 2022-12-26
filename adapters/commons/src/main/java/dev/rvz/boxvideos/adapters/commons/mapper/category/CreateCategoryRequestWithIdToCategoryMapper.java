package dev.rvz.boxvideos.adapters.commons.mapper.category;

import dev.rvz.boxvideos.adapters.commons.requests.categories.CreateCategoryRequest;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.mapper.MapperWithId;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryRequestWithIdToCategoryMapper implements MapperWithId<CreateCategoryRequest, Category> {
    @Override
    public Category to(CreateCategoryRequest to, Long id) {
        return new Category(id, to.title(), to.color());
    }
}
