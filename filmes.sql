-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 14-Abr-2025 às 15:08
-- Versão do servidor: 10.4.32-MariaDB
-- versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `filmes`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `carrinho`
--

CREATE TABLE `carrinho` (
  `id_carrinho` int(11) NOT NULL,
  `id_cliente` int(11) NOT NULL,
  `criado_em` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `carrinho_filme`
--

CREATE TABLE `carrinho_filme` (
  `id` int(11) NOT NULL,
  `id_carrinho` int(11) NOT NULL,
  `id_filme` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cliente`
--

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL,
  `nome_cliente` varchar(30) NOT NULL,
  `email_cliente` varchar(30) NOT NULL,
  `data_de_nasc_cliente` date NOT NULL,
  `numero_de_telefone` varchar(20) NOT NULL,
  `password_cliente` varchar(30) NOT NULL,
  `contribuinte` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `cliente`
--

INSERT INTO `cliente` (`id_cliente`, `nome_cliente`, `email_cliente`, `data_de_nasc_cliente`, `numero_de_telefone`, `password_cliente`, `contribuinte`) VALUES
(1, 'João Silva', 'cliente@gmail.com', '2000-08-03', '912345678', 'cliente123', '000000000'),
(2, 'asdaas', 'eeeeee@gmail.com', '2004-08-03', '123123123', 'asdasdasd', NULL),
(3, 'Eduardo Marcelino', 'eduardosilva@gmail.com', '2005-08-03', '968128373', 'cliente123', '505777808'),
(4, 'Afonso Costa', 'afonsocosta@gmail.com', '2000-01-01', '123456987', 'afonso123', '000000000'),
(5, 'Cliente Menor', 'clientemenor@cliente', '2007-05-20', '34565435454', 'menor123', NULL);

-- --------------------------------------------------------

--
-- Estrutura da tabela `filme`
--

CREATE TABLE `filme` (
  `id_filme` int(11) NOT NULL,
  `titulo` varchar(50) NOT NULL,
  `ano` int(11) NOT NULL,
  `duracao_horas` int(11) NOT NULL,
  `duracao_minutos` int(11) NOT NULL,
  `imdb` decimal(3,1) DEFAULT NULL,
  `rotten_tomatoes` varchar(10) DEFAULT NULL,
  `genero1` varchar(20) NOT NULL,
  `genero2` varchar(20) DEFAULT NULL,
  `genero3` varchar(20) DEFAULT NULL,
  `ator1` varchar(50) NOT NULL,
  `ator2` varchar(50) DEFAULT NULL,
  `ator3` varchar(50) DEFAULT NULL,
  `sinopse` text NOT NULL,
  `preco` decimal(5,2) NOT NULL DEFAULT 5.00,
  `foto` varchar(255) DEFAULT NULL,
  `disponivel` tinyint(1) NOT NULL DEFAULT 1,
  `idade_recomendada` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `filme`
--

INSERT INTO `filme` (`id_filme`, `titulo`, `ano`, `duracao_horas`, `duracao_minutos`, `imdb`, `rotten_tomatoes`, `genero1`, `genero2`, `genero3`, `ator1`, `ator2`, `ator3`, `sinopse`, `preco`, `foto`, `disponivel`, `idade_recomendada`) VALUES
(1, 'Dune: Part 2', 2024, 2, 48, 8.5, '95', 'Sci-Fi', 'Aventura', 'Ação', 'Timothée Chalamet', 'Zendaya', 'Rebecca Ferguson', 'Paul Atreides se une a Chani e aos Fremen enquanto busca vingança contra os conspiradores que destruíram sua família. Enfrentando uma escolha entre o amor de sua vida e o destino do universo, ele deve evitar um futuro terrível que só ele pode prever.', 5.00, 'imagens/Duna.jpg', 0, 16),
(2, 'Interstellar', 2014, 2, 49, 8.0, '87', 'Épico', 'Sci-Fi', 'Drama', 'Matthew McConaughey', 'Anne Hathaway', 'Jessica Chastain', 'When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.', 4.50, 'imagens/71-uZ6tkY8L._AC_SL1500_.jpg', 0, 0),
(3, 'Fight Club', 1999, 2, 19, 8.0, '96', 'Thriller', 'Crime', 'Drama', 'Edward Norton', 'Brad Pitt', 'Helena Bonham Carter', 'Um trabalhador de escritório e um fabricante de sabonetes formam um clube de luta clandestino que evolui para algo muito maior.', 6.00, 'imagens/fight club.jpeg', 1, 0),
(4, 'La La Land', 2016, 2, 0, 8.0, '82', 'Musical', 'Drama', 'Comédia', 'Ryan Gosling', 'Emma Stone', '', 'Um pianista é uma frustrada actora se apaixonam enquanto tentam trabalhar por seus futuros.', 5.80, 'imagens/la la land.jpg', 0, 0),
(7, 'DeadPool & Wolverine', 2024, 2, 40, 22.0, '22', 'asd', '', '', 'asd', '', '', 'dsa', 4.00, 'imagens/deadpool.jpeg', 1, 0),
(8, 'Evil Dead 2', 1987, 1, 25, 7.0, '89', 'Terror', 'Comédia', '', 'Bruce Campbell', ' Sarah Berry', ' Denise Bixler', 'O único sobrevivente de uma avalanche de espíritos possuidores de carne entra em uma cabana com um grupo de estranhos, enquanto os demônios continuam seu ataque.', 4.00, 'imagens/EvilDead2.jpg', 1, 0),
(9, 'A Minecraft Movie', 2025, 1, 41, 6.0, '88', 'Comédia', '', '', 'Jack Black', 'Jason Momoa', 'Danielle Brooks', 'Quatro desajustados são transportados para um bizarro país das maravilhas cúbico onde impera a imaginação. Para voltar para casa, eles terão que dominar este mundo enquanto embarcam em uma missão com um experiente construtor imprevisível.', 8.00, 'imagens/minecraft.jpg', 0, 3),
(10, 'Pantera Negra', 2018, 2, 14, 7.3, '79', 'Super Heróis', 'Aventura', 'Ação', 'Chadwick Boseman', 'Michael B. Jordan', 'Lupita Nyong\'o', 'T\'Challa, herdeiro do reino oculto, mas avançado de Wakanda, deve dar um passo adiante para conduzir seu povo a um novo futuro e deve enfrentar um adversário do passado de seu país.', 4.00, 'imagens/Pantera Negra.jpg', 1, 14),
(11, 'Baby Driver', 2017, 1, 53, 7.5, '92', 'Ação', 'Crime', 'Drama', 'Ansel Elgort', 'Jon Hamm', 'Lily James', 'Depois de ser obrigado a trabalhar para um chefe do crime, um jovem condutor se ve envolvido num assalto destinado a fracassar.', 4.90, 'imagens/Baby Driver.jpg', 1, 14),
(13, 'Pulp Fiction', 1994, 2, 34, 8.9, '92', 'Crime', 'Humor Negro', 'Gangster', 'John Travolta', 'Samuel L Jackson', 'Bruce Willis', 'As vidas de dois assassinos da máfia, um boxeador, um gângster e sua esposa, e um par de bandidos se entrelaçam em quatro histórias de violência e redenção.', 6.50, 'imagens/Pulp_Fiction.jpg', 1, 18);

-- --------------------------------------------------------

--
-- Estrutura da tabela `funcionario`
--

CREATE TABLE `funcionario` (
  `id_funcionario` int(11) NOT NULL,
  `nome_funcionario` varchar(30) NOT NULL,
  `email_funcionario` varchar(30) NOT NULL,
  `telefone` varchar(20) DEFAULT NULL,
  `password_funcionario` varchar(30) NOT NULL,
  `perm_leitura` tinyint(1) DEFAULT 0,
  `perm_criacao` tinyint(1) DEFAULT 0,
  `perm_edicao` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `funcionario`
--

INSERT INTO `funcionario` (`id_funcionario`, `nome_funcionario`, `email_funcionario`, `telefone`, `password_funcionario`, `perm_leitura`, `perm_criacao`, `perm_edicao`) VALUES
(1, 'Afonso Neves Costa ', 'afonsocosta@gmail.com', '123456789', 'Afonso123', 0, 1, 1),
(2, 'Eduardo Silva', 'eduardosilva.boss@gmail.com', '957 017 163', 'MarcTheKid', 1, 1, 1),
(3, 'Bruno Fernandes', 'brunaocanhao@hotmail.com', '1223333333', 'cenas123', 1, 1, 1),
(4, 'sdasd', 'adsdsa@ho', '2134432', 'asdasdasd', 0, 1, 0);

-- --------------------------------------------------------

--
-- Estrutura da tabela `tipo_utilizador`
--

CREATE TABLE `tipo_utilizador` (
  `id_tipo` int(11) NOT NULL,
  `descricao` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Extraindo dados da tabela `tipo_utilizador`
--

INSERT INTO `tipo_utilizador` (`id_tipo`, `descricao`) VALUES
(1, 'Administrador'),
(2, 'Funcionário'),
(3, 'Cliente'),
(4, 'Utilizador_Sem_Registo');

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `carrinho`
--
ALTER TABLE `carrinho`
  ADD PRIMARY KEY (`id_carrinho`),
  ADD KEY `id_cliente` (`id_cliente`);

--
-- Índices para tabela `carrinho_filme`
--
ALTER TABLE `carrinho_filme`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_carrinho` (`id_carrinho`),
  ADD KEY `id_filme` (`id_filme`);

--
-- Índices para tabela `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id_cliente`);

--
-- Índices para tabela `filme`
--
ALTER TABLE `filme`
  ADD PRIMARY KEY (`id_filme`);

--
-- Índices para tabela `funcionario`
--
ALTER TABLE `funcionario`
  ADD PRIMARY KEY (`id_funcionario`),
  ADD UNIQUE KEY `email_funcionario` (`email_funcionario`);

--
-- Índices para tabela `tipo_utilizador`
--
ALTER TABLE `tipo_utilizador`
  ADD PRIMARY KEY (`id_tipo`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `carrinho`
--
ALTER TABLE `carrinho`
  MODIFY `id_carrinho` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `carrinho_filme`
--
ALTER TABLE `carrinho_filme`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de tabela `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id_cliente` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de tabela `filme`
--
ALTER TABLE `filme`
  MODIFY `id_filme` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT de tabela `funcionario`
--
ALTER TABLE `funcionario`
  MODIFY `id_funcionario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `carrinho`
--
ALTER TABLE `carrinho`
  ADD CONSTRAINT `carrinho_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`);

--
-- Limitadores para a tabela `carrinho_filme`
--
ALTER TABLE `carrinho_filme`
  ADD CONSTRAINT `carrinho_filme_ibfk_1` FOREIGN KEY (`id_carrinho`) REFERENCES `carrinho` (`id_carrinho`),
  ADD CONSTRAINT `carrinho_filme_ibfk_2` FOREIGN KEY (`id_filme`) REFERENCES `filme` (`id_filme`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
