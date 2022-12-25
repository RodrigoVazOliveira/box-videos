package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.DeleteVideoByIdPortIn;
import dev.rvz.boxvideos.port.out.video.DeleteVideoByIdPortOut;

public class DeleteVideoByIdService implements DeleteVideoByIdPortIn {
    private final DeleteVideoByIdPortOut deleteVideoByIdPortOut;

    public DeleteVideoByIdService(DeleteVideoByIdPortOut deleteVideoByIdPortOut) {
        this.deleteVideoByIdPortOut = deleteVideoByIdPortOut;
    }

    @Override
    public void run(Video video) {
        Long id = video.id();
        Boolean notExitsVideo = deleteVideoByIdPortOut.notExitsVideo(id);
        if (notExitsVideo) {
            throw new VideoNotFoundException("Não existe vídeo com id %d".formatted(id));
        }

        deleteVideoByIdPortOut.deleteById(video);
    }
}
