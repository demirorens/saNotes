package com.sanotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.commons.model.user.User;
import com.sanotes.web.exception.SAExceptionHandler;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.BooleanResponse;
import com.sanotes.web.payload.UserRequest;
import com.sanotes.web.payload.UserResponse;
import com.sanotes.web.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController controller;
    @Mock
    ModelMapper modelMapperM;

    ModelMapper modelMapper;
    User user;
    BooleanResponse booleanResponse;
    ObjectMapper mapper;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        user = new User("firstName", "lastName",
                "username", "password",
                "email@gmail.com");
        user.setId(1l);
        booleanResponse = new BooleanResponse(Boolean.TRUE);
        modelMapper = new ModelMapper();
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new SAExceptionHandler())
                .build();
    }


    @Test
    void isUsernameAvailable() throws Exception {
        when(userService.checkUsernameAvailability(ArgumentMatchers.any())).thenReturn(booleanResponse);
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = get("/api/v1/users/isUsernameAvailable");
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
    void isEmailAvailable() throws Exception {
        when(userService.checkEmailAvailability(ArgumentMatchers.any())).thenReturn(booleanResponse);
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = get("/api/v1/users/isEmailAvailable");
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
    void addUser() throws Exception {
        when(userService.addUser(ArgumentMatchers.any())).thenReturn(user);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(user, UserResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = post("/api/v1/users");
        request.content(mapper.writeValueAsString(
                new UserRequest("firstName", "lastName",
                        "username", "password",
                        "email@gmail.com")));
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
    void updateUser() throws Exception {
        when(userService.updateUser(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(user);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(user, UserResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = put("/api/v1/users/{username}", "username");
        request.content(mapper.writeValueAsString(
                new UserRequest("firstName", "lastName",
                        "username", "password",
                        "email@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse();

        verify(userService).updateUser(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void deleteUser() throws Exception {
        when(userService.deleteUser(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ApiResponse(true, "success"));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = delete("/api/v1/users/{username}", "username");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getResponse();

        verify(userService).deleteUser(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

}
