package br.edu.unichristus.biblioteca_virtual.controller;

import br.edu.unichristus.biblioteca_virtual.model.SessaoLeitura;
import br.edu.unichristus.biblioteca_virtual.service.SessaoLeituraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ResponseEntity<SessaoLeitura> create(@RequestBody SessaoLeitura sessao) {
        SessaoLeitura newSessao = service.create(sessao);
        return ResponseEntity.status(HttpStatus.CREATED).body(newSessao);
    }

    @GetMapping("/{token}")
    @Operation(summary = "Retoma a leitura buscando o progresso pelo token do dispositivo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessão encontrada e leitura retomada!"),
            @ApiResponse(responseCode = "404", description = "Token inválido ou sessão expirada.")
    })
    public ResponseEntity<SessaoLeitura> findByToken(@PathVariable String token) {
        return service.findByToken(token)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{token}/pagina")
    @Operation(summary = "Salva a página atual lida pelo usuário (Virar de página).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página atualizada e progresso salvo!"),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada.")
    })
    public ResponseEntity<SessaoLeitura> updatePage(@PathVariable String token, @RequestParam Integer novaPagina) {
        SessaoLeitura updatedSessao = service.updatePage(token, novaPagina);
        if (updatedSessao == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedSessao);
    }
}