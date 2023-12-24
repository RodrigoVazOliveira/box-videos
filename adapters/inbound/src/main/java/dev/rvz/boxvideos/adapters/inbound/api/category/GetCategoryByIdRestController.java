package dev.rvz.boxvideos.adapters.inbound.api.category;

import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class GetCategoryByIdRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(GetCategoryByIdRestController.class);
    private final GetCategoryByIdPortIn getCategoryByIdPortIn;
    private final CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;

    public GetCategoryByIdRestController(GetCategoryByIdPortIn getCategoryByIdPortIn, CategoryToCategoryResponseMapper categoryToCategoryResponseMapper) {
        this.getCategoryByIdPortIn = getCategoryByIdPortIn;
        this.categoryToCategoryResponseMapper = categoryToCategoryResponseMapper;
    }

    @GetMapping("/{id}/")
    @ResponseStatus(HttpStatus.OK)
    CategoryResponse getCategoryById(@PathVariable("id") Long id) {
        LOGGER.info("getCategoryById - id {}", id);
        Category category = getCategoryByIdPortIn.getCategoryById(id);
        LOGGER.info("getCategoryById - category {}", category);

        return categoryToCategoryResponseMapper.to(category);
    }
}
