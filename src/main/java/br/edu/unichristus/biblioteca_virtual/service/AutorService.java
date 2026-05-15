package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.dto.AutorRequestDTO;
import br.edu.unichristus.biblioteca_virtual.dto.AutorResponseDTO;
import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Autor;
import br.edu.unichristus.biblioteca_virtual.repository.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutorService {

    //A injeção de dependência é feita de forma automática pelo @RequiredArgsConstructor
    private final AutorRepository repository;

    //Lista todos os autores.
    public List<AutorResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Busca pelo nome.
    public List<AutorResponseDTO> searchByNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Busca pela nacionalidade.
    public List<AutorResponseDTO> searchByNacionalidade(String nacionalidade) {
        return repository.findByNacionalidadeContainingIgnoreCase(nacionalidade).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Realiza uma busca por ID do autor.
    public AutorResponseDTO findById(Long id) {
        Autor autor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado para o ID: " + id + "."));
        return convertToResponseDTO(autor);
    }

    //Cria um autor.
    public AutorResponseDTO create(AutorRequestDTO requestDTO) {
        Autor autor = new Autor();
        autor.setNome(requestDTO.getNome());
        autor.setBiografia(requestDTO.getBiografia());
        autor.setNacionalidade(requestDTO.getNacionalidade());
        autor.setDataNascimento(requestDTO.getDataNascimento());

        Autor savedAutor = repository.save(autor);
        return convertToResponseDTO(savedAutor);
    }

    //Atualiza um autor.
    public AutorResponseDTO update(Long id, AutorRequestDTO requestDTO) {
        Autor existingAutor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado para o ID: " + id + "."));

        existingAutor.setNome(requestDTO.getNome());
        existingAutor.setBiografia(requestDTO.getBiografia());
        existingAutor.setNacionalidade(requestDTO.getNacionalidade());
        existingAutor.setDataNascimento(requestDTO.getDataNascimento());

        Autor updatedAutor = repository.save(existingAutor);
        return convertToResponseDTO(updatedAutor);
    }

    //Deleta um autor.
    public void delete(Long id) {
        Autor existingAutor = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado para o ID: " + id + "."));
        repository.delete(existingAutor);
    }

    //Método auxiliar privado para converter Entidade em DTO de saída.
    private AutorResponseDTO convertToResponseDTO(Autor autor) {
        AutorResponseDTO responseDTO = new AutorResponseDTO();
        responseDTO.setId(autor.getId());
        responseDTO.setNome(autor.getNome());
        responseDTO.setBiografia(autor.getBiografia());
        responseDTO.setNacionalidade(autor.getNacionalidade());
        responseDTO.setDataNascimento(autor.getDataNascimento());
        return responseDTO;
    }
}