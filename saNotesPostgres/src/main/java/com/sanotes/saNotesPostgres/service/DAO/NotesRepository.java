package com.sanotes.saNotesPostgres.service.DAO;

import com.sanotes.saNotesPostgres.service.model.NotesModel;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<NotesModel, Long> {
}
