package dev.rvz.boxvideos.port.in;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface UpdatePartialVideoPortIn {
    Video updateVideoAlreadyExists(Video video);
}
