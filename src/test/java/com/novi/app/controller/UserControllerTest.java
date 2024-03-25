package com.novi.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.app.service.testData.TestUser;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @WithMockUser
    @Test
    void testSuccessfulGetAllUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/users/all"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSuccessfulGetSpecificUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/users/75"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSuccessfulGetSpecificUserGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/75/groups"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testCreateUser() throws Exception {
        mockMvc.perform(post("/users/new")
                        //TODO: can be formatted into request body in json format
                        .content("""
                                {
                                    "firstName": "Test",
                                    "lastName": "WithoutLogin",
                                    "middleName": null,
                                    "phoneNumber": "89803489886",
                                    "email": "1@mail.com",
                                    "birthday": "1940-12-09",
                                    "password": "12345Yes!"
                                }""")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    public void testUpdateSpecificUser() throws Exception {
        mockMvc.perform(post("/users/updateUser/126")
                        .content(asJsonString(TestUser.updateSimpleUser(126)))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(roles="ADMIN")
    public void testTerminateSpecificUser() throws Exception {
        mockMvc.perform(post("/users/terminateUser/125"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles="ADMIN")
    public void testDeleteSpecificUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/users/deleteUser/181"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSuccessfulGetNewUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/newUsers"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testSuccessfulGetActiveUsers() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/activeUsers"))
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
