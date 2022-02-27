package com.sanotes.saNotesWeb.listeners;

import com.sanotes.saNotesCommons.model.NoteBookModel;
import com.sanotes.saNotesCommons.model.NotesModel;
import com.sanotes.saNotesCommons.model.user.User;
import com.sanotes.saNotesWeb.events.NewUserEvent;
import com.sanotes.saNotesWeb.security.UserPrincipal;
import com.sanotes.saNotesWeb.service.NoteBookService;
import com.sanotes.saNotesWeb.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class NewUserListener {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private NoteBookService noteBookService;

    @Autowired
    private NotesService notesService;

    @Async
    @EventListener
    public void listen(NewUserEvent event) {
        /*try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        User user = event.getUser();
        if (user.getId() > 0) {
            //Create sampla Notebook and note
            NoteBookModel noteBook = new NoteBookModel();
            noteBook.setName("Sample Notebook");
            noteBook.setDescription("Sample Notebook");
            UserPrincipal userPrincipal = UserPrincipal.create(user);
            noteBook = noteBookService.saveNoteBook(noteBook, userPrincipal);
            NotesModel notes = new NotesModel();
            notes.setNotebook(noteBook);
            notes.setTopic("Sample Note");
            notes.setText("Sample Note");
            notesService.saveNote(notes, userPrincipal);
        }
    }
}
