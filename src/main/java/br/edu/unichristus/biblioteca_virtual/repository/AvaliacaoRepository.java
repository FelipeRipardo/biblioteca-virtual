package br.edu.unichristus.biblioteca_virtual.repository;

import br.edu.unichristus.biblioteca_virtual.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    //Busca todas as avaliações que tenham uma nota específica (ex: todas as notas 5)
    List<Avaliacao> findByNota(Integer nota);

    //Busca todas as avaliações atreladas a um Livro específico
    List<Avaliacao> findByLivroId(Long livroId);

}