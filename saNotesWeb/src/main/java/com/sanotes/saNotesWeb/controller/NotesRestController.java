package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesMongo.model.NotModel;
import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesPostgres.service.model.TagModel;
import com.sanotes.saNotesWeb.service.NoteBookService;
import com.sanotes.saNotesWeb.service.NotesService;
import com.sanotes.saNotesWeb.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class NotesRestController {

   @Autowired
   NoteBookService noteBookService;
   @Autowired
   NotesService notesService;
   @Autowired
   TagService tagService;

    @GetMapping("/notes")
    public ResponseEntity<List<NotModel>> getNotes(){
        return ResponseEntity.ok().body(notesService.getNotes());
    }

    @GetMapping("/notebooks")
    public ResponseEntity<Iterable<NoteBookModel>> getNoteBooks(){
        return ResponseEntity.ok().body(noteBookService.getNoteBooks());
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagModel>> getTags(){
        return ResponseEntity.ok().body(tagService.getTags());
    }

    @PostMapping("/addnotebook")
    public ResponseEntity<NoteBookModel> addNoteBook(@RequestBody NoteBookModel noteBook){
        return ResponseEntity.ok().body(noteBookService.saveNoteBook(noteBook));
    }

    @PostMapping("/addtag")
    public ResponseEntity<TagModel> addTag(@RequestBody TagModel tag){
        return ResponseEntity.ok().body(tagService.saveTag(tag));
    }


}
