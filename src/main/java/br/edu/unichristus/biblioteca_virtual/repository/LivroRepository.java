package br.edu.unichristus.biblioteca_virtual.repository;

import br.edu.unichristus.biblioteca_virtual.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    //Utilização do Spring Data JPA para uma Derived Query Methods.
    //Finalidade: Realizar consultas sem necessidade de escrever o código SQL puro, o Spring Data JPA já faz isso através de anotações.
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findBySerieRecomendadaContainingIgnoreCase(String serieRecomendada);

}