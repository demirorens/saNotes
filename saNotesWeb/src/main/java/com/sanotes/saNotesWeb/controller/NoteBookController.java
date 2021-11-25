package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.NotesModel;
import com.sanotes.saNotesPostgres.service.model.user.User;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.payload.NoteBookResponse;
import com.sanotes.saNotesWeb.payload.NoteResponse;
import com.sanotes.saNotesWeb.security.CurrentUser;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.NoteBookService;
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
@RequestMapping("/api/noteBooks")
public class NoteBookController {

    @Autowired
    private NoteBookService noteBookService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<NoteBookResponse> addNoteBook(@Valid @RequestBody NoteBookModel noteBook,
                                                        @CurrentUser UserPrincipal userPrincipal){
        NoteBookModel newNoteBook = noteBookService.saveNoteBook(noteBook,userPrincipal);
        NoteBookResponse noteBookResponse =modelMapper.map(newNoteBook, NoteBookResponse.class);
        return new ResponseEntity<>(noteBookResponse, HttpStatus.CREATED);
    }

    @GetMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<NoteResponse>> getNoteBookNotes(@Valid @RequestBody ByIdRequest byIdRequest,
                                                          @CurrentUser UserPrincipal userPrincipal){
        List<NotesModel> notes = noteBookService.getNotes(byIdRequest,userPrincipal);
        List<NoteResponse> noteResponses = Arrays.asList(modelMapper.map(notes, NoteResponse[].class));
        return new ResponseEntity<>(noteResponses, HttpStatus.OK);
    }




}
