package dev.rvz.boxvideos.application.category.service;

import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.out.category.GetAllCategoriesPortOut;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class GetAllCategoriesServiceTest {

    @Test
    void get_all_categories() {

        GetAllCategoriesPortOut getAllCategoriesPortOut = new GetAllCategoriesPortOut() {
            @Override
            public Iterable<Category> execute() {
                List<Category> categories = new ArrayList<>();
                categories.add(new Category(1L, "LIVRE", "blue"));
                categories.add(new Category(2L, "FILME", "green"));

                return categories;
            }
        };

        GetAllCategoriesService getAllCategoriesService = new GetAllCategoriesService(getAllCategoriesPortOut);
        Iterable<Category> resultCategories = getAllCategoriesService.execute();
        List<Category> categories = new ArrayList<>();
        resultCategories.forEach(categories::add);

        Assertions.assertFalse(categories.isEmpty());
        Assertions.assertEquals(2, categories.size());
    }
}