SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Banco de dados: `oceantrack`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `acoes_limpeza`
--

CREATE TABLE `acoes_limpeza` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `data_acao` date NOT NULL,
  `local_acao` varchar(180) NOT NULL,
  `numero_participantes` int(11) NOT NULL,
  `quantidade_lixo_kg` decimal(12,2) NOT NULL,
  `organizador` varchar(150) NOT NULL,
  `tipo_residuo_predominante` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `acoes_limpeza`
--

INSERT INTO `acoes_limpeza` (`id`, `nome`, `data_acao`, `local_acao`, `numero_participantes`, `quantidade_lixo_kg`, `organizador`, `tipo_residuo_predominante`) VALUES
(1, 'Mutirão Praia Limpa Ipanema', '2026-11-02', 'Praia De Ipanema', 45, '128.50', 'Instituto Mar Limpo', 'Plástico'),
(2, 'Operação Mangue Vivo', '2026-11-04', 'Manguezal De Cubatão', 30, '340.00', 'Coletivo EcoLitoral', 'Entulho E Plástico'),
(3, 'Limpeza Costeira Guarujá', '2026-11-06', 'Praia Da Enseada', 60, '215.80', 'Prefeitura Do Guarujá', 'Plástico'),
(4, 'Mergulho Sustentável Noronha', '2026-11-08', 'Porto De Santo Antônio', 18, '64.20', 'Projeto Tamar', 'Redes De Pesca'),
(5, 'Dia De Praia Sem Lixo', '2026-11-10', 'Praia De Copacabana', 52, '190.40', 'Ondas Verdes', 'Bitucas E Plástico'),
(6, 'Resgate Ambiental Baía Norte', '2026-11-11', 'Baía Norte Florianópolis', 25, '112.00', 'Associação Marinha Sul', 'Isopor'),
(7, 'Limpeza De Costão Rochoso', '2026-11-13', 'Praia De Maresias', 15, '48.70', 'Guardiões Do Oceano', 'Vidro E Metal'),
(8, 'Ação Ecológica Ponta Verde', '2026-11-14', 'Praia De Ponta Verde', 40, '156.30', 'Instituto Biota', 'Plástico'),
(9, 'Mutirão Ilha Do Mel', '2026-11-16', 'Praia De Encantadas', 22, '73.90', 'Amigos Da Ilha', 'Plástico'),
(10, 'Operação Recifes Limpos', '2026-11-17', 'Porto De Galinhas', 28, '95.50', 'EcoRecife', 'Microplástico E Latas'),
(11, 'Limpeza Da Orla De Santos', '2026-11-19', 'Praia Do Gonzaga', 75, '284.60', 'Secretaria De Meio Ambiente', 'Plástico'),
(12, 'Faxina Marinha Arraial', '2026-11-20', 'Praia Dos Anjos', 34, '142.10', 'Projeto Azul', 'Redes E Cordas'),
(13, 'Mutirão Juvenil Praia Grande', '2026-11-21', 'Canto Do Forte', 48, '167.00', 'Escoteiros Do Mar', 'Plástico'),
(14, 'Coleta Subaquática Ilhabela', '2026-11-22', 'Praia Do Perequê', 16, '58.40', 'Mergulhadores Unidos', 'Metal E Plástico'),
(15, 'Ação Costeira Barra Da Tijuca', '2026-11-23', 'Quebra-Mar Da Barra', 65, '231.90', 'Movimento Rio Limpo', 'Plástico');

-- --------------------------------------------------------

--
-- Estrutura da tabela `area_protecao_marinha`
--

CREATE TABLE `area_protecao_marinha` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(150) NOT NULL,
  `estado` varchar(100) NOT NULL,
  `area_km2` int(11) NOT NULL,
  `data_cadastro` date NOT NULL,
  `orgao_responsavel` varchar(150) NOT NULL,
  `ativa` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `area_protecao_marinha`
--

INSERT INTO `area_protecao_marinha` (`id`, `nome`, `estado`, `area_km2`, `data_cadastro`, `orgao_responsavel`, `ativa`) VALUES
(1, 'Parque Nacional Marinho Dos Abrolhos', 'Bahia', 913, '2026-11-23', 'Icmbio', 1),
(2, 'Parque Nacional Marinho De Fernando De Noronha', 'Pernambuco', 112, '2026-11-23', 'Icmbio', 1),
(3, 'Reserva Extrativista Marinha Prainha Do Canto Verde', 'Ceará', 298, '2026-11-23', 'Conselho Gestor Local', 0),
(4, 'Área De Proteção Ambiental Municipal De Guarapari', 'Espírito Santo', 215, '2026-11-23', 'Secretaria Municipal De Meio Ambiente', 1),
(5, 'Refúgio De Vida Silvestre Do Arquipélago De Alcatrazes', 'São Paulo', 674, '2026-11-23', 'Icmbio', 0),
(6, 'Parque Estadual Marinho Do Parcel De Manuel Luís', 'Maranhão', 459, '2026-11-23', 'Secretaria De Estado Do Meio Ambiente', 1),
(7, 'Reserva De Desenvolvimento Sustentável Mamirauá', 'Amazonas', 11240, '2026-11-23', 'Instituto De Desenvolvimento Sustentável', 1),
(8, 'Área De Proteção Ambiental Marinha Do Litoral Norte', 'São Paulo', 1800, '2026-11-23', 'Secretaria De Meio Ambiente', 1),
(9, 'Área De Proteção Ambiental Da Baleia Franca', 'Santa Catarina', 1561, '2026-11-23', 'Icmbio', 0),
(10, 'Reserva Extrativista Marinha De Arraial Do Cabo', 'Rio De Janeiro', 567, '2026-11-23', 'Icmbio', 0),
(11, 'Reserva Biológica Do Atol Das Rocas', 'Rio Grande Do Norte', 362, '2026-11-24', 'Icmbio', 1),
(12, 'Estação Ecológica De Tupinambás', 'São Paulo', 24, '2026-11-24', 'Icmbio', 1),
(13, 'Área De Proteção Ambiental Costa Dos Corais', 'Alagoas', 4135, '2026-11-24', 'Icmbio', 1),
(14, 'Parque Estadual Da Ilha Anchieta', 'São Paulo', 8, '2026-11-24', 'Fundação Florestal', 1),
(15, 'Reserva Extrativista Marinha Do Delta Do Parnaíba', 'Piauí', 270, '2026-11-24', 'Icmbio', 0),
(16, 'Área De Proteção Ambiental De Guaraqueçaba', 'Paraná', 2824, '2026-11-24', 'Instituto Água E Terra', 1),
(17, 'Monumento Natural Das Ilhas Cagarras', 'Rio De Janeiro', 1, '2026-11-24', 'Icmbio', 0),
(18, 'Reserva De Desenvolvimento Sustentável Ponta Do Tubarão', 'Rio Grande Do Norte', 129, '2026-11-24', 'Idema', 1);

-- --------------------------------------------------------

--
-- Estrutura da tabela `especies_marinhas`
--

CREATE TABLE `especies_marinhas` (
  `id` bigint(20) NOT NULL,
  `nome_comum` varchar(120) NOT NULL,
  `nome_cientifico` varchar(150) DEFAULT NULL,
  `habitat` varchar(150) NOT NULL,
  `status_conservacao` varchar(80) NOT NULL,
  `data_cadastro` date NOT NULL,
  `data_atualizacao` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `especies_marinhas`
--

INSERT INTO `especies_marinhas` (`id`, `nome_comum`, `nome_cientifico`, `habitat`, `status_conservacao`, `data_cadastro`, `data_atualizacao`) VALUES
(1, 'Baleia Azul', 'Balaenoptera Musculus', 'Oceanos', 'Ameaçada', '2026-11-22', '2026-11-23'),
(2, 'Baleia Orca', 'Orcinus Orca', 'Oceanos', 'Ativa', '2026-11-22', '2026-11-23'),
(3, 'Cavalo Marinho', '', 'Oceano Atlântico', 'Ameaçada', '2026-11-22', '2026-11-23'),
(4, 'Peixe Palhaço', 'Amphiprion Ocellaris', 'Recifes De Coral', 'Em extinção', '2026-11-22', '2026-11-23'),
(5, 'Boto Cor De Rosa', 'Inia Geoffrensis', 'Rios De Água Doce', 'Em extinção', '2026-11-22', '2026-11-23'),
(6, 'Arraia', '', 'Oceanos Tropicais E Subtropicais', 'Ameaçada', '2026-11-22', '2026-11-23'),
(7, 'Polvo-de-anéis-azuis', 'Hapalochlaena Maculosa', 'Oceano Pacífico E Índico', 'Ativa', '2026-11-22', '2026-11-23'),
(8, 'Agua Viva', '', 'Oceanos', 'Ativa', '2026-11-22', '2026-11-23'),
(9, 'Tartaruga Marinha', '', 'Oceano Atlantico', 'Em extinção', '2026-11-22', '2026-11-23'),
(10, 'Tartaruga-verde', '', 'Recifes E Águas Costeiras', 'Ameaçada', '2026-11-23', '2026-11-23'),
(11, 'Polvo-dumbo', 'Grimpoteuthis', 'Águas Profundas', 'Ativa', '2026-11-22', '2026-11-23'),
(12, 'Tubarão-baleia', 'Rhincodon Typus', 'Oceanos Tropicais E Subtropicais', 'Em extinção', '2026-11-22', '2026-11-23'),
(13, 'Peixe-boi-marinho', 'Trichechus Manatus', 'Águas Costeiras E Estuários', 'Ameaçada', '2026-11-23', '2026-11-23'),
(14, 'Tubarão-branco', '', 'Oceanos', 'Ameaçada', '2026-11-23', '2026-11-23'),
(15, 'Estrela-do-mar', '', 'Fundos Marinhos E Recifes', 'Ativa', '2026-11-23', '2026-11-23'),
(16, 'Pinguim-imperador', 'Aptenodytes Forsteri', 'Antártida', 'Ameaçada', '2026-11-23', '2026-11-23');

-- --------------------------------------------------------

--
-- Estrutura da tabela `expedicoes`
--

CREATE TABLE `expedicoes` (
  `id` bigint(20) NOT NULL,
  `titulo` varchar(150) NOT NULL,
  `data_inicio` date NOT NULL,
  `data_fim` date DEFAULT NULL,
  `localizacao` varchar(150) NOT NULL,
  `embarcacao` varchar(120) NOT NULL,
  `orcamento` decimal(12,2) NOT NULL,
  `objetivo` varchar(500) DEFAULT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `expedicoes`
--

INSERT INTO `expedicoes` (`id`, `titulo`, `data_inicio`, `data_fim`, `localizacao`, `embarcacao`, `orcamento`, `objetivo`, `status`) VALUES
(1, 'Monitoramento De Corais Abrolhos', '2026-10-01', '2026-10-15', 'Arquipélago Dos Abrolhos', 'Navio Oceanográfico Cruzeiro Do Sul', '45000.00', 'Mapear branqueamento de recifes de coral', 'CONCLUIDA'),
(2, 'Rota Das Baleias Jubarte', '2026-11-10', '2026-11-30', 'Litoral Sul Da Bahia', 'Embarcação Horizonte Azul', '32500.50', 'Contagem populacional e registro acústico de cetáceos', 'EM ANDAMENTO'),
(3, 'Coleta De Microplásticos Santos', '2026-12-05', '2026-12-12', 'Baía De Santos', 'Lancha Pesquisa I', '12800.00', 'Analisar concentração de microplásticos na coluna d\'água', 'PLANEJADA'),
(4, 'Mapeamento Bentônico Alcatrazes', '2026-09-15', '2026-09-25', 'Arquipélago De Alcatrazes', 'Catamarã Estrela Do Mar', '28900.00', 'Catalogar esponjas e invertebrados marinhos', 'CONCLUIDA'),
(5, 'Expedição Atol Das Rocas', '2027-01-10', '2027-01-28', 'Atol Das Rocas', 'Navio Polar Almirante Maximiano', '85000.00', 'Avaliar nidificação de aves marinhas e tartarugas', 'PLANEJADA'),
(6, 'Estudo De Correntes Noronha', '2026-11-03', '2026-11-18', 'Fernando De Noronha', 'Veleiro Científico Gaia', '39400.75', 'Medir temperatura e salinidade em águas profundas', 'CANCELADA'),
(7, 'Levantamento Ictiológico Litoral Norte', '2026-11-18', '2026-11-29', 'Ilhabela E Ubatuba', 'Barco Pesqueiro Adaptado Netuno', '19500.00', 'Monitorar espécies de peixes recifais ameaçadas', 'EM ANDAMENTO'),
(8, 'Diagnóstico Ambiental Guanabara', '2026-08-08', '2026-08-20', 'Baía De Guanabara', 'Lancha Oceânica Sol', '15200.00', 'Coletar sedimentos para análise de metais pesados', 'CONCLUIDA'),
(9, 'Expedição Parcel De Manuel Luís', '2027-02-14', '2027-02-26', 'Costa Do Maranhão', 'Navio Hidroceanográfico Taurus', '62000.00', 'Realizar batimetria e mapeamento dos bancos de corais', 'PLANEJADA'),
(10, 'Monitoramento Tartarugas Marinhas', '2026-11-20', '2026-12-05', 'Praia Do Forte', 'Embarcação Tamar II', '24750.00', 'Marcar fêmeas reprodutoras e acompanhar desovas', 'EM ANDAMENTO');

-- --------------------------------------------------------

--
-- Estrutura da tabela `ocorrencias_poluicao`
--

CREATE TABLE `ocorrencias_poluicao` (
  `id` bigint(20) NOT NULL,
  `tipo` varchar(40) NOT NULL,
  `local_ocorrencia` varchar(180) NOT NULL,
  `data_ocorrencia` date NOT NULL,
  `gravidade` varchar(30) NOT NULL,
  `volume_estimado` decimal(12,2) NOT NULL,
  `descricao` text NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `ocorrencias_poluicao`
--

INSERT INTO `ocorrencias_poluicao` (`id`, `tipo`, `local_ocorrencia`, `data_ocorrencia`, `gravidade`, `volume_estimado`, `descricao`, `status`) VALUES
(1, 'Óleo', 'Baía De Guanabara', '2026-11-10', 'Crítica', '45.80', 'Vazamento de óleo combustível próximo ao terminal portuário', 'Em análise'),
(2, 'Plástico', 'Praia Do Futuro', '2026-11-12', 'Alta', '18.50', 'Acúmulo de garrafas PET e redes de pesca trazidas pela maré', 'Aberta'),
(3, 'Óleo', 'Canal De São Sebastião', '2026-11-14', 'Alta', '12.00', 'Mancha oleosa avistada após manobra de abastecimento de navio', 'Em contenção'),
(4, 'Esgoto', 'Lagoa Rodrigo De Freitas', '2026-11-15', 'Crítica', '120.00', 'Despejo irregular de efluentes domésticos sem tratamento', 'Em análise'),
(5, 'Microplástico', 'Praia De Boa Viagem', '2026-11-16', 'Média', '3.20', 'Alta concentração de pellets plásticos na faixa de areia', 'Monitorada'),
(6, 'Esgoto', 'Estuário De Santos', '2026-11-17', 'Alta', '85.40', 'Extravasamento da rede coletora próximo ao manguezal', 'Aberta'),
(7, 'Plástico', 'Ilha Grande', '2026-11-18', 'Baixa', '4.70', 'Resíduos de embalagens descartáveis deixados por embarcações', 'Concluída'),
(8, 'Óleo', 'Praia Do Cassino', '2026-11-19', 'Média', '9.50', 'Fragmentos de piche dispersos ao longo de 3 km da costa', 'Em contenção'),
(9, 'Microplástico', 'Baía De Todos Os Santos', '2026-11-20', 'Baixa', '1.80', 'Amostras de água apontam presença de microfibras sintéticas', 'Monitorada'),
(10, 'Esgoto', 'Praia De Camburi', '2026-11-21', 'Média', '32.00', 'Lançamento clandestino em galeria pluvial desaguando no mar', 'Aberta'),
(11, 'Plástico', 'Arquipélago De Fernando De Noronha', '2026-11-22', 'Baixa', '2.10', 'Lixo oceânico trazido por correntes marítimas na baía', 'Concluída'),
(12, 'Óleo', 'Porto De Paranaguá', '2026-11-23', 'Crítica', '64.30', 'Rompimento de mangote durante operação de carga', 'Em contenção');

-- --------------------------------------------------------

--
-- Estrutura da tabela `pesquisadores`
--

CREATE TABLE `pesquisadores` (
  `id` bigint(20) NOT NULL,
  `nome` varchar(120) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `email` varchar(120) DEFAULT NULL,
  `instituicao` varchar(120) DEFAULT NULL,
  `tipo` varchar(20) NOT NULL,
  `data_ingresso` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `pesquisadores`
--

INSERT INTO `pesquisadores` (`id`, `nome`, `cpf`, `telefone`, `email`, `instituicao`, `tipo`, `data_ingresso`) VALUES
(1, 'Dra. Helena Vasconcelos', '109.876.543-21', '(11) 98888-1122', 'helena.vasconcelos@usp.br', 'USP', 'PESQUISADOR', '2026-11-24'),
(2, 'Dra. Mariana Costa Silva', '123.456.789-01', '(11) 98765-4321', 'mariana.silva@usp.br', 'USP', 'PESQUISADOR', '2025-03-10'),
(3, 'Dr. Lucas Andrade Pereira', '234.567.890-12', '(21) 99876-5432', 'lucas.pereira@ufrj.br', 'UFRJ', 'PESQUISADOR', '2025-04-15'),
(4, 'Camila Rocha Fernandes', '345.678.901-23', '(13) 98123-4567', 'camila.rocha@gmail.com', 'Instituto Mar Azul', 'VOLUNTARIO', '2026-01-20'),
(5, 'Prof. Rafael Mendes Oliveira', '456.789.012-34', '(48) 99123-9876', 'rafael.mendes@ufsc.br', 'UFSC', 'PESQUISADOR', '2024-08-05'),
(6, 'Beatriz Almeida Santos', '567.890.123-45', '(71) 98877-6655', 'beatriz.santos@ufba.br', 'UFBA', 'PESQUISADOR', '2025-06-12'),
(7, 'Thiago Nogueira Lima', '678.901.234-56', '(81) 99654-3210', 'thiago.lima@hotmail.com', 'Projeto Coral Vivo', 'VOLUNTARIO', '2026-02-14'),
(8, 'Dra. Fernanda Ribeiro Gomes', '789.012.345-67', '(12) 98234-5678', 'fernanda.gomes@inpe.br', 'INPE', 'PESQUISADOR', '2024-11-01'),
(9, 'Gabriel Martins Souza', '890.123.456-78', '(27) 99345-6789', 'gabriel.souza@gmail.com', 'Instituto Baleia Jubarte', 'VOLUNTARIO', '2026-03-18'),
(10, 'Juliana Carvalho Barbosa', '901.234.567-89', '(85) 98456-7890', 'juliana.barbosa@ufc.br', 'UFC', 'PESQUISADOR', '2025-05-22'),
(11, 'Rodrigo Teixeira Dias', '012.345.678-90', '(53) 99456-1234', 'rodrigo.dias@furg.br', 'FURG', 'PESQUISADOR', '2025-07-09'),
(12, 'Larissa Monteiro Castro', '112.233.445-56', '(11) 97111-2233', 'larissa.castro@gmail.com', 'ONG Guardiões Do Mar', 'VOLUNTARIO', '2026-04-02'),
(13, 'Dr. Henrique Batista Araújo', '223.344.556-67', '(21) 97222-3344', 'henrique.araujo@fiocruz.br', 'Fiocruz', 'PESQUISADOR', '2024-09-19'),
(14, 'Amanda Cardoso Vieira', '334.455.667-78', '(41) 97333-4455', 'amanda.vieira@ufpr.br', 'UFPR', 'PESQUISADOR', '2025-10-11'),
(15, 'Bruno Ferreira Campos', '445.566.778-89', '(84) 97444-5566', 'bruno.campos@yahoo.com', 'Projeto Tamar', 'VOLUNTARIO', '2026-05-07'),
(16, 'Patrícia Azevedo Correia', '556.677.889-90', '(91) 97555-6677', 'patricia.correia@ufpa.br', 'UFPA', 'PESQUISADOR', '2025-02-28'),
(17, 'Diego Nascimento Freitas', '667.788.990-01', '(13) 97666-7788', 'diego.freitas@gmail.com', 'Instituto Gremar', 'VOLUNTARIO', '2026-06-15'),
(18, 'Dra. Vanessa Pires Moreira', '778.899.001-12', '(11) 97777-8899', 'vanessa.moreira@unesp.br', 'UNESP', 'PESQUISADOR', '2024-12-03'),
(19, 'Felipe Duarte Ramos', '889.900.112-23', '(82) 97888-9900', 'felipe.ramos@gmail.com', 'Instituto Biota', 'VOLUNTARIO', '2026-07-21'),
(20, 'Carolina Santana Lopes', '990.011.223-34', '(98) 97999-0011', 'carolina.lopes@ufma.br', 'UFMA', 'PESQUISADOR', '2025-08-30'),
(21, 'Matheus Guimarães Pinto', '102.304.506-78', '(22) 97010-2030', 'matheus.pinto@gmail.com', 'EcoCosta', 'VOLUNTARIO', '2026-08-10');
