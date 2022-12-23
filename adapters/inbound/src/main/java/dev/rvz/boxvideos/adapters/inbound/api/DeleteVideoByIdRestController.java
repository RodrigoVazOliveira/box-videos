package dev.rvz.boxvideos.adapters.inbound.api;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.DeleteVideoByIdPortIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/videos")
public class DeleteVideoByIdRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(DeleteVideoByIdRestController.class);
    private final DeleteVideoByIdPortIn deleteVideoByIdPortIn;

    public DeleteVideoByIdRestController(DeleteVideoByIdPortIn deleteVideoByIdPortIn) {
        this.deleteVideoByIdPortIn = deleteVideoByIdPortIn;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteVideoById(@PathVariable Long id) {
        LOGGER.info("deleteVideoById - id {}", id);
        deleteVideoByIdPortIn.run(new Video(id, "", "", ""));
    }
}
