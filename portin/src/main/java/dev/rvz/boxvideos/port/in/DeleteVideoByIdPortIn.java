package dev.rvz.boxvideos.port.in;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface DeleteVideoByIdPortIn {
    void run(Video video);
}
