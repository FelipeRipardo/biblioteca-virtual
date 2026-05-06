package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.model.Autor;
import br.edu.unichristus.biblioteca_virtual.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;

    public List<Autor> listAll() {
        return repository.findAll();
    }

    public Optional<Autor> findById(Long id) {
        return repository.findById(id);
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
        return repository.findById(id).map(existingAutor -> {
            existingAutor.setNome(autor.getNome());
            existingAutor.setBiografia(autor.getBiografia());
            existingAutor.setNacionalidade(autor.getNacionalidade());
            existingAutor.setDataNascimento(autor.getDataNascimento());
            return repository.save(existingAutor);
        }).orElse(null);
    }

    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}