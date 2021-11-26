package com.sanotes.saNotesWeb;

import com.sanotes.saNotesWeb.security.JwtAuthenticationFilter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication (scanBasePackages = {"com.sanotes"})
@EnableMongoRepositories (basePackages = {"com.sanotes"})
@EnableJpaRepositories (basePackages = {"com.sanotes"})
@EntityScan (basePackages = {"com.sanotes"})
public class SaNotesWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaNotesWebApplication.class, args);
	}

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
