package dev.rvz.boxvideos.adapters.inbound.api.category;

import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CreateCategoryRequestWithIdToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.requests.categories.CreateCategoryRequest;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.UpdatePartialCategoryPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class UpdatePartialCategoryRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(UpdateCompleteCategoryRestController.class);
    private final UpdatePartialCategoryPortIn updatePartialCategoryPortIn;

    private final CreateCategoryRequestWithIdToCategoryMapper createCategoryRequestWithIdToCategoryMapper;
    private final CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;


    public UpdatePartialCategoryRestController(UpdatePartialCategoryPortIn updatePartialCategoryPortIn, CreateCategoryRequestWithIdToCategoryMapper createCategoryRequestWithIdToCategoryMapper, CategoryToCategoryResponseMapper categoryToCategoryResponseMapper) {
        this.updatePartialCategoryPortIn = updatePartialCategoryPortIn;
        this.createCategoryRequestWithIdToCategoryMapper = createCategoryRequestWithIdToCategoryMapper;
        this.categoryToCategoryResponseMapper = categoryToCategoryResponseMapper;
    }

    @PatchMapping("/{id}/")
    @ResponseStatus(HttpStatus.OK)
    CategoryResponse updatePartial(
            @PathVariable Long id,
            @RequestBody CreateCategoryRequest createCategoryRequest
    ) {
        LOGGER.info("updatePartial - id {}, createCategoryRequest {}", id, createCategoryRequest);
        Category category = createCategoryRequestWithIdToCategoryMapper.to(createCategoryRequest, id);
        Category updateCategory = updatePartialCategoryPortIn.update(category);
        LOGGER.info("updatePartial - updateCategory {}", updateCategory);

        return categoryToCategoryResponseMapper.to(updateCategory);
    }
}
