package dev.rvz.boxvideos.port.out.video;

import dev.rvz.boxvideos.core.domain.video.model.Video;

public interface DeleteVideoByIdPortOut {
    void deleteById(Video video);

    Boolean notExitsVideo(Long id);

}
