package dev.rvz.boxvideos.port.in;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface GetVideoByIdPortIn {
    Video execute(Long id);
}