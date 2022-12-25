package dev.rvz.boxvideos.adapters.inbound.api.category;

import dev.rvz.boxvideos.adapters.commons.mapper.category.AllCategoriesToAllCategoriesResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.GetAllCategoriesPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class GetAllCategoriesRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(GetAllCategoriesRestController.class);
    private final GetAllCategoriesPortIn getAllCategoriesPortIn;
    private final AllCategoriesToAllCategoriesResponseMapper allCategoriesToAllCategoriesResponseMapper;

    public GetAllCategoriesRestController(GetAllCategoriesPortIn getAllCategoriesPortIn, AllCategoriesToAllCategoriesResponseMapper allCategoriesToAllCategoriesResponseMapper) {
        this.getAllCategoriesPortIn = getAllCategoriesPortIn;
        this.allCategoriesToAllCategoriesResponseMapper = allCategoriesToAllCategoriesResponseMapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    Iterable<CategoryResponse> getAllCategories() {
        LOGGER.info("getAllCategories - get all categories");
        Iterable<Category> categories = getAllCategoriesPortIn.execute();

        return allCategoriesToAllCategoriesResponseMapper.to(categories);
    }
}
