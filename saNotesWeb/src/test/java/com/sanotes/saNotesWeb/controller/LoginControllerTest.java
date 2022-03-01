package com.sanotes.saNotesWeb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.exception.SAExceptionHandler;
import com.sanotes.saNotesWeb.payload.SignUpRequest;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LoginControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    LoginController controller;


    User user;
    ObjectMapper mapper;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        user = new User("firstName", "lastName", "username", "password", "email@gmail.com");
        user.setId(1l);
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new SAExceptionHandler())
                .build();
    }


    @Test
    public void signUp() throws Exception {
        when(userService.addUser(ArgumentMatchers.any())).thenReturn(user);
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = post("/api/auth/signup");
        request.content(mapper.writeValueAsString(new SignUpRequest("firstName", "lastName", "username", "password", "email@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andReturn().getResponse();

        verify(userService).addUser(ArgumentMatchers.any());
    }

}
