package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesMongo.DAO.NoteRepository;
import com.sanotes.saNotesMongo.model.NotModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotesService {

    @Autowired
    NoteRepository  noteRepository;


    public NotModel saveNote(NotModel notModel){

        return noteRepository.save(notModel);
    }

    public List<NotModel> getNotes(){

        return noteRepository.findAll();
    }

    public Optional<NotModel> getNote(String id){
        return noteRepository.findById(id);
    }
}
