package com.sanotes.web.service;

import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.TagModel;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.security.UserPrincipal;

import java.util.List;

public interface TagService {

    TagModel saveTag(TagModel tagModel, UserPrincipal userPrincipal);

    TagModel updateTag(TagModel tagModel, UserPrincipal userPrincipal);

    List<NotesModel> getNotes(Long id, UserPrincipal userPrincipal);

    ApiResponse deleteTag(ByIdRequest byIdRequest, UserPrincipal userPrincipal);
}
