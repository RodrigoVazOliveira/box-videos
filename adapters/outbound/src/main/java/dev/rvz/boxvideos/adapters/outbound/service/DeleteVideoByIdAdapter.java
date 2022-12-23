package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.DeleteVideoByIdPortOut;
import dev.rvz.boxvideos.port.out.GetVideoByIdPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeleteVideoByIdAdapter implements DeleteVideoByIdPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(DeleteVideoByIdAdapter.class);
    private final VideoRepository videoRepository;
    private final GetVideoByIdPortOut getVideoByIdPortOut;

    public DeleteVideoByIdAdapter(VideoRepository videoRepository, GetVideoByIdPortOut getVideoByIdPortOut) {
        this.videoRepository = videoRepository;
        this.getVideoByIdPortOut = getVideoByIdPortOut;
    }

    @Override
    public void deleteById(Video video) {
        LOGGER.info("deleteById - video {}", video);
        Long id = video.id();
        videoRepository.deleteById(id);
    }

    @Override
    public Boolean notExitsVideo(Long id) {
        return getVideoByIdPortOut.notExistsVideoById(id);
    }
}
