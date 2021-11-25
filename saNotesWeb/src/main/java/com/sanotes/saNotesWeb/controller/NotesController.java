package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.NotesModel;
import com.sanotes.saNotesWeb.payload.NoteBookResponse;
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
    public ResponseEntity<NoteResponse> addNote(@Valid @RequestBody NotesModel note){
        NotesModel newNote = notesService.saveNote(note);
        NoteResponse noteResponse =modelMapper.map(note, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }




}
