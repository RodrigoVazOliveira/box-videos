package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.video.IterableVideoEntityToIterableVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.SearchVideoByTitlePortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchVideoByTitleAdapter implements SearchVideoByTitlePortOut {
    private final Logger LOGGER = LoggerFactory.getLogger(SearchVideoByTitleAdapter.class);
    private final VideoRepository videoRepository;
    private final IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper;

    public SearchVideoByTitleAdapter(VideoRepository videoRepository, IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper) {
        this.videoRepository = videoRepository;
        this.iterableVideoEntityToIterableVideoMapper = iterableVideoEntityToIterableVideoMapper;
    }

    @Override
    public Iterable<Video> run(String title) {
        LOGGER.info("run - title {}", title);
        Iterable<VideoEntity> videosByTitleIterable = videoRepository.findByTitleContains(title);

        List<VideoEntity> videoEntities = new ArrayList<>();
        videosByTitleIterable.forEach(videoEntities::add);

        LOGGER.info("run - get {} videos", videoEntities.size());

        return iterableVideoEntityToIterableVideoMapper.to(videoEntities);
    }
}
