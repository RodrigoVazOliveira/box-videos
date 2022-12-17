package dev.rvz.boxvideos.adapters.inbound.api;

import dev.rvz.boxvideos.adapters.commons.mapper.CreateVideoRequestToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToCreateVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.requests.videos.CreateVideoRequest;
import dev.rvz.boxvideos.adapters.commons.responses.videos.CreateVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.CreateVideoPortIn;
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

    public VideoRestController(CreateVideoPortIn createVideoPortIn, CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper, VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper) {
        this.createVideoPortIn = createVideoPortIn;
        this.createVideoRequestToVideoMapper = createVideoRequestToVideoMapper;
        this.videoToCreateVideoResponseMapper = videoToCreateVideoResponseMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CreateVideoResponse> create(@RequestBody CreateVideoRequest createVideoRequest, HttpServletRequest httpServletRequest) throws URISyntaxException {
        LOGGER.info("create - createVideoRequest: {}", createVideoRequest);
        Video video = createVideoRequestToVideoMapper.to(createVideoRequest);

        Video videoCreated = createVideoPortIn.execute(video);
        LOGGER.info("create - videoCreated: {}", videoCreated);

        String url = httpServletRequest.getRequestURL().toString() + "/%s".formatted(videoCreated.id());
        URI uri = new URI(url);
        LOGGER.info("create - Location: {}", url);

        return ResponseEntity.created(uri).body(videoToCreateVideoResponseMapper.to(videoCreated));
    }
}
