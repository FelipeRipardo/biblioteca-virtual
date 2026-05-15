package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Avaliacao;
import br.edu.unichristus.biblioteca_virtual.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository repository;

    public List<Avaliacao> listAll() {
        return repository.findAll();
    }

    public Avaliacao findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada para o ID: " + id + "."));
    }

    public List<Avaliacao> searchByNota(Integer nota) {
        return repository.findByNota(nota);
    }

    public List<Avaliacao> searchByLivroId(Long livroId) {
        return repository.findByLivroId(livroId);
    }

    public Avaliacao create(Avaliacao avaliacao) {
        return repository.save(avaliacao);
    }

    public Avaliacao update(Long id, Avaliacao avaliacao) {
        Avaliacao existingAvaliacao = findById(id);

        existingAvaliacao.setApelidoLeitor(avaliacao.getApelidoLeitor());
        existingAvaliacao.setNota(avaliacao.getNota());
        existingAvaliacao.setComentario(avaliacao.getComentario());
        existingAvaliacao.setLivro(avaliacao.getLivro());

        return repository.save(existingAvaliacao);
    }

    public void delete(Long id) {
        Avaliacao existingAvaliacao = findById(id);
        repository.delete(existingAvaliacao);
    }
}