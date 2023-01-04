package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.video.IterableVideoEntityToIterableVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.GetVideoByCategoryIdPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GetVideoByCategoryIdAdapter implements GetVideoByCategoryIdPortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(GetVideoByCategoryIdAdapter.class);
    private final VideoRepository videoRepository;
    private final IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper;

    public GetVideoByCategoryIdAdapter(VideoRepository videoRepository, IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper) {
        this.videoRepository = videoRepository;
        this.iterableVideoEntityToIterableVideoMapper = iterableVideoEntityToIterableVideoMapper;
    }

    @Override
    public Iterable<Video> run(Long id) {
        LOGGER.info("run - id {}", id);
        Iterable<VideoEntity> videos = videoRepository.findByCategoryId(id);
        LOGGER.info("run - videos {}", videos);
        List<VideoEntity> videoEntities = new ArrayList<>();
        videos.forEach(videoEntities::add);

        return iterableVideoEntityToIterableVideoMapper.to(videoEntities);
    }
}
