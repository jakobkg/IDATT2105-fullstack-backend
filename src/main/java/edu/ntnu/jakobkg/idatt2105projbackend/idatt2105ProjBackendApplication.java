package edu.ntnu.jakobkg.idatt2105projbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;


@SpringBootApplication
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class idatt2105ProjBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(idatt2105ProjBackendApplication.class, args);
	}

}
