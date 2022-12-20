package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.GetAllVideosPortIn;
import dev.rvz.boxvideos.port.out.GetAllVideosPortOut;

public class GetAllVideoService implements GetAllVideosPortIn {

    private final GetAllVideosPortOut getAllVideosPortOut;

    public GetAllVideoService(GetAllVideosPortOut getAllVideosPortOut) {
        this.getAllVideosPortOut = getAllVideosPortOut;
    }

    @Override
    public Iterable<Video> execute() {
        return getAllVideosPortOut.execute();
    }
}
