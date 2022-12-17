package dev.rvz.boxvideos.adapters.commons.mapper;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class VideoToVideoEntityMapper implements Mapper<Video, VideoEntity> {

    @Override
    public VideoEntity to(Video to) {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle(to.title());
        videoEntity.setDescription(to.description());
        videoEntity.setUrl(to.url());

        return videoEntity;
    }
}
