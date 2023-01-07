package dev.rvz.boxvideos.adapters.inbound.api.video;

import dev.rvz.boxvideos.adapters.commons.mapper.video.IterableVideoToIterableGetAllVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.SearchVideoByTitlePortIn;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/videos")
public class SearchVideoByTitleRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(SearchVideoByTitleRestController.class);
    private final SearchVideoByTitlePortIn searchVideoByTitlePortIn;
    private final IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper;

    public SearchVideoByTitleRestController(SearchVideoByTitlePortIn searchVideoByTitlePortIn, IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper) {
        this.searchVideoByTitlePortIn = searchVideoByTitlePortIn;
        this.iterableVideoToIterableGetAllVideoResponseMapper = iterableVideoToIterableGetAllVideoResponseMapper;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    Iterable<GetAllVideoResponse> getVideoByTitle(@PathParam("search") String search) {
        LOGGER.info("getVideoByTitle - title {}", search);
        Iterable<Video> videos = searchVideoByTitlePortIn.run(search);

        return iterableVideoToIterableGetAllVideoResponseMapper.to(videos);
    }
}
