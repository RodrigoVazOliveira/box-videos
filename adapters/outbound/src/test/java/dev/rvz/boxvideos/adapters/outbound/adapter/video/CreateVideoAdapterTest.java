package dev.rvz.boxvideos.adapters.outbound.adapter.video;

import dev.rvz.boxvideos.adapters.commons.entity.CategoryEntity;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryEntityToCategoryMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.category.CategoryToCategoryEntityMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.VideoEntityToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.VideoToVideoEntityMapper;
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
        VideoToVideoEntityMapper.class,
        CategoryEntityToCategoryMapper.class,
        CreateVideoAdapter.class,
        CategoryRepository.class,
        CategoryToCategoryEntityMapper.class
})
@EntityScan("dev.rvz.*")
@EnableJpaRepositories("dev.rvz.*")
class CreateVideoAdapterTest {
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryEntityToCategoryMapper categoryEntityToCategoryMapper;

    @Autowired
    private VideoEntityToVideoMapper videoEntityToVideoMapper;

    @Autowired
    private VideoToVideoEntityMapper videoToVideoEntityMapper;

    @Autowired
    private CategoryToCategoryEntityMapper categoryToCategoryEntityMapper;

    @Autowired
    private CreateVideoAdapter createVideoAdapter;

    @Test
    void save_with_success() {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setTitle("LIVRE");
        categoryEntity.setColor("BLUE");
        categoryRepository.save(categoryEntity);

        Video video = new Video(null, "Titulo 1", "Descriacao 1", "http://localhost",
                new Category(categoryEntity.getId(), categoryEntity.getTitle(), categoryEntity.getColor()));

        Video result = createVideoAdapter.execute(video);

        Assertions.assertEquals(video.title(), result.title());
        Assertions.assertEquals(video.description(), result.description());
        Assertions.assertEquals(video.url(), result.url());
        Assertions.assertNotNull(result.id());

        videoRepository.deleteAll();
        categoryRepository.deleteAll();
        ;
    }
}