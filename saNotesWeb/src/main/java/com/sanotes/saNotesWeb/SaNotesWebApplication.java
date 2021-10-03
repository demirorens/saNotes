package com.sanotes.saNotesWeb;

import com.sanotes.saNotesMongo.DAO.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication (scanBasePackages = {"com.sanotes"})
@EnableMongoRepositories (basePackages = {"com.sanotes"})
public class SaNotesWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaNotesWebApplication.class, args);
	}

}
