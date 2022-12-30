package dev.rvz.boxvideos.adapters.outbound.repository;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<VideoEntity, Long> {
    Iterable<Video> findByTitleContains(String title);
}
