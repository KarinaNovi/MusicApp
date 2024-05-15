package com.novi.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.app.model.request.CreateUserRequest;
import com.novi.app.model.request.ModifyUserRequest;
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
    @WithMockUser(roles="LEADER")
    void testSuccessfulGetSpecificUserGroups() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/users/75/groups"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void testCreateUser() throws Exception {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setFirstName("Test");
        createUserRequest.setLastName("MockUser");
        createUserRequest.setPhoneNumber("89803489886");
        createUserRequest.setEmail("1@mail.com");
        createUserRequest.setBirthday("1997-09-21");
        createUserRequest.setPassword("Test1234!");
        mockMvc.perform(post("/users/new")
                        .content(asJsonString(createUserRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles="USER_126")
    public void testUpdateSpecificUser() throws Exception {
        ModifyUserRequest modifyUserRequest = new ModifyUserRequest();
        modifyUserRequest.setUserLogin("updated_login");
        mockMvc.perform(post("/users/updateUser/126")
                        .content(asJsonString(modifyUserRequest))
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
