package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.dto.AvaliacaoRequestDTO;
import br.edu.unichristus.biblioteca_virtual.dto.AvaliacaoResponseDTO;
import br.edu.unichristus.biblioteca_virtual.dto.AutorResponseDTO;
import br.edu.unichristus.biblioteca_virtual.dto.CategoriaResponseDTO;
import br.edu.unichristus.biblioteca_virtual.dto.LivroResponseDTO;
import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Avaliacao;
import br.edu.unichristus.biblioteca_virtual.model.Livro;
import br.edu.unichristus.biblioteca_virtual.repository.AvaliacaoRepository;
import br.edu.unichristus.biblioteca_virtual.repository.LivroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliacaoService {

    //A injeção de dependência é feita de forma automática pelo @RequiredArgsConstructor
    private final AvaliacaoRepository repository;
    private final LivroRepository livroRepository;

    //Lista todas as avaliações.
    public List<AvaliacaoResponseDTO> listAll() {
        return repository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Realiza uma busca por ID da avaliação.
    public AvaliacaoResponseDTO findById(Long id) {
        Avaliacao avaliacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada para o ID: " + id + "."));
        return convertToResponseDTO(avaliacao);
    }

    //Busca pela nota.
    public List<AvaliacaoResponseDTO> searchByNota(Integer nota) {
        return repository.findByNota(nota).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Busca pelo ID do livro.
    public List<AvaliacaoResponseDTO> searchByLivroId(Long livroId) {
        return repository.findByLivroId(livroId).stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Cria uma avaliação.
    public AvaliacaoResponseDTO create(AvaliacaoRequestDTO requestDTO) {
        Livro livro = livroRepository.findById(requestDTO.getLivroId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o ID: " + requestDTO.getLivroId() + "."));

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setApelidoLeitor(requestDTO.getApelidoLeitor());
        avaliacao.setNota(requestDTO.getNota());
        avaliacao.setComentario(requestDTO.getComentario());
        avaliacao.setLivro(livro);

        Avaliacao savedAvaliacao = repository.save(avaliacao);
        return convertToResponseDTO(savedAvaliacao);
    }

    //Atualiza uma avaliação.
    public AvaliacaoResponseDTO update(Long id, AvaliacaoRequestDTO requestDTO) {
        Avaliacao existingAvaliacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada para o ID: " + id + "."));

        Livro livro = livroRepository.findById(requestDTO.getLivroId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o ID: " + requestDTO.getLivroId() + "."));

        existingAvaliacao.setApelidoLeitor(requestDTO.getApelidoLeitor());
        existingAvaliacao.setNota(requestDTO.getNota());
        existingAvaliacao.setComentario(requestDTO.getComentario());
        existingAvaliacao.setLivro(livro);

        Avaliacao updatedAvaliacao = repository.save(existingAvaliacao);
        return convertToResponseDTO(updatedAvaliacao);
    }

    //Deleta uma avaliação.
    public void delete(Long id) {
        Avaliacao existingAvaliacao = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Avaliação não encontrada para o ID: " + id + "."));
        repository.delete(existingAvaliacao);
    }

    //Método auxiliar privado para converter Entidade em DTO de saída.
    private AvaliacaoResponseDTO convertToResponseDTO(Avaliacao avaliacao) {
        AvaliacaoResponseDTO responseDTO = new AvaliacaoResponseDTO();
        responseDTO.setId(avaliacao.getId());
        responseDTO.setApelidoLeitor(avaliacao.getApelidoLeitor());
        responseDTO.setNota(avaliacao.getNota());
        responseDTO.setComentario(avaliacao.getComentario());

        if (avaliacao.getLivro() != null) {
            LivroResponseDTO livroDTO = new LivroResponseDTO();
            livroDTO.setId(avaliacao.getLivro().getId());
            livroDTO.setTitulo(avaliacao.getLivro().getTitulo());
            livroDTO.setResumo(avaliacao.getLivro().getResumo());
            livroDTO.setAnoPublicacao(avaliacao.getLivro().getAnoPublicacao());
            livroDTO.setTotalPaginas(avaliacao.getLivro().getTotalPaginas());
            livroDTO.setIsbn(avaliacao.getLivro().getIsbn());
            livroDTO.setSerieRecomendada(avaliacao.getLivro().getSerieRecomendada());

            if (avaliacao.getLivro().getAutor() != null) {
                livroDTO.setNomeAutor(avaliacao.getLivro().getAutor().getNome());
            }

            if (avaliacao.getLivro().getCategoria() != null) {
                livroDTO.setNomeCategoria(avaliacao.getLivro().getCategoria().getNome());
                livroDTO.setAreaConhecimentoCategoria(avaliacao.getLivro().getCategoria().getAreaConhecimento());
                livroDTO.setDepartamentoResponsavelCategoria(avaliacao.getLivro().getCategoria().getDepartamentoResponsavel());
            }

            responseDTO.setLivro(livroDTO);
        }

        return responseDTO;
    }
}