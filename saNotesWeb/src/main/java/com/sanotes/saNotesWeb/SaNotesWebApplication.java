package com.sanotes.saNotesWeb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication (scanBasePackages = {"com.sanotes"})
public class SaNotesWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaNotesWebApplication.class, args);
	}

}
