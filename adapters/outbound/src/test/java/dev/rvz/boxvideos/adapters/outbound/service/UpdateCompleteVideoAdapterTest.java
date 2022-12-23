package dev.rvz.boxvideos.adapters.outbound.service;

import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToVideoEntityMapper;
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
        VideoRepository.class, CreateVideoAdapter.class, UpdateCompleteVideoAdapter.class,
        VideoEntityToVideoMapper.class, VideoToVideoEntityMapper.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class UpdateCompleteVideoAdapterTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CreateVideoAdapter createVideoAdapter;

    @Autowired
    private UpdateCompleteVideoAdapter updateCompleteVideoAdapter;

    @Test
    void test_exists_video_by_id_is_true() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");

        videoRepository.save(videoEntity);
        Boolean resultExists = updateCompleteVideoAdapter.existsVideoById(videoEntity.getId());

        Assertions.assertTrue(resultExists);
        videoRepository.deleteById(videoEntity.getId());
    }

    @Test
    void test_exists_video_by_id_is_false() {
        Boolean resultExists = updateCompleteVideoAdapter.existsVideoById(1L);

        Assertions.assertFalse(resultExists);
    }

    @Test
    void test_create_video_if_not_exists_with_sucess() {
        Video video = new Video(2L, "Teste1", "Teste1", "http://localhost:000");
        Video result = updateCompleteVideoAdapter.createVideoIfNotExists(video);

        Assertions.assertEquals(video.title(), result.title());
        Assertions.assertEquals(video.description(), result.description());
        Assertions.assertEquals(video.url(), result.url());

        videoRepository.deleteById(result.id());
    }

    @Test
    void test_update_already_exists() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");
        videoRepository.save(videoEntity);

        Video video = new Video(videoEntity.getId(), "Teste1", "Teste1", "http://localhost:000");
        Video result = updateCompleteVideoAdapter.updateAlreadyExists(video);

        Assertions.assertEquals(videoEntity.getId(), result.id());
//        Assertions.assertEquals(videoEntity.getTitle(), result.title());
//        Assertions.assertEquals(videoEntity.getDescription(), result.description());
//        Assertions.assertEquals(videoEntity.getUrl(), result.url());

        videoRepository.delete(videoEntity);
    }
}