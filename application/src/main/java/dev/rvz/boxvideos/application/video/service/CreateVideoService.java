package dev.rvz.boxvideos.application.video.service;


import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.CreateVideoPortIn;
import dev.rvz.boxvideos.port.out.video.CreateVideoPortout;

public class CreateVideoService implements CreateVideoPortIn {

    private final CreateVideoPortout createVideoPortout;

    public CreateVideoService(CreateVideoPortout createVideoPortout) {
        this.createVideoPortout = createVideoPortout;
    }

    @Override
    public Video execute(Video video) {
        ValidateInputService validateInputService = new ValidateInputService(video);
        validateInputService.validateInputs();

        return createVideoPortout.execute(video);
    }
}
