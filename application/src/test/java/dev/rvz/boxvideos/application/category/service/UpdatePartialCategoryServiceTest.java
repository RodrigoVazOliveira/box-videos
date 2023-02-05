package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.CreateCategoryPortIn;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UpdatePartialCategoryServiceTest {


    @Test
    void update_category_exists() {
        final GetCategoryByIdPortIn getCategoryByIdPortIn = id -> new Category(id, "FILME", "BLUE");
        final CreateCategoryPortIn createCategoryPortIn = new CreateCategoryPortIn() {
            @Override
            public Category create(Category category) {
                return new Category(category.id(), "FILME", "BLUE");
            }

            @Override
            public void validateCategory(Category category) {
            }
        };

        final UpdatePartialCategoryService updatePartialCategoryService = new UpdatePartialCategoryService(createCategoryPortIn, getCategoryByIdPortIn);
        Category update = updatePartialCategoryService.update(new Category(1L, "FILME", "BLUE"));

        Assertions.assertNotNull(update);
        Assertions.assertEquals(1L, update.id());
    }

    @Test
    void update_category_not_exists() {
        final GetCategoryByIdPortIn getCategoryByIdPortIn = new GetCategoryByIdPortIn() {
            @Override
            public Category getCategoryById(Long id) {
                throw new CategoryNotFoundException("categoria com id %d não existe.".formatted(id));
            }
        };
        final CreateCategoryPortIn createCategoryPortIn = new CreateCategoryPortIn() {
            @Override
            public Category create(Category category) {
                return new Category(category.id(), "FILME", "BLUE");
            }

            @Override
            public void validateCategory(Category category) {
            }
        };

        final UpdatePartialCategoryService updatePartialCategoryService = new UpdatePartialCategoryService(createCategoryPortIn, getCategoryByIdPortIn);
        CategoryNotFoundException resultException = Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            Category update = updatePartialCategoryService.update(new Category(1L, "FILME", "BLUE"));
        });
        Assertions.assertEquals("categoria com id 1 não existe.", resultException.getMessage());
    }

}