package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesCommons.model.NotesVersionModel;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.security.UserPrincipal;

import java.util.List;

public interface NotesService {
    NotesModel saveNote(NotesModel note, UserPrincipal userPrincipal);

    NotesModel updateNote(NotesModel note, UserPrincipal userPrincipal);

    NotesModel getNote(Long id, UserPrincipal userPrincipal);

    List<NotesVersionModel> getNoteVersions(Long id, UserPrincipal userPrincipal);

    ApiResponse deleteNote(ByIdRequest byIdRequest, UserPrincipal userPrincipal);
}
