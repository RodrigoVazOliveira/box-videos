package dev.rvz.boxvideos.port.in;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface UpdateCompleteVideoPortIn {
    Video execute(Video video);

    Boolean videoExists(Long id);
}