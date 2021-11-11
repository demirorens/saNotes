package com.sanotes.saNotesWeb.service;

import com.sanotes.saNotesPostgres.service.DAO.NoteTagsRepository;
import com.sanotes.saNotesPostgres.service.DAO.TagRepository;
import com.sanotes.saNotesPostgres.service.model.NoteTagsModel;
import com.sanotes.saNotesPostgres.service.model.TagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    NoteTagsRepository noteTagsRepository;

    @Autowired
    TagRepository  tagRepository;

    public TagModel saveTag(TagModel tagModel){

        return tagRepository.save(tagModel);
    }

    public List<TagModel> getTags(){

        return (List<TagModel>) tagRepository.findAll();
    }

    public NoteTagsModel saveNoteTags(NoteTagsModel noteTagsModel){

        return noteTagsRepository.save(noteTagsModel);
    }

    public List<NoteTagsModel> getNoteTags(){

        return (List<NoteTagsModel>) noteTagsRepository.findAll();
    }
}
