package dev.rvz.boxvideos.adapters.commons.mapper;

import dev.rvz.boxvideos.adapters.commons.requests.videos.UpdatePartialRequest;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.MapperWithId;
import org.springframework.stereotype.Component;

@Component
public class UpdatePartialRequestToVideoMapper implements MapperWithId<UpdatePartialRequest, Video> {
    @Override
    public Video to(UpdatePartialRequest to, Long id) {
        return new Video(id, to.title(), to.description(), to.url());
    }
}
