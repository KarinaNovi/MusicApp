package com.novi.app.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.app.service.testData.TestUser;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSuccessfulGetAllUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetSpecificUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/75"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetSpecificUserGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/75/groups"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/new")
                        .content(asJsonString(TestUser.createSimpleUser()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateSpecificUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/updateUser/126")
                        .content(asJsonString(TestUser.updateSimpleUser(126)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testTerminateSpecificUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users/terminateUser/125"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteSpecificUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/deleteUser/125"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetNewUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/newUsers"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetActiveUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/activeUsers"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testSuccessfulGetUsersWithCurrentMusicStyle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/usersWithStyle/1"))
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
