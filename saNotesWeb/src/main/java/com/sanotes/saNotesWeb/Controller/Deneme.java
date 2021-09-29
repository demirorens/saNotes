package com.sanotes.saNotesWeb.Controller;

import com.sanotes.saNotesMongo.DenemeService;
import com.sanotes.saNotesPostgres.Service.DenemeServiceP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("deneme")
public class Deneme {

   @Autowired
   DenemeService denemeServiceM;
   @Autowired
   DenemeServiceP denemeServiceP;

    @GetMapping("isimler")
    public List<String> getNames(){
        return denemeServiceM.getNames();
    }
    @GetMapping("soyisimler")
    public List<String> getSurNames(){
        return denemeServiceP.getSurNames();
    }
}
