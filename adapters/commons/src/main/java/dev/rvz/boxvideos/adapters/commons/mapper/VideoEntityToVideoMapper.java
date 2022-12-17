package dev.rvz.boxvideos.adapters.commons.mapper;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;


@Component
public class VideoEntityToVideoMapper implements Mapper<VideoEntity, Video> {
    @Override
    public Video to(VideoEntity to) {
        return new Video(
                to.getId(),
                to.getTitle(),
                to.getDescription(),
                to.getUrl()
        );
    }
}
