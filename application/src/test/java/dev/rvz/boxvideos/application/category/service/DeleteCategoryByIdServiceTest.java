package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.commons.enumerations.MessageDeleteEnum;
import dev.rvz.boxvideos.core.domain.commons.responses.Message;
import dev.rvz.boxvideos.port.out.category.DeleteCategoryByIdPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DeleteCategoryByIdServiceTest {

    @Test
    void category_delete_by_id() {
        DeleteCategoryByIdPortOut deleteCategoryByIdPortOut = new DeleteCategoryByIdPortOut() {
            @Override
            public void deleteById(Long id) {

            }

            @Override
            public Boolean notExistsCategoryById(Long id) {
                return false;
            }
        };
        DeleteCategoryByIdService deleteCategoryByIdService = new DeleteCategoryByIdService(deleteCategoryByIdPortOut);

        Message result = deleteCategoryByIdService.deleteById(1L);

        Assertions.assertEquals(MessageDeleteEnum.SUCCESS.getMessage(), result.message());
    }

    @Test
    void category_not_found_to_delete_by_id() {
        DeleteCategoryByIdPortOut deleteCategoryByIdPortOut = new DeleteCategoryByIdPortOut() {
            @Override
            public void deleteById(Long id) {

            }

            @Override
            public Boolean notExistsCategoryById(Long id) {
                return true;
            }
        };
        DeleteCategoryByIdService deleteCategoryByIdService = new DeleteCategoryByIdService(deleteCategoryByIdPortOut);

        CategoryNotFoundException resultException = Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            deleteCategoryByIdService.deleteById(1L);
        });

        Assertions.assertEquals("categoria com id 1 não existe.", resultException.getMessage());
    }
}