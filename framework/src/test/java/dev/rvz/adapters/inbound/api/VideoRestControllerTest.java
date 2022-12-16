package rvz.dev.adapters.inbound.api;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;

@WebMvcTest
@AutoConfigureMockMvc
class VideoRestControllerTest {

    @MockBean
    private CreateVideoPortIn createVideoPortIn;

    @Autowired
    private MockMvc mockMvc;

    VideoRestControllerTest() {
    }

    @Test
    void test_create_video_with_success() {
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
        Video video = new Video(1L, "Video Test1", "Descrição teste", "http://wwww.google.com.br");
        Mockito.when(createVideoPortIn.execute(Mockito.any())).thenReturn(video);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/videos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createVideoRequest)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        ).andExpect(
                MockMvcResultMatchers.content().contentType()
        );
    }
}
