package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
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
        VideoEntityToVideoMapper.class,
        CategoryEntityToCategoryMapper.class,
        GetVideoByIdAdapter.class,
        CategoryRepository.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class GetVideoByIdAdapterTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoEntityToVideoMapper videoEntityToVideoMapper;

    @Autowired
    private CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private GetVideoByIdAdapter getVideoByIdAdapter;

    @Test
    void test_get_video_by_id_with_success() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("LIVRE");
        categoryEntity.setColor("BLUE");
        categoryRepository.save(categoryEntity);

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Titulo 1");
        videoEntity.setDescription("Descrição 1");
        videoEntity.setUrl("http://localhost");
        videoEntity.setCategoryEntity(categoryEntity);
        videoRepository.save(videoEntity);

        Video result = getVideoByIdAdapter.execute(videoEntity.getId());

        Assertions.assertEquals(videoEntity.getTitle(), result.title());
        Assertions.assertEquals(videoEntity.getDescription(), result.description());
        Assertions.assertEquals(videoEntity.getUrl(), result.url());
        Assertions.assertEquals(videoEntity.getId(), result.id());

        videoRepository.delete(videoEntity);
        categoryRepository.deleteAll();
    }


    @Test
    void test_get_video_by_id_exists() {
        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Titulo 1");
        videoEntity.setDescription("Descrição 1");
        videoEntity.setUrl("http://localhost");
        videoRepository.save(videoEntity);

        Boolean result = getVideoByIdAdapter.notExistsVideoById(videoEntity.getId());

        Assertions.assertFalse(result);
        videoRepository.delete(videoEntity);
    }

    @Test
    void test_get_video_by_id_not_exists() {
        Boolean result = getVideoByIdAdapter.notExistsVideoById(1L);

        Assertions.assertTrue(result);
    }
}