package dev.rvz.boxvideos.adapters.inbound.api;

import dev.rvz.boxvideos.adapters.commons.mapper.*;
import dev.rvz.boxvideos.adapters.commons.requests.videos.CreateVideoRequest;
import dev.rvz.boxvideos.adapters.commons.responses.videos.CreateVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.*;
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
public class CreateVideoController extends VideoRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(CreateVideoController.class);
    private final CreateVideoPortIn createVideoPortIn;
    private final CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper;
    private final VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper;

    public CreateVideoController(CreateVideoPortIn ignoredCreateVideoPortIn, CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper, VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper, GetAllVideosPortIn getAllVideosPortIn, IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper, GetVideoByIdPortIn getVideoByIdPortIn, VideoToGetVideoResponseMapper videoToGetVideoResponseMapper, UpdateCompleteVideoPortIn updateCompleteVideoPortIn, UpdateCompleteVideoRequestToVideoMapper updateCompleteVideoRequestToVideoMapper, VideoToUpdateCompleteVideoResponseMapper videoToUpdateCompleteVideoResponseMapper, UpdatePartialVideoPortIn updatePartialVideoPortIn, UpdatePartialRequestToVideoMapper updatePartialRequestToVideoMapper, CreateVideoPortIn createVideoPortIn1, CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper1, VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper1) {
        this.createVideoPortIn = createVideoPortIn1;
        this.createVideoRequestToVideoMapper = createVideoRequestToVideoMapper1;
        this.videoToCreateVideoResponseMapper = videoToCreateVideoResponseMapper1;
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
}
