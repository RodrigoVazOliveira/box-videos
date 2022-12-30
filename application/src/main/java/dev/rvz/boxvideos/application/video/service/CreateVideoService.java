package dev.rvz.boxvideos.application.video.service;


import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.category.GetCategoryByIdPortIn;
import dev.rvz.boxvideos.port.in.video.CreateVideoPortIn;
import dev.rvz.boxvideos.port.out.video.CreateVideoPortout;

public class CreateVideoService implements CreateVideoPortIn {

    private final CreateVideoPortout createVideoPortout;
    private final GetCategoryByIdPortIn getCategoryByIdPortIn;

    public CreateVideoService(CreateVideoPortout createVideoPortout, GetCategoryByIdPortIn getCategoryByIdPortIn) {
        this.createVideoPortout = createVideoPortout;
        this.getCategoryByIdPortIn = getCategoryByIdPortIn;
    }

    @Override
    public Video execute(Video video) {
        validateInputs(video);
        Category category = video.category();
        Video videoWithCategory = getVideoWithCategoryVerified(video, category);

        return createVideoPortout.execute(videoWithCategory);
    }

    private Video getVideoWithCategoryVerified(Video video, Category category) {
        if (category.id() == null) {
            Long id = 1L;
            Category categoryDefault = getCategoryByIdPortIn.getCategoryById(id);

            return new Video(video.id(), video.title(), video.description(), video.url(), categoryDefault);
        }

        Category categoryInformation = getCategoryByIdPortIn.getCategoryById(category.id());

        return new Video(video.id(), video.title(), video.description(), video.url(), categoryInformation);
    }

    private static void validateInputs(Video video) {
        ValidateInputService validateInputService = new ValidateInputService(video);
        validateInputService.validateInputs();
    }
}
