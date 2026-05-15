package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.SessaoLeitura;
import br.edu.unichristus.biblioteca_virtual.repository.SessaoLeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessaoLeituraService {

    private final SessaoLeituraRepository repository;

    public SessaoLeitura create(SessaoLeitura sessao) {
        return repository.save(sessao);
    }

    public SessaoLeitura findByToken(String token) {
        return repository.findByTokenDispositivo(token)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão de leitura não encontrada para o token informado."));
    }

    public SessaoLeitura updatePage(String token, Integer novaPagina) {
        SessaoLeitura existingSessao = findByToken(token);

        existingSessao.setUltimaPaginaLida(novaPagina);

        return repository.save(existingSessao);
    }
}