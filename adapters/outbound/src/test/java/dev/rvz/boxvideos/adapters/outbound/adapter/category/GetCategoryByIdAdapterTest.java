package dev.rvz.boxvideos.adapters.outbound.adapter.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
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

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@PropertySource({
        "classpath:application.properties"
})
@ContextConfiguration(classes = {
        CategoryRepository.class, CategoryEntityToCategoryMapper.class,
        GetCategoryByIdAdapter.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class GetCategoryByIdAdapterTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    @Autowired
    private GetCategoryByIdAdapter getCategoryByIdAdapter;

    @Test
    void test_exits_category_by_id() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setColor("blue");
        categoryEntity.setTitle("LIVRE");
        categoryRepository.save(categoryEntity);

        Boolean result = getCategoryByIdAdapter.exitsCategoryById(categoryEntity.getId());

        Assertions.assertTrue(result);

        categoryRepository.deleteAll();
    }

    @Test
    void test_not_exits_category_by_id() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setColor("blue");
        categoryEntity.setTitle("LIVRE");
        categoryRepository.save(categoryEntity);

        Boolean result = getCategoryByIdAdapter.exitsCategoryById(42243L);

        Assertions.assertFalse(result);

        categoryRepository.deleteAll();
    }

    @Test
    void get_category_by_id() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setColor("blue");
        categoryEntity.setTitle("LIVRE");
        categoryRepository.save(categoryEntity);

        Category resultCategory = getCategoryByIdAdapter.getCategoryById(categoryEntity.getId());

        Assertions.assertNotNull(resultCategory);
        Assertions.assertEquals(categoryEntity.getId(), resultCategory.id());
        Assertions.assertEquals(categoryEntity.getTitle(), resultCategory.title());
        Assertions.assertEquals(categoryEntity.getColor(), resultCategory.color());

        categoryRepository.deleteAll();
    }
}