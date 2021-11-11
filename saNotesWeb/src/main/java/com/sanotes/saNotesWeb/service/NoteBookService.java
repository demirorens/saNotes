package com.sanotes.saNotesWeb.Service;

import com.sanotes.saNotesPostgres.Service.DAO.NoteBookRepository;
import com.sanotes.saNotesPostgres.Service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.Service.Model.NoteBookModel;
import com.sanotes.saNotesPostgres.Service.Model.NotesModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteBookService {

    @Autowired
    NoteBookRepository noteBookRepository;

    @Autowired
    NotesRepository notesRepository;

    public NoteBookModel saveNoteBook(NoteBookModel noteBookModel){

        return noteBookRepository.save(noteBookModel);
    }

    public Iterable<NoteBookModel> getNoteBooks(){

        return  noteBookRepository.findAll();
    }

    public NotesModel saveNotes(NotesModel notesModel){

        return notesRepository.save(notesModel);
    }

    public List<NotesModel> getNotes(){

        return (List<NotesModel>) notesRepository.findAll();
    }

}
