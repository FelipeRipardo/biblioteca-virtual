package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.model.Categoria;
import br.edu.unichristus.biblioteca_virtual.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    //A injeção de dependência é feita de forma automática pelo @RequiredArgsContructor
    private final CategoriaRepository repository;

    //Lista todas as categorias
    public List<Categoria> listAll() {
        return repository.findAll();
    }

    //Busca pelo nome.
    public List<Categoria> searchByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }
    //Realiza uma busca por ID das categorias.

    public Optional<Categoria> findById(Long id) {
        return repository.findById(id);
    }
    //Cria uma categoria.
    public Categoria create(Categoria categoria) {
        return repository.save(categoria);
    }

    //Atualiza uma categoria.
    public Categoria update(Long id, Categoria updatedCategoria) {
        return repository.findById(id).map(existingCategoria -> {
            existingCategoria.setNome(updatedCategoria.getNome());
            existingCategoria.setDescricao(updatedCategoria.getDescricao());
            existingCategoria.setAreaConhecimento(updatedCategoria.getAreaConhecimento());
            existingCategoria.setDepartamentoResponsavel(updatedCategoria.getDepartamentoResponsavel());
            return repository.save(existingCategoria);
        }).orElse(null); //Retorna null se tentar atualizar um ID de Categoria que não existe.
    }

    //Deleta uma categoria.
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}