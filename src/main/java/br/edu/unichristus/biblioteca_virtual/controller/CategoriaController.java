package br.edu.unichristus.biblioteca_virtual.controller;

import br.edu.unichristus.biblioteca_virtual.dto.CategoriaRequestDTO;
import br.edu.unichristus.biblioteca_virtual.dto.CategoriaResponseDTO;
import br.edu.unichristus.biblioteca_virtual.service.CategoriaService;
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
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categoria", description = "Endpoints para gerenciamento de categorias do acervo.")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    @Operation(summary = "Lista todas as categorias cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso!")
    public ResponseEntity<List<CategoriaResponseDTO>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma categoria específica pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    public ResponseEntity<CategoriaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Busca categorias pelo nome (parcial ou completo).")
    @ApiResponse(responseCode = "200", description = "Lista de categorias filtrada retornada com sucesso!")
    public ResponseEntity<List<CategoriaResponseDTO>> searchByNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.searchByNome(nome));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova categoria no sistema.")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso!")
    public ResponseEntity<CategoriaResponseDTO> create(@Valid @RequestBody CategoriaRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(requestDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma categoria existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para atualização.")
    })
    public ResponseEntity<CategoriaResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO requestDTO) {
        return ResponseEntity.ok(service.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma categoria pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para remoção.")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}