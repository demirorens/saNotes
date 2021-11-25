package com.sanotes.saNotesWeb.service.implement;

import com.sanotes.saNotesMongo.DAO.NoteRepository;
import com.sanotes.saNotesMongo.model.NotModel;
import com.sanotes.saNotesPostgres.service.DAO.NoteBookRepository;
import com.sanotes.saNotesPostgres.service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.service.DAO.TagRepository;
import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.NotesModel;
import com.sanotes.saNotesPostgres.service.model.TagModel;
import com.sanotes.saNotesWeb.service.NotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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



    public NotesModel saveNote(NotesModel note){

        Optional<NoteBookModel>  noteBook = noteBookRepository.findById(note.getNotebook().getId());
        if(noteBook.isEmpty()) {
            note.setNotebook(noteBookRepository.save(note.getNotebook()));
        }else{
            note.setNotebook(noteBook.get());
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
        return notesRepository.save(note);
    }
}
