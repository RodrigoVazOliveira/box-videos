package dev.rvz.boxvideos.adapters.inbound.api.category;

import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CreateCategoryRequestWithIdToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.requests.categories.CreateCategoryRequest;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.UpdateCompleteCategoryPortIn;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/categories")
public class UpdateCompleteCategoryRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(UpdateCompleteCategoryRestController.class);
    private final UpdateCompleteCategoryPortIn updateCompleteCategoryPortIn;
    private final CreateCategoryRequestWithIdToCategoryMapper createCategoryRequestWithIdToCategoryMapper;
    private final CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;

    public UpdateCompleteCategoryRestController(UpdateCompleteCategoryPortIn updateCompleteCategoryPortIn, CreateCategoryRequestWithIdToCategoryMapper createCategoryRequestWithIdToCategoryMapper, CategoryToCategoryResponseMapper categoryToCategoryResponseMapper) {
        this.updateCompleteCategoryPortIn = updateCompleteCategoryPortIn;
        this.createCategoryRequestWithIdToCategoryMapper = createCategoryRequestWithIdToCategoryMapper;
        this.categoryToCategoryResponseMapper = categoryToCategoryResponseMapper;
    }

    @PutMapping("/{id}/")
    ResponseEntity<CategoryResponse> update(
            @RequestBody CreateCategoryRequest createCategoryRequest,
            @PathVariable Long id,
            HttpServletRequest httpServletRequest
    ) throws URISyntaxException {
        LOGGER.info("update - id {}, createCategoryRequest {}", id, createCategoryRequest);
        Category category = createCategoryRequestWithIdToCategoryMapper.to(createCategoryRequest, id);
        Boolean existsCategoryById = updateCompleteCategoryPortIn.existsCategoryById(id);

        String url = httpServletRequest.getRequestURL().toString();
        URI uri = new URI(url);
        LOGGER.info("update - Location: {}", url);

        if (existsCategoryById) {
            LOGGER.info("update - doing updating");
            Category updateCategory = updateCompleteCategoryPortIn.update(category, true);
            CategoryResponse categoryResponse = categoryToCategoryResponseMapper.to(updateCategory);

            return ResponseEntity.ok(categoryResponse);
        }

        Category newCategory = updateCompleteCategoryPortIn.update(category, false);
        LOGGER.info("update - create new resource - newCategory {}", newCategory);

        CategoryResponse categoryResponse = categoryToCategoryResponseMapper.to(newCategory);

        return ResponseEntity.created(uri).body(categoryResponse);
    }
}
