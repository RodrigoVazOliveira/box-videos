package dev.rvz.boxvideos.adapters.inbound.api;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class VideoRestController {
    private final Logger LOGGER = LoggerFactory.getLogger(VideoRestController.class);

    protected URI getUri(HttpServletRequest httpServletRequest, Long id) throws URISyntaxException {
        String url = httpServletRequest.getRequestURL().toString() + "/%s".formatted(id);
        URI uri = new URI(url);
        LOGGER.info("getUri - Location: {}", url);

        return uri;
    }

    protected URI getUriWithoutId(HttpServletRequest httpServletRequest) throws URISyntaxException {
        String url = httpServletRequest.getRequestURL().toString();
        URI uri = new URI(url);
        LOGGER.info("getUri - Location: {}", url);

        return uri;
    }
}
