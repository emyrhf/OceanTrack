CREATE DATABASE IF NOT EXISTS oceantrack
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

USE oceantrack;

CREATE TABLE IF NOT EXISTS especies_marinhas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome_comum VARCHAR(120) NOT NULL,
    nome_cientifico VARCHAR(150),
    habitat VARCHAR(150) NOT NULL,
    status_conservacao VARCHAR(80) NOT NULL,
    data_cadastro DATE NOT NULL,
    data_atualizacao DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS pesquisadores (
  id            BIGINT AUTO_INCREMENT PRIMARY KEY,
  nome          VARCHAR(120) NOT NULL,
  cpf           VARCHAR(14) NOT NULL UNIQUE,
  telefone      VARCHAR(20),
  email         VARCHAR(120),
  instituicao   VARCHAR(120),
  tipo          VARCHAR(20) NOT NULL,
  data_ingresso DATE        NOT NULL
  );


CREATE TABLE IF NOT EXISTS ocorrencias_poluicao (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(40) NOT NULL,
    local_ocorrencia VARCHAR(180) NOT NULL,
    data_ocorrencia DATE NOT NULL,
    gravidade VARCHAR(30) NOT NULL,
    volume_estimado DECIMAL(12,2) NOT NULL,
    descricao TEXT NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS acoes_limpeza (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    data_acao DATE NOT NULL,
    local_acao VARCHAR(180) NOT NULL,
    numero_participantes INT NOT NULL,
    quantidade_lixo_kg DECIMAL(12,2) NOT NULL,
    organizador VARCHAR(150) NOT NULL,
    tipo_residuo_predominante VARCHAR(80) NOT NULL

  CREATE TABLE IF NOT EXISTS expedicoes (
    id            BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo        VARCHAR(150)  NOT NULL,
    data_inicio   DATE          NOT NULL,
    data_fim      DATE,
    localizacao   VARCHAR(150)  NOT NULL,
    embarcacao    VARCHAR(120)  NOT NULL,
    orcamento     DECIMAL(12,2) NOT NULL,
    objetivo      VARCHAR(500),
    status        VARCHAR(20)   NOT NULL

);
