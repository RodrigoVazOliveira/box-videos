package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.entity.VideoEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.outbound.repository.CategoryRepository;
import dev.rvz.boxvideos.adapters.outbound.repository.VideoRepository;
import dev.rvz.boxvideos.core.domain.category.model.Category;
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
        UpdatePartialVideoAdapter.class,
        CategoryRepository.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class UpdatePartialVideoAdapterTest {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoEntityToVideoMapper videoEntityToVideoMapper;

    @Autowired
    private CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    @Autowired
    private UpdatePartialVideoAdapter updatePartialVideoAdapter;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void not_exists_is_true() {
        Boolean notExistsVideoById = updatePartialVideoAdapter.notExistsVideoById(34324234L);

        Assertions.assertTrue(notExistsVideoById);
    }

    @Test
    void not_exists_is_false() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("LIVRE");
        categoryEntity.setColor("BLUE");
        categoryRepository.save(categoryEntity);

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");
        videoEntity.setCategoryEntity(categoryEntity);

        videoRepository.save(videoEntity);

        Boolean notExistsVideoById = updatePartialVideoAdapter.notExistsVideoById(videoEntity.getId());

        Assertions.assertFalse(notExistsVideoById);
        videoRepository.deleteById(videoEntity.getId());
        categoryRepository.deleteAll();
    }

    @Test
    void update_partial_with_video_exists_title_not_null_or_blank_or_empty() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("LIVRE");
        categoryEntity.setColor("BLUE");
        categoryRepository.save(categoryEntity);

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");
        videoEntity.setCategoryEntity(categoryEntity);

        videoRepository.save(videoEntity);

        Video video = new Video(videoEntity.getId(), "Video 2", "", "",
                new Category(1L, "LIVRE", "BLUE"));
        Video resultUpdate = updatePartialVideoAdapter.updateAlreadyExists(video);


        Assertions.assertEquals(video.title(), resultUpdate.title());
        Assertions.assertNotEquals(videoEntity.getTitle(), resultUpdate.title());
        videoRepository.deleteById(videoEntity.getId());
        categoryRepository.deleteAll();
    }

    @Test
    void update_partial_with_video_exists_description_not_null_or_blank_or_empty() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("LIVRE");
        categoryEntity.setColor("BLUE");
        categoryRepository.save(categoryEntity);

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");
        videoEntity.setCategoryEntity(categoryEntity);

        videoRepository.save(videoEntity);

        Video video = new Video(videoEntity.getId(), null, "Description", "",
                new Category(1L, "LIVRE", "BLUE"));
        Video resultUpdate = updatePartialVideoAdapter.updateAlreadyExists(video);


        Assertions.assertEquals(video.description(), resultUpdate.description());
        Assertions.assertNotEquals(videoEntity.getDescription(), resultUpdate.description());
        videoRepository.deleteById(videoEntity.getId());
        categoryRepository.deleteAll();

    }

    @Test
    void update_partial_with_video_exists_url_not_null_or_blank_or_empty() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("LIVRE");
        categoryEntity.setColor("BLUE");
        categoryRepository.save(categoryEntity);

        VideoEntity videoEntity = new VideoEntity();
        videoEntity.setTitle("Video 1");
        videoEntity.setDescription("Video 1 descricao");
        videoEntity.setUrl("http://localhost");
        videoEntity.setCategoryEntity(categoryEntity);

        videoRepository.save(videoEntity);

        Video video = new Video(videoEntity.getId(), null, "", "http://localhost/1",
                new Category(1L, "LIVRE", "BLUE"));
        Video resultUpdate = updatePartialVideoAdapter.updateAlreadyExists(video);


        Assertions.assertEquals(video.url(), resultUpdate.url());
        Assertions.assertNotEquals(videoEntity.getUrl(), resultUpdate.url());
        videoRepository.deleteById(videoEntity.getId());
        categoryRepository.deleteAll();
    }
}