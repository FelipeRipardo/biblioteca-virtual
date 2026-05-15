package br.edu.unichristus.biblioteca_virtual.controller;

import br.edu.unichristus.biblioteca_virtual.model.Categoria;
import br.edu.unichristus.biblioteca_virtual.service.CategoriaService;
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
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
@Tag(name = "Categoria", description = "Endpoints para gerenciamento de categorias do acervo.")
public class CategoriaController {

    private final CategoriaService service;

    @GetMapping
    @Operation(summary = "Lista todas as categorias cadastradas.")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso!")
    public ResponseEntity<List<Categoria>> listAll() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma categoria específica pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada.")
    })
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        // A tratativa do 404 agora é automática via Exception Handler
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/buscar")
    @Operation(summary = "Busca categorias pelo nome (parcial ou completo.")
    @ApiResponse(responseCode = "200", description = "Lista de categorias filtrada retornada com sucesso.")
    public ResponseEntity<List<Categoria>> searchByNome(@RequestParam String nome) {
        return ResponseEntity.ok(service.searchByNome(nome));
    }

    @PostMapping
    @Operation(summary = "Cadastra uma nova categoria no sistema.")
    @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso!")
    public ResponseEntity<Categoria> create(@RequestBody Categoria categoria) {
        Categoria newCategoria = service.create(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCategoria);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma categoria existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para atualização.")
    })
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody Categoria categoria) {
        // Se o ID não existir, o Service vai disparar a exceção antes de chegar na linha de baixo
        Categoria updatedCategoria = service.update(id, categoria);
        return ResponseEntity.ok(updatedCategoria);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove uma categoria pelo ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada para remoção.")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}