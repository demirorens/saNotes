package com.sanotes.saNotesPostgres.Service.DAO;

import com.sanotes.saNotesPostgres.Service.Model.NotesModel;
import org.springframework.data.repository.CrudRepository;

public interface NotesRepository extends CrudRepository<NotesModel, Long> {
}
