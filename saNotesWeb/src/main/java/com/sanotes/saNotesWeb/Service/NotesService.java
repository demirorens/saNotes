package com.sanotes.saNotesWeb.Service;

import com.sanotes.saNotesMongo.DAO.NoteRepository;
import com.sanotes.saNotesMongo.Model.NoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    NoteRepository  noteRepository;


    public NoteModel saveNote(NoteModel noteModel){

        return noteRepository.save(noteModel);
    }

    public List<NoteModel> getNotes(){

        return noteRepository.findAll();
    }

    public Optional<NoteModel> getNote(String id){
        return noteRepository.findById(id);
    }
}
