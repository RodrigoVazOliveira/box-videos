package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.CreateVideoPortIn;
import dev.rvz.boxvideos.port.in.video.UpdateCompleteVideoPortIn;
import dev.rvz.boxvideos.port.out.video.GetVideoByIdPortOut;

public class UpdateCompleteVideoService implements UpdateCompleteVideoPortIn {
    private final CreateVideoPortIn createVideoPortIn;
    private final GetVideoByIdPortOut getVideoByIdPortOut;

    public UpdateCompleteVideoService(CreateVideoPortIn createVideoPortIn, GetVideoByIdPortOut getVideoByIdPortOut) {
        this.createVideoPortIn = createVideoPortIn;
        this.getVideoByIdPortOut = getVideoByIdPortOut;
    }
    
    @Override
    public Video execute(Video video, Boolean videExists) {
        if (videExists) {
            return createVideoPortIn.execute(video);
        }

        Video newVideo = new Video(null, video.title(), video.description(), video.url());
        return createVideoPortIn.execute(newVideo);
    }

    @Override
    public Boolean videoExists(Long id) {
        return !getVideoByIdPortOut.notExistsVideoById(id);
    }
}
