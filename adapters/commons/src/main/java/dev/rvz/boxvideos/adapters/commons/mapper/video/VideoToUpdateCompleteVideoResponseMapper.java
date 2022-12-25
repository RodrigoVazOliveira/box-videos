package dev.rvz.boxvideos.adapters.commons.mapper.video;

import dev.rvz.boxvideos.adapters.commons.responses.videos.UpdateCompleteVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class VideoToUpdateCompleteVideoResponseMapper implements Mapper<Video, UpdateCompleteVideoResponse> {
    @Override
    public UpdateCompleteVideoResponse to(Video to) {
        return new UpdateCompleteVideoResponse(to.id(), to.title(), to.description(), to.url());
    }
}
