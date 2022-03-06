package com.sanotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.commons.model.user.Role;
import com.sanotes.commons.model.user.RoleName;
import com.sanotes.postgres.repository.RoleRepository;
import com.sanotes.web.payload.LoginRequest;
import com.sanotes.web.payload.SignUpRequest;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LoginControllerTest {

    @Autowired
    WebApplicationContext context;

    @Autowired
    RoleRepository roleRepository;

    ObjectMapper mapper;
    MockMvc mockMvc;

    private final MediaType MEDIA_TYPE_JSON_UTF8 =
            new MediaType("application", "json", StandardCharsets.UTF_8);
    private static final String username = "testuser" + Instant.now().getEpochSecond();
    private String accessToken;

    @BeforeAll
    void setUp() {
        if (roleRepository.findByName(RoleName.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(RoleName.ROLE_USER));
        }
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }


    @Test
    @Order(1)
    void signUp() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/v1/auth/signup");
        request.content(mapper.writeValueAsString(new SignUpRequest("firstName", "lastName", username, "password", username + "@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Order(2)
    void login() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/v1/auth/login");
        request.content(mapper.writeValueAsString(new LoginRequest(username, "password")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.accessToken").exists())
                .andReturn().getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        accessToken = jsonObject.get("accessToken") + "";
    }


    @AfterAll
    void cleanup() throws Exception {
        MockHttpServletRequestBuilder request =
                delete("/api/v1/user/{username}", username)
                        .header("Authorization", "Bearer " + accessToken);
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true));
    }

}
