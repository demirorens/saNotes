package com.sanotes.saNotesWeb;

import com.sanotes.saNotesWeb.security.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@SecurityScheme(name = "bearerAuth",
		scheme = "bearer",
		bearerFormat = "JWT",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "saNotes API", version = "1.0", description = "Note Application"))
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
