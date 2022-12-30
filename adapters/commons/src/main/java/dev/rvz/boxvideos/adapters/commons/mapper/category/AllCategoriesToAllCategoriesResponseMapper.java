package dev.rvz.boxvideos.adapters.commons.mapper.category;

import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AllCategoriesToAllCategoriesResponseMapper implements Mapper<Iterable<Category>, Iterable<CategoryResponse>> {
    @Override
    public Iterable<CategoryResponse> to(Iterable<Category> to) {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        to.forEach(category -> {
            categoryResponses.add(new CategoryResponse(category.id(), category.title(), category.color()));
        });

        return categoryResponses;
    }
}
