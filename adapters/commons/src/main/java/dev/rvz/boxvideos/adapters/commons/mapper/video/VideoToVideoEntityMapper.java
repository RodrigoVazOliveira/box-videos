package dev.rvz.boxvideos.adapters.commons.mapper.video;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryEntityMapper;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class VideoToVideoEntityMapper implements Mapper<Video, VideoEntity> {
    private final CategoryToCategoryEntityMapper categoryToCategoryEntityMapper;

    public VideoToVideoEntityMapper(CategoryToCategoryEntityMapper categoryToCategoryEntityMapper) {
        this.categoryToCategoryEntityMapper = categoryToCategoryEntityMapper;
    }

    @Override
    public VideoEntity to(Video to) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setId(to.id());
        videoEntity.setTitle(to.title());
        videoEntity.setDescription(to.description());
        videoEntity.setUrl(to.url());
        videoEntity.setCategoryEntity(categoryToCategoryEntityMapper.to(to.category()));

        return videoEntity;
    }
}
