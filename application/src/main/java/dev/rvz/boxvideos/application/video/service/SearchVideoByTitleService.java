package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.SearchVideoByTitlePortIn;
import dev.rvz.boxvideos.port.out.video.SearchVideoByTitlePortOut;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchVideoByTitleService implements SearchVideoByTitlePortIn {
    private final Logger LOGGER = Logger.getLogger(SearchVideoByTitleService.class.getName());
    private final SearchVideoByTitlePortOut searchVideoByTitlePortOut;

    public SearchVideoByTitleService(SearchVideoByTitlePortOut searchVideoByTitlePortOut) {
        this.searchVideoByTitlePortOut = searchVideoByTitlePortOut;
    }

    @Override
    public Iterable<Video> run(String title) {
        LOGGER.log(Level.INFO, "run - title : " + title);
        Iterable<Video> videos = searchVideoByTitlePortOut.run(title);
        validateIsEmpty(title, videos);

        return videos;
    }

    private static void validateIsEmpty(String title, Iterable<Video> videos) {
        List<Video> videosList = new ArrayList<>();
        videos.forEach(videosList::add);

        if (videosList.isEmpty()) {
            throw new VideoNotFoundException("Não foi enctrado nenhum video com o título %s".formatted(title));
        }
    }
}
