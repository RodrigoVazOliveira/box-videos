package dev.rvz.adapters.inbound.api.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.boxvideos.adapters.commons.mapper.video.*;
import dev.rvz.boxvideos.adapters.commons.requests.videos.CreateVideoRequest;
import dev.rvz.boxvideos.adapters.commons.requests.videos.UpdateCompleteVideoRequest;
import dev.rvz.boxvideos.adapters.commons.requests.videos.UpdatePartialRequest;
import dev.rvz.boxvideos.adapters.commons.responses.categories.CategoryResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.CreateVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetAllVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.GetVideoResponse;
import dev.rvz.boxvideos.adapters.commons.responses.videos.UpdateCompleteVideoResponse;
import dev.rvz.boxvideos.adapters.exceptions.ExceptionHandlerDefaultRest;
import dev.rvz.boxvideos.adapters.inbound.api.video.*;
import dev.rvz.boxvideos.core.domain.category.model.Category;
import dev.rvz.boxvideos.core.domain.commons.exception.ResponseException;
import dev.rvz.boxvideos.core.domain.video.exception.VideoNotFoundException;
import dev.rvz.boxvideos.core.domain.video.model.Video;
import dev.rvz.boxvideos.port.in.video.*;
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
@ContextConfiguration(classes = {ExceptionHandlerDefaultRest.class, CreateVideoController.class, GetAllVideoRestController.class, GetVideoByIdRestController.class, UpdateCompleteVideoRestController.class, UpdatePartialVideoRestController.class, DeleteVideoByIdRestController.class})
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

    @MockBean
    private UpdateCompleteVideoPortIn updateCompleteVideoPortIn;

    @MockBean
    private UpdateCompleteVideoRequestToVideoMapper updateCompleteVideoRequestToVideoMapper;

    @MockBean
    private VideoToUpdateCompleteVideoResponseMapper videoToUpdateCompleteVideoResponseMapper;

    @MockBean
    private UpdatePartialVideoPortIn updatePartialVideoPortIn;

    @MockBean
    private UpdatePartialRequestToVideoMapper updatePartialRequestToVideoMapper;

    @MockBean
    private DeleteVideoByIdPortIn deleteVideoByIdPortIn;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_create_video_with_success() throws Exception {
        CreateVideoRequest createVideoRequest = new CreateVideoRequest("Video Test1", "Descrição teste", "http://wwww.google.com.br", 1L);

        CreateVideoResponse createVideoResponse = new CreateVideoResponse(1L, "Video Test1", "Descrição teste", "http://wwww.google.com.br", new CategoryResponse(1L, "LIVRE", "WHITE"));

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(createVideoRequest);
        String responseJson = objectMapper.writeValueAsString(createVideoResponse);

        Video video = new Video(1L, "Video Test1", "Descrição teste", "http://wwww.google.com.br", new Category(1L, "", ""));
        Mockito.when(createVideoPortIn.execute(Mockito.any())).thenReturn(video);
        Mockito.when(videoToCreateVideoResponseMapper.to(Mockito.any())).thenReturn(createVideoResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/videos").contentType(MediaType.APPLICATION_JSON_VALUE).content(jsonRequest)).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().json(responseJson)).andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/videos/1"));
    }


    @Test
    void test_get_all_videos() throws Exception {
        Iterable<Video> videos = Arrays.asList(new Video(1L, "titulo 1", "Teste descricao 1", "http://www.filme1.com.br", new Category(1L, "", "")), new Video(2L, "titulo 2", "Teste descricao 2", "http://www.filme2.com.br", new Category(1L, "", "")), new Video(1L, "titulo 3", "Teste descricao 3", "http://www.filme2.com.br", new Category(1L, "", "")));

        Iterable<GetAllVideoResponse> allVideos = Arrays.asList(
                new GetAllVideoResponse(1L, "titulo 1", "Teste descricao 1", "http://www.filme1.com.br",
                        new CategoryResponse(1L, "LIVRE", "WHITE")),
                new GetAllVideoResponse(2L, "titulo 2", "Teste descricao 2", "http://www.filme2.com.br",
                        new CategoryResponse(1L, "LIVRE", "WHITE")),
                new GetAllVideoResponse(1L, "titulo 3", "Teste descricao 3", "http://www.filme2.com.br",
                        new CategoryResponse(1L, "LIVRE", "WHITE")));

        ObjectMapper objectMapper = new ObjectMapper();
        String expectResponse = objectMapper.writeValueAsString(allVideos);
        Mockito.when(getAllVideosPortIn.execute()).thenReturn(videos);
        Mockito.when(iterableVideoToIterableGetAllVideoResponseMapper.to(Mockito.any())).thenReturn(allVideos);

        mockMvc.perform(MockMvcRequestBuilders.get("/videos")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(expectResponse));
    }

    @Test
    void test_get_video_by_id_with_success_handred_two_ok() throws Exception {
        Video video = new Video(1L, "", "", "", new Category(1L, "", ""));
        GetVideoResponse getVideoResponse = new GetVideoResponse(1L, "", "", "");

        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(getVideoResponse);

        Mockito.when(getVideoByIdPortIn.execute(Mockito.any())).thenReturn(video);
        Mockito.when(videoToGetVideoResponseMapper.to(Mockito.any())).thenReturn(getVideoResponse);

        mockMvc.perform(MockMvcRequestBuilders.get("/videos/1")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_get_video_by_id_with_not_found_resource() throws Exception {
        ResponseException responseException = new ResponseException(404, "Não existe vídeo com id 1");
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(responseException);

        Mockito.when(videoToGetVideoResponseMapper.to(Mockito.any())).thenThrow(new VideoNotFoundException("Não existe vídeo com id 1"));

        mockMvc.perform(MockMvcRequestBuilders.get("/videos/1")).andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_update_complete_not_exists_videos_response_handred_two_one() throws Exception {
        UpdateCompleteVideoRequest updateCompleteVideoRequest = new UpdateCompleteVideoRequest("Filme 1", "Fileme descriacao", "http://localhost", 1L);
        UpdateCompleteVideoResponse updateCompleteVideoResponse = new UpdateCompleteVideoResponse(1L, "Filme 1", "Fileme descriacao", "http://localhost");
        Video video = new Video(1L, "Filme 1", "Fileme descriacao", "http://localhost", new Category(1L, "", ""));

        ObjectMapper objectMapper = new ObjectMapper();
        String updateRequest = objectMapper.writeValueAsString(updateCompleteVideoRequest);
        String response = objectMapper.writeValueAsString(updateCompleteVideoResponse);


        Mockito.when(updateCompleteVideoPortIn.execute(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updateCompleteVideoRequestToVideoMapper.to(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updateCompleteVideoPortIn.videoExists(Mockito.any())).thenReturn(false);
        Mockito.when(videoToUpdateCompleteVideoResponseMapper.to(Mockito.any())).thenReturn(updateCompleteVideoResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos/1").contentType(MediaType.APPLICATION_JSON).content(updateRequest)).andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/videos/1")).andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_update_complete_exists_videos_response_handred_two_four() throws Exception {
        UpdateCompleteVideoRequest updateCompleteVideoRequest = new UpdateCompleteVideoRequest("Filme 1", "Fileme descriacao", "http://localhost", 1L);
        Video video = new Video(1L, "Filme 1", "Fileme descriacao", "http://localhost", new Category(1L, "", ""));
        UpdateCompleteVideoResponse updateCompleteVideoResponse = new UpdateCompleteVideoResponse(video.id(), video.title(), video.description(), video.url());
        ObjectMapper objectMapper = new ObjectMapper();
        String updateRequest = objectMapper.writeValueAsString(updateCompleteVideoRequest);
        String response = objectMapper.writeValueAsString(updateCompleteVideoResponse);

        Mockito.when(updateCompleteVideoPortIn.execute(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updateCompleteVideoRequestToVideoMapper.to(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updateCompleteVideoPortIn.videoExists(Mockito.any())).thenReturn(true);
        Mockito.when(videoToUpdateCompleteVideoResponseMapper.to(Mockito.any())).thenReturn(updateCompleteVideoResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos/1").contentType(MediaType.APPLICATION_JSON).content(updateRequest)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_update_partial_with_sucess() throws Exception {
        Video video = new Video(1L, "Title1", "Descricao1", "http://localhost", new Category(1L, "", ""));
        UpdatePartialRequest updatePartialRequest = new UpdatePartialRequest("Title1", "Descricao1", "http://localhost", 1L);
        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(updatePartialRequest);
        Mockito.when(updatePartialRequestToVideoMapper.to(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updatePartialVideoPortIn.updateVideoAlreadyExists(Mockito.any())).thenReturn(video);

        mockMvc.perform(MockMvcRequestBuilders.patch("/videos/1").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(MockMvcResultMatchers.status().isNoContent()).andExpect(MockMvcResultMatchers.header().string("Content-Location", "http://localhost/videos/1"));
    }

    @Test
    void test_update_partial_response_video_not_found() throws Exception {
        ResponseException responseException = new ResponseException(404, "Não existe vídeo com id 1");
        Video video = new Video(1L, "Title1", "Descricao1", "http://localhost", new Category(1L, "", ""));
        UpdatePartialRequest updatePartialRequest = new UpdatePartialRequest("Title1", "Descricao1", "http://localhost", 1L);
        ObjectMapper objectMapper = new ObjectMapper();
        String request = objectMapper.writeValueAsString(updatePartialRequest);
        String response = objectMapper.writeValueAsString(responseException);
        Mockito.when(updatePartialRequestToVideoMapper.to(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updatePartialVideoPortIn.updateVideoAlreadyExists(Mockito.any())).thenThrow(new VideoNotFoundException("Não existe vídeo com id 1"));

        mockMvc.perform(MockMvcRequestBuilders.patch("/videos/1").contentType(MediaType.APPLICATION_JSON).content(request)).andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_delete_video_by_id_with_success() throws Exception {
        Mockito.doNothing().when(deleteVideoByIdPortIn).run(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/videos/1")).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void test_delete_video_by_id_with_not_found() throws Exception {
        ResponseException responseException = new ResponseException(404, "Não existe vídeo com id 1");
        ObjectMapper objectMapper = new ObjectMapper();
        String response = objectMapper.writeValueAsString(responseException);
        Mockito.doThrow(new VideoNotFoundException("Não existe vídeo com id 1")).when(deleteVideoByIdPortIn).run(Mockito.any());
        mockMvc.perform(MockMvcRequestBuilders.delete("/videos/1")).andExpect(MockMvcResultMatchers.status().isNotFound()).andExpect(MockMvcResultMatchers.content().json(response));
    }
}
