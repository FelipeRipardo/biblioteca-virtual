package br.edu.unichristus.biblioteca_virtual.controller;

import br.edu.unichristus.biblioteca_virtual.model.Livro;
import br.edu.unichristus.biblioteca_virtual.service.LivroService;
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
@RequestMapping("/api/livros")
@RequiredArgsConstructor
@Tag(name = "Livro", description = "Endpoints para gerenciamento de livros do acervo.")
public class LivroController {

    private final LivroService service;

    @GetMapping
    @Operation(summary = "Lista todos os livros cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de livros retornada com sucesso!")
    public ResponseEntity<List<Livro>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um livro específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado.")
    })
    public ResponseEntity<Livro> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar-por-titulo")
    @Operation(summary = "Busca livros pelo título (parcial ou completo).")
    @ApiResponse(responseCode = "200", description = "Lista de livros filtrada retornada com sucesso.")
    public ResponseEntity<List<Livro>> searchByTitulo(@RequestParam String titulo) {
        return ResponseEntity.ok(service.searchByTitulo(titulo));
    }

    @GetMapping("/buscar-por-serie")
    @Operation(summary = "Busca livros pela série recomendada.")
    @ApiResponse(responseCode = "200", description = "Lista de livros filtrada retornada com sucesso.")
    public ResponseEntity<List<Livro>> searchBySerieRecomendada(@RequestParam String serieRecomendada) {
        return ResponseEntity.ok(service.searchBySerieRecomendada(serieRecomendada));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo livro no sistema.")
    @ApiResponse(responseCode = "201", description = "Livro criado com sucesso!")
    public ResponseEntity<Livro> create(@RequestBody Livro livro) {
        Livro newLivro = service.create(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(newLivro);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um livro existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado para atualização.")
    })
    public ResponseEntity<Livro> update(@PathVariable Long id, @RequestBody Livro livro) {
        Livro updatedLivro = service.update(id, livro);
        if (updatedLivro == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedLivro);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um livro pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Livro removido com sucesso."),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado para remoção.")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}