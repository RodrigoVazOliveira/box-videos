package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.GetVideoByIdPortIn;
import dev.rvz.boxvideos.port.out.GetVideoByIdPortOut;

public class GetVideoByIdService implements GetVideoByIdPortIn {
    private final GetVideoByIdPortOut getVideoByIdPortOut;

    public GetVideoByIdService(GetVideoByIdPortOut getVideoByIdPortOut) {
        this.getVideoByIdPortOut = getVideoByIdPortOut;
    }

    @Override
    public Video execute(Long id) {
        Video video = getVideoByIdPortOut.execute(id);

        return video;
    }
}
