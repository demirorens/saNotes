package com.sanotes.saNotesWeb.service.implement;

import com.sanotes.saNotesPostgres.service.DAO.NoteBookRepository;
import com.sanotes.saNotesPostgres.service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.service.DAO.UserRepository;
import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.NotesModel;
import com.sanotes.saNotesPostgres.service.model.user.User;
import com.sanotes.saNotesWeb.exception.ResourceNotFoundException;
import com.sanotes.saNotesWeb.exception.UnauthorizedException;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteBookServiceImpl implements NoteBookService {

    @Autowired
    private NoteBookRepository noteBookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotesRepository notesRepository;

    public NoteBookModel saveNoteBook(NoteBookModel noteBookModel, UserPrincipal userPrincipal){
        User user = userRepository.findById(userPrincipal.getId()).orElseThrow(
                ()->new UnauthorizedException("User don't have permission for this request"));
        noteBookModel.setUser(user);
        return noteBookRepository.save(noteBookModel);
    }

    public List<NotesModel> getNotes(ByIdRequest byIdRequest, UserPrincipal userPrincipal){
        NoteBookModel noteBookModel = noteBookRepository.findById(byIdRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Notebook", "by id",byIdRequest.getId().toString()));
        if(!noteBookModel.getUser().getId().equals(userPrincipal.getId())){
            throw  new UnauthorizedException("User don't have permission for this request");
        }
        return (List<NotesModel>) notesRepository.findByNotebook_Id(noteBookModel.getId());
    }

}
