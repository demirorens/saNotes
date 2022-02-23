package com.sanotes.saNotesWeb.service.helper;

import com.sanotes.saNotesMongo.DAO.NoteRepository;
import com.sanotes.saNotesCommons.model.NotModel;
import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesWeb.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteHelper {

    @Autowired
    private NoteRepository  noteRepository;

    public List<NotesModel> fillNotes(List<NotesModel> notes) {

        for (int i =0; i<notes.size();i++){
            NotesModel note = notes.get(i);
            NotModel notModel = noteRepository.findById(note.getNoteId())
                    .orElseThrow(()-> new ResourceNotFoundException("Note", "by id",note.getNoteId()));
            note.setTopic(notModel.getTopic());
            note.setText(notModel.getText());
            notes.set(i,note);
        }
        return notes;
    }
}
