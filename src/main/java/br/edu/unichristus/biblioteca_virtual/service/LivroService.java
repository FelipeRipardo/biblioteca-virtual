package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.model.Livro;
import br.edu.unichristus.biblioteca_virtual.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public List<Livro> listAll() {
        return repository.findAll();
    }

    public Optional<Livro> findById(Long id) {
        return repository.findById(id);
    }

    public Livro create(Livro livro) {
        return repository.save(livro);
    }

    public Livro update(Long id, Livro livro) {
        return repository.findById(id).map(existingLivro -> {
            existingLivro.setTitulo(livro.getTitulo());
            existingLivro.setResumo(livro.getResumo());
            existingLivro.setAnoPublicacao(livro.getAnoPublicacao());
            existingLivro.setTotalPaginas(livro.getTotalPaginas());
            existingLivro.setIsbn(livro.getIsbn());
            existingLivro.setSerieRecomendada(livro.getSerieRecomendada());
            // Atualização dos relacionamentos:
            existingLivro.setAutor(livro.getAutor());
            existingLivro.setCategoria(livro.getCategoria());

            return repository.save(existingLivro);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Livro> searchByTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Livro> searchBySerieRecomendada(String serieRecomendada) {
        return repository.findBySerieRecomendadaContainingIgnoreCase(serieRecomendada);
    }
}