package ru.mcx73.gis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class GisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GisApplication.class, args);
		
	}

}
