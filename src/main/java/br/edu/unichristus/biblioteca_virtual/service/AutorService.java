package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Autor;
import br.edu.unichristus.biblioteca_virtual.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;

    public List<Autor> listAll() {
        return repository.findAll();
    }

    public Autor findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado para o ID: " + id));
    }

    public List<Autor> searchByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Autor> searchByNacionalidade(String nacionalidade) {
        return repository.findByNacionalidadeContainingIgnoreCase(nacionalidade);
    }

    public Autor create(Autor autor) {
        return repository.save(autor);
    }

    public Autor update(Long id, Autor autor) {
        Autor existingAutor = findById(id);

        existingAutor.setNome(autor.getNome());
        existingAutor.setBiografia(autor.getBiografia());
        existingAutor.setNacionalidade(autor.getNacionalidade());
        existingAutor.setDataNascimento(autor.getDataNascimento());

        return repository.save(existingAutor);
    }

    public void delete(Long id) {
        Autor existingAutor = findById(id);
        repository.delete(existingAutor);
    }
}