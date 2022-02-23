package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesCommons.model.NoteBookModel;
import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.security.UserPrincipal;

import java.util.List;

public interface NoteBookService {

    NoteBookModel saveNoteBook(NoteBookModel noteBookModel, UserPrincipal userPrincipal);

    NoteBookModel updateNoteBook(NoteBookModel noteBookModel, UserPrincipal userPrincipal);

    List<NotesModel> getNotes(ByIdRequest byIdRequest,UserPrincipal userPrincipal);

    ApiResponse deleteNoteBook(ByIdRequest byIdRequest, UserPrincipal userPrincipal);
}
