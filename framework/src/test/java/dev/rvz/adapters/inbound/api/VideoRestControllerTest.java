package dev.rvz.adapters.inbound.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.CreateVideoRequestToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToCreateVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.requests.videos.CreateVideoRequest;
import dev.rvz.boxvideos.adapters.commons.responses.videos.CreateVideoResponse;
import dev.rvz.boxvideos.adapters.inbound.api.VideoRestController;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.CreateVideoPortIn;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = VideoRestController.class)
class VideoRestControllerTest {

    @MockBean
    private CreateVideoPortIn createVideoPortIn;

    @MockBean
    private CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper;

    @MockBean
    private VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper;

    @Autowired
    private MockMvc mockMvc;

    VideoRestControllerTest() {
    }

    @Test
    void test_create_video_with_success() throws Exception {
        CreateVideoRequest createVideoRequest = new CreateVideoRequest(
                "Video Test1",
                "Descrição teste",
                "http://wwww.google.com.br"
        );

        CreateVideoResponse createVideoResponse = new CreateVideoResponse(
                1L,
                "Video Test1",
                "Descrição teste",
                "http://wwww.google.com.br"
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(createVideoRequest);
        String responseJson = objectMapper.writeValueAsString(createVideoResponse);

        Video video = new Video(1L, "Video Test1", "Descrição teste", "http://wwww.google.com.br");
        Mockito.when(createVideoPortIn.execute(Mockito.any())).thenReturn(video);
        Mockito.when(videoToCreateVideoResponseMapper.to(Mockito.any())).thenReturn(createVideoResponse);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/videos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonRequest)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.content().json(responseJson)
        ).andExpect(
                MockMvcResultMatchers.header().string("Location", "http://localhost/videos/1")
        );

    }
}
