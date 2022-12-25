package dev.rvz.boxvideos.port.out.video;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface UpdateCompleteVideoPortOut {
    Video updateAlreadyExists(Video video);

    Video createVideoIfNotExists(Video video);

    Boolean existsVideoById(Long id);
}
