package dev.rvz.boxvideos.port.out;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface UpdatePartialVideoPortOut {
    Video updateAlreadyExists(Video video);
    Boolean notExistsVideoById(Long id);
}
