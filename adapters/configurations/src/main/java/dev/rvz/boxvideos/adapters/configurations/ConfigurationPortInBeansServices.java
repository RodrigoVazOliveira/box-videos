package dev.rvz.boxvideos.adapters.configurations;


import dev.rvz.boxvideos.application.video.service.CreateVideoService;
import dev.rvz.boxvideos.application.video.service.GetAllVideoService;
import dev.rvz.boxvideos.application.video.service.GetVideoByIdService;
import dev.rvz.boxvideos.application.video.service.UpdateCompleteVideoService;
import dev.rvz.boxvideos.port.out.CreateVideoPortout;
import dev.rvz.boxvideos.port.out.GetAllVideosPortOut;
import dev.rvz.boxvideos.port.out.GetVideoByIdPortOut;
import dev.rvz.boxvideos.port.out.UpdateCompleteVideoPortOut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConfigurationPortInBeansServices {

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
    UpdateCompleteVideoService updateCompleteVideoService(UpdateCompleteVideoPortOut updateCompleteVideoPortOut) {
        return new UpdateCompleteVideoService(updateCompleteVideoPortOut);
    }
}
