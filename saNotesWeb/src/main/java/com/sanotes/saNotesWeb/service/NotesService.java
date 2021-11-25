package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesPostgres.service.model.NotesModel;

public interface NotesService {
    NotesModel saveNote(NotesModel note);

}
