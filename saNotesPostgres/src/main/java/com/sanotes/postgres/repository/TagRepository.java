package com.sanotes.postgres.repository;

import com.sanotes.commons.model.TagModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TagRepository extends CrudRepository<TagModel, Long> {
    Optional<TagModel> findByName(String name);


}
