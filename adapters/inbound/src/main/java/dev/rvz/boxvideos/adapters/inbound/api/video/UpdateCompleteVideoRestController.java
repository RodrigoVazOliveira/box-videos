package dev.rvz.boxvideos.adapters.inbound.api.video;

import dev.rvz.boxvideos.adapters.commons.mapper.video.UpdateCompleteVideoRequestToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.VideoToUpdateCompleteVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.requests.videos.UpdateCompleteVideoRequest;
import dev.rvz.boxvideos.adapters.commons.responses.videos.UpdateCompleteVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.UpdateCompleteVideoPortIn;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/videos")
public class UpdateCompleteVideoRestController extends VideoRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(UpdateCompleteVideoRestController.class);
    private final UpdateCompleteVideoPortIn updateCompleteVideoPortIn;
    private final UpdateCompleteVideoRequestToVideoMapper updateCompleteVideoRequestToVideoMapper;
    private final VideoToUpdateCompleteVideoResponseMapper videoToUpdateCompleteVideoResponseMapper;

    public UpdateCompleteVideoRestController(UpdateCompleteVideoPortIn updateCompleteVideoPortIn, UpdateCompleteVideoRequestToVideoMapper updateCompleteVideoRequestToVideoMapper, VideoToUpdateCompleteVideoResponseMapper videoToUpdateCompleteVideoResponseMapper) {
        this.updateCompleteVideoPortIn = updateCompleteVideoPortIn;
        this.updateCompleteVideoRequestToVideoMapper = updateCompleteVideoRequestToVideoMapper;
        this.videoToUpdateCompleteVideoResponseMapper = videoToUpdateCompleteVideoResponseMapper;
    }

    @PutMapping("/{id}")
    ResponseEntity<UpdateCompleteVideoResponse> updatecompleteVideo(@PathVariable("id") Long id, @RequestBody UpdateCompleteVideoRequest updateCompleteVideoRequest, HttpServletRequest httpServletRequest) throws URISyntaxException {
        LOGGER.info("updatecompleteVideo - id {}, updateCompleteVideoRequest {}", id, updateCompleteVideoRequest);
        Video video = updateCompleteVideoRequestToVideoMapper.to(updateCompleteVideoRequest, id);
        Boolean videoExists = updateCompleteVideoPortIn.videoExists(id);
        URI uri = getUriWithoutId(httpServletRequest);

        if (videoExists) {
            Video updateVideo = updateCompleteVideoPortIn.execute(video, true);
            UpdateCompleteVideoResponse updateCompleteVideoResponse = videoToUpdateCompleteVideoResponseMapper.to(updateVideo);

            return ResponseEntity.ok(updateCompleteVideoResponse);
        }

        Video newVideo = updateCompleteVideoPortIn.execute(video, false);

        return ResponseEntity
                .created(uri)
                .body(videoToUpdateCompleteVideoResponseMapper.to(newVideo));
    }
}
