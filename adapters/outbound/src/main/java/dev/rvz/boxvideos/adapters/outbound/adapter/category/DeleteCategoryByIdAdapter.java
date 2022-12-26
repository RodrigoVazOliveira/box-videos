package dev.rvz.boxvideos.adapters.outbound.adapter.category;

import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
import dev.rvz.boxvideos.port.out.category.DeleteCategoryByIdPortOut;
import dev.rvz.boxvideos.port.out.category.GetCategoryByIdPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteCategoryByIdAdapter implements DeleteCategoryByIdPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(DeleteCategoryByIdAdapter.class);
    private final CategoryRepository categoryRepository;
    private final GetCategoryByIdPortOut getCategoryByIdPortOut;

    public DeleteCategoryByIdAdapter(CategoryRepository categoryRepository, GetCategoryByIdPortOut getCategoryByIdPortOut) {
        this.categoryRepository = categoryRepository;
        this.getCategoryByIdPortOut = getCategoryByIdPortOut;
    }

    @Override
    public void deleteById(Long id) {
        LOGGER.info("deleteById - id {}", id);
        categoryRepository.deleteById(id);
        LOGGER.info("delete with success!");
    }

    @Override
    public Boolean notExistsCategoryById(Long id) {
        LOGGER.info("notExistsCategoryById - id {}", id);
        Boolean exitsCategoryById = getCategoryByIdPortOut.exitsCategoryById(id);
        LOGGER.info("notExistsCategoryById - exitsCategoryById {}", !exitsCategoryById);

        return !exitsCategoryById;
    }
}
