package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.dto.AutorResponseDTO;
import br.edu.unichristus.biblioteca_virtual.dto.CategoriaResponseDTO;
import br.edu.unichristus.biblioteca_virtual.dto.LivroRequestDTO;
import br.edu.unichristus.biblioteca_virtual.dto.LivroResponseDTO;
import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Autor;
import br.edu.unichristus.biblioteca_virtual.model.Categoria;
import br.edu.unichristus.biblioteca_virtual.model.Livro;
import br.edu.unichristus.biblioteca_virtual.repository.AutorRepository;
import br.edu.unichristus.biblioteca_virtual.repository.CategoriaRepository;
import br.edu.unichristus.biblioteca_virtual.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LivroService {

    //A injeção de dependência é feita de forma automática pelo @RequiredArgsConstructor
    private final LivroRepository repository;
    private final AutorRepository autorRepository;
    private final CategoriaRepository categoriaRepository;

    //Lista todos os livros.
    public List<LivroResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Busca pelo título.
    public List<LivroResponseDTO> searchByTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Busca pela série recomendada.
    public List<LivroResponseDTO> searchBySerieRecomendada(String serieRecomendada) {
        return repository.findBySerieRecomendadaContainingIgnoreCase(serieRecomendada).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Realiza uma busca por ID do livro.
    public LivroResponseDTO findById(Long id) {
        Livro livro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o ID: " + id + "."));
        return convertToResponseDTO(livro);
    }

    //Cria um livro.
    public LivroResponseDTO create(LivroRequestDTO requestDTO) {
        Autor autor = autorRepository.findById(requestDTO.getAutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado para o ID: " + requestDTO.getAutorId() + "."));

        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + requestDTO.getCategoriaId() + "."));

        Livro livro = new Livro();
        livro.setTitulo(requestDTO.getTitulo());
        livro.setResumo(requestDTO.getResumo());
        livro.setAnoPublicacao(requestDTO.getAnoPublicacao());
        livro.setTotalPaginas(requestDTO.getTotalPaginas());
        livro.setIsbn(requestDTO.getIsbn());
        livro.setSerieRecomendada(requestDTO.getSerieRecomendada());
        livro.setAutor(autor);
        livro.setCategoria(categoria);

        Livro savedLivro = repository.save(livro);
        return convertToResponseDTO(savedLivro);
    }

    //Atualiza um livro.
    public LivroResponseDTO update(Long id, LivroRequestDTO requestDTO) {
        Livro existingLivro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o ID: " + id + "."));

        Autor autor = autorRepository.findById(requestDTO.getAutorId())
                .orElseThrow(() -> new ResourceNotFoundException("Autor não encontrado para o ID: " + requestDTO.getAutorId() + "."));

        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + requestDTO.getCategoriaId() + "."));

        existingLivro.setTitulo(requestDTO.getTitulo());
        existingLivro.setResumo(requestDTO.getResumo());
        existingLivro.setAnoPublicacao(requestDTO.getAnoPublicacao());
        existingLivro.setTotalPaginas(requestDTO.getTotalPaginas());
        existingLivro.setIsbn(requestDTO.getIsbn());
        existingLivro.setSerieRecomendada(requestDTO.getSerieRecomendada());
        existingLivro.setAutor(autor);
        existingLivro.setCategoria(categoria);

        Livro updatedLivro = repository.save(existingLivro);
        return convertToResponseDTO(updatedLivro);
    }

    //Deleta um livro.
    public void delete(Long id) {
        Livro existingLivro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o ID: " + id + "."));
        repository.delete(existingLivro);
    }

    //Método auxiliar privado para converter Entidade em DTO de saída.
    private LivroResponseDTO convertToResponseDTO(Livro livro) {
        LivroResponseDTO responseDTO = new LivroResponseDTO();
        responseDTO.setId(livro.getId());
        responseDTO.setTitulo(livro.getTitulo());
        responseDTO.setResumo(livro.getResumo());
        responseDTO.setAnoPublicacao(livro.getAnoPublicacao());
        responseDTO.setTotalPaginas(livro.getTotalPaginas());
        responseDTO.setIsbn(livro.getIsbn());
        responseDTO.setSerieRecomendada(livro.getSerieRecomendada());

        if (livro.getAutor() != null) {
            responseDTO.setNomeAutor(livro.getAutor().getNome());
        }

        if (livro.getCategoria() != null) {
            responseDTO.setNomeCategoria(livro.getCategoria().getNome());
            responseDTO.setAreaConhecimentoCategoria(livro.getCategoria().getAreaConhecimento());
            responseDTO.setDepartamentoResponsavelCategoria(livro.getCategoria().getDepartamentoResponsavel());
        }

        return responseDTO;
    }
}