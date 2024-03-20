package com.atividade1.atividade1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude={SecurityAutoConfiguration.class})
@ComponentScan("com.atividade1.atividade1")
public class Atividade1Application {

	public static void main(String[] args) {
		SpringApplication.run(Atividade1Application.class, args);
	}

}
