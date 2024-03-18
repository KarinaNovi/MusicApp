package com.novi.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.app.service.testData.TestMusicInstrument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MusicInstrumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    void testSuccessfulGetAllMusicInstruments() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/musicInstruments/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSuccessfulGetSpecificMusicInstrument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/musicInstruments/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSuccessfulGetSpecificMusicInstrumentGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/musicInstruments/1/groups"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testCreateMusicInstrument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/musicInstruments/new")
                        .content(asJsonString(TestMusicInstrument.createSimpleMusicInstrument()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testUpdateSpecificMusicInstrument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/musicInstruments/updateMusicInstrument/4")
                        .content(asJsonString(TestMusicInstrument.updateSimpleMusicInstrument()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testDeleteSpecificInstrument() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/musicInstruments/deleteMusicInstrument/4"))
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
