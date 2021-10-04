package com.sanotes.saNotesWeb;

import com.sanotes.saNotesMongo.DAO.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication (scanBasePackages = {"com.sanotes"})
@EnableMongoRepositories (basePackages = {"com.sanotes"})
@EnableJpaRepositories (basePackages = {"com.sanotes"})
@EntityScan (basePackages = {"com.sanotes"})
public class SaNotesWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaNotesWebApplication.class, args);
	}

}
