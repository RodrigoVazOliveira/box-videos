package dev.rvz.boxvideos.port.out.video;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface SearchVideoByTitlePortOut {
    Iterable<Video> run(String title);
}
