package com.sanotes.saNotesWeb.Controller;

import com.sanotes.saNotesMongo.Model.NoteModel;
import com.sanotes.saNotesWeb.Service.NotesService;
import com.sanotes.saNotesPostgres.Service.Model.NoteBookModel;
import com.sanotes.saNotesWeb.Service.NoteBookService;
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
    public List<NoteModel> getNotes(){
        return notesService.getNotes();
    }

    @PostMapping("savenote")
    public ResponseEntity<NoteModel> saveNote(@RequestBody NoteModel  noteModel){
        try {
            NoteModel _NoteModel1 = notesService.saveNote(new NoteModel(noteModel.getTopic(),noteModel.getText()));
            return new ResponseEntity<>(_NoteModel1, HttpStatus.CREATED);
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
