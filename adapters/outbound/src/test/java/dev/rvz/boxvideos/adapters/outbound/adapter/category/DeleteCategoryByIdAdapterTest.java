package dev.rvz.boxvideos.adapters.outbound.adapter.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
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
        CategoryRepository.class,
        GetCategoryByIdAdapter.class,
        CategoryEntityToCategoryMapper.class,
        DeleteCategoryByIdAdapter.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class DeleteCategoryByIdAdapterTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GetCategoryByIdAdapter GetCategoryByIdAdapter;

    @Autowired
    private CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    @Autowired
    private DeleteCategoryByIdAdapter deleteCategoryByIdAdapter;


    @Test
    void not_exists_category_by_id() {
        Boolean notExistsCategoryById = deleteCategoryByIdAdapter.notExistsCategoryById(1L);
        Assertions.assertTrue(notExistsCategoryById);
    }

    @Test
    void exists_category_by_id() {
        CategoryEntity categoryEntity = createCategory();

        Boolean notExistsCategoryById = deleteCategoryByIdAdapter.notExistsCategoryById(categoryEntity.getId());
        Assertions.assertFalse(notExistsCategoryById);

        categoryRepository.deleteAll();
        ;
    }

    @Test
    void delete_category_by_id() {
        CategoryEntity categoryEntity = createCategory();
        deleteCategoryByIdAdapter.deleteById(categoryEntity.getId());

        Boolean notExistsCategoryById = deleteCategoryByIdAdapter.notExistsCategoryById(categoryEntity.getId());
        Assertions.assertTrue(notExistsCategoryById);
    }

    private CategoryEntity createCategory() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("FILME");
        categoryEntity.setColor("RED");
        categoryRepository.save(categoryEntity);
        return categoryEntity;
    }
}