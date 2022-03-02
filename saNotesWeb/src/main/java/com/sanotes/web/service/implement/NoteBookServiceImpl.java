package com.sanotes.web.service.implement;

import com.sanotes.postgres.repository.NoteBookRepository;
import com.sanotes.postgres.repository.NotesRepository;
import com.sanotes.postgres.repository.UserRepository;
import com.sanotes.commons.model.NoteBookModel;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.user.User;
import com.sanotes.web.exception.ResourceNotFoundException;
import com.sanotes.web.exception.UnauthorizedException;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.security.UserPrincipal;
import com.sanotes.web.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public NoteBookModel updateNoteBook(NoteBookModel noteBookModel, UserPrincipal userPrincipal){
        NoteBookModel oldNoteBookModel = noteBookRepository.findById(noteBookModel.getId())
                .orElseThrow(()->new ResourceNotFoundException("NoteBook","by id", noteBookModel.getId().toString()));
        if(!oldNoteBookModel.getUser().getId().equals(userPrincipal.getId()))
                throw new UnauthorizedException("User don't have permission for this request");
        oldNoteBookModel.setName(noteBookModel.getName());
        oldNoteBookModel.setDescription(noteBookModel.getDescription());
        return noteBookRepository.save(oldNoteBookModel);
    }

    public List<NotesModel> getNotes(Long id, UserPrincipal userPrincipal){
        NoteBookModel noteBookModel = noteBookRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Notebook", "by id",id.toString()));
        if(!noteBookModel.getUser().getId().equals(userPrincipal.getId())){
            throw  new UnauthorizedException("User don't have permission for this request");
        }
        List<NotesModel> notes= notesRepository.findByNotebook_Id(noteBookModel.getId());
        return notes;
    }


    public ApiResponse deleteNoteBook(ByIdRequest byIdRequest, UserPrincipal userPrincipal) {
        Optional<NoteBookModel> noteBook = noteBookRepository.findById(byIdRequest.getId());
        if(noteBook.isEmpty()) {
            throw new ResourceNotFoundException("Note", "by id",byIdRequest.getId().toString());
        }else{
            if(!noteBook.get().getUser().getId().equals(userPrincipal.getId())){
                throw  new UnauthorizedException("User don't have permission for this request");
            }
        }
        noteBookRepository.delete(noteBook.get());
        return new ApiResponse(Boolean.TRUE,"You successfully delete notebook ");
    }

}
