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