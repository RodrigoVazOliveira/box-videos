package dev.rvz.boxvideos.adapters.outbound.adapter.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.AllCategoriesEntityToAllCategoriesMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@PropertySource({
        "classpath:application.properties"
})
@ContextConfiguration(classes = {
        CategoryRepository.class, AllCategoriesEntityToAllCategoriesMapper.class,
        GetAllCategoriesAdapter.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class GetAllCategoriesAdapterTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AllCategoriesEntityToAllCategoriesMapper allCategoriesEntityToAllCategoriesMapper;

    @Autowired
    private GetAllCategoriesAdapter getAllCategoriesAdapter;

    @Test
    void gt_all_categories() {
        CategoryEntity categoryEntityOne = new CategoryEntity();
        categoryEntityOne.setTitle("LIVRE");
        categoryEntityOne.setColor("blue");

        CategoryEntity categoryEntityTwo = new CategoryEntity();
        categoryEntityTwo.setTitle("FILME");
        categoryEntityTwo.setColor("green");

        categoryRepository.save(categoryEntityOne);
        categoryRepository.save(categoryEntityTwo);


        Iterable<Category> result = getAllCategoriesAdapter.execute();
        List<Category> categories = new ArrayList<>();
        result.forEach(categories::add);

        Assertions.assertFalse(categories.isEmpty());
        Assertions.assertEquals(2, categories.size());

        categoryRepository.deleteAll();
    }
}