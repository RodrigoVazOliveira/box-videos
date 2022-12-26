package dev.rvz.boxvideos.adapters.configurations;


import dev.rvz.boxvideos.application.video.service.*;
import dev.rvz.boxvideos.port.in.video.CreateVideoPortIn;
import dev.rvz.boxvideos.port.out.video.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConfigurationPortInVideoBeansService {

    @Bean
    CreateVideoService createVideoServiceBean(CreateVideoPortout createVideoPortout) {
        return new CreateVideoService(createVideoPortout);
    }

    @Bean
    GetAllVideoService getAllVideoServiceBean(GetAllVideosPortOut getAllVideosPortOut) {
        return new GetAllVideoService(getAllVideosPortOut);
    }

    @Bean
    GetVideoByIdService getVideoByIdService(GetVideoByIdPortOut getVideoByIdPortOut) {
        return new GetVideoByIdService(getVideoByIdPortOut);
    }

    @Bean
    UpdateCompleteVideoService updateCompleteVideoService(CreateVideoPortIn createVideoPortIn,
                                                          GetVideoByIdPortOut getVideoByIdPortOut) {
        return new UpdateCompleteVideoService(createVideoPortIn, getVideoByIdPortOut);
    }

    @Bean
    UpdatePartialVideoService updatePartialVideoService(UpdatePartialVideoPortOut updatePartialVideoPortOut) {
        return new UpdatePartialVideoService(updatePartialVideoPortOut);
    }

    @Bean
    DeleteVideoByIdService deleteVideoByIdService(DeleteVideoByIdPortOut deleteVideoByIdPortOut) {
        return new DeleteVideoByIdService(deleteVideoByIdPortOut);
    }
}
