package com.sanotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.commons.model.user.Role;
import com.sanotes.commons.model.user.RoleName;
import com.sanotes.postgres.repository.RoleRepository;
import com.sanotes.web.payload.LoginRequest;
import com.sanotes.web.payload.SignUpRequest;
import com.sanotes.web.payload.UserRequest;
import com.sanotes.web.service.UserService;
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

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    UserRequest userRequest = new UserRequest();
    ObjectMapper mapper;
    MockMvc mockMvc;

    private final MediaType MEDIA_TYPE_JSON_UTF8 =
            new MediaType("application", "json", StandardCharsets.UTF_8);

    private static final String username = "testuser" + Instant.now().getEpochSecond();
    private static final String usernameTest = "testuser2" + Instant.now().getEpochSecond();
    private String accessToken;

    @BeforeAll
    void setUp() throws Exception {
        if (roleRepository.findByName(RoleName.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(RoleName.ROLE_USER));
        }
        if (roleRepository.findByName(RoleName.ROLE_ADMIN).isEmpty()) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }
        mapper = new ObjectMapper();
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        /* Signing up a test user*/
        MockHttpServletRequestBuilder request = post("/api/v1/auth/signup");
        request.content(mapper.writeValueAsString(new SignUpRequest("firstName", "lastName", username, "password", username + "@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful());
        /* Give admin role to user*/
        userService.giveAdmin(username);


        /* Login and get token for test user*/
        request = post("/api/v1/auth/login");
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


    @Test
    @Order(1)
    void isUsernameAvailable() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/v1/user/isUsernameAvailable");
        request.param("username", "impossibleusername");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(2)
    void isEmailAvailable() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/v1/user/isEmailAvailable");
        request.param("email", "impossibleusername@email.com");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.result").exists());
    }

    @Test
    @Order(3)
    void addUserWithExistingUserName() throws Exception {
        MockHttpServletRequestBuilder request =
                post("/api/v1/user")
                        .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(
                new UserRequest("firstName", "lastName",
                        username, "password",
                        usernameTest + "@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(4)
    void addUserWithExistingMail() throws Exception {
        MockHttpServletRequestBuilder request =
                post("/api/v1/user")
                        .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(
                new UserRequest("firstName", "lastName",
                        usernameTest, "password",
                        username + "@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(5)
    void addUser() throws Exception {
        MockHttpServletRequestBuilder request =
                post("/api/v1/user")
                        .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(
                new UserRequest("firstName", "lastName",
                        usernameTest, "password",
                        usernameTest + "@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(greaterThan(0)))
                .andExpect(jsonPath("$.firstname").value("firstName"))
                .andExpect(jsonPath("$.lastname").value("lastName"))
                .andReturn().getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        userRequest.setId(Long.valueOf(jsonObject.get("id") + ""));
    }


    @Test
    @Order(6)
    void updateUser() throws Exception {
        MockHttpServletRequestBuilder request =
                put("/api/v1/user/{username}", usernameTest)
                        .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(
                new UserRequest("firstNameNew", "lastNameNew",
                        usernameTest, "passwordNew",
                        usernameTest + "@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(userRequest.getId()))
                .andExpect(jsonPath("$.firstname").value("firstNameNew"))
                .andExpect(jsonPath("$.lastname").value("lastNameNew"));
    }

    @Test
    @Order(7)
    void updateUserUnExist() throws Exception {
        MockHttpServletRequestBuilder request =
                put("/api/v1/user/{username}", "unExistedUser")
                        .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(
                new UserRequest("firstNameNew", "lastNameNew",
                        usernameTest, "passwordNew",
                        usernameTest + "@gmail.com")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }


    @Test
    @Order(8)
    void getUserItems() throws Exception {
        MockHttpServletRequestBuilder request =
                get("/api/v1/user//{username}/getUserItems", username)
                        .header("Authorization", "Bearer " + accessToken);
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Order(9)
    void getUserItemsUnExist() throws Exception {
        MockHttpServletRequestBuilder request =
                get("/api/v1/user//{username}/getUserItems", "unExistedUser")
                        .header("Authorization", "Bearer " + accessToken);
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(10)
    void deleteUser() throws Exception {
        MockHttpServletRequestBuilder request =
                delete("/api/v1/user/{username}", usernameTest)
                        .header("Authorization", "Bearer " + accessToken);
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Order(11)
    void deleteUserUnExist() throws Exception {
        MockHttpServletRequestBuilder request =
                delete("/api/v1/user/{username}", "unExistedUser")
                        .header("Authorization", "Bearer " + accessToken);
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }

    @AfterAll
    void cleanup() throws Exception {
        userService.removeAdmin(username);
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
