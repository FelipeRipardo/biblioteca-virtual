package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Categoria;
import br.edu.unichristus.biblioteca_virtual.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    //A injeção de dependência é feita de forma automática pelo @RequiredArgsConstructor
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
    public Categoria findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + id));
    }

    //Cria uma categoria.
    public Categoria create(Categoria categoria) {
        return repository.save(categoria);
    }

    //Atualiza uma categoria.
    public Categoria update(Long id, Categoria updatedCategoria) {
        // Usa o findById que já valida se o ID existe e dispara o erro 404 se não achar
        Categoria existingCategoria = findById(id);

        existingCategoria.setNome(updatedCategoria.getNome());
        existingCategoria.setDescricao(updatedCategoria.getDescricao());
        existingCategoria.setAreaConhecimento(updatedCategoria.getAreaConhecimento());
        existingCategoria.setDepartamentoResponsavel(updatedCategoria.getDepartamentoResponsavel());

        return repository.save(existingCategoria);
    }

    //Deleta uma categoria.
    public void delete(Long id) {
        // Usa o findById para garantir que existe antes de tentar deletar
        Categoria existingCategoria = findById(id);
        repository.delete(existingCategoria);
    }
}