package com.sanotes.saNotesWeb.service.implement;

import com.sanotes.saNotesPostgres.service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.service.DAO.TagRepository;
import com.sanotes.saNotesPostgres.service.DAO.UserRepository;
import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesCommons.model.TagModel;
import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.exception.ResourceNotFoundException;
import com.sanotes.saNotesWeb.exception.UnauthorizedException;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository  tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesRepository notesRepository;

    public TagModel saveTag(TagModel tagModel, UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(
                ()->new UnauthorizedException("User don't have permission for this request"));
        tagModel.setUser(user);
        return tagRepository.save(tagModel);
    }

    public TagModel updateTag(TagModel tagModel, UserPrincipal userPrincipal){
        TagModel oldTagModel = tagRepository.findById(tagModel.getId())
                .orElseThrow(()->new ResourceNotFoundException("Tag","by id",tagModel.getId().toString()));
        if(!oldTagModel.getUser().getId().equals(userPrincipal.getId()))
                throw new UnauthorizedException("User don't have permission for this request");
        oldTagModel.setName(tagModel.getName());
        oldTagModel.setDescription(tagModel.getDescription());
        return tagRepository.save(oldTagModel);
    }

    public List<NotesModel> getNotes(Long id, UserPrincipal userPrincipal){
        TagModel tag = tagRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Tag", "by id",id.toString()));
        if(!tag.getUser().getId().equals(userPrincipal.getId())){
            throw  new UnauthorizedException("User don't have permission for this request");
        }
        List<NotesModel> notes= notesRepository.findByTags_Id(tag.getId());
        return notes;
    }

    public ApiResponse deleteTag(ByIdRequest byIdRequest, UserPrincipal userPrincipal) {
        TagModel tag = tagRepository.findById(byIdRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Tag", "by id",byIdRequest.getId().toString()));
        if(!tag.getUser().getId().equals(userPrincipal.getId())){
            throw  new UnauthorizedException("User don't have permission for this request");
        }
        tagRepository.delete(tag);
        return new ApiResponse(Boolean.TRUE,"You successfully delete tag ");
    }
}
