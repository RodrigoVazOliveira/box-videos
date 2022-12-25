package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.core.validation.strings.ValidationStringChain;
import dev.rvz.boxvideos.port.out.video.UpdatePartialVideoPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UpdatePartialVideoAdapter implements UpdatePartialVideoPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(UpdatePartialVideoAdapter.class);
    private final VideoRepository videoRepository;
    private final VideoEntityToVideoMapper videoEntityToVideoMapper;

    public UpdatePartialVideoAdapter(VideoRepository videoRepository, VideoEntityToVideoMapper videoEntityToVideoMapper) {
        this.videoRepository = videoRepository;
        this.videoEntityToVideoMapper = videoEntityToVideoMapper;
    }

    @Override
    public Video updateAlreadyExists(Video video) {
        LOGGER.info("updateAlreadyExists - video {}", video);
        Optional<VideoEntity> optionalVideoEntity = videoRepository.findById(video.id());
        VideoEntity videoEntity = optionalVideoEntity.get();
        validateInputs(video, videoEntity);
        videoRepository.save(videoEntity);

        LOGGER.info("updateAlreadyExists - updated!");

        return videoEntityToVideoMapper.to(videoEntity);
    }

    private void validateInputs(Video video, VideoEntity videoEntity) {
        LOGGER.info("validateInputs - validate is inputs null, empty or blank");
        ValidationStringChain validateTitle = new ValidationStringChain(video.title());
        ValidationStringChain validateDescription = new ValidationStringChain(video.description());
        ValidationStringChain validateUrl = new ValidationStringChain(video.url());

        if (validateTitle.isValid()) {
            videoEntity.setTitle(video.title());
        }

        if (validateDescription.isValid()) {
            videoEntity.setDescription(video.description());
        }

        if (validateUrl.isValid()) {
            videoEntity.setUrl(video.url());
        }
    }

    @Override
    public Boolean notExistsVideoById(Long id) {
        LOGGER.info("notExistsVideoById - ID {}", id);
        boolean existsById = videoRepository.existsById(id);
        LOGGER.info("notExistsVideoById - existsById {}", !existsById);


        return !existsById;
    }
}
