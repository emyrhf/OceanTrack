# OceanTrack

Sistema de monitoramento e preservação da vida no oceano, desenvolvido em Java, JavaFX e JDBC, com arquitetura em camadas.

![Java](https://img.shields.io/badge/Java-SE-orange?logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-25-blue?logo=openjdk&logoColor=white)
![SQL](https://img.shields.io/badge/SQL-MariaDB-003545?logo=mariadb&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-MariaDB%20Driver-lightgrey)
![Gradle](https://img.shields.io/badge/Gradle-build-02303A?logo=gradle&logoColor=white)
![Status](https://img.shields.io/badge/status-concluído-success)
![Licença](https://img.shields.io/badge/licença-MIT-green)


## Integrantes

- Emily Rharysa Furtado
- Felipe Lima
- Ângelo Emanuel Cordesco

## Tema escolhido

Monitoramento e preservação da vida no oceano, relacionado ao ODS 14 – Vida na Água (conservação e uso sustentável dos oceanos, mares e recursos marinhos).

## Problema resolvido

Instituições de pesquisa e ONGs que atuam na conservação marinha costumam controlar dados sobre espécies, áreas protegidas, expedições e ocorrências de poluição em planilhas ou papel. Isso dificulta o acompanhamento das espécies ameaçadas, a consulta ao histórico e a organização das ações de proteção.

O OceanTrack centraliza esses registros em um sistema único, com cadastro, consulta, edição e exclusão dos dados, validação das informações digitadas e gravação em banco de dados.

## Entidades implementadas

| Entidade | Campos |
|---|---|
| EspecieMarinha | id, nome comum, nome científico (opcional), habitat, status de conservação, data de cadastro, data da última atualização |
| AreaProtecaoMarinha | id, nome, estado, área (km²), órgão responsável, ativa, data de cadastro |
| [Entidade do integrante B] | [campos] |
| [Entidade do integrante B] | [campos] |
| [Entidade do integrante C] | [campos] |
| [Entidade do integrante C] | [campos] |

## Arquitetura

O projeto segue o BCE adaptado, dividido em camadas:

| Pacote | Camada | Função |
|---|---|---|
| `boundary` | Fronteira | Telas em JavaFX (sem FXML), com formulário e `TableView` com coluna de ações (Atualizar e Excluir). A classe `Principal` reúne as telas em abas. |
| `control` | Controle | Properties ligadas aos campos da tela por bindings, validação dos dados e mensagens de erro para o usuário. |
| `entity` | Entidade | Classes do domínio (atributos, getters e setters). |
| `dao` | Persistência | Interface DAO e implementação com JDBC (`...DAO` e `...DAOImpl`), mais a classe `Conexao`. |

## Tecnologias

- Java 25 (toolchain configurada no Gradle)
- JavaFX 25
- Gradle (Wrapper incluído, não precisa instalar o Gradle)
- MariaDB e driver JDBC `mariadb-java-client` 3.5.11

## Instruções para execução

1. Instalar o JDK 25 e o MariaDB. Se o JDK não estiver instalado, o Gradle tenta baixá-lo automaticamente na primeira execução.
2. Criar o banco de dados e as tabelas executando o script `database/oceantrack.sql` no MariaDB (por exemplo, pelo HeidiSQL ou pelo terminal: `mariadb -u root -p < database/oceantrack.sql`).
3. Conferir a conexão em `app/src/main/java/dao/Conexao.java`. Por padrão, o sistema usa:
   - URL: `jdbc:mariadb://localhost:3306/oceantrack`
   - usuário: `root`
   - senha: vazia

   Se o seu MariaDB usa outra senha, altere a constante `PASSWORD`.
4. Executar o sistema, na pasta raiz do projeto:
   - `gradlew.bat :app:run`

   A classe principal é `boundary.Principal`. Na primeira execução, o Gradle baixa as dependências, então é necessário ter internet.

## Divisão de responsabilidades

| Integrante | Entidades | Camadas |
|---|---|---|
| Emily Furtado | EspecieMarinha e AreaProtecaoMarinha | Fronteira, Controle, Entidade e DAO de cada uma |
| [Nome do integrante B] | [Entidade 1] e [Entidade 2] | Fronteira, Controle, Entidade e DAO de cada uma |
| [Nome do integrante C] | [Entidade 1] e [Entidade 2] | Fronteira, Controle, Entidade e DAO de cada uma |

## Vídeo de demonstração

[Colar aqui o link do YouTube, público ou não listado]

