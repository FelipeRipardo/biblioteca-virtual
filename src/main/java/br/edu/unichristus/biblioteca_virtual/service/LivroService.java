package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Livro;
import br.edu.unichristus.biblioteca_virtual.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroService {

    private final LivroRepository repository;

    public List<Livro> listAll() {
        return repository.findAll();
    }

    public Livro findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o ID: " + id));
    }

    public List<Livro> searchByTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo);
    }

    public List<Livro> searchBySerieRecomendada(String serieRecomendada) {
        return repository.findBySerieRecomendadaContainingIgnoreCase(serieRecomendada);
    }

    public Livro create(Livro livro) {
        return repository.save(livro);
    }

    public Livro update(Long id, Livro livro) {
        Livro existingLivro = findById(id);

        existingLivro.setTitulo(livro.getTitulo());
        existingLivro.setResumo(livro.getResumo());
        existingLivro.setAnoPublicacao(livro.getAnoPublicacao());
        existingLivro.setTotalPaginas(livro.getTotalPaginas());
        existingLivro.setIsbn(livro.getIsbn());
        existingLivro.setSerieRecomendada(livro.getSerieRecomendada());
        existingLivro.setAutor(livro.getAutor());
        existingLivro.setCategoria(livro.getCategoria());

        return repository.save(existingLivro);
    }

    public void delete(Long id) {
        Livro existingLivro = findById(id);
        repository.delete(existingLivro);
    }
}