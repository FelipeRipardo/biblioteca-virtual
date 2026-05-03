package br.edu.unichristus.biblioteca_virtual.repository;

import br.edu.unichristus.biblioteca_virtual.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
}