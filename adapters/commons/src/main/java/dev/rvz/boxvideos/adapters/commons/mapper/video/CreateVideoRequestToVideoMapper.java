package dev.rvz.boxvideos.adapters.commons.mapper.video;

import dev.rvz.boxvideos.adapters.commons.requests.videos.CreateVideoRequest;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class CreateVideoRequestToVideoMapper implements Mapper<CreateVideoRequest, Video> {
    @Override
    public Video to(CreateVideoRequest to) {
        return new Video(null, to.title(), to.description(), to.url());
    }
}
