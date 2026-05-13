package br.edu.unichristus.biblioteca_virtual.repository;

import br.edu.unichristus.biblioteca_virtual.model.SessaoLeitura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessaoLeituraRepository extends JpaRepository<SessaoLeitura, Long> {

    //A busca principal do Front-end será usando a chave do dispositivo
    Optional<SessaoLeitura> findByTokenDispositivo(String tokenDispositivo);

}