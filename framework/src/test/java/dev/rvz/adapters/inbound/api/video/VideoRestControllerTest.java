package dev.rvz.adapters.inbound.api.video;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.rvz.adapters.inbound.api.TokenEnum;
import dev.rvz.adapters.inbound.api.commons.MockSpringSecurity;
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
@ContextConfiguration(classes = {
        ExceptionHandlerDefaultRest.class,
        CreateVideoController.class,
        GetAllVideoRestController.class,
        GetVideoByIdRestController.class,
        UpdateCompleteVideoRestController.class,
        UpdatePartialVideoRestController.class,
        DeleteVideoByIdRestController.class})
class VideoRestControllerTest extends MockSpringSecurity {

    @MockBean
    CreateVideoPortIn createVideoPortIn;

    @MockBean
    CreateVideoRequestToVideoMapper createVideoRequestToVideoMapper;

    @MockBean
    VideoToCreateVideoResponseMapper videoToCreateVideoResponseMapper;

    @MockBean
    GetAllVideosPortIn getAllVideosPortIn;

    @MockBean
    IterableVideoToIterableGetAllVideoResponseMapper iterableVideoToIterableGetAllVideoResponseMapper;

    @MockBean
    GetVideoByIdPortIn getVideoByIdPortIn;

    @MockBean
    VideoToGetVideoResponseMapper videoToGetVideoResponseMapper;

    @MockBean
    UpdateCompleteVideoPortIn updateCompleteVideoPortIn;

    @MockBean
    UpdateCompleteVideoRequestToVideoMapper updateCompleteVideoRequestToVideoMapper;

    @MockBean
    VideoToUpdateCompleteVideoResponseMapper videoToUpdateCompleteVideoResponseMapper;

    @MockBean
    UpdatePartialVideoPortIn updatePartialVideoPortIn;

    @MockBean
    UpdatePartialRequestToVideoMapper updatePartialRequestToVideoMapper;

    @MockBean
    DeleteVideoByIdPortIn deleteVideoByIdPortIn;

    @Autowired
    MockMvc mockMvc;

    @Test
    void test_create_video_with_success() throws Exception {
        final CreateVideoRequest createVideoRequest = new CreateVideoRequest("Video Test1", "Descrição teste", "http://wwww.google.com.br", 1L);

        final CreateVideoResponse createVideoResponse = new CreateVideoResponse(1L, "Video Test1", "Descrição teste", "http://wwww.google.com.br", new CategoryResponse(1L, "LIVRE", "WHITE"));

        final ObjectMapper objectMapper = new ObjectMapper();
        final String jsonRequest = objectMapper.writeValueAsString(createVideoRequest);
        final String responseJson = objectMapper.writeValueAsString(createVideoResponse);

        Video video = new Video(1L, "Video Test1", "Descrição teste", "http://wwww.google.com.br", new Category(1L, "", ""));
        Mockito.when(createVideoRequestToVideoMapper.to(createVideoRequest)).thenReturn(video);
        Mockito.when(createVideoPortIn.execute(Mockito.any())).thenReturn(video);
        Mockito.when(videoToCreateVideoResponseMapper.to(Mockito.any())).thenReturn(createVideoResponse);
        mockMvc.perform(MockMvcRequestBuilders.post("/videos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andExpect(MockMvcResultMatchers.content().json(responseJson)).andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/videos/1"));
    }


    @Test
    void test_get_all_videos() throws Exception {
        final Iterable<Video> videos = Arrays.asList(new Video(1L, "titulo 1", "Teste descricao 1", "http://www.filme1.com.br", new Category(1L, "", "")), new Video(2L, "titulo 2", "Teste descricao 2", "http://www.filme2.com.br", new Category(1L, "", "")), new Video(1L, "titulo 3", "Teste descricao 3", "http://www.filme2.com.br", new Category(1L, "", "")));

        final Iterable<GetAllVideoResponse> allVideos = Arrays.asList(
                new GetAllVideoResponse(1L, "titulo 1", "Teste descricao 1", "http://www.filme1.com.br",
                        new CategoryResponse(1L, "LIVRE", "WHITE")),
                new GetAllVideoResponse(2L, "titulo 2", "Teste descricao 2", "http://www.filme2.com.br",
                        new CategoryResponse(1L, "LIVRE", "WHITE")),
                new GetAllVideoResponse(1L, "titulo 3", "Teste descricao 3", "http://www.filme2.com.br",
                        new CategoryResponse(1L, "LIVRE", "WHITE")));

        final ObjectMapper objectMapper = new ObjectMapper();
        final String expectResponse = objectMapper.writeValueAsString(allVideos);
        Mockito.when(getAllVideosPortIn.execute()).thenReturn(videos);
        Mockito.when(iterableVideoToIterableGetAllVideoResponseMapper.to(Mockito.any())).thenReturn(allVideos);

        mockMvc.perform(MockMvcRequestBuilders.get("/videos")
                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(expectResponse));
    }

    @Test
    void test_get_video_by_id_with_success_handred_two_ok() throws Exception {
        final Video video = new Video(1L, "", "", "", new Category(1L, "", ""));
        final GetVideoResponse getVideoResponse = new GetVideoResponse(1L, "", "", "");

        final ObjectMapper objectMapper = new ObjectMapper();
        final String response = objectMapper.writeValueAsString(getVideoResponse);

        Mockito.when(getVideoByIdPortIn.execute(Mockito.any())).thenReturn(video);
        Mockito.when(videoToGetVideoResponseMapper.to(Mockito.any())).thenReturn(getVideoResponse);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/videos/1")
                        .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_get_video_by_id_with_not_found_resource() throws Exception {
        final ResponseException responseException = new ResponseException(404, "Não existe vídeo com id 1");
        final ObjectMapper objectMapper = new ObjectMapper();
        final String response = objectMapper.writeValueAsString(responseException);

        Mockito.when(videoToGetVideoResponseMapper.to(Mockito.any())).thenThrow(new VideoNotFoundException("Não existe vídeo com id 1"));

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/videos/1")
                                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_update_complete_not_exists_videos_response_handred_two_one() throws Exception {
        final UpdateCompleteVideoRequest updateCompleteVideoRequest = new UpdateCompleteVideoRequest("Filme 1", "Fileme descriacao", "http://localhost", 1L);
        final UpdateCompleteVideoResponse updateCompleteVideoResponse = new UpdateCompleteVideoResponse(1L, "Filme 1", "Fileme descriacao", "http://localhost");
        final Video video = new Video(1L, "Filme 1", "Fileme descriacao", "http://localhost", new Category(1L, "", ""));

        final ObjectMapper objectMapper = new ObjectMapper();
        final String updateRequest = objectMapper.writeValueAsString(updateCompleteVideoRequest);
        final String response = objectMapper.writeValueAsString(updateCompleteVideoResponse);


        Mockito.when(updateCompleteVideoPortIn.execute(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updateCompleteVideoRequestToVideoMapper.to(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updateCompleteVideoPortIn.videoExists(Mockito.any())).thenReturn(false);
        Mockito.when(videoToUpdateCompleteVideoResponseMapper.to(Mockito.any())).thenReturn(updateCompleteVideoResponse);

        mockMvc.perform(MockMvcRequestBuilders.put("/videos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                        .content(updateRequest)).
                andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost/videos/1"))
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_update_complete_exists_videos_response_handred_two_four() throws Exception {
        final UpdateCompleteVideoRequest updateCompleteVideoRequest = new UpdateCompleteVideoRequest("Filme 1", "Fileme descriacao", "http://localhost", 1L);
        final Video video = new Video(1L, "Filme 1", "Fileme descriacao", "http://localhost", new Category(1L, "", ""));
        final UpdateCompleteVideoResponse updateCompleteVideoResponse = new UpdateCompleteVideoResponse(video.id(), video.title(), video.description(), video.url());
        final ObjectMapper objectMapper = new ObjectMapper();
        final String updateRequest = objectMapper.writeValueAsString(updateCompleteVideoRequest);
        final String response = objectMapper.writeValueAsString(updateCompleteVideoResponse);

        Mockito.when(updateCompleteVideoPortIn.execute(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updateCompleteVideoRequestToVideoMapper.to(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updateCompleteVideoPortIn.videoExists(Mockito.any())).thenReturn(true);
        Mockito.when(videoToUpdateCompleteVideoResponseMapper.to(Mockito.any())).thenReturn(updateCompleteVideoResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/videos/1")
                                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateRequest))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_update_partial_with_sucess() throws Exception {
        final Video video = new Video(1L, "Title1", "Descricao1", "http://localhost", new Category(1L, "", ""));
        final UpdatePartialRequest updatePartialRequest = new UpdatePartialRequest("Title1", "Descricao1", "http://localhost", 1L);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String request = objectMapper.writeValueAsString(updatePartialRequest);
        Mockito.when(updatePartialRequestToVideoMapper.to(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updatePartialVideoPortIn.updateVideoAlreadyExists(Mockito.any())).thenReturn(video);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/videos/1")
                                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andExpect(MockMvcResultMatchers.header().string("Content-Location", "http://localhost/videos/1"));
    }

    @Test
    void test_update_partial_response_video_not_found() throws Exception {
        final ResponseException responseException = new ResponseException(404, "Não existe vídeo com id 1");
        final Video video = new Video(1L, "Title1", "Descricao1", "http://localhost", new Category(1L, "", ""));
        final UpdatePartialRequest updatePartialRequest = new UpdatePartialRequest("Title1", "Descricao1", "http://localhost", 1L);
        final ObjectMapper objectMapper = new ObjectMapper();
        final String request = objectMapper.writeValueAsString(updatePartialRequest);
        final String response = objectMapper.writeValueAsString(responseException);
        Mockito.when(updatePartialRequestToVideoMapper.to(Mockito.any(), Mockito.any())).thenReturn(video);
        Mockito.when(updatePartialVideoPortIn.updateVideoAlreadyExists(Mockito.any())).thenThrow(new VideoNotFoundException("Não existe vídeo com id 1"));

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/videos/1")
                                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(request))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }

    @Test
    void test_delete_video_by_id_with_success() throws Exception {
        Mockito.doNothing().when(deleteVideoByIdPortIn).run(Mockito.any());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/videos/1")
                                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void test_delete_video_by_id_with_not_found() throws Exception {
        final ResponseException responseException = new ResponseException(404, "Não existe vídeo com id 1");
        final ObjectMapper objectMapper = new ObjectMapper();
        final String response = objectMapper.writeValueAsString(responseException);
        Mockito.doThrow(new VideoNotFoundException("Não existe vídeo com id 1")).when(deleteVideoByIdPortIn).run(Mockito.any());
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/videos/1")
                                .header(TokenEnum.AUTORIZATION.getName(), TokenEnum.AUTORIZATION.getValue()))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().json(response));
    }
}
