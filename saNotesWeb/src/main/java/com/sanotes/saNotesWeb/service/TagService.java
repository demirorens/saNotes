package com.sanotes.saNotesWeb.Service;

import com.sanotes.saNotesPostgres.Service.DAO.NoteBookRepository;
import com.sanotes.saNotesPostgres.Service.DAO.NoteTagsRepository;
import com.sanotes.saNotesPostgres.Service.DAO.NotesRepository;
import com.sanotes.saNotesPostgres.Service.DAO.TagRepository;
import com.sanotes.saNotesPostgres.Service.Model.NoteBookModel;
import com.sanotes.saNotesPostgres.Service.Model.NoteTagsModel;
import com.sanotes.saNotesPostgres.Service.Model.NotesModel;
import com.sanotes.saNotesPostgres.Service.Model.TagModel;
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
