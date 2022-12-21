package dev.rvz.boxvideos.adapters.inbound.api;

import dev.rvz.boxvideos.adapters.commons.mapper.*;
import dev.rvz.boxvideos.adapters.commons.requests.videos.CreateVideoRequest;
import dev.rvz.boxvideos.adapters.commons.requests.videos.UpdateCompleteVideoRequest;
import dev.rvz.boxvideos.adapters.commons.responses.videos.CreateVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.UpdateCompleteVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.CreateVideoPortIn;
import dev.rvz.boxvideos.port.in.GetAllVideosPortIn;
import dev.rvz.boxvideos.port.in.GetVideoByIdPortIn;
import dev.rvz.boxvideos.port.in.UpdateCompleteVideoPortIn;
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
    private final UpdateCompleteVideoPortIn updateCompleteVideoPortIn;
    private final UpdateCompleteVideoRequestToVideoMapper updateCompleteVideoRequestToVideoMapper;
    private final VideoToUpdateCompleteVideoResponseMapper updateCompleteVideoResponseMapper;

    public VideoRestController(CreateVideoPortIn createVideoPortIn, CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper, VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper, GetAllVideosPortIn getAllVideosPortIn, IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper, GetVideoByIdPortIn getVideoByIdPortIn, VideoToGetVideoResponseMapper videoToGetVideoResponseMapper, UpdateCompleteVideoPortIn updateCompleteVideoPortIn, UpdateCompleteVideoRequestToVideoMapper updateCompleteVideoRequestToVideoMapper, VideoToUpdateCompleteVideoResponseMapper updateCompleteVideoResponseMapper) {
        this.createVideoPortIn = createVideoPortIn;
        this.createVideoRequestToVideoMapper = createVideoRequestToVideoMapper;
        this.videoToCreateVideoResponseMapper = videoToCreateVideoResponseMapper;
        this.getAllVideosPortIn = getAllVideosPortIn;
        this.iterableVideoToIterableGetAllVideoResponseMapper = iterableVideoToIterableGetAllVideoResponseMapper;
        this.getVideoByIdPortIn = getVideoByIdPortIn;
        this.videoToGetVideoResponseMapper = videoToGetVideoResponseMapper;
        this.updateCompleteVideoPortIn = updateCompleteVideoPortIn;
        this.updateCompleteVideoRequestToVideoMapper = updateCompleteVideoRequestToVideoMapper;
        this.updateCompleteVideoResponseMapper = updateCompleteVideoResponseMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<CreateVideoResponse> create(@RequestBody CreateVideoRequest createVideoRequest, HttpServletRequest httpServletRequest) throws URISyntaxException {
        LOGGER.info("create - createVideoRequest: {}", createVideoRequest);
        Video video = createVideoRequestToVideoMapper.to(createVideoRequest);

        Video videoCreated = createVideoPortIn.execute(video);
        LOGGER.info("create - videoCreated: {}", videoCreated);

        URI uri = getUri(httpServletRequest, videoCreated.id());

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

    @PutMapping("/{id}")
    ResponseEntity<UpdateCompleteVideoResponse> updatecompleteVideo(@PathVariable Long id, @RequestBody UpdateCompleteVideoRequest updateCompleteVideoRequest, HttpServletRequest httpServletRequest) throws URISyntaxException {
        LOGGER.info("updatecompleteVideo - id {}, updateCompleteVideoRequest {}", id, updateCompleteVideoRequest);
        Video video = updateCompleteVideoRequestToVideoMapper.to(updateCompleteVideoRequest, id);
        Boolean videoExists = updateCompleteVideoPortIn.videoExists(id);
        Video videoProcessed = updateCompleteVideoPortIn.execute(video);

        String url = httpServletRequest.getRequestURL().toString();
        URI uri = new URI(url);
        LOGGER.info("getUri - Location: {}", url);

        if (videoExists) {
            return ResponseEntity
                    .noContent()
                    .header("Content-Location", uri.toString())
                    .build();
        }

        return ResponseEntity
                .created(uri)
                .body(updateCompleteVideoResponseMapper.to(videoProcessed));
    }

    private URI getUri(HttpServletRequest httpServletRequest, Long id) throws URISyntaxException {
        String url = httpServletRequest.getRequestURL().toString() + "/%s".formatted(id);
        URI uri = new URI(url);
        LOGGER.info("getUri - Location: {}", url);

        return uri;
    }
}
