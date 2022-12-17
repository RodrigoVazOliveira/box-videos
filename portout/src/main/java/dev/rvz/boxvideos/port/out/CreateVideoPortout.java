package dev.rvz.boxvideos.port.out;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface CreateVideoPortout {
    Video execute(Video video);
}
