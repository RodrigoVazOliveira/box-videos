package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToVideoEntityMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.UpdateCompleteVideoPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdateCompleteVideoAdapter implements UpdateCompleteVideoPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(UpdateCompleteVideoAdapter.class);
    private final VideoRepository videoRepository;
    private final VideoToVideoEntityMapper videoToVideoEntityMapper;
    private final VideoEntityToVideoMapper videoEntityToVideoMapper;

    public UpdateCompleteVideoAdapter(VideoRepository videoRepository, VideoToVideoEntityMapper videoToVideoEntityMapper, VideoEntityToVideoMapper videoEntityToVideoMapper) {
        this.videoRepository = videoRepository;
        this.videoToVideoEntityMapper = videoToVideoEntityMapper;
        this.videoEntityToVideoMapper = videoEntityToVideoMapper;
    }

    @Override
    public Video updateAlreadyExists(Video video) {
        LOGGER.info("updateAlreadyExists - video {}", video);
        Optional<VideoEntity> optionalVideoEntity = videoRepository.findById(video.id());
        VideoEntity videoEntity = optionalVideoEntity.get();

        videoEntity.setTitle(video.title());
        videoEntity.setDescription(video.description());
        videoEntity.setUrl(video.url());
        videoRepository.save(videoEntity);

        return videoEntityToVideoMapper.to(videoEntity);
    }

    @Override
    public Video createVideoIfNotExists(Video video) {
        LOGGER.info("createVideoIfNotExists - not found video, creating new video");
        VideoEntity videoEntity = videoToVideoEntityMapper.to(video);
        videoRepository.save(videoEntity);
        Video newVideo = videoEntityToVideoMapper.to(videoEntity);
        LOGGER.info("createVideoIfNotExists - new video {}", newVideo);

        return newVideo;
    }

    @Override
    public Boolean existsVideoById(Long id) {
        LOGGER.info("existsVideoById - search by video id {}", id);
        return videoRepository.existsById(id);
    }
}
