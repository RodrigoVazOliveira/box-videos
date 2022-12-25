package dev.rvz.boxvideos.adapters.inbound.api;

import dev.rvz.boxvideos.adapters.commons.mapper.UpdatePartialRequestToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.requests.videos.UpdatePartialRequest;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.UpdatePartialVideoPortIn;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/videos")
public class UpdatePartialVideoRestController extends VideoRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(UpdatePartialVideoRestController.class);
    private final UpdatePartialVideoPortIn updatePartialVideoPortIn;
    private final UpdatePartialRequestToVideoMapper updatePartialRequestToVideoMapper;

    public UpdatePartialVideoRestController(UpdatePartialVideoPortIn updatePartialVideoPortIn, UpdatePartialRequestToVideoMapper updatePartialRequestToVideoMapper) {
        this.updatePartialVideoPortIn = updatePartialVideoPortIn;
        this.updatePartialRequestToVideoMapper = updatePartialRequestToVideoMapper;
    }

    @PatchMapping("/{id}")
    ResponseEntity<?> updatePartial(@PathVariable Long id, @RequestBody UpdatePartialRequest updatePartialRequest, HttpServletRequest httpServletRequest) throws URISyntaxException {
        LOGGER.info("updatePartial - id {}, updatePartialRequest {}", id, updatePartialRequest);
        Video video = updatePartialRequestToVideoMapper.to(updatePartialRequest, id);
        updatePartialVideoPortIn.updateVideoAlreadyExists(video);
        URI uri = getUriWithoutId(httpServletRequest);


        return ResponseEntity
                .noContent()
                .header("Content-Location", uri.toString())
                .build();
    }
}
