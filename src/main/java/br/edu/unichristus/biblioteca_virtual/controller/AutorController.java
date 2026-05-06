package br.edu.unichristus.biblioteca_virtual.controller;

import br.edu.unichristus.biblioteca_virtual.model.Autor;
import br.edu.unichristus.biblioteca_virtual.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/autores")
@RequiredArgsConstructor
@Tag(name = "Autor", description = "Endpoints para gerenciamento de autores do acervo.")
public class AutorController {

    private final AutorService service;

    @GetMapping
    @Operation(summary = "Lista todos os autores cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso!")
    public ResponseEntity<List<Autor>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um autor específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
    public ResponseEntity<Autor> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo autor no sistema.")
    @ApiResponse(responseCode = "201", description = "Autor criado com sucesso!")
    public ResponseEntity<Autor> create(@RequestBody Autor autor) {
        Autor newAutor = service.create(autor);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAutor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado para atualização.")
    })
    public ResponseEntity<Autor> update(@PathVariable Long id, @RequestBody Autor autor) {
        Autor updatedAutor = service.update(id, autor);
        if (updatedAutor == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedAutor);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um autor pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado para remoção.")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}