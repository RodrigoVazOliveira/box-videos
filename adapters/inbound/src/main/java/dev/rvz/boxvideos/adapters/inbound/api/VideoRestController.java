package dev.rvz.boxvideos.adapters.inbound.api;

import dev.rvz.boxvideos.adapters.commons.mapper.CreateVideoRequestToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.IterableVideoToIterableGetAllVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToCreateVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToGetVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.requests.videos.CreateVideoRequest;
import dev.rvz.boxvideos.adapters.commons.responses.videos.CreateVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.CreateVideoPortIn;
import dev.rvz.boxvideos.port.in.GetAllVideosPortIn;
import dev.rvz.boxvideos.port.in.GetVideoByIdPortIn;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/videos")
public class VideoRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(VideoRestController.class);
    private final CreateVideoPortIn createVideoPortIn;
    private final CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper;
    private final VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper;
    private final GetAllVideosPortIn getAllVideosPortIn;
    private final IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper;
    private final GetVideoByIdPortIn getVideoByIdPortIn;
    private final VideoToGetVideoResponseMapper videoToGetVideoResponseMapper;

    public VideoRestController(CreateVideoPortIn createVideoPortIn, CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper, VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper, GetAllVideosPortIn getAllVideosPortIn, IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper, GetVideoByIdPortIn getVideoByIdPortIn, VideoToGetVideoResponseMapper getVideoResponseMapper) {
        this.createVideoPortIn = createVideoPortIn;
        this.createVideoRequestToVideoMapper = createVideoRequestToVideoMapper;
        this.videoToCreateVideoResponseMapper = videoToCreateVideoResponseMapper;
        this.getAllVideosPortIn = getAllVideosPortIn;
        this.iterableVideoToIterableGetAllVideoResponseMapper = iterableVideoToIterableGetAllVideoResponseMapper;
        this.getVideoByIdPortIn = getVideoByIdPortIn;
        this.videoToGetVideoResponseMapper = getVideoResponseMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CreateVideoResponse> create(@RequestBody CreateVideoRequest createVideoRequest, HttpServletRequest httpServletRequest) throws URISyntaxException {
        LOGGER.info("create - createVideoRequest: {}", createVideoRequest);
        Video video = createVideoRequestToVideoMapper.to(createVideoRequest);

        Video videoCreated = createVideoPortIn.execute(video);
        LOGGER.info("create - videoCreated: {}", videoCreated);

        String url = httpServletRequest.getRequestURL().toString() + "/%s".formatted(videoCreated.id());
        URI uri = new URI(url);
        LOGGER.info("create - Location: {}", url);

        return ResponseEntity.created(uri).body(videoToCreateVideoResponseMapper.to(videoCreated));
    }

    @GetMapping
    ResponseEntity<Iterable<GetAllVideoResponse>> getAllVideos() {
        LOGGER.info("getAllVideos");
        Iterable<Video> allVideos = getAllVideosPortIn.execute();
        LOGGER.info("getAllVideos - allVideos {}", allVideos);

        Iterable<GetAllVideoResponse> getAllVideoResponses = iterableVideoToIterableGetAllVideoResponseMapper.to(allVideos);

        return ResponseEntity.ok(getAllVideoResponses);
    }

    @GetMapping("/{id}")
    ResponseEntity<GetVideoResponse> getVideoById(@PathVariable Long id) {
        LOGGER.info("getVideoById - id {}", id);
        Video video = getVideoByIdPortIn.execute(id);

        return ResponseEntity.ok(videoToGetVideoResponseMapper.to(video));
    }
}
