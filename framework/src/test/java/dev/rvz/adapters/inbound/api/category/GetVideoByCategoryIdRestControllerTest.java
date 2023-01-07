package dev.rvz.adapters.inbound.api.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.IterableVideoToIterableGetAllVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.adapters.exceptions.ExceptionHandlerDefaultRest;
import dev.rvz.boxvideos.adapters.inbound.api.category.GetVideoByCategoryIdRestController;
import dev.rvz.boxvideos.core.domain.category.exception.CategoryNotFoundException;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.GetVideoByCategoryIdPortIn;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = GetVideoByCategoryIdRestController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        GetVideoByCategoryIdRestController.class,
        GetVideoByCategoryIdPortIn.class,
        IterableVideoToIterableGetAllVideoResponseMapper.class,
        ExceptionHandlerDefaultRest.class

})
class GetVideoByCategoryIdRestControllerTest {

    @MockBean
    IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper;

    @MockBean
    GetVideoByCategoryIdPortIn getVideoByCategoryIdPortIn;


    @Autowired
    MockMvc mockMvc;

    @Test
    void get_video_by_category_id_with_success() throws Exception {
        final Category category = new Category(1L, "LIVRE", "WHITE");
        final Video video = new Video(1L, "TITULO 1", "DESCRICAO 1", "http://localhost", category);

        ObjectMapper objectMapper = new ObjectMapper();

        List<Video> videos = List.of(video);
        final String response = objectMapper.writeValueAsString(videos);

        List<GetAllVideoResponse> getAllVideoResponses = new ArrayList<>();
        final CategoryResponse categoryResponse = new CategoryResponse(
                category.id(),
                category.title(),
                category.color()
        );
        final GetAllVideoResponse getAllVideoResponse = new GetAllVideoResponse(
                video.id(),
                video.title(),
                video.description(),
                video.url(),
                categoryResponse
        );


        getAllVideoResponses.add(getAllVideoResponse);


        Mockito.when(getVideoByCategoryIdPortIn.run(Mockito.any())).thenReturn(videos);
        Mockito.when(iterableVideoToIterableGetAllVideoResponseMapper.to(Mockito.any()))
                .thenReturn(getAllVideoResponses);
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/1/videos"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }

    @Test
    void get_video_by_category_id_category_not_found() throws Exception {
        final ResponseException responseException = new ResponseException(404, "categoria com id 1 não existe.");
        final ObjectMapper objectMapper = new ObjectMapper();
        final String response = objectMapper.writeValueAsString(responseException);

        Mockito.when(getVideoByCategoryIdPortIn.run(Mockito.any()))
                .thenThrow(new CategoryNotFoundException("categoria com id 1 não existe."));
        mockMvc.perform(MockMvcRequestBuilders.get("/categories/1/videos"))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }


}
