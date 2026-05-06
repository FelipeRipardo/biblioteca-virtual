package br.edu.unichristus.biblioteca_virtual.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "apelido_leitor", nullable = false, length = 100)
    private String apelidoLeitor;

    @Column(nullable = false)
    private Integer nota;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    @Column(name = "data_avaliacao")
    private LocalDateTime dataAvaliacao;

    //Relacionamento N:1 - Várias avaliações podem pertencer a um único livro.
    //Uso do FetchType.LAZY no lugar do EAGER é por performance, já que se for utilizar o EAGER o Spring
    //tentará fazer um Join muito grande no banco, o que poderá afetar em consultas futuras caso o banco se expanda muito.
    //Com o LAZY, o Spring traz apenas a Avaliacao, escondendo o Livro que só é chamado caso deixe explícito.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    //Preenche a data automaticamente no momento em que o registro for salvo
    @PrePersist //É um gatilho que salva em questão de milissegundos a última ação do usuário, sem necessidade de setar a data manualmente.
    protected void onCreate(){
        this.dataAvaliacao = LocalDateTime.now();
    }
}
