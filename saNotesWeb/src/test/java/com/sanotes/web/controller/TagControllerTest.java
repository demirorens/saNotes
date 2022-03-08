package com.sanotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.commons.model.user.Role;
import com.sanotes.commons.model.user.RoleName;
import com.sanotes.postgres.repository.RoleRepository;
import com.sanotes.web.payload.*;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TagControllerTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    RoleRepository roleRepository;

    TagResponse tagResponse = new TagResponse();
    ObjectMapper mapper;
    MockMvc mockMvc;

    private final MediaType MEDIA_TYPE_JSON_UTF8 =
            new MediaType("application", "json", StandardCharsets.UTF_8);

    private static final String username = "testuser" + Instant.now().getEpochSecond();
    private String accessToken;

    @BeforeAll
    void setUp() throws Exception {
        if (roleRepository.findByName(RoleName.ROLE_USER).isEmpty()) {
            roleRepository.save(new Role(RoleName.ROLE_USER));
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
    void addTag() throws Exception {
        MockHttpServletRequestBuilder request = post("/api/v1/tag")
                .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(new TagRequest("sample", "sample tag")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(greaterThan(0)))
                .andExpect(jsonPath("$.name").value("sample"))
                .andExpect(jsonPath("$.description").value("sample tag"))
                .andReturn().getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        tagResponse.setId(Long.valueOf(jsonObject.get("id") + ""));
    }

    @Test
    @Order(2)
    void updateTag() throws Exception {
        MockHttpServletRequestBuilder request = put("/api/v1/tag")
                .header("Authorization", "Bearer " + accessToken);
        tagResponse.setName("update");
        tagResponse.setDescription("update tag");
        request.content(mapper.writeValueAsString(tagResponse));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(tagResponse.getId()))
                .andExpect(jsonPath("$.name").value("update"))
                .andExpect(jsonPath("$.description").value("update tag"));
    }

    @Test
    @Order(3)
    void updateTagUnExist() throws Exception {
        MockHttpServletRequestBuilder request = put("/api/v1/tag")
                .header("Authorization", "Bearer " + accessToken);
        tagResponse.setName("update");
        tagResponse.setDescription("update tag");
        long temp = tagResponse.getId();
        tagResponse.setId(0l);
        request.content(mapper.writeValueAsString(tagResponse));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
        tagResponse.setId(temp);
    }

    @Test
    @Order(4)
    void getTagNotes() throws Exception {
        /*Inserting a notebook*/
        MockHttpServletRequestBuilder request = post("/api/v1/noteBook")
                .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(new NoteBookRequest("sample", "sample notebook")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        NoteBookRequest noteBook = new NoteBookRequest(Long.valueOf(jsonObject.get("id") + ""));

        /*Inserting a note to the notebook*/
        request = post("/api/v1/note")
                .header("Authorization", "Bearer " + accessToken);
        List<TagRequest> tags = new ArrayList<>();
        tags.add(new TagRequest(tagResponse.getId(), "sample", "sample tag"));
        request.content(mapper.writeValueAsString(
                new NoteRequest("sample note", "sample note text", noteBook, tags)));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful());

        request = get("/api/v1/tag/notes")
                .header("Authorization", "Bearer " + accessToken);
        request.param("id", String.valueOf(tagResponse.getId()));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id").value(greaterThan(0)))
                .andExpect(jsonPath("$.[0].noteId").exists())
                .andExpect(jsonPath("$.[0].topic").value("sample note"))
                .andExpect(jsonPath("$.[0].text").value("sample note text"));
    }

    @Test
    @Order(5)
    void getTagNotesUnExist() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/v1/tag/notes")
                .header("Authorization", "Bearer " + accessToken);
        request.param("id", String.valueOf(0));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Order(5)
    void deleteTag() throws Exception {
        MockHttpServletRequestBuilder request = delete("/api/v1/tag")
                .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(new ByIdRequest(tagResponse.getId())));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @Order(6)
    void deleteTagUnExist() throws Exception {
        MockHttpServletRequestBuilder request = delete("/api/v1/tag")
                .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(new ByIdRequest(0l)));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());
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