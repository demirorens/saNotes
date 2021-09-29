package com.sanotes.saNotesPostgres.Service;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DenemeServiceP {
    public List<String> getSurNames(){
        return List.of(
                "Demirören",
                "Veli",
                "Işıklar"
        );
    }
}
