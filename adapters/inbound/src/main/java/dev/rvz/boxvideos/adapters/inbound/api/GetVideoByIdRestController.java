package dev.rvz.boxvideos.adapters.inbound.api;

import dev.rvz.boxvideos.adapters.commons.mapper.VideoToGetVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.GetVideoByIdPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
public class GetVideoByIdRestController extends VideoRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(GetVideoByIdRestController.class);
    private final GetVideoByIdPortIn getVideoByIdPortIn;
    private final VideoToGetVideoResponseMapper videoToGetVideoResponseMapper;

    public GetVideoByIdRestController(GetVideoByIdPortIn getVideoByIdPortIn, VideoToGetVideoResponseMapper videoToGetVideoResponseMapper) {
        this.getVideoByIdPortIn = getVideoByIdPortIn;
        this.videoToGetVideoResponseMapper = videoToGetVideoResponseMapper;
    }

    @GetMapping("/{id}")
    ResponseEntity<GetVideoResponse> getVideoById(@PathVariable Long id) {
        LOGGER.info("getVideoById - id {}", id);
        Video video = getVideoByIdPortIn.execute(id);

        return ResponseEntity.ok(videoToGetVideoResponseMapper.to(video));
    }
}
