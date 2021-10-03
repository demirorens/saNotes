package com.sanotes.saNotesWeb.Controller;

import com.sanotes.saNotesMongo.DenemeService;
import com.sanotes.saNotesMongo.Model.NoteModel;
import com.sanotes.saNotesMongo.NotesService;
import com.sanotes.saNotesPostgres.Service.DenemeServiceP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("deneme")
public class Deneme {

   @Autowired
   DenemeService denemeServiceM;
   @Autowired
   DenemeServiceP denemeServiceP;
   @Autowired
   NotesService notesService;

    @GetMapping("isimler")
    public List<String> getNames(){
        return denemeServiceM.getNames();
    }
    @GetMapping("soyisimler")
    public List<String> getSurNames(){
        return denemeServiceP.getSurNames();
    }

    @GetMapping("notes")
    public List<NoteModel> getNotes(){
        return notesService.getNotes();
    }

    @PostMapping("save")
    public ResponseEntity<NoteModel> saveNote(@RequestBody NoteModel  noteModel){
        try {
            NoteModel _NoteModel1 = notesService.saveNote(new NoteModel(noteModel.getTopic(),noteModel.getText()));
            return new ResponseEntity<>(_NoteModel1, HttpStatus.CREATED);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
