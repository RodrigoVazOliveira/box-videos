package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.in.category.CreateCategoryPortIn;
import dev.rvz.boxvideos.port.out.category.GetCategoryByIdPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UpdateCompleteCategoryServiceTest {

    @Test
    void category_exists() {
        CreateCategoryPortIn createCategoryPortIn = new CreateCategoryPortIn() {
            @Override
            public Category create(Category category) {
                return new Category(category.id(), category.title(), category.color());
            }

            @Override
            public void validateCategory(Category category) {

            }
        };
        GetCategoryByIdPortOut getCategoryByIdPortOut = new GetCategoryByIdPortOut() {
            @Override
            public Category getCategoryById(Long id) {
                return null;
            }

            @Override
            public Boolean exitsCategoryById(Long id) {
                return true;
            }
        };

        UpdateCompleteCategoryService updateCompleteCategoryService = new UpdateCompleteCategoryService(
                getCategoryByIdPortOut, createCategoryPortIn);
        Boolean existsCategoryById = updateCompleteCategoryService.existsCategoryById(1L);

        Assertions.assertTrue(existsCategoryById);
    }

    @Test
    void category_not_exists() {
        CreateCategoryPortIn createCategoryPortIn = new CreateCategoryPortIn() {
            @Override
            public Category create(Category category) {
                return new Category(category.id(), category.title(), category.color());
            }

            @Override
            public void validateCategory(Category category) {

            }
        };
        GetCategoryByIdPortOut getCategoryByIdPortOut = new GetCategoryByIdPortOut() {
            @Override
            public Category getCategoryById(Long id) {
                return null;
            }

            @Override
            public Boolean exitsCategoryById(Long id) {
                return false;
            }
        };

        UpdateCompleteCategoryService updateCompleteCategoryService = new UpdateCompleteCategoryService(
                getCategoryByIdPortOut, createCategoryPortIn);
        Boolean existsCategoryById = updateCompleteCategoryService.existsCategoryById(1L);

        Assertions.assertFalse(existsCategoryById);
    }

    @Test
    void update_category_not_exists() {
        Category category = new Category(1L, "FILME", "RED");
        CreateCategoryPortIn createCategoryPortIn = new CreateCategoryPortIn() {
            @Override
            public Category create(Category category) {
                return new Category(category.id(), category.title(), category.color());
            }

            @Override
            public void validateCategory(Category category) {

            }
        };
        GetCategoryByIdPortOut getCategoryByIdPortOut = new GetCategoryByIdPortOut() {
            @Override
            public Category getCategoryById(Long id) {
                return null;
            }

            @Override
            public Boolean exitsCategoryById(Long id) {
                return true;
            }
        };

        UpdateCompleteCategoryService updateCompleteCategoryService = new UpdateCompleteCategoryService(
                getCategoryByIdPortOut, createCategoryPortIn);
        Category categoryResult = updateCompleteCategoryService.update(category, true);

        Assertions.assertEquals(category, categoryResult);
    }

    @Test
    void update_category_exists() {
        Category category = new Category(1L, "FILME", "RED");
        CreateCategoryPortIn createCategoryPortIn = new CreateCategoryPortIn() {
            @Override
            public Category create(Category category) {
                return new Category(2L, category.title(), category.color());
            }

            @Override
            public void validateCategory(Category category) {

            }
        };
        GetCategoryByIdPortOut getCategoryByIdPortOut = new GetCategoryByIdPortOut() {
            @Override
            public Category getCategoryById(Long id) {
                return null;
            }

            @Override
            public Boolean exitsCategoryById(Long id) {
                return true;
            }
        };

        UpdateCompleteCategoryService updateCompleteCategoryService = new UpdateCompleteCategoryService(
                getCategoryByIdPortOut, createCategoryPortIn);
        Category categoryResult = updateCompleteCategoryService.update(category, true);

        Assertions.assertEquals(2L, categoryResult.id());
    }
}