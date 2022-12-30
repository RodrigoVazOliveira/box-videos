package dev.rvz.boxvideos.adapters.commons.mapper.video;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IterableVideoEntityToIterableVideoMapper implements Mapper<List<VideoEntity>, List<Video>> {

    private final CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    public IterableVideoEntityToIterableVideoMapper(CategoryEntityToCategoryMapper categoryEntityToCategoryMapper) {
        this.categoryEntityToCategoryMapper = categoryEntityToCategoryMapper;
    }

    @Override
    public List<Video> to(List<VideoEntity> to) {
        return to.stream().map(
                videoEntity -> new Video(
                        videoEntity.getId(),
                        videoEntity.getTitle(),
                        videoEntity.getDescription(),
                        videoEntity.getUrl(),
                        categoryEntityToCategoryMapper.to(videoEntity.getCategoryEntity())
                )).toList();
    }
}
