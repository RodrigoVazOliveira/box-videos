package dev.rvz.adapters.inbound.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.CreateVideoRequestToVideoMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.IterableVideoToIterableGetAllVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.VideoToCreateVideoResponseMapper;
import dev.rvz.boxvideos.adapters.commons.requests.videos.CreateVideoRequest;
import dev.rvz.boxvideos.adapters.commons.responses.videos.CreateVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.adapters.inbound.api.VideoRestController;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.CreateVideoPortIn;
import dev.rvz.boxvideos.port.in.GetAllVideosPortIn;
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

import java.util.Arrays;

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

    @MockBean
    private GetAllVideosPortIn getAllVideosPortIn;

    @MockBean
    private IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper;

    @MockBean
    private GetVideoByIdPortIn getVideoByIdPortIn;


    @MockBean
    private VideoToGetVideoResponseMapper videoToGetVideoResponseMapper;


    @Autowired
    private MockMvc mockMvc;

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


    @Test
    void test_get_all_videos() throws Exception {
        Iterable<Video> videos = Arrays.asList(
                new Video(1L, "titulo 1", "Teste descricao 1", "http://www.filme1.com.br"),
                new Video(2L, "titulo 2", "Teste descricao 2", "http://www.filme2.com.br"),
                new Video(1L, "titulo 3", "Teste descricao 3", "http://www.filme2.com.br")
        );

        Iterable<GetAllVideoResponse> allVideos = Arrays.asList(
                new GetAllVideoResponse(1L, "titulo 1", "Teste descricao 1", "http://www.filme1.com.br"),
                new GetAllVideoResponse(2L, "titulo 2", "Teste descricao 2", "http://www.filme2.com.br"),
                new GetAllVideoResponse(1L, "titulo 3", "Teste descricao 3", "http://www.filme2.com.br")
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String expectResponse = objectMapper.writeValueAsString(allVideos);
        Mockito.when(getAllVideosPortIn.execute()).thenReturn(videos);
        Mockito.when(iterableVideoToIterableGetAllVideoResponseMapper.to(Mockito.any()))
                .thenReturn(allVideos);

        mockMvc.perform(MockMvcRequestBuilders.get("/videos"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(expectResponse)
                );
    }

    @Test
    void test_get_video_by_id_with_success_handred_two_ok() throws Exception {
        Video video = new Video(1L, "", "", "");
        GetVideoResponse getVideoResponse = new GetVideoResponse(1L, "", "", "");

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(getVideoResponse);

        Mockito.when(VideoToGetVideoResponseMapper.execute(Mockito.anyString())).thenReturn(video);
        Mockito.when(videoToGetVideoResponseMapper.to(Mockito.any())).thenReturn(getVideoResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/videos/1"))
                .andExpect(
                        MockMvcResultMatchers.status().isOk()
                ).andExpect(
                        MockMvcResultMatchers.content().json(response)
                );
    }
}
