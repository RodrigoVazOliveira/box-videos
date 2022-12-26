package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.video.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
@PropertySource({
        "classpath:application.properties"
})
@ContextConfiguration(classes = {
        VideoRepository.class,
        GetVideoByIdAdapter.class,
        VideoEntityToVideoMapper.class,
        DeleteVideoByIdAdapter.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class DeleteVideoByIdAdapterTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private GetVideoByIdAdapter getVideoByIdAdapter;

    @Autowired
    private DeleteVideoByIdAdapter deleteVideoByIdAdapter;


    @Test
    void not_exists_video_by_id() {
        Boolean notExitsVideo = deleteVideoByIdAdapter.notExitsVideo(1L);
        Assertions.assertTrue(notExitsVideo);
    }

    @Test
    void exists_video_by_id() {
        VideoEntity videoEntity = createVideo();

        Boolean notExitsVideo = deleteVideoByIdAdapter.notExitsVideo(videoEntity.getId());
        Assertions.assertFalse(notExitsVideo);

        videoRepository.deleteAll();
    }


    @Test
    void delete_video_exists() {
        VideoEntity videoEntity = createVideo();
        Video video = new Video(videoEntity.getId(), "", "", "");
        deleteVideoByIdAdapter.deleteById(video);

        Boolean notExitsVideo = deleteVideoByIdAdapter.notExitsVideo(videoEntity.getId());
        Assertions.assertTrue(notExitsVideo);

        videoRepository.deleteAll();
    }

    private VideoEntity createVideo() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Titulo teste");
        videoEntity.setDescription("Descrição para teste");
        videoEntity.setUrl("http://localhost");
        videoRepository.save(videoEntity);
        return videoEntity;
    }
}