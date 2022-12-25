package dev.rvz.boxvideos.adapters.outbound.adapter.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.out.category.GetCategoryByIdPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetCategoryByIdAdapter implements GetCategoryByIdPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(GetCategoryByIdAdapter.class);
    private final CategoryRepository categoryRepository;
    private final CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    public GetCategoryByIdAdapter(CategoryRepository categoryRepository, CategoryEntityToCategoryMapper categoryEntityToCategoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryEntityToCategoryMapper = categoryEntityToCategoryMapper;
    }

    @Override
    public Category getCategoryById(Long id) {
        LOGGER.info("getCategoryById - id {}", id);
        Optional<CategoryEntity> optionalCategory = categoryRepository.findById(id);
        CategoryEntity categoryEntity = optionalCategory.get();
        LOGGER.info("getCategoryById - categoryEntity {}", categoryEntity);

        return categoryEntityToCategoryMapper.to(categoryEntity);
    }

    @Override
    public Boolean exitsCategoryById(Long id) {
        LOGGER.info("exitsCategoryById - ID {}", id);
        boolean existsById = categoryRepository.existsById(id);
        LOGGER.info("exitsCategoryById - exits {}", existsById);

        return existsById;
    }
}
