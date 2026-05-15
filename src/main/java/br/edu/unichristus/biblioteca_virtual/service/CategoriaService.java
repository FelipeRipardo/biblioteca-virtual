package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.dto.CategoriaRequestDTO;
import br.edu.unichristus.biblioteca_virtual.dto.CategoriaResponseDTO;
import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Categoria;
import br.edu.unichristus.biblioteca_virtual.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    //A injeção de dependência é feita de forma automática pelo @RequiredArgsConstructor
    private final CategoriaRepository repository;

    //Lista todas as categorias.
    public List<CategoriaResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Busca pelo nome.
    public List<CategoriaResponseDTO> searchByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Realiza uma busca por ID das categorias.
    public CategoriaResponseDTO findById(Long id) {
        Categoria categoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + id + "."));
        return convertToResponseDTO(categoria);
    }

    //Cria uma categoria.
    public CategoriaResponseDTO create(CategoriaRequestDTO requestDTO) {
        Categoria categoria = new Categoria();
        categoria.setNome(requestDTO.getNome());
        categoria.setDescricao(requestDTO.getDescricao());
        categoria.setAreaConhecimento(requestDTO.getAreaConhecimento());
        categoria.setDepartamentoResponsavel(requestDTO.getDepartamentoResponsavel());

        Categoria savedCategoria = repository.save(categoria);
        return convertToResponseDTO(savedCategoria);
    }

    //Atualiza uma categoria.
    public CategoriaResponseDTO update(Long id, CategoriaRequestDTO requestDTO) {
        Categoria existingCategoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + id + "."));

        existingCategoria.setNome(requestDTO.getNome());
        existingCategoria.setDescricao(requestDTO.getDescricao());
        existingCategoria.setAreaConhecimento(requestDTO.getAreaConhecimento());
        existingCategoria.setDepartamentoResponsavel(requestDTO.getDepartamentoResponsavel());

        Categoria updatedCategoria = repository.save(existingCategoria);
        return convertToResponseDTO(updatedCategoria);
    }

    //Deleta uma categoria.
    public void delete(Long id) {
        Categoria existingCategoria = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + id + "."));
        repository.delete(existingCategoria);
    }

    // Método auxiliar privado para converter Entidade em DTO de saída.
    private CategoriaResponseDTO convertToResponseDTO(Categoria categoria) {
        CategoriaResponseDTO responseDTO = new CategoriaResponseDTO();
        responseDTO.setId(categoria.getId());
        responseDTO.setNome(categoria.getNome());
        responseDTO.setDescricao(categoria.getDescricao());
        responseDTO.setAreaConhecimento(categoria.getAreaConhecimento());
        responseDTO.setDepartamentoResponsavel(categoria.getDepartamentoResponsavel());
        return responseDTO;
    }
}