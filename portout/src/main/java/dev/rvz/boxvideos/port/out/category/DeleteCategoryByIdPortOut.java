package dev.rvz.boxvideos.port.out.category;

public interface DeleteCategoryByIdPortOut {
    void deleteById(Long id);

    void notExistsCategoryById(Long id);
}
