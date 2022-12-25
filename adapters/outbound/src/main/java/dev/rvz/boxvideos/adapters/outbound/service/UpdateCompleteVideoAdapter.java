package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.CreateVideoPortout;
import dev.rvz.boxvideos.port.out.video.UpdateCompleteVideoPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateCompleteVideoAdapter implements UpdateCompleteVideoPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(UpdateCompleteVideoAdapter.class);
    private final VideoRepository videoRepository;
    private final CreateVideoPortout createVideoPortout;

    public UpdateCompleteVideoAdapter(VideoRepository videoRepository, CreateVideoPortout createVideoPortout) {
        this.videoRepository = videoRepository;
        this.createVideoPortout = createVideoPortout;
    }

    @Override
    public Video updateAlreadyExists(Video video) {
        LOGGER.info("updateAlreadyExists - video {}", video);

        return createVideoPortout.execute(video);
    }

    @Override
    public Video createVideoIfNotExists(Video video) {
        LOGGER.info("createVideoIfNotExists - not found video, creating new video");
        Video createVideo = new Video(null, video.title(), video.title(), video.url());
        Video newVideo = createVideoPortout.execute(createVideo);
        LOGGER.info("createVideoIfNotExists - new video {}", newVideo);

        return newVideo;
    }

    @Override
    public Boolean existsVideoById(Long id) {
        LOGGER.info("existsVideoById - search by video id {}", id);
        return videoRepository.existsById(id);
    }
}
