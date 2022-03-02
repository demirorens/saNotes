package com.sanotes.web.listeners;

import com.sanotes.commons.model.NoteBookModel;
import com.sanotes.commons.model.NotesModel;
import com.sanotes.commons.model.user.User;
import com.sanotes.web.events.NewUserEvent;
import com.sanotes.web.security.UserPrincipal;
import com.sanotes.web.service.NoteBookService;
import com.sanotes.web.service.NotesService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

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
