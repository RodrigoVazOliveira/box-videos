package dev.rvz.boxvideos.adapters.commons.mapper.video;


import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.videos.CreateVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

@Component
public class VideoToCreateVideoResponseMapper implements Mapper<Video, CreateVideoResponse> {
    private final CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;

    public VideoToCreateVideoResponseMapper(CategoryToCategoryResponseMapper categoryToCategoryResponseMapper) {
        this.categoryToCategoryResponseMapper = categoryToCategoryResponseMapper;
    }

    @Override
    public CreateVideoResponse to(Video to) {
        return new CreateVideoResponse(to.id(), to.title(), to.description(), to.url(),
                categoryToCategoryResponseMapper.to(to.category()));
    }
}
