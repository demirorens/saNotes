package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.NotesModel;
import com.sanotes.saNotesPostgres.service.model.TagModel;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.payload.NoteBookResponse;
import com.sanotes.saNotesWeb.payload.NoteResponse;
import com.sanotes.saNotesWeb.payload.TagResponse;
import com.sanotes.saNotesWeb.security.CurrentUser;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.NoteBookService;
import com.sanotes.saNotesWeb.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TagResponse> addTag(@Valid @RequestBody TagModel tag,
                                                   @CurrentUser UserPrincipal userPrincipal){
        TagModel newTag = tagService.saveTag(tag,userPrincipal);
        TagResponse tagResponse =modelMapper.map(newTag, TagResponse.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.CREATED);
    }

    @GetMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<NoteResponse>> getTagNotes(@Valid @RequestBody ByIdRequest byIdRequest,
                                                          @CurrentUser UserPrincipal userPrincipal){
        List<NotesModel> notes = tagService.getNotes(byIdRequest,userPrincipal);
        List<NoteResponse> noteResponses = Arrays.asList(modelMapper.map(notes, NoteResponse[].class));
        return new ResponseEntity<>(noteResponses, HttpStatus.OK);
    }
}
