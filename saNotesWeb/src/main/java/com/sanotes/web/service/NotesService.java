package com.sanotes.web.service;

import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.NotesVersionModel;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.security.UserPrincipal;

import java.util.List;

public interface NotesService {
    NotesModel saveNote(NotesModel note, UserPrincipal userPrincipal);

    NotesModel updateNote(NotesModel note, UserPrincipal userPrincipal);

    NotesModel getNote(Long id, UserPrincipal userPrincipal);

    List<NotesVersionModel> getNoteVersions(Long id, UserPrincipal userPrincipal);

    ApiResponse deleteNote(ByIdRequest byIdRequest, UserPrincipal userPrincipal);
}
