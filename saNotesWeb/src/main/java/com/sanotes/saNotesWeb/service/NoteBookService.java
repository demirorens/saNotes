package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesPostgres.service.DAO.NoteBookRepository;
import com.sanotes.saNotesPostgres.service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.NotesModel;
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
