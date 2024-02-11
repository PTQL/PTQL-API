package com.sebasgoy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {


	
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		System.out.println("Tu aplicación Spring se ha iniciado correctamente.");
		System.out.println("Puedes acceder a ella en: http://localhost:8080/");



	}
	

	

}
