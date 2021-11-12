package com.sanotes.saNotesWeb.controller;

import com.sanotes.saNotesMongo.model.NotModel;
import com.sanotes.saNotesWeb.service.NotesService;
import com.sanotes.saNotesPostgres.service.model.NoteBookModel;
import com.sanotes.saNotesWeb.service.NoteBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("deneme")
public class Deneme {

   @Autowired
   NoteBookService noteBookService;
   @Autowired
   NotesService notesService;


    @GetMapping("notes")
    public List<NotModel> getNotes(){
        return notesService.getNotes();
    }

    @PostMapping("savenote")
    public ResponseEntity<NotModel> saveNote(@RequestBody NotModel notModel){
        try {
            NotModel _NotModel11 = notesService.saveNote(new NotModel(notModel.getTopic(), notModel.getText()));
            return new ResponseEntity<>(_NotModel11, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("notebooks")
    public Iterable<NoteBookModel> getNoteBooks(){
        return noteBookService.getNoteBooks();
    }

    @PostMapping("savenotebook")
    public ResponseEntity<NoteBookModel> saveNoteBook(@RequestBody NoteBookModel  noteBookModel){
        try {
            NoteBookModel _NoteBookModel1 = noteBookService.saveNoteBook(new NoteBookModel(noteBookModel.getName(),noteBookModel.getDescription()));
            return new ResponseEntity<>(_NoteBookModel1, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
