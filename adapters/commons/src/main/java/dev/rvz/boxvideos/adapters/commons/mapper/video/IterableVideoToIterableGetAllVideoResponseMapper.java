package dev.rvz.boxvideos.adapters.commons.mapper.video;

import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IterableVideoToIterableGetAllVideoResponseMapper implements Mapper<Iterable<Video>, Iterable<GetAllVideoResponse>> {

    private final CategoryToCategoryResponseMapper categoryToCategoryResponseMapper;

    public IterableVideoToIterableGetAllVideoResponseMapper(CategoryToCategoryResponseMapper categoryToCategoryResponseMapper) {
        this.categoryToCategoryResponseMapper = categoryToCategoryResponseMapper;
    }

    @Override
    public Iterable<GetAllVideoResponse> to(Iterable<Video> to) {
        List<GetAllVideoResponse> getAllVideoResponses = new ArrayList<>();
        for (Video video : to) {
            getAllVideoResponses.add(new GetAllVideoResponse(video.id(), video.title(), video.description(), video.url(),
                    categoryToCategoryResponseMapper.to(video.category())));
        }

        return getAllVideoResponses;
    }
}
