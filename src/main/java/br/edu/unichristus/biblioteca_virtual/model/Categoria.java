package br.edu.unichristus.biblioteca_virtual.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "area_conhecimento", nullable = false, length = 100)
    private String areaConhecimento;

    @Column(name = "departamento_responsavel", length = 100)
    private String departamentoResponsavel;

}
