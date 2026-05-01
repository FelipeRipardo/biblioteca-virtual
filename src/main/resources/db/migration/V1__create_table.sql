-- V1__create_tables.sql

-- 1 - Tabela Categoria
CREATE TABLE categoria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    area_conhecimento VARCHAR(100) NOT NULL,
    departamento_responsavel VARCHAR(100)
);

-- 2 - Tabela Autor
CREATE TABLE autor (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    biografia TEXT,
    nacionalidade VARCHAR(100),
    data_nascimento DATE
);

-- 3 - Tabela Livro
CREATE TABLE livro (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    resumo TEXT,
    ano_publicacao INTEGER,
    serie_recomendada VARCHAR(100),
    total_paginas INTEGER NOT NULL,
    isbn VARCHAR(20),
    autor_id BIGINT NOT NULL REFERENCES autor(id),
    categoria_id BIGINT NOT NULL REFERENCES categoria(id)
);

-- 4 - Tabela Avaliação
CREATE TABLE avaliacao (
    id BIGSERIAL PRIMARY KEY,
    apelido_leitor VARCHAR(100) NOT NULL,
    nota INTEGER NOT NULL CHECK (nota >= 1 AND nota <= 5),
    comentario TEXT,
    data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    livro_id BIGINT NOT NULL REFERENCES livro(id) ON DELETE CASCADE
);

-- 5 - Tabela Sessão de Leitura (A funcionalidade extra)
CREATE TABLE sessao_leitura (
    id BIGSERIAL PRIMARY KEY,
    token_dispositivo UUID NOT NULL UNIQUE,
    ultima_pagina_lida INTEGER NOT NULL DEFAULT 0,
    data_inicio TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_ultimo_acesso TIMESTAMP,
    livro_id BIGINT NOT NULL REFERENCES livro(id) ON DELETE CASCADE
);