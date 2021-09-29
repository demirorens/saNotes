package com.sanotes.saNotesMongo;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DenemeService {
    public List<String> getNames(){
        return List.of(
                "serkan",
                "ali",
                "Alptekin"
        );
    }
}
