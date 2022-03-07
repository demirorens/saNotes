package com.sanotes.web.service.implement;

import com.sanotes.commons.model.*;
import com.sanotes.commons.model.user.User;
import com.sanotes.mongo.repository.NoteRepository;
import com.sanotes.postgres.repository.NoteBookRepository;
import com.sanotes.postgres.repository.NotesRepository;
import com.sanotes.postgres.repository.NotesVersionRepository;
import com.sanotes.postgres.repository.TagRepository;
import com.sanotes.web.exception.ResourceNotFoundException;
import com.sanotes.web.exception.UnauthorizedException;
import com.sanotes.web.payload.ApiResponse;
import com.sanotes.web.payload.ByIdRequest;
import com.sanotes.web.security.UserPrincipal;
import com.sanotes.web.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesServiceImpl implements NotesService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private NotesRepository notesRepository;

    @Autowired
    private NotesVersionRepository notesVersionRepository;

    @Autowired
    private NoteBookRepository noteBookRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String USER_DONT_HAVE_PERMISSION = "User don't have permission for this request";


    public NotesModel saveNote(NotesModel note, UserPrincipal userPrincipal) {

        Optional<NoteBookModel> noteBook = noteBookRepository.findById(note.getNotebook().getId());
        if (noteBook.isEmpty()) {
            note.setNotebook(noteBookRepository.save(note.getNotebook()));
        } else {
            note.setNotebook(noteBook.get());
            if (!noteBook.get().getUser().getId().equals(userPrincipal.getId())) {
                throw new UnauthorizedException(USER_DONT_HAVE_PERMISSION);
            }
        }
        for (int i = 0; i < (note.getTags() != null ? note.getTags().size() : 0); i++) {
            Optional<TagModel> tagModel = tagRepository.findById(note.getTags().get(i).getId());
            if (tagModel.isEmpty()) {
                note.getTags().set(i, tagRepository.save(note.getTags().get(i)));
            } else {
                note.getTags().set(i, tagModel.get());
            }
        }
        NotModel notModel = new NotModel(note.getTopic(), note.getText());
        notModel = noteRepository.save(notModel);
        note.setNoteId(notModel.getId());
        NotesModel newNote = notesRepository.save(note);
        newNote.setTopic(notModel.getTopic());
        newNote.setText(notModel.getText());
        return newNote;
    }

    public NotesModel updateNote(NotesModel note, UserPrincipal userPrincipal) {
        NotesModel oldNote = notesRepository.findById(note.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", note.getId().toString()));
        Optional<NoteBookModel> noteBook = noteBookRepository.findById(oldNote.getNotebook().getId());
        if (noteBook.isEmpty()) {
            throw new ResourceNotFoundException("Note", "by id", note.getId().toString());
        } else {
            note.setNotebook(noteBook.get());
            if (!noteBook.get().getUser().getId().equals(userPrincipal.getId())) {
                throw new UnauthorizedException(USER_DONT_HAVE_PERMISSION);
            }
        }
        for (int i = 0; i < (note.getTags() != null ? note.getTags().size() : 0); i++) {
            Optional<TagModel> tagModel = tagRepository.findById(note.getTags().get(i).getId());
            User user = new User();
            user.setId(userPrincipal.getId());
            if (tagModel.isEmpty()) {
                note.getTags().get(i).setUser(user);
                note.getTags().set(i, tagRepository.save(note.getTags().get(i)));
            } else {
                note.getTags().set(i, tagModel.get());
            }
        }
        NotesVersionModel notesVersion = modelMapper.map(oldNote, NotesVersionModel.class);
        NotModel notModel = new NotModel(note.getTopic(), note.getText());
        notModel = noteRepository.save(notModel);
        note.setNoteId(notModel.getId());
        NotesModel newNote = notesRepository.save(note);
        newNote.setTopic(notModel.getTopic());
        newNote.setText(notModel.getText());
        /******Save old version begin ******/
        notesVersionRepository.save(notesVersion);
        /******Save old version end ******/
        return newNote;
    }

    public NotesModel getNote(Long id, UserPrincipal userPrincipal) {
        NotesModel note = notesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", id.toString()));
        Optional<NoteBookModel> noteBook = noteBookRepository.findById(note.getNotebook().getId());
        if (noteBook.isEmpty()) {
            throw new ResourceNotFoundException("Note", "by id", id.toString());
        } else {
            note.setNotebook(noteBook.get());
            if (!noteBook.get().getUser().getId().equals(userPrincipal.getId())) {
                throw new UnauthorizedException(USER_DONT_HAVE_PERMISSION);
            }
        }
        NotModel notModel = noteRepository.findById(note.getNoteId())
                .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", id.toString()));
        note.setNoteId(notModel.getId());
        note.setTopic(notModel.getTopic());
        note.setText(notModel.getText());
        return note;
    }

    public List<NotesVersionModel> getNoteVersions(Long id, UserPrincipal userPrincipal) {
        List<NotesVersionModel> noteversions = notesVersionRepository.getVersionsByNotesId(id);
        if (!noteversions.isEmpty()) {
            NotesVersionModel notesVersion = noteversions.get(0);
            Optional<NoteBookModel> noteBook = noteBookRepository.findById(notesVersion.getNotebook().getId());
            if (noteBook.isEmpty()) {
                throw new ResourceNotFoundException("Note", "by id", id.toString());
            } else {
                notesVersion.setNotebook(noteBook.get());
                if (!noteBook.get().getUser().getId().equals(userPrincipal.getId())) {
                    throw new UnauthorizedException(USER_DONT_HAVE_PERMISSION);
                }
            }
        }
        return noteversions;
    }


    public ApiResponse deleteNote(ByIdRequest byIdRequest, UserPrincipal userPrincipal) {
        NotesModel note = notesRepository.findById(byIdRequest.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Note", "by id", byIdRequest.getId().toString()));
        Optional<NoteBookModel> noteBook = noteBookRepository.findById(note.getNotebook().getId());
        if (noteBook.isEmpty()) {
            throw new ResourceNotFoundException("Note", "by id", byIdRequest.getId().toString());
        } else {
            if (!noteBook.get().getUser().getId().equals(userPrincipal.getId())) {
                throw new UnauthorizedException(USER_DONT_HAVE_PERMISSION);
            }
        }
        notesRepository.delete(note);
        NotModel notModel = new NotModel(note.getTopic(), note.getText());
        notModel.setId(note.getNoteId());
        noteRepository.delete(notModel);
        return new ApiResponse(Boolean.TRUE, "You successfully delete note ");
    }
}
