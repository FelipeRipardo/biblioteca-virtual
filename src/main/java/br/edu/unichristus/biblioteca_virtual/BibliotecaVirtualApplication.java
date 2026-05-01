package br.edu.unichristus.biblioteca_virtual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
		info = @Info(
				title = "API Biblioteca Virtual Pública",
				version = "1.0",
				description = "API Rest desenvolvida para gestão da biblioteca virtual do colégio, permitindo o controle de livros, " +
						"categorias, autores, avaliações e sessões de leitura dos alunos.",
				contact = @Contact(
						name = "Felipe Ripardo",
						email = "felipe.ripardov@gmail.com",
						url = "https://www.api-bibliotecavirtual.com")
		)
)

@SpringBootApplication
public class BibliotecaVirtualApplication {

	public static void main(String[] args) {
		SpringApplication.run(BibliotecaVirtualApplication.class, args);
	}
}
