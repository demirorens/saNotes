package com.sanotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.commons.model.user.Role;
import com.sanotes.commons.model.user.RoleName;
import com.sanotes.postgres.repository.NotesVersionRepository;
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

import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class NotesControllerTest {

    @Autowired
    WebApplicationContext context;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    NotesVersionRepository notesVersionRepository;

    NoteRequest noteRequest;
    NoteBookRequest noteBook;
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

        /* Add a notebook*/
        request = post("/api/v1/noteBook")
                .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(new NoteBookRequest("sample", "sample notebook")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andReturn().getResponse();
        jsonObject = new JSONObject(response.getContentAsString());
        noteBook = new NoteBookRequest(Long.valueOf(jsonObject.get("id") + ""));

        noteRequest = new NoteRequest(Long.valueOf(jsonObject.get("id") + ""), "sample", "sample note");
    }

    @Test
    @Order(1)
    void addNote() throws Exception {
        MockHttpServletRequestBuilder request =
                post("/api/v1/note")
                        .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(noteRequest));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(greaterThan(0)))
                .andExpect(jsonPath("$.noteId").exists())
                .andExpect(jsonPath("$.topic").value("sample"))
                .andExpect(jsonPath("$.text").value("sample note"))
                .andReturn().getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        noteRequest.setId(Long.valueOf(jsonObject.get("id") + ""));
        noteRequest.setNoteId(jsonObject.get("noteId") + "");
    }

    @Test
    @Order(2)
    void updateNote() throws Exception {
        MockHttpServletRequestBuilder request =
                put("/api/v1/note")
                        .header("Authorization", "Bearer " + accessToken);
        noteRequest.setTopic("update");
        noteRequest.setText("update note");
        request.content(mapper.writeValueAsString(noteRequest));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(noteRequest.getId()))
                .andExpect(jsonPath("$.noteId").exists())
                .andExpect(jsonPath("$.topic").value("update"))
                .andExpect(jsonPath("$.text").value("update note"))
                .andReturn().getResponse();
        JSONObject jsonObject = new JSONObject(response.getContentAsString());
        noteRequest.setNoteId(jsonObject.get("noteId") + "");
    }

    @Test
    @Order(3)
    void getNote() throws Exception {
        MockHttpServletRequestBuilder request =
                get("/api/v1/note")
                        .header("Authorization", "Bearer " + accessToken);
        request.param("id", noteRequest.getId() + "");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(noteRequest.getId()))
                .andExpect(jsonPath("$.noteId").value(noteRequest.getNoteId()))
                .andExpect(jsonPath("$.topic").value("update"))
                .andExpect(jsonPath("$.text").value("update note"));
    }

    @Test
    @Order(4)
    void getNoteVersions() throws Exception {
        MockHttpServletRequestBuilder request =
                get("/api/v1/note/versions")
                        .header("Authorization", "Bearer " + accessToken);
        request.param("id", noteRequest.getId() + "");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[0].id").value(greaterThan(0)))
                .andExpect(jsonPath("$.[0].noteId").exists())
                .andExpect(jsonPath("$.[0].topic").value("sample"))
                .andExpect(jsonPath("$.[0].text").value("sample note"));
    }

    @Test
    @Order(5)
    void deleteNote() throws Exception {
        MockHttpServletRequestBuilder request =
                delete("/api/v1/note")
                        .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(new ByIdRequest(noteRequest.getId())));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true));
    }

    @AfterAll
    void cleanup() throws Exception {
        notesVersionRepository.deleteByIdEquals(noteRequest.getId());
        MockHttpServletRequestBuilder request = delete("/api/v1/noteBook")
                .header("Authorization", "Bearer " + accessToken);
        request.content(mapper.writeValueAsString(new ByIdRequest(noteBook.getId())));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true));

        request = delete("/api/v1/user/{username}", username)
                .header("Authorization", "Bearer " + accessToken);
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true));
    }
}