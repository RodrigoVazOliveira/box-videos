package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.out.category.GetCategoryByIdPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GetCategoryByIdServiceTest {


    @Test
    void get_category_by_id_not_found() {
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
        GetCategoryByIdService getCategoryByIdService = new GetCategoryByIdService(getCategoryByIdPortOut);

        CategoryNotFoundException resultException = Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            getCategoryByIdService.getCategoryById(1L);
        });

        Assertions.assertEquals("categoria com id 1 não existe.", resultException.getMessage());
    }

    @Test
    void get_category_by_id_with_sucess() {
        GetCategoryByIdPortOut getCategoryByIdPortOut = new GetCategoryByIdPortOut() {
            @Override
            public Category getCategoryById(Long id) {
                return new Category(1L, "LIVRE", "BLUE");
            }

            @Override
            public Boolean exitsCategoryById(Long id) {
                return true;
            }
        };
        GetCategoryByIdService getCategoryByIdService = new GetCategoryByIdService(getCategoryByIdPortOut);
        Category result = getCategoryByIdService.getCategoryById(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.id());
    }
}