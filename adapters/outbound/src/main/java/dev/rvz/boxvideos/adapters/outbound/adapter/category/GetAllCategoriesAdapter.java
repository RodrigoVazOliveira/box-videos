package dev.rvz.boxvideos.adapters.outbound.adapter.category;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.AllCategoriesEntityToAllCategoriesMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.port.out.category.GetAllCategoriesPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GetAllCategoriesAdapter implements GetAllCategoriesPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(GetAllCategoriesAdapter.class);
    private final CategoryRepository categoryRepository;
    private final AllCategoriesEntityToAllCategoriesMapper allCategoriesEntityToAllCategoriesMapper;

    public GetAllCategoriesAdapter(CategoryRepository categoryRepository, AllCategoriesEntityToAllCategoriesMapper allCategoriesEntityToAllCategoriesMapper) {
        this.categoryRepository = categoryRepository;
        this.allCategoriesEntityToAllCategoriesMapper = allCategoriesEntityToAllCategoriesMapper;
    }

    @Override
    public Iterable<Category> execute() {
        LOGGER.info("execute - get all categories");
        Iterable<CategoryEntity> allCategories = categoryRepository.findAll();
        Iterable<Category> categories = allCategoriesEntityToAllCategoriesMapper.to(allCategories);

        return categories;
    }
}
