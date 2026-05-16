package br.edu.unichristus.biblioteca_virtual.service;

import br.edu.unichristus.biblioteca_virtual.dto.AutorResponseDTO;
import br.edu.unichristus.biblioteca_virtual.dto.CategoriaResponseDTO;
import br.edu.unichristus.biblioteca_virtual.dto.LivroResponseDTO;
import br.edu.unichristus.biblioteca_virtual.dto.SessaoLeituraRequestDTO;
import br.edu.unichristus.biblioteca_virtual.dto.SessaoLeituraResponseDTO;
import br.edu.unichristus.biblioteca_virtual.exception.ResourceNotFoundException;
import br.edu.unichristus.biblioteca_virtual.model.Livro;
import br.edu.unichristus.biblioteca_virtual.model.SessaoLeitura;
import br.edu.unichristus.biblioteca_virtual.repository.LivroRepository;
import br.edu.unichristus.biblioteca_virtual.repository.SessaoLeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessaoLeituraService {

    //A injeção de dependência é feita de forma automática pelo @RequiredArgsConstructor
    private final SessaoLeituraRepository repository;
    private final LivroRepository livroRepository;

    //Cria (inicia) uma nova sessão de leitura.
    public SessaoLeituraResponseDTO create(SessaoLeituraRequestDTO requestDTO) {
        Livro livro = livroRepository.findById(requestDTO.getLivroId())
                .orElseThrow(() -> new ResourceNotFoundException("Livro não encontrado para o ID: " + requestDTO.getLivroId() + "."));

        SessaoLeitura sessao = new SessaoLeitura();
        sessao.setLivro(livro);
        //O token, data e pagina 1 serão gerados automaticamente pelo @PrePersist no Model

        SessaoLeitura savedSessao = repository.save(sessao);
        return convertToResponseDTO(savedSessao);
    }

    //Busca a sessão pelo token invisível do dispositivo.
    public SessaoLeituraResponseDTO findByToken(String token) {
        SessaoLeitura sessao = repository.findByTokenDispositivo(token)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão de leitura não encontrada para o token informado."));
        return convertToResponseDTO(sessao);
    }

    //Atualiza a página lida (Virar a página).
    public SessaoLeituraResponseDTO updatePage(String token, Integer novaPagina) {
        SessaoLeitura existingSessao = repository.findByTokenDispositivo(token)
                .orElseThrow(() -> new ResourceNotFoundException("Sessão de leitura não encontrada para o token informado."));

        existingSessao.setUltimaPaginaLida(novaPagina);

        SessaoLeitura updatedSessao = repository.save(existingSessao);
        return convertToResponseDTO(updatedSessao);
    }

    //Método auxiliar privado para converter Entidade em DTO de saída.
    private SessaoLeituraResponseDTO convertToResponseDTO(SessaoLeitura sessao) {
        SessaoLeituraResponseDTO responseDTO = new SessaoLeituraResponseDTO();
        responseDTO.setTokenDispositivo(sessao.getTokenDispositivo());
        responseDTO.setUltimaPaginaLida(sessao.getUltimaPaginaLida());
        responseDTO.setDataInicio(sessao.getDataInicio());
        responseDTO.setUltimoAcesso(sessao.getDataUltimoAcesso());

        if (sessao.getLivro() != null) {
            LivroResponseDTO livroDTO = new LivroResponseDTO();
            livroDTO.setId(sessao.getLivro().getId());
            livroDTO.setTitulo(sessao.getLivro().getTitulo());
            livroDTO.setResumo(sessao.getLivro().getResumo());
            livroDTO.setAnoPublicacao(sessao.getLivro().getAnoPublicacao());
            livroDTO.setTotalPaginas(sessao.getLivro().getTotalPaginas());
            livroDTO.setIsbn(sessao.getLivro().getIsbn());
            livroDTO.setSerieRecomendada(sessao.getLivro().getSerieRecomendada());

            if (sessao.getLivro().getAutor() != null) {
                livroDTO.setNomeAutor(sessao.getLivro().getAutor().getNome());
            }

            if (sessao.getLivro().getCategoria() != null) {
                livroDTO.setNomeCategoria(sessao.getLivro().getCategoria().getNome());
                livroDTO.setAreaConhecimentoCategoria(sessao.getLivro().getCategoria().getAreaConhecimento());
                livroDTO.setDepartamentoResponsavelCategoria(sessao.getLivro().getCategoria().getDepartamentoResponsavel());
            }

            responseDTO.setLivro(livroDTO);
        }

        return responseDTO;
    }
}