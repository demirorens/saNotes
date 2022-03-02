package com.sanotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.commons.model.NoteBookModel;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.NotesVersionModel;
import com.sanotes.web.exception.SAExceptionHandler;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.payload.NoteResponse;
import com.sanotes.web.service.NotesService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NotesControllerTest {

    @Mock
    NotesService notesService;

    @InjectMocks
    NotesController controller;

    @Mock
    ModelMapper modelMapperM;

    ModelMapper modelMapper;
    NotesModel note;
    NotesVersionModel noteVersion;
    List<NotesVersionModel> noteVersions;
    ObjectMapper mapper;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        NoteBookModel noteBook = new NoteBookModel("sample", "sample notebook");
        noteBook.setId(1l);
        note = new NotesModel(1l, "1", "topic", "text", noteBook);

        noteVersion = new NotesVersionModel(note);
        noteVersion.setVersion_id(1l);

        noteVersions = new ArrayList<NotesVersionModel>();
        noteVersions.add(noteVersion);

        mapper = new ObjectMapper();
        modelMapper = new ModelMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new SAExceptionHandler())
                .build();
    }

    @Test
    void addNote() throws Exception {
        when(notesService.saveNote(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(note);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(note, NoteResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = post("/api/notes");
        request.content(mapper.writeValueAsString(note));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.noteId").exists())
                .andReturn().getResponse();

        verify(notesService).saveNote(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void updateNote() throws Exception {
        when(notesService.updateNote(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(note);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(note, NoteResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = put("/api/notes");
        request.content(mapper.writeValueAsString(note));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.noteId").exists())
                .andReturn().getResponse();

        verify(notesService).updateNote(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void getNote() throws Exception {
        when(notesService.getNote(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(note);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(note, NoteResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = get("/api/notes");
        request.param("id", "1");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.noteId").exists())
                .andReturn().getResponse();

        verify(notesService).getNote(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void getNoteVersions() throws Exception {
        when(notesService.getNoteVersions(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(noteVersions);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(noteVersions, NoteResponse[].class));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = get("/api/notes/versions");
        request.param("id", "1");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[*].id").value(1))
                .andExpect(jsonPath("$.[*].noteId").exists())
                .andExpect(jsonPath("$.[*].topic").exists())
                .andExpect(jsonPath("$.[*].text").exists())
                .andReturn().getResponse();

        verify(notesService).getNoteVersions(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void deleteNote() throws Exception {
        when(notesService.deleteNote(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ApiResponse(true, "success"));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = delete("/api/notes");
        request.content(mapper.writeValueAsString(new ByIdRequest(1l)));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getResponse();

        verify(notesService).deleteNote(ArgumentMatchers.any(), ArgumentMatchers.any());
    }
}