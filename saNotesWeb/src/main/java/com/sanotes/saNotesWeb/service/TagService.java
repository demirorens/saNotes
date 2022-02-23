package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesCommons.model.TagModel;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.security.UserPrincipal;

import java.util.List;

public interface TagService {

    TagModel saveTag(TagModel tagModel, UserPrincipal userPrincipal);

    TagModel updateTag(TagModel tagModel, UserPrincipal userPrincipal);

    List<NotesModel> getNotes(ByIdRequest byIdRequest, UserPrincipal userPrincipal);

    ApiResponse deleteTag(ByIdRequest byIdRequest, UserPrincipal userPrincipal);
}
