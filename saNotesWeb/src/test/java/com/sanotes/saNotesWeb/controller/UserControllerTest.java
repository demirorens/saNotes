package com.sanotes.saNotesWeb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.exception.SAExceptionHandler;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.BooleanResponse;
import com.sanotes.saNotesWeb.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;


    User user;
    BooleanResponse booleanResponse;
    ObjectMapper mapper;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        user = new User("firstName", "lastName", "username", "password", "email@gmail.com");
        user.setId(1l);
        booleanResponse = new BooleanResponse(Boolean.TRUE);
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new SAExceptionHandler())
                .build();
    }


    @Test
    public void isUsernameAvailable() throws Exception {
        when(userService.checkUsernameAvailability(ArgumentMatchers.any())).thenReturn(booleanResponse);
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = get("/api/users/isUsernameAvailable");
        request.param("username", "username");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result").value(true))
                .andReturn().getResponse();

        verify(userService).checkUsernameAvailability(ArgumentMatchers.any());
    }

    @Test
    public void isEmailAvailable() throws Exception {
        when(userService.checkEmailAvailability(ArgumentMatchers.any())).thenReturn(booleanResponse);
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = get("/api/users/isEmailAvailable");
        request.param("email", "email@email.com");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result").value(true))
                .andReturn().getResponse();

        verify(userService).checkEmailAvailability(ArgumentMatchers.any());
    }

    @Test
    public void addUser() throws Exception {
        when(userService.addUser(ArgumentMatchers.any())).thenReturn(user);
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = post("/api/users");
        request.content(mapper.writeValueAsString(new User("firstName", "lastName", "username", "password", "email@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstname").exists())
                .andExpect(jsonPath("$.lastname").exists())
                .andReturn().getResponse();

        verify(userService).addUser(ArgumentMatchers.any());
    }

    @Test
    public void updateUser() throws Exception {
        when(userService.updateUser(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(user);
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = put("/api/users/{username}", "username");
        request.content(mapper.writeValueAsString(new User("firstName", "lastName", "username", "password", "email@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse();

        verify(userService).updateUser(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    public void deleteUser() throws Exception {
        when(userService.deleteUser(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(new ApiResponse(true,"success" ));
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = delete("/api/users/{username}", "username");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getResponse();

        verify(userService).deleteUser(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

}
