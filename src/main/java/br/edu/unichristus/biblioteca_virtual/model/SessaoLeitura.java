package br.edu.unichristus.biblioteca_virtual.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessao_leitura")
public class SessaoLeitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token_dispositivo", nullable = false, unique = true)
    private String tokenDispositivo;

    @Column(name = "ultima_pagina_lida", nullable = false)
    private Integer ultimaPaginaLida = 0;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_ultimo_acesso")
    private LocalDateTime dataUltimoAcesso;

    //Relacionamento N:1 - Várias sessões podem ser criadas para o mesmo livro.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;


    //LifeCycle Callbacks

    //Gera o token e a data de início na primeira vez que o aluno acessar o livro.
    @PrePersist
    protected void onCreate(){
        this.dataInicio = LocalDateTime.now();
        if (this.tokenDispositivo == null){
            this.tokenDispositivo = UUID.randomUUID().toString();
        }
        this.ultimaPaginaLida = 1; // Sempre começa na página 1
        this.dataInicio = LocalDateTime.now();
        this.dataUltimoAcesso = this.dataInicio;
    }

    //Atualiza a data automaticamente toda vez que ele ler uma página nova e salva.
    @PreUpdate
    protected void onUpdate(){
        this.dataUltimoAcesso = LocalDateTime.now();
    }

}
