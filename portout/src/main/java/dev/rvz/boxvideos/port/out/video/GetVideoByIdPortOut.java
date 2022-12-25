package dev.rvz.boxvideos.port.out.video;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface GetVideoByIdPortOut {
    Video execute(Long id);

    Boolean notExistsVideoById(Long id);
}
