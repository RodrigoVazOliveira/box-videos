package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.GetVideoByIdPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetVideoByIdAdapter implements GetVideoByIdPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(GetVideoByIdAdapter.class);
    private final VideoRepository videoRepository;
    private final VideoEntityToVideoMapper videoEntityToVideoMapper;

    public GetVideoByIdAdapter(VideoRepository videoRepository, VideoEntityToVideoMapper videoEntityToVideoMapper) {
        this.videoRepository = videoRepository;
        this.videoEntityToVideoMapper = videoEntityToVideoMapper;
    }

    @Override
    public Video execute(Long id) {
        Optional<VideoEntity> optionalVideoEntity = videoRepository.findById(id);
        Video video = videoEntityToVideoMapper.to(optionalVideoEntity.get());
        LOGGER.info("execute - video encontrado - {}", video);

        return video;
    }

    @Override
    public Boolean notExistsVideoById(Long id) {
        LOGGER.info("notExistsVideoById - id {}", id);
        boolean existsById = videoRepository.existsById(id);
        LOGGER.info("notExistsVideoById - existsById {}", existsById);

        return !existsById;
    }
}
