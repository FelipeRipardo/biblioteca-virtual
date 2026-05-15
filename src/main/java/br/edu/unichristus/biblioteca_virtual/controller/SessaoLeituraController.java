package br.edu.unichristus.biblioteca_virtual.controller;

import br.edu.unichristus.biblioteca_virtual.dto.SessaoLeituraRequestDTO;
import br.edu.unichristus.biblioteca_virtual.dto.SessaoLeituraResponseDTO;
import br.edu.unichristus.biblioteca_virtual.service.SessaoLeituraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessoes")
@RequiredArgsConstructor
@Tag(name = "Sessão de Leitura", description = "Endpoints para controle automático do progresso de leitura.")
public class SessaoLeituraController {

    private final SessaoLeituraService service;

    @PostMapping
    @Operation(summary = "Inicia uma nova sessão de leitura e gera o token do dispositivo.")
    @ApiResponse(responseCode = "201", description = "Sessão iniciada com sucesso!")
    public ResponseEntity<SessaoLeituraResponseDTO> create(@Valid @RequestBody SessaoLeituraRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(requestDTO));
    }

    @GetMapping("/{token}")
    @Operation(summary = "Retoma a leitura buscando o progresso pelo token do dispositivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão encontrada e leitura retomada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Token inválido ou sessão expirada.")
    })
    public ResponseEntity<SessaoLeituraResponseDTO> findByToken(@PathVariable String token) {
        return ResponseEntity.ok(service.findByToken(token));
    }

    @PatchMapping("/{token}/pagina")
    @Operation(summary = "Salva a página atual lida pelo usuário (Virar de página).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página atualizada e progresso salvo com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada.")
    })
    public ResponseEntity<SessaoLeituraResponseDTO> updatePage(@PathVariable String token, @RequestParam Integer novaPagina) {
        return ResponseEntity.ok(service.updatePage(token, novaPagina));
    }
}