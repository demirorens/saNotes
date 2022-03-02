package com.sanotes.web.service;

import com.sanotes.commons.model.NoteBookModel;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.security.UserPrincipal;

import java.util.List;

public interface NoteBookService {

    NoteBookModel saveNoteBook(NoteBookModel noteBookModel, UserPrincipal userPrincipal);

    NoteBookModel updateNoteBook(NoteBookModel noteBookModel, UserPrincipal userPrincipal);

    List<NotesModel> getNotes(Long id,UserPrincipal userPrincipal);

    ApiResponse deleteNoteBook(ByIdRequest byIdRequest, UserPrincipal userPrincipal);
}
