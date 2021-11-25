package com.sanotes.saNotesWeb.service.implement;

import com.sanotes.saNotesPostgres.service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.service.DAO.TagRepository;
import com.sanotes.saNotesPostgres.service.DAO.UserRepository;
import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.NotesModel;
import com.sanotes.saNotesPostgres.service.model.TagModel;
import com.sanotes.saNotesPostgres.service.model.user.User;
import com.sanotes.saNotesWeb.exception.ResourceNotFoundException;
import com.sanotes.saNotesWeb.exception.UnauthorizedException;
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
    NotesRepository notesRepository;

    public TagModel saveTag(TagModel tagModel, UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(
                ()->new UnauthorizedException("User don't have permission for this request"));
        tagModel.setUser(user);
        return tagRepository.save(tagModel);
    }

    public List<NotesModel> getNotes(ByIdRequest byIdRequest, UserPrincipal userPrincipal){
        TagModel tag = tagRepository.findById(byIdRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Tag", "by id",byIdRequest.getId().toString()));
        if(!tag.getUser().getId().equals(userPrincipal.getId())){
            throw  new UnauthorizedException("User don't have permission for this request");
        }
        return (List<NotesModel>) notesRepository.findByTags_Id(tag.getId());
    }
}
