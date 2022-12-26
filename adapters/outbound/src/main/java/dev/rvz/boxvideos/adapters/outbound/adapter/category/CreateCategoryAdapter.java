package dev.rvz.boxvideos.adapters.outbound.adapter.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryEntityMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.out.category.CreateCategoryPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoryAdapter implements CreateCategoryPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(CreateCategoryAdapter.class);
    private final CategoryRepository categoryRepository;
    private final CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;
    private final CategoryToCategoryEntityMapper categoryToCategoryEntityMapper;

    public CreateCategoryAdapter(CategoryRepository categoryRepository, CategoryEntityToCategoryMapper categoryEntityToCategoryMapper, CategoryToCategoryEntityMapper categoryToCategoryEntityMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityToCategoryMapper = categoryEntityToCategoryMapper;
        this.categoryToCategoryEntityMapper = categoryToCategoryEntityMapper;
    }

    @Override
    public Category create(Category category) {
        LOGGER.info("create - category {}", category);
        CategoryEntity categoryEntity = categoryToCategoryEntityMapper.to(category);
        categoryRepository.save(categoryEntity);
        LOGGER.info("create - categoryEntity {}", categoryEntity);

        return categoryEntityToCategoryMapper.to(categoryEntity);
    }
}
