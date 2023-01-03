package dev.rvz.boxvideos.adapters.outbound.repository;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<VideoEntity, Long> {
    Iterable<VideoEntity> findByTitleContains(String title);
}
