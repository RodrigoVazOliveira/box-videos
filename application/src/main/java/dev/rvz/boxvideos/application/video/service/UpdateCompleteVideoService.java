package dev.rvz.boxvideos.application.video.service;

import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.UpdateCompleteVideoPortIn;
import dev.rvz.boxvideos.port.out.UpdateCompleteVideoPortOut;

public class UpdateCompleteVideoService implements UpdateCompleteVideoPortIn {
    private final UpdateCompleteVideoPortOut updateCompleteVideoPortOut;

    public UpdateCompleteVideoService(UpdateCompleteVideoPortOut updateCompleteVideoPortOut) {
        this.updateCompleteVideoPortOut = updateCompleteVideoPortOut;
    }

    @Override
    public Video execute(Video video) {
        new ValidateInputService(video).validateInputs();
        Boolean existsVideoById = videoExists(video.id());
        if (existsVideoById) {
            return updateCompleteVideoPortOut.updateAlreadyExists(video);
        }

        return updateCompleteVideoPortOut.createVideoIfNotExists(video);
    }

    @Override
    public Boolean videoExists(Long id) {
        return updateCompleteVideoPortOut.existsVideoById(id);
    }
}
