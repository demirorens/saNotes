package com.sanotes.web.controller;

import com.sanotes.commons.model.NoteBookModel;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.web.payload.*;
import com.sanotes.web.security.CurrentUser;
import com.sanotes.web.security.UserPrincipal;
import com.sanotes.web.service.NoteBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/api/v1/noteBook")
@Tag(name = "notebook", description = "the Notebook API")
public class NoteBookController {

    @Autowired
    private NoteBookService noteBookService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Add notebook",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notebook"})
    public ResponseEntity<NoteBookResponse> addNoteBook(
            @Parameter(description = "Notebook parameters", required = true)
            @Valid @RequestBody NoteBookRequest noteBook,
            @CurrentUser UserPrincipal userPrincipal) {
        NoteBookModel noteBookModel = new NoteBookModel(noteBook.getName(), noteBook.getDescription());
        noteBookModel = noteBookService.saveNoteBook(noteBookModel, userPrincipal);
        NoteBookResponse noteBookResponse = modelMapper.map(noteBookModel, NoteBookResponse.class);
        return new ResponseEntity<>(noteBookResponse, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Update notebook",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notebook"})
    public ResponseEntity<NoteBookResponse> updateNoteBook(
            @Parameter(description = "Notebook parameters", required = true)
            @Valid @RequestBody NoteBookRequest noteBook,
            @CurrentUser UserPrincipal userPrincipal) {
        NoteBookModel noteBookModel = new NoteBookModel(noteBook.getName(), noteBook.getDescription());
        noteBookModel.setId(noteBook.getId());
        noteBookModel = noteBookService.updateNoteBook(noteBookModel, userPrincipal);
        NoteBookResponse noteBookResponse = modelMapper.map(noteBookModel, NoteBookResponse.class);
        return new ResponseEntity<>(noteBookResponse, HttpStatus.CREATED);
    }

    @GetMapping("/notes")
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Get notebook notes",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notebook"})
    public ResponseEntity<List<NoteResponse>> getNoteBookNotes(
            @Parameter(description = "Notebook id", required = true)
            @RequestParam(value = "id") Long id,
            @CurrentUser UserPrincipal userPrincipal) {
        List<NotesModel> notes = noteBookService.getNotes(id, userPrincipal);
        List<NoteResponse> noteResponses = Arrays.asList(modelMapper.map(notes, NoteResponse[].class));
        return new ResponseEntity<>(noteResponses, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    @Operation(summary = "Delete notebook",
            security = @SecurityRequirement(name = "bearerAuth"),
            tags = {"notebook"})
    public ResponseEntity<ApiResponse> deleteNoteBook(
            @Parameter(description = "Notebook id", required = true)
            @Valid @RequestBody ByIdRequest byIdRequest,
            @CurrentUser UserPrincipal userPrincipal) {
        ApiResponse apiResponse = noteBookService.deleteNoteBook(byIdRequest, userPrincipal);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }


}
