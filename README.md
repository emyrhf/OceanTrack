# OceanTrack

  Sistema de monitoramento e preservação da vida no oceano, desenvolvido em Java, JavaFX e JDBC, com arquitetura em camadas.

![Java](https://img.shields.io/badge/Java-SE-orange?logo=java)
![JavaFX](https://img.shields.io/badge/JavaFX-25-blue?logo=openjdk&logoColor=white)
![SQL](https://img.shields.io/badge/SQL-MariaDB-003545?logo=mariadb&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-MariaDB%20Driver-lightgrey)
![Gradle](https://img.shields.io/badge/Gradle-build-02303A?logo=gradle&logoColor=white)
![Status](https://img.shields.io/badge/status-finalizado-success)
![Licença](https://img.shields.io/badge/licença-MIT-green)

---

## Integrantes

- Emily Rharysa Furtado
- Felipe Lima
- Angelo Emanuel Cordesco

## Tema escolhido

Monitoramento e preservação da vida no oceano, relacionado ao ODS 14 – Vida na Água (conservação e uso sustentável dos oceanos, mares e recursos marinhos).

## Descrição do problema resolvido

Instituições de pesquisa e ONGs que atuam na conservação marinha costumam controlar seus dados em planilhas ou papel: espécies observadas, áreas protegidas, pesquisadores, expedições de campo, ocorrências de poluição e ações de limpeza. Isso dificulta acompanhar as espécies ameaçadas, consultar o histórico e organizar as ações de proteção.

O OceanTrack centraliza esses registros em um único sistema. Ele permite cadastrar, consultar, editar e excluir os dados de cada área, valida as informações digitadas e grava tudo em um banco de dados, de modo que a equipe tenha uma visão única do que está sendo monitorado e das ameaças ao ecossistema marinho.

## Entidades implementadas

O sistema tem 6 entidades, cada uma com um CRUD completo (cadastrar, listar, atualizar e remover).

| Entidade | Tabela no banco | Campos | Responsável |
|---|---|---|---|
| EspecieMarinha | `especies_marinhas` | id, nome comum, nome científico (opcional), habitat, status de conservação, data de cadastro, data de atualização | Emily Rharysa Furtado |
| AreaProtecaoMarinha | `area_protecao_marinha` | id, nome, estado, área (km²), data de cadastro, órgão responsável, ativa | Emily Rharysa Furtado |
| OcorrenciaPoluicao | `ocorrencias_poluicao` | id, tipo, local, data, gravidade, volume estimado, descrição, status | Felipe Lima |
| AcaoLimpeza | `acoes_limpeza` | id, nome, data, local, número de participantes, quantidade de lixo (kg), organizador, tipo de resíduo predominante | Felipe Lima |
| Pesquisador | `pesquisadores` | id, nome, CPF, telefone, e-mail, instituição, tipo (pesquisador ou voluntário), data de ingresso | Angelo Emanuel Cordesco |
| Expedicao | `expedicoes` | id, título, data de início, data de fim (opcional), localização, embarcação, orçamento, objetivo (opcional), status | Angelo Emanuel Cordesco |

## Arquitetura

O sistema é dividido nas camadas Fronteira, Controle, Entidade e DAO, cada uma em um pacote:

| Pacote | Camada | Função |
|---|---|---|
| `boundary` | Fronteira | Telas em JavaFX, sem FXML, com formulário e `TableView` para cada CRUD. A classe `Principal` reúne as seis telas em abas. |
| `control` | Controle | Validação dos dados, regras de cada CRUD e comunicação com o DAO. |
| `entity` | Entidade | Classes do domínio (atributos, construtores, getters e setters). |
| `dao` | Persistência | Uma interface (`...DAO`) e uma implementação com JDBC (`...DAOImpl`) para cada entidade, mais a classe `Conexao`. |

Fluxo de cada CRUD: Boundary → Control → DAO → Banco de dados, com a entidade trafegando entre as camadas.

Nos CRUDs de Espécie Marinha, Área de Proteção, Ocorrência de Poluição e Ação de Limpeza, os campos da tela são ligados ao Control por *bindings* com Properties do JavaFX, e cada campo recebe a sua própria mensagem de erro. A tabela tem coluna de ações. Os CRUDs de Pesquisador e Expedição usam formulário e `TableView` com botões de Salvar, Atualizar, Remover e Limpar, e o Control valida os dados antes de acionar o DAO.

## Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 25 (toolchain do Gradle) | Linguagem do sistema |
| JavaFX | 25 | Interface gráfica |
| Gradle | 9.8.1 (Wrapper incluído) | Build e dependências |
| MariaDB | 10 ou superior | Banco de dados |
| mariadb-java-client | 3.5.11 | Driver JDBC |

## Instruções para execução

Pré-requisitos: JDK 25 e MariaDB instalados. O Gradle não precisa ser instalado, porque o projeto traz o Wrapper. Se o JDK não for encontrado, o Gradle tenta baixá-lo automaticamente na primeira execução.

1. Criar o banco de dados. No MariaDB (pelo HeidiSQL ou pelo terminal), crie o banco `oceantrack`:

   ```sql
   CREATE DATABASE oceantrack CHARACTER SET utf8mb4;
   ```

2. Criar as tabelas. Importe o script `database/oceantrack.sql` no banco `oceantrack`. Ele cria as 6 tabelas e já traz alguns dados de exemplo:

   ```bash
   mariadb -u root -p oceantrack < database/oceantrack.sql
   ```

3. Conferir a conexão em `app/src/main/java/dao/Conexao.java`. Por padrão, o sistema usa:
   - URL: `jdbc:mariadb://localhost:3306/oceantrack`
   - usuário: `root`
   - senha: vazia

   Se o seu MariaDB usa outra senha, altere a constante `PASSWORD`.

4. Executar o sistema, na pasta raiz do projeto:

   ```bash
   # Windows
   gradlew.bat :app:run

   # Linux / Mac
   ./gradlew :app:run
   ```

   A classe principal é `boundary.Principal`. Na primeira execução, o Gradle baixa as dependências, então é necessário ter internet.

## Divisão de responsabilidades

Cada integrante desenvolveu os seus dois CRUDs em todas as camadas (Fronteira, Controle, Entidade e DAO), integrou as suas telas na classe `Principal` e acrescentou as suas tabelas no script `database/oceantrack.sql`.

| Integrante | CRUDs desenvolvidos | Responsabilidades adicionais |
|---|---|---|
| Emily Rharysa Furtado | Espécie Marinha e Área de Proteção Marinha | Documentação do projeto (este README) |
| Felipe Lima | Ocorrência de Poluição e Ação de Limpeza | Gravação do vídeo de demonstração do sistema |
| Angelo Emanuel Cordesco | Pesquisador e Expedição | |

## Vídeo de demonstração

[Colar aqui o link do YouTube, público ou não listado]

## Estrutura do projeto

```
OceanTrack/
├── app/
│   ├── build.gradle
│   └── src/main/java/
│       ├── boundary/   telas JavaFX e a classe Principal
│       ├── control/    controles com validação e regras
│       ├── entity/     classes de entidade
│       └── dao/        interfaces DAO, implementações JDBC e Conexao
├── database/
│   └── oceantrack.sql  script com as tabelas e dados de exemplo
├── gradle/             Gradle Wrapper
├── gradlew
├── gradlew.bat
├── settings.gradle
├── LICENSE
└── README.md
```

---

<p align="center">
  Projeto acadêmico desenvolvido na FATEC Zona Leste | Licença MIT
</p>