package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesPostgres.service.model.NotesModel;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.payload.NoteResponse;
import com.sanotes.saNotesWeb.security.CurrentUser;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/notes")
public class NotesController {

    @Autowired
    NotesService notesService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoteResponse> addNote(@Valid @RequestBody NotesModel note,
                                                @CurrentUser UserPrincipal userPrincipal){
        NotesModel newNote = notesService.saveNote(note,userPrincipal);
        NoteResponse noteResponse =modelMapper.map(newNote, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoteResponse> updateNote(@Valid @RequestBody NotesModel note,
                                                   @CurrentUser UserPrincipal userPrincipal){
        NotesModel newNote = notesService.updateNote(note,userPrincipal);
        NoteResponse noteResponse =modelMapper.map(newNote, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoteResponse> getNote(@Valid @RequestBody ByIdRequest byIdRequest,
                                                @CurrentUser UserPrincipal userPrincipal){
        NotesModel note = notesService.getNote(byIdRequest, userPrincipal);
        NoteResponse noteResponse =modelMapper.map(note, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.FOUND);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ApiResponse> deleteNote(@Valid @RequestBody ByIdRequest byIdRequest,
                                                @CurrentUser UserPrincipal userPrincipal){
        ApiResponse apiResponse = notesService.deleteNote(byIdRequest, userPrincipal);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
