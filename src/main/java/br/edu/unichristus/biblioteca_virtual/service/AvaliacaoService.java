package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.model.Avaliacao;
import br.edu.unichristus.biblioteca_virtual.repository.AvaliacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    private final AvaliacaoRepository repository;

    public List<Avaliacao> listAll() {
        return repository.findAll();
    }

    public Optional<Avaliacao> findById(Long id) {
        return repository.findById(id);
    }

    public Avaliacao create(Avaliacao avaliacao) {
        return repository.save(avaliacao);
    }

    public Avaliacao update(Long id, Avaliacao avaliacao) {
        return repository.findById(id).map(existingAvaliacao -> {
            existingAvaliacao.setApelidoLeitor(avaliacao.getApelidoLeitor());
            existingAvaliacao.setNota(avaliacao.getNota());
            existingAvaliacao.setComentario(avaliacao.getComentario());
            existingAvaliacao.setLivro(avaliacao.getLivro());

            return repository.save(existingAvaliacao);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Avaliacao> searchByNota(Integer nota) {
        return repository.findByNota(nota);
    }

    public List<Avaliacao> searchByLivroId(Long livroId) {
        return repository.findByLivroId(livroId);
    }
}