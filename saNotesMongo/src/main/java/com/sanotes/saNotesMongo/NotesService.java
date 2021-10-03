package com.sanotes.saNotesMongo;

import com.sanotes.saNotesMongo.DAO.NoteRepository;
import com.sanotes.saNotesMongo.Model.NoteModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
