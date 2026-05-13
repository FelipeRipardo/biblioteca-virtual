package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.model.SessaoLeitura;
import br.edu.unichristus.biblioteca_virtual.repository.SessaoLeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessaoLeituraService {

    private final SessaoLeituraRepository repository;

    public SessaoLeitura create(SessaoLeitura sessao) {
        return repository.save(sessao);
    }

    public Optional<SessaoLeitura> findByToken(String token) {
        return repository.findByTokenDispositivo(token);
    }

    public SessaoLeitura updatePage(String token, Integer novaPagina) {
        return repository.findByTokenDispositivo(token).map(existingSessao -> {
            existingSessao.setUltimaPaginaLida(novaPagina);
            return repository.save(existingSessao);
        }).orElse(null);
    }
}