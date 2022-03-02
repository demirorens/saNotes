package com.sanotes.web.service.implement;

import com.sanotes.postgres.repository.NotesRepository;
import com.sanotes.postgres.repository.TagRepository;
import com.sanotes.postgres.repository.UserRepository;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.TagModel;
import com.sanotes.commons.model.user.User;
import com.sanotes.web.exception.ResourceNotFoundException;
import com.sanotes.web.exception.UnauthorizedException;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.security.UserPrincipal;
import com.sanotes.web.service.TagService;
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
