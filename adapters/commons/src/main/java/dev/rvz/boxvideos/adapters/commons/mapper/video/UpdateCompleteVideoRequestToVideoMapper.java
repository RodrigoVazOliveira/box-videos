package dev.rvz.boxvideos.adapters.commons.mapper.video;

import dev.rvz.boxvideos.adapters.commons.requests.videos.UpdateCompleteVideoRequest;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.MapperWithId;
import org.springframework.stereotype.Component;

@Component
public class UpdateCompleteVideoRequestToVideoMapper implements MapperWithId<UpdateCompleteVideoRequest, Video> {

    @Override
    public Video to(UpdateCompleteVideoRequest to, Long id) {
        Category category = new Category(to.categoryId(), null, null);
        return new Video(id, to.title(), to.description(), to.url(), category);
    }
}
