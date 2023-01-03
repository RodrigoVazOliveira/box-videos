package dev.rvz.boxvideos.port.in.video;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface SearchVideoByTitlePortIn {
    Iterable<Video> run(String title);
}
