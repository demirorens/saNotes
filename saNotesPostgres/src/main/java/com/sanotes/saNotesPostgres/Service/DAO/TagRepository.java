package com.sanotes.saNotesPostgres.Service.DAO;

import com.sanotes.saNotesPostgres.Service.Model.TagModel;
import org.springframework.data.repository.CrudRepository;

public interface TagRepository extends CrudRepository<TagModel, Long> {
}
