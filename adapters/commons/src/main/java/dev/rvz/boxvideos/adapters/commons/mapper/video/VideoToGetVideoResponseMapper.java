package dev.rvz.boxvideos.adapters.commons.mapper.video;

import dev.rvz.boxvideos.adapters.commons.responses.videos.GetVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class VideoToGetVideoResponseMapper implements Mapper<Video, GetVideoResponse> {
    @Override
    public GetVideoResponse to(Video to) {
        return new GetVideoResponse(to.id(), to.title(), to.description(), to.url());
    }
}
