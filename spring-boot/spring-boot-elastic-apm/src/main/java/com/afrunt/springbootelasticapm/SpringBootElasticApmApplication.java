package com.afrunt.springbootelasticapm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootElasticApmApplication {

	public static void main(String[] args) {
		System.setProperty("elastic.apm.disable_instrumentations", "");
		SpringApplication.run(SpringBootElasticApmApplication.class, args);
	}

}
