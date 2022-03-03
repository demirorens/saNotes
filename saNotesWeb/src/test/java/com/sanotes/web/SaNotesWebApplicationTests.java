package com.sanotes.web;

import com.sanotes.web.controller.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SaNotesWebApplicationTests {
    @Autowired
    LoginController loginController;
    @Autowired
    NoteBookController noteBookController;
    @Autowired
    NotesController notesController;
    @Autowired
    TagController tagController;
    @Autowired
    UserController userController;

    @Test
    void contextLoads() {
        assertNotNull(loginController);
        assertNotNull(noteBookController);
        assertNotNull(notesController);
        assertNotNull(tagController);
        assertNotNull(userController);
    }

}
