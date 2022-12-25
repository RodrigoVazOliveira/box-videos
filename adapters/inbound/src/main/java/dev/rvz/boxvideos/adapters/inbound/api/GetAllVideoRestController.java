package dev.rvz.boxvideos.adapters.inbound.api;

import dev.rvz.boxvideos.adapters.commons.mapper.IterableVideoToIterableGetAllVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.GetAllVideosPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
public class GetAllVideoRestController extends VideoRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(GetAllVideoRestController.class);
    private final GetAllVideosPortIn getAllVideosPortIn;
    private final IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper;

    public GetAllVideoRestController(GetAllVideosPortIn getAllVideosPortIn, IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper) {
        this.getAllVideosPortIn = getAllVideosPortIn;
        this.iterableVideoToIterableGetAllVideoResponseMapper = iterableVideoToIterableGetAllVideoResponseMapper;
    }

    @GetMapping
    ResponseEntity<Iterable<GetAllVideoResponse>> getAllVideos() {
        LOGGER.info("getAllVideos");
        Iterable<Video> allVideos = getAllVideosPortIn.execute();
        LOGGER.info("getAllVideos - allVideos {}", allVideos);

        Iterable<GetAllVideoResponse> getAllVideoResponses = iterableVideoToIterableGetAllVideoResponseMapper.to(allVideos);

        return ResponseEntity.ok(getAllVideoResponses);
    }
}
