package com.novi.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.app.service.testData.TestMusicStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicStyleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSuccessfulGetAllMusicStyles() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/musicStyles/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetSpecificMusicStyle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/musicStyles/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetSpecificMusicStyleGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/musicStyles/1/groups"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateMusicStyle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/musicStyles/new")
                        .content(asJsonString(TestMusicStyle.createSimpleMusicStyle()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateSpecificMusicStyle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/musicStyles/updateMusicStyle/6")
                        .content(asJsonString(TestMusicStyle.updateSimpleMusicStyle()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSpecificUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/musicStyles/deleteMusicStyle/125"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
