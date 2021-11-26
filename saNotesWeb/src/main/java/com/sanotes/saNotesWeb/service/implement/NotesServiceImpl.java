package com.sanotes.saNotesWeb.service.implement;

import com.sanotes.saNotesMongo.DAO.NoteRepository;
import com.sanotes.saNotesMongo.model.NotModel;
import com.sanotes.saNotesPostgres.service.DAO.NoteBookRepository;
import com.sanotes.saNotesPostgres.service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.service.DAO.TagRepository;
import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.NotesModel;
import com.sanotes.saNotesPostgres.service.model.TagModel;
import com.sanotes.saNotesPostgres.service.model.user.User;
import com.sanotes.saNotesWeb.exception.ResourceNotFoundException;
import com.sanotes.saNotesWeb.exception.UnauthorizedException;
import com.sanotes.saNotesWeb.payload.ApiResponse;
import com.sanotes.saNotesWeb.payload.ByIdRequest;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NoteRepository  noteRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private NoteBookRepository noteBookRepository;

    @Autowired
    private TagRepository tagRepository;



    public NotesModel saveNote(NotesModel note, UserPrincipal userPrincipal){

        Optional<NoteBookModel>  noteBook = noteBookRepository.findById(note.getNotebook().getId());
        if(noteBook.isEmpty()) {
            note.setNotebook(noteBookRepository.save(note.getNotebook()));
        }else{
            note.setNotebook(noteBook.get());
            if(!noteBook.get().getUser().getId().equals(userPrincipal.getId())){
                throw  new UnauthorizedException("User don't have permission for this request");
            }
        }
        for (int i = 0; i < note.getTags().size(); i++) {
            Optional<TagModel>  tagModel = tagRepository.findByName(note.getTags().get(i).getName());
            if(tagModel.isEmpty()) {
                note.getTags().set(i,tagRepository.save(note.getTags().get(i)));
            }else{
                note.getTags().set(i,tagModel.get());
            }
        }
        NotModel notModel = new NotModel(note.getTopic(),note.getText());
        notModel = noteRepository.save(notModel);
        note.setNoteId(notModel.getId());
        NotesModel  newNote = notesRepository.save(note);
        newNote.setTopic(notModel.getTopic());
        newNote.setText(notModel.getText());
        return newNote;
    }

    public NotesModel updateNote(NotesModel note, UserPrincipal userPrincipal){
        NotesModel  oldNote = notesRepository.findById(note.getId())
                .orElseThrow(()->new ResourceNotFoundException("Note", "by id",note.getId().toString()));
        Optional<NoteBookModel>  noteBook = noteBookRepository.findById(note.getNotebook().getId());
        if(noteBook.isEmpty()) {
            throw new ResourceNotFoundException("Note", "by id",note.getId().toString());
        }else{
            note.setNotebook(noteBook.get());
            if(!noteBook.get().getUser().getId().equals(userPrincipal.getId())){
                throw  new UnauthorizedException("User don't have permission for this request");
            }
        }
        for (int i = 0; i < note.getTags().size(); i++) {
            Optional<TagModel>  tagModel = tagRepository.findByName(note.getTags().get(i).getName());
            User user = new User();
            user.setId(userPrincipal.getId());
            if(tagModel.isEmpty()) {
                note.getTags().get(i).setUser(user);
                note.getTags().set(i,tagRepository.save(note.getTags().get(i)));
            }else{
                note.getTags().set(i,tagModel.get());
            }
        }
        NotModel notModel = new NotModel(note.getTopic(),note.getText());
        notModel.setId(note.getNoteId());
        notModel = noteRepository.save(notModel);
        note.setNoteId(notModel.getId());
        NotesModel  newNote = notesRepository.save(note);
        newNote.setTopic(notModel.getTopic());
        newNote.setText(notModel.getText());
        return newNote;
    }

    public NotesModel getNote(ByIdRequest byIdRequest, UserPrincipal userPrincipal){
        NotesModel  note = notesRepository.findById(byIdRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Note", "by id",byIdRequest.getId().toString()));
        Optional<NoteBookModel>  noteBook = noteBookRepository.findById(note.getNotebook().getId());
        if(noteBook.isEmpty()) {
            throw new ResourceNotFoundException("Note", "by id",byIdRequest.getId().toString());
        }else{
            note.setNotebook(noteBook.get());
            if(!noteBook.get().getUser().getId().equals(userPrincipal.getId())){
                throw  new UnauthorizedException("User don't have permission for this request");
            }
        }
        NotModel notModel = noteRepository.findById(note.getNoteId())
                .orElseThrow(()->new ResourceNotFoundException("Note", "by id",byIdRequest.getId().toString()));
        note.setNoteId(notModel.getId());
        note.setTopic(notModel.getTopic());
        note.setText(notModel.getText());
        return  note;
    }


    public ApiResponse deleteNote(ByIdRequest byIdRequest, UserPrincipal userPrincipal) {
        NotesModel  note = notesRepository.findById(byIdRequest.getId())
                .orElseThrow(()->new ResourceNotFoundException("Note", "by id",byIdRequest.getId().toString()));
        Optional<NoteBookModel>  noteBook = noteBookRepository.findById(note.getNotebook().getId());
        if(noteBook.isEmpty()) {
            throw new ResourceNotFoundException("Note", "by id",byIdRequest.getId().toString());
        }else{
            if(!noteBook.get().getUser().getId().equals(userPrincipal.getId())){
                throw  new UnauthorizedException("User don't have permission for this request");
            }
        }
        notesRepository.delete(note);
        NotModel notModel = new NotModel(note.getTopic(),note.getText());
        notModel.setId(note.getNoteId());
        noteRepository.delete(notModel);
        return new ApiResponse(Boolean.TRUE,"You successfully delete note ");
    }
}
