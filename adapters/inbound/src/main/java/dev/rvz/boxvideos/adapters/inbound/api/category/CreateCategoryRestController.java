package dev.rvz.boxvideos.adapters.inbound.api.category;

import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CreateCategoryRequestToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.requests.categories.CreateCategoryRequest;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.CreateCategoryPortIn;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
public class CreateCategoryRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(CreateCategoryRestController.class);
    private final CreateCategoryPortIn createCategoryPortIn;
    private final CreateCategoryRequestToCategoryMapper createCategoryRequestToCategoryMapper;
    private final CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;


    public CreateCategoryRestController(CreateCategoryPortIn createCategoryPortIn, CreateCategoryRequestToCategoryMapper createCategoryRequestToCategoryMapper, CategoryToCategoryResponseMapper categoryToCategoryResponseMapper) {
        this.createCategoryPortIn = createCategoryPortIn;
        this.createCategoryRequestToCategoryMapper = createCategoryRequestToCategoryMapper;
        this.categoryToCategoryResponseMapper = categoryToCategoryResponseMapper;
    }

    @PostMapping("/")
    ResponseEntity<CategoryResponse> createNewCategory(
            @RequestBody CreateCategoryRequest createCategoryRequest,
            HttpServletRequest httpServletRequest
    ) throws URISyntaxException {
        LOGGER.info("createNewCategory - createCategoryRequest {}", createCategoryRequest);
        Category category = createCategoryRequestToCategoryMapper.to(createCategoryRequest);
        Category newCategory = createCategoryPortIn.create(category);
        LOGGER.info("createNewCategory - newCategory {}", newCategory);
        Long id = newCategory.id();

        String url = httpServletRequest.getRequestURL().toString() + "%s/".formatted(id);
        URI uri = new URI(url);
        LOGGER.info("getUri - Location: {}", url);

        CategoryResponse categoryResponse = categoryToCategoryResponseMapper.to(newCategory);

        return ResponseEntity.created(uri).body(categoryResponse);
    }
}
