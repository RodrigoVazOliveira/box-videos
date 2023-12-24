package dev.rvz.boxvideos.adapters.inbound.api.category;

import dev.rvz.boxvideos.adapters.commons.mapper.video.IterableVideoToIterableGetAllVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.GetVideoByCategoryIdPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class GetVideoByCategoryIdRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(GetVideoByCategoryIdRestController.class);
    private final GetVideoByCategoryIdPortIn getVideoByCategoryIdPortIn;
    private final IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper;

    public GetVideoByCategoryIdRestController(GetVideoByCategoryIdPortIn getVideoByCategoryIdPortIn, IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper) {
        this.getVideoByCategoryIdPortIn = getVideoByCategoryIdPortIn;
        this.iterableVideoToIterableGetAllVideoResponseMapper = iterableVideoToIterableGetAllVideoResponseMapper;
    }

    @GetMapping("/{id}/videos")
    @ResponseStatus(HttpStatus.OK)
    Iterable<GetAllVideoResponse> getVideoByCategoryId(@PathVariable("id") Long id) {
        LOGGER.info("getVideoByCategoryId - id {}", id);
        Iterable<Video> videos = getVideoByCategoryIdPortIn.run(id);

        return iterableVideoToIterableGetAllVideoResponseMapper.to(videos);
    }
}
