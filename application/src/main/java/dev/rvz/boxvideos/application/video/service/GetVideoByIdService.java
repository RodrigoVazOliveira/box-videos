package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.GetVideoByIdPortIn;
import dev.rvz.boxvideos.port.out.GetVideoByIdPortOut;

public class GetVideoByIdService implements GetVideoByIdPortIn {
    private final GetVideoByIdPortOut getVideoByIdPortOut;

    public GetVideoByIdService(GetVideoByIdPortOut getVideoByIdPortOut) {
        this.getVideoByIdPortOut = getVideoByIdPortOut;
    }

    @Override
    public Video execute(Long id) {
        Boolean notExistsVideoById = getVideoByIdPortOut.notExistsVideoById(id);
        if (notExistsVideoById) {
            throw new VideoNotFoundException("Não existe vídeo com id %d".formatted(id));
        }
        return getVideoByIdPortOut.execute(id);
    }
}
