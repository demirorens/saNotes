package com.sanotes.saNotesWeb.service.implement;

import com.sanotes.saNotesPostgres.service.DAO.NoteBookRepository;
import com.sanotes.saNotesPostgres.service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.service.DAO.UserRepository;
import com.sanotes.saNotesCommons.model.NoteBookModel;
import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.exception.ResourceNotFoundException;
import com.sanotes.saNotesWeb.exception.UnauthorizedException;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.NoteBookService;
import com.sanotes.saNotesWeb.service.helper.NoteHelper;
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

    @Autowired
    private NoteHelper noteHelper;

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

    public List<NotesModel> getNotes(ByIdRequest byIdRequest, UserPrincipal userPrincipal){
        NoteBookModel noteBookModel = noteBookRepository.findById(byIdRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Notebook", "by id",byIdRequest.getId().toString()));
        if(!noteBookModel.getUser().getId().equals(userPrincipal.getId())){
            throw  new UnauthorizedException("User don't have permission for this request");
        }
        List<NotesModel> notes= notesRepository.findByNotebook_Id(noteBookModel.getId());
        return noteHelper.fillNotes(notes);
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
