package dev.rvz.adapters.inbound.api.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.IterableVideoToIterableGetAllVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.adapters.exceptions.ExceptionHandlerDefaultRest;
import dev.rvz.boxvideos.adapters.inbound.api.video.SearchVideoByTitleRestController;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseException;
import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.SearchVideoByTitlePortIn;
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

import java.util.List;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = {
        SearchVideoByTitlePortIn.class,
        IterableVideoToIterableGetAllVideoResponseMapper.class,
        SearchVideoByTitleRestController.class,
        ExceptionHandlerDefaultRest.class
})
class SearchVideoByTitleRestControllerTest {

    @MockBean
    SearchVideoByTitlePortIn searchVideoByTitlePortIn;

    @MockBean
    IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_search_video_by_title_with_success() throws Exception {
        Category category = new Category(1L, "LIVRE", "WHITE");
        Video video = new Video(1L, "Titulo", "Desccrição", "http://localhost", category);
        Iterable<Video> videosIterable = List.of(video);

        GetAllVideoResponse getAllVideoResponse = new GetAllVideoResponse(video.id(), video.title(), video.description(), video.url(),
                new CategoryResponse(category.id(), category.title(), category.color()));
        Iterable<GetAllVideoResponse> videoResponses = List.of(getAllVideoResponse);


        Mockito.when(searchVideoByTitlePortIn.run(Mockito.anyString())).thenReturn(videosIterable);
        Mockito.when(iterableVideoToIterableGetAllVideoResponseMapper.to(Mockito.any()))
                .thenReturn(videoResponses);

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(videoResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/videos/?search=Titulo"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }

    @Test
    void test_search_video_by_title_with_not_found() throws Exception {
        VideoNotFoundException videoNotFoundException = new VideoNotFoundException("Não foi enctrado nenhum video com o título Titulo");
        ResponseException responseException = new ResponseException(404, videoNotFoundException.getMessage());

        Mockito.when(searchVideoByTitlePortIn.run(Mockito.anyString()))
                .thenThrow(videoNotFoundException);
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(responseException);

        mockMvc.perform(MockMvcRequestBuilders.get("/videos/?search=Titulo"))
                .andExpect(
                        MockMvcResultMatchers.status().isNotFound()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }


}
