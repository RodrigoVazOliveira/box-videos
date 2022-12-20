package dev.rvz.boxvideos.adapters.commons.mapper;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IterableVideoEntityToIterableVideoMapper implements Mapper<List<VideoEntity>, List<Video>> {
    @Override
    public List<Video> to(List<VideoEntity> to) {
        return to.stream().map(videoEntity -> new Video(videoEntity.getId(), videoEntity.getTitle(), videoEntity.getDescription(), videoEntity.getUrl())).toList();
    }
}
