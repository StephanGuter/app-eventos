package com.api.eventos.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI EventosOpenAPI() {
		return new OpenAPI().info(new Info()
				.title("API do Projeto de Eventos")
				.description("Esta API é utilizada na disciplina Desenvolvimento para Servidores-II da FATEC-RL").version("v0.0.1")
				.contact(new Contact().name("Stephan Güter Ferreira Cunha").email("stephancunha@gmail.com"))
				.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}