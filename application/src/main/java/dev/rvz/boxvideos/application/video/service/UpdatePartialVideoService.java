package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.UpdatePartialVideoPortIn;
import dev.rvz.boxvideos.port.out.UpdatePartialVideoPortOut;

public class UpdatePartialVideoService implements UpdatePartialVideoPortIn {
    private final UpdatePartialVideoPortOut updatePartialVideoPortOut;

    public UpdatePartialVideoService(UpdatePartialVideoPortOut updatePartialVideoPortOut) {
        this.updatePartialVideoPortOut = updatePartialVideoPortOut;
    }

    @Override
    public Video updateVideoAlreadyExists(Video video) {
        Long id = video.id();
        if (updatePartialVideoPortOut.notExistsVideoById(id)) {
            throw new VideoNotFoundException("Não existe vídeo com id %d".formatted(id));
        }

        return updatePartialVideoPortOut.updateAlreadyExists(video);
    }
}
