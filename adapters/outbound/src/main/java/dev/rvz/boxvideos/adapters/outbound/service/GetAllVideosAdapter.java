package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.IterableVideoEntityToIterableVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.out.video.GetAllVideosPortOut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllVideosAdapter implements GetAllVideosPortOut {

    private final Logger LOGGER = LoggerFactory.getLogger(GetAllVideosAdapter.class);
    private final VideoRepository videoRepository;
    private final IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper;

    public GetAllVideosAdapter(VideoRepository videoRepository, IterableVideoEntityToIterableVideoMapper iterableVideoEntityToIterableVideoMapper) {
        this.videoRepository = videoRepository;
        this.iterableVideoEntityToIterableVideoMapper = iterableVideoEntityToIterableVideoMapper;
    }

    @Override
    public Iterable<Video> execute() {
        LOGGER.info("execute - searching all datas to videos");
        Iterable<VideoEntity> allVideos = videoRepository.findAll();
        int sizeVideo = getSizeVideo(allVideos);
        LOGGER.info("execute - total to allVideos : {}", sizeVideo);

        return iterableVideoEntityToIterableVideoMapper.to((List<VideoEntity>) allVideos).stream().toList();
    }

    private static int getSizeVideo(Iterable<VideoEntity> allVideos) {
        int sizeVideo = 0;
        for (VideoEntity videoEntity : allVideos)
            sizeVideo++;

        return sizeVideo;
    }
}
