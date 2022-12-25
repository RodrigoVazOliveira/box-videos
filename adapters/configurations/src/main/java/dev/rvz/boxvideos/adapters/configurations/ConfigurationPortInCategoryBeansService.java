package dev.rvz.boxvideos.adapters.configurations;

import dev.rvz.boxvideos.application.category.service.GetAllCategoriesService;
import dev.rvz.boxvideos.port.out.category.GetAllCategoriesPortOut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ConfigurationPortInCategoryBeansService {

    @Bean
    GetAllCategoriesService getAllCategoriesService(GetAllCategoriesPortOut getAllCategoriesPortOut) {
        return new GetAllCategoriesService(getAllCategoriesPortOut);
    }
}
