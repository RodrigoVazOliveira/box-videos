package dev.rvz.boxvideos.adapters.outbound.adapter.category;

import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryEntityMapper;
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
        CategoryRepository.class,
        CategoryEntityToCategoryMapper.class,
        CategoryToCategoryEntityMapper.class,
        CreateCategoryAdapter.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class CreateCategoryAdapterTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;


    @Autowired
    private CategoryToCategoryEntityMapper categoryToCategoryEntityMapper;

    @Autowired
    private CreateCategoryAdapter createCategoryAdapter;

    @Test
    void create_category_with_success() {
        Category category = new Category(null, "SERIE", "RED");
        Category result = createCategoryAdapter.create(category);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1L, result.id());
        categoryRepository.deleteAll();
    }
}