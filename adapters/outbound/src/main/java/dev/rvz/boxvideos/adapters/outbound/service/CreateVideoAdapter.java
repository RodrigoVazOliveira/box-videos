package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToVideoEntityMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.CreateVideoPortout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CreateVideoAdapter implements CreateVideoPortout {
    private final Logger LOGGER = LoggerFactory.getLogger(CreateVideoAdapter.class);
    private final VideoRepository videoRepository;
    private final VideoEntityToVideoMapper videoEntityToVideoMapper;
    private final VideoToVideoEntityMapper videoToVideoEntityMapper;

    public CreateVideoAdapter(VideoRepository videoRepository, VideoEntityToVideoMapper videoEntityToVideoMapper, VideoToVideoEntityMapper videoToVideoEntityMapper) {
        this.videoRepository = videoRepository;
        this.videoEntityToVideoMapper = videoEntityToVideoMapper;
        this.videoToVideoEntityMapper = videoToVideoEntityMapper;
    }

    @Override
    public Video execute(Video video) {
        VideoEntity videoEntity = videoToVideoEntityMapper.to(video);
        videoRepository.save(videoEntity);
        LOGGER.info("execute - videoEntity {}", videoEntity);

        return videoEntityToVideoMapper.to(videoEntity);
    }
}
