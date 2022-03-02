package com.sanotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.commons.model.NoteBookModel;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.web.exception.SAExceptionHandler;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.payload.NoteBookResponse;
import com.sanotes.web.payload.NoteResponse;
import com.sanotes.web.service.NoteBookService;
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
class NoteBookControllerTest {
    @Mock
    NoteBookService noteBookService;

    @InjectMocks
    NoteBookController controller;

    @Mock
    ModelMapper modelMapperM;

    ModelMapper modelMapper;
    NoteBookModel noteBook;
    List<NotesModel> notes;
    ObjectMapper mapper;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        noteBook = new NoteBookModel("sample", "sample notebook");
        noteBook.setId(1l);
        NotesModel note = new NotesModel(1l, "1", "topic", "text", noteBook);
        notes = new ArrayList<NotesModel>();
        notes.add(note);
        noteBook.setNotes(notes);
        mapper = new ObjectMapper();
        modelMapper = new ModelMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new SAExceptionHandler())
                .build();
    }

    @Test
    void addNoteBook() throws Exception {
        when(noteBookService.saveNoteBook(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(noteBook);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(noteBook, NoteBookResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = post("/api/noteBooks");
        request.content(mapper.writeValueAsString(new NoteBookModel("sample", "sample tag")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andReturn().getResponse();

        verify(noteBookService).saveNoteBook(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void updateNoteBook() throws Exception {
        when(noteBookService.updateNoteBook(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(noteBook);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(noteBook, NoteBookResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = put("/api/noteBooks");
        request.content(mapper.writeValueAsString(new NoteBookModel("sample", "sample tag")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andReturn().getResponse();

        verify(noteBookService).updateNoteBook(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void getNoteBookNotes() throws Exception {
        when(noteBookService.getNotes(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(notes);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(modelMapper.map(notes, NoteResponse[].class));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = get("/api/noteBooks/notes");
        request.param("id", "1");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[*].noteId").exists())
                .andExpect(jsonPath("$.[*].topic").exists())
                .andExpect(jsonPath("$.[*].text").exists())
                .andReturn().getResponse();

        verify(noteBookService).getNotes(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void deleteNoteBook() throws Exception {
        when(noteBookService.deleteNoteBook(ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(new ApiResponse(true, "success"));
        MediaType MEDIA_TYPE_JSON_UTF8 =
                new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = delete("/api/noteBooks");
        request.content(mapper.writeValueAsString(new ByIdRequest(1l)));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getResponse();

        verify(noteBookService).deleteNoteBook(ArgumentMatchers.any(), ArgumentMatchers.any());
    }
}