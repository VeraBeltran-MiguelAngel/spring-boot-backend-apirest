package com.bolsadeideas.springboot.backend.apirest.springbootbackendapirest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*dentro de esta anotacion (@SpringBootApplication) esta
 * @componentScan que busca y registra en el contenedor de Spring
 * todas las clases anotadas con @RestController, @Controller, @Componente, 
 * @Repository y @Service
 */
@SpringBootApplication
public class SpringBootBackendApirestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBackendApirestApplication.class, args);
	}

}
