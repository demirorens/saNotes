package com.sanotes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanotes.commons.model.NoteBookModel;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.TagModel;
import com.sanotes.web.exception.SAExceptionHandler;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.payload.NoteResponse;
import com.sanotes.web.payload.TagResponse;
import com.sanotes.web.service.TagService;
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
class TagControllerTest {

    @Mock
    TagService tagService;

    @InjectMocks
    TagController controller;

    @Mock
    ModelMapper modelMapperM;

    ModelMapper modelMapper;
    TagModel tag;
    List<NotesModel> notes;
    ObjectMapper mapper;
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        tag = new TagModel("sample", "sample tag");
        tag.setId(1l);
        NoteBookModel noteBookModel = new NoteBookModel("notebook", "description");
        noteBookModel.setId(1l);
        NotesModel note = new NotesModel(1l, "1", "topic", "text", noteBookModel);
        notes = new ArrayList<NotesModel>();
        notes.add(note);
        tag.setNotes(notes);
        mapper = new ObjectMapper();
        modelMapper = new ModelMapper();
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(new SAExceptionHandler())
                .build();
    }

    @Test
    void addTag() throws Exception {
        when(tagService.saveTag(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(tag);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(modelMapper.map(tag, TagResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = post("/api/v1/tags");
        request.content(mapper.writeValueAsString(new TagModel("sample", "sample tag")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andReturn().getResponse();

        verify(tagService).saveTag(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void updateTag() throws Exception {
        when(tagService.updateTag(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(tag);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(modelMapper.map(tag, TagResponse.class));
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = put("/api/v1/tags");
        request.content(mapper.writeValueAsString(new TagModel("sample", "sample tag")));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andReturn().getResponse();

        verify(tagService).updateTag(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void getTagNotes() throws Exception {
        when(tagService.getNotes(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(notes);
        when(modelMapperM.map(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(modelMapper.map(notes, NoteResponse[].class));
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = get("/api/v1/tags/notes");
        request.param("id", "1");
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.[*].noteId").exists())
                .andExpect(jsonPath("$.[*].topic").exists())
                .andExpect(jsonPath("$.[*].text").exists())
                .andReturn().getResponse();

        verify(tagService).getNotes(ArgumentMatchers.any(), ArgumentMatchers.any());
    }

    @Test
    void deleteTag() throws Exception {
        when(tagService.deleteTag(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(new ApiResponse(true, "success"));
        MediaType MEDIA_TYPE_JSON_UTF8 = new MediaType("application", "json", java.nio.charset.Charset.forName("UTF-8"));
        MockHttpServletRequestBuilder request = delete("/api/v1/tags");
        request.content(mapper.writeValueAsString(new ByIdRequest(1l)));
        request.accept(MEDIA_TYPE_JSON_UTF8);
        request.contentType(MEDIA_TYPE_JSON_UTF8);
        MockHttpServletResponse response = mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getResponse();

        verify(tagService).deleteTag(ArgumentMatchers.any(), ArgumentMatchers.any());
    }
}