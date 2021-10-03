package com.sanotes.saNotesPostgres.Service.DAO;

import com.sanotes.saNotesPostgres.Service.Model.NoteBookModel;
import org.springframework.data.repository.CrudRepository;

public interface NoteBookRepository extends CrudRepository<NoteBookModel, Long> {
}
