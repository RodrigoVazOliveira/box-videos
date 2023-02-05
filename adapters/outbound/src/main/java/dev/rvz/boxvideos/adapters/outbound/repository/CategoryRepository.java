package dev.rvz.boxvideos.adapters.outbound.repository;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
}
