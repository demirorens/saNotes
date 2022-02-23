package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.security.UserPrincipal;

public interface NotesService {
    NotesModel saveNote(NotesModel note, UserPrincipal userPrincipal);

    NotesModel updateNote(NotesModel note, UserPrincipal userPrincipal);

    NotesModel getNote(ByIdRequest byIdRequest, UserPrincipal userPrincipal);

    ApiResponse deleteNote(ByIdRequest byIdRequest, UserPrincipal userPrincipal);
}
