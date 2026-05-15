package br.edu.unichristus.biblioteca_virtual.controller;

import br.edu.unichristus.biblioteca_virtual.model.Avaliacao;
import br.edu.unichristus.biblioteca_virtual.service.AvaliacaoService;
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
@RequestMapping("/api/avaliacoes")
@RequiredArgsConstructor
@Tag(name = "Avaliação", description = "Endpoints para gerenciamento de avaliações de livros.")
public class AvaliacaoController {

    private final AvaliacaoService service;

    @GetMapping
    @Operation(summary = "Lista todas as avaliações registradas.")
    @ApiResponse(responseCode = "200", description = "Lista de avaliações retornada com sucesso!")
    public ResponseEntity<List<Avaliacao>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma avaliação específica pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação encontrada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada.")
    })
    public ResponseEntity<Avaliacao> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/buscar-por-nota")
    @Operation(summary = "Busca avaliações filtrando por uma nota específica (1 a 5).")
    @ApiResponse(responseCode = "200", description = "Lista de avaliações filtrada retornada com sucesso!")
    public ResponseEntity<List<Avaliacao>> searchByNota(@RequestParam Integer nota) {
        return ResponseEntity.ok(service.searchByNota(nota));
    }

    @GetMapping("/buscar-por-livro")
    @Operation(summary = "Busca todas as avaliações atreladas a um Livro específico.")
    @ApiResponse(responseCode = "200", description = "Lista de avaliações do livro retornada com sucesso!")
    public ResponseEntity<List<Avaliacao>> searchByLivroId(@RequestParam Long livroId) {
        return ResponseEntity.ok(service.searchByLivroId(livroId));
    }

    @PostMapping
    @Operation(summary = "Registra uma nova avaliação para um livro.")
    @ApiResponse(responseCode = "201", description = "Avaliação registrada com sucesso!")
    public ResponseEntity<Avaliacao> create(@RequestBody Avaliacao avaliacao) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(avaliacao));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma avaliação existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Avaliação atualizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Avaliação não encontrada para atualização.")
    })
    public ResponseEntity<Avaliacao> update(@PathVariable Long id, @RequestBody Avaliacao avaliacao) {
        return ResponseEntity.ok(service.update(id, avaliacao));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma avaliação pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso!")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}