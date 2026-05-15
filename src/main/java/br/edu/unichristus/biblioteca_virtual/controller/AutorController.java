package br.edu.unichristus.biblioteca_virtual.controller;

import br.edu.unichristus.biblioteca_virtual.dto.AutorRequestDTO;
import br.edu.unichristus.biblioteca_virtual.dto.AutorResponseDTO;
import br.edu.unichristus.biblioteca_virtual.service.AutorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    @Operation(summary = "Lista todos os autores cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de autores retornada com sucesso!")
    public ResponseEntity<List<AutorResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um autor específico pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado.")
    })
    public ResponseEntity<AutorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/buscar-por-nome")
    @Operation(summary = "Busca autores pelo nome (parcial ou completo).")
    @ApiResponse(responseCode = "200", description = "Lista de autores filtrada retornada com sucesso!")
    public ResponseEntity<List<AutorResponseDTO>> searchByNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.searchByNome(nome));
    }

    @GetMapping("/buscar-por-nacionalidade")
    @Operation(summary = "Busca autores pela nacionalidade.")
    @ApiResponse(responseCode = "200", description = "Lista de autores filtrada retornada com sucesso!")
    public ResponseEntity<List<AutorResponseDTO>> searchByNacionalidade(@RequestParam String nacionalidade) {
        return ResponseEntity.ok(service.searchByNacionalidade(nacionalidade));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo autor no sistema.")
    @ApiResponse(responseCode = "201", description = "Autor criado com sucesso!")
    public ResponseEntity<AutorResponseDTO> create(@Valid @RequestBody AutorRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(requestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de um autor existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor atualizado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado para atualização.")
    })
    public ResponseEntity<AutorResponseDTO> update(@PathVariable Long id, @Valid @RequestBody AutorRequestDTO requestDTO) {
        return ResponseEntity.ok(service.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um autor pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor removido com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado para remoção.")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}