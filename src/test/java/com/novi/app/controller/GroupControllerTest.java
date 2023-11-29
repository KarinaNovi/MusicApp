package com.novi.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.app.service.testData.TestGroup;
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
public class GroupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSuccessfulGetAllGroups() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/groups/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetSpecificGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/groups/75"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetSpecificGroupGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/7/users"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/new")
                        .content(asJsonString(TestGroup.createSimpleGroup()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateSpecificGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/updateGroup/11")
                        .content(asJsonString(TestGroup.updateSimpleGroup(11)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testTerminateSpecificGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/groups/terminateGroup/125"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSpecificGroup() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/groups/deleteGroup/125"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetNewGroups() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/newGroups"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetActiveGroups() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/activeGroups"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetGroupsWithCurrentMusicStyle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/groups/groupsWithStyle/1"))
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
