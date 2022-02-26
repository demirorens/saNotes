package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesCommons.model.NotesVersionModel;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.payload.NoteResponse;
import com.sanotes.saNotesWeb.security.CurrentUser;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.NotesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@Tag(name = "notes", description = "the Note API")
public class NotesController {

    @Autowired
    NotesService notesService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add note",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<NoteResponse> addNote(
            @Parameter(description = "Note parameters", required = true)
            @Valid @RequestBody NotesModel note,
            @CurrentUser UserPrincipal userPrincipal){
        NotesModel newNote = notesService.saveNote(note,userPrincipal);
        NoteResponse noteResponse =modelMapper.map(newNote, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update note",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<NoteResponse> updateNote(
            @Parameter(description = "Note parameters", required = true)
            @Valid @RequestBody NotesModel note,
            @CurrentUser UserPrincipal userPrincipal){
        NotesModel newNote = notesService.updateNote(note,userPrincipal);
        NoteResponse noteResponse =modelMapper.map(newNote, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get note",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<NoteResponse> getNote(
            @Parameter(description = "Note id", required = true)
            @RequestParam(value = "id") Long id,
            @CurrentUser UserPrincipal userPrincipal){
        NotesModel note = notesService.getNote(id, userPrincipal);
        NoteResponse noteResponse =modelMapper.map(note, NoteResponse.class);
        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @GetMapping("/versions")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get note versions",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<List<NoteResponse>> getNoteVersions(
            @Parameter(description = "Note id", required = true)
            @RequestParam(value = "id") Long id,
            @CurrentUser UserPrincipal userPrincipal){
        List<NotesVersionModel> notesVersions = notesService.getNoteVersions(id, userPrincipal);
        List<NoteResponse> noteResponses =modelMapper.map(notesVersions,  new TypeToken<List<NoteResponse>>() {}.getType());
        return new ResponseEntity<>(noteResponses, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete note",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notes"})
    public ResponseEntity<ApiResponse> deleteNote(
            @Parameter(description = "Note id", required = true)
            @Valid @RequestBody ByIdRequest byIdRequest,
            @CurrentUser UserPrincipal userPrincipal){
        ApiResponse apiResponse = notesService.deleteNote(byIdRequest, userPrincipal);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
