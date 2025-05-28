CREATE DATABASE IF NOT EXISTS filmes;
USE filmes;
SET NAMES UTF8MB4;

ALTER TABLE `admin` DROP FOREIGN KEY `admin_ibfk_1`;
ALTER TABLE `aluguer` DROP FOREIGN KEY `aluguer_ibfk_1`;
ALTER TABLE `aluguer` DROP FOREIGN KEY `aluguer_ibfk_2`;
ALTER TABLE `aluguer_filme` DROP FOREIGN KEY `aluguer_filme_ibfk_1`;
ALTER TABLE `aluguer_filme` DROP FOREIGN KEY `aluguer_filme_ibfk_2`;
ALTER TABLE `carrinho_filme` DROP FOREIGN KEY `carrinho_filme_ibfk_1`;
ALTER TABLE `carrinho_filme` DROP FOREIGN KEY `carrinho_filme_ibfk_2`;
ALTER TABLE `carrinho` DROP FOREIGN KEY `carrinho_ibfk_1`;
ALTER TABLE `cliente` DROP FOREIGN KEY `cliente_ibfk_1`;
ALTER TABLE `funcionario` DROP FOREIGN KEY `funcionario_ibfk_1`;
ALTER TABLE `listadesejos` DROP FOREIGN KEY `listadesejos_ibfk_1`;
ALTER TABLE `listadesejos` DROP FOREIGN KEY `listadesejos_ibfk_2`;
ALTER TABLE `recomendacao` DROP FOREIGN KEY `recomendacao_ibfk_1`;
ALTER TABLE `recomendacao_filme` DROP FOREIGN KEY `recomendacao_filme_ibfk_1`;
ALTER TABLE `recomendacao_filme` DROP FOREIGN KEY `recomendacao_filme_ibfk_2`;

DROP TABLE IF EXISTS `admin`;
DROP TABLE IF EXISTS `aluguer`;
DROP TABLE IF EXISTS `aluguer_filme`;
DROP TABLE IF EXISTS `carrinho`;
DROP TABLE IF EXISTS `carrinho_filme`;
DROP TABLE IF EXISTS `cliente`;
DROP TABLE IF EXISTS `filme`;
DROP TABLE IF EXISTS `funcionario`;
DROP TABLE IF EXISTS `listadesejos`;
DROP TABLE IF EXISTS `recomendacao`;
DROP TABLE IF EXISTS `recomendacao_filme`;
DROP TABLE IF EXISTS `tipo_utilizador`;

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

CREATE TABLE `admin`(
	`id_admin` INT(2) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome_admin` VARCHAR(30) NOT NULL,
    `email_admin` VARCHAR(30) NOT NULL UNIQUE,
    `password_admin` VARCHAR(100) NOT NULL,
    `id_tipo` INT(11) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `admin`(`id_admin`,`nome_admin`,`email_admin`,`password_admin`,`id_tipo`) VALUES
	(1,'João Neves','admin@gmail.com','$2a$10$wJKCymcnUPtxylDoQXN3Gup7/x4VGs.oxn4fTCqGbwwZsOGP4xQ..',1);
    
CREATE TABLE `aluguer` (
    `id_aluguer` INT AUTO_INCREMENT PRIMARY KEY,
    `id_cliente` INT NOT NULL,
    `data_levantamento` DATE NOT NULL,
    `data_devolucao` DATE,
    `estado` VARCHAR(50)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
    
INSERT INTO `aluguer`(`id_aluguer`,`id_cliente`,`data_levantamento`,`data_devolucao`,`estado`)VALUES
	(1,7,'2025-05-10','2025-05-15', 'devolvido'),
	(2,7,'2025-05-10','2025-05-15', 'devolvido'),
	(3,7,'2025-05-10','2025-05-15', 'devolvido'),
	(4,7,'2025-05-10','2025-05-15', 'devolvido'),
	(5,7,'2025-05-10','2025-05-15', 'devolvido'),
	(6,7,'2025-05-10','2025-05-15', 'devolvido');

CREATE TABLE `aluguer_filme` (
    `id` INT(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
    `id_aluguer` INT(11) NOT NULL,
    `id_filme` INT(11) NOT NULL
)ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE=UTF8MB4_GENERAL_CI;

CREATE TABLE `carrinho` (
    `id_carrinho` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_cliente` INT(11) NOT NULL UNIQUE,
    `criado_em` DATETIME DEFAULT CURRENT_TIMESTAMP()
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE=UTF8MB4_GENERAL_CI;

INSERT INTO `carrinho`(`id_carrinho`,`id_cliente`,`criado_em`)VALUES
	(1,1,'2025-05-15'),
	(2,2,'2025-05-15'),
	(3,3,'2025-05-15'),
	(4,4,'2025-05-15'),
	(5,7,'2025-05-15');
    
CREATE TABLE `carrinho_filme` (
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `id_carrinho` INT(11) NOT NULL,
    `id_filme` INT(11) NOT NULL
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE=UTF8MB4_GENERAL_CI;


CREATE TABLE `cliente` (
    `id_cliente` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome_cliente` VARCHAR(30) NOT NULL,
    `email_cliente` VARCHAR(30) NOT NULL UNIQUE,
    `data_de_nasc_cliente` DATE NOT NULL,
    `numero_de_telefone` VARCHAR(20) NOT NULL UNIQUE,
    `password_cliente` VARCHAR(100) NOT NULL,
    `contribuinte` VARCHAR(9) UNIQUE DEFAULT NULL,
    `id_tipo` INT(11) NOT NULL DEFAULT 3
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE=UTF8MB4_GENERAL_CI;

INSERT INTO `cliente` (`id_cliente`, `nome_cliente`, `email_cliente`, `data_de_nasc_cliente`, `numero_de_telefone`, `password_cliente`, `contribuinte`,`id_tipo`) VALUES
    (1, 'João Silva', 'cliente@gmail.com', '2000-08-03', '912345678', 'cliente123', '000000000', 3),
    (2, 'asdaas', 'eeeeee@gmail.com', '2004-08-03', '123123123', 'asdasdasd', NULL, 3),
    (3, 'Eduardo Marcelino', 'eduardosilva@gmail.com', '2005-08-03', '968128373', 'cliente123', '505777808', 3),
    (4, 'Afonso Costa', 'afonsocosta@gmail.com', '2000-01-01', '123456987', 'afonso123', '000000001', 3);

CREATE TABLE `filme` (
    `id_filme` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `titulo` VARCHAR(50) NOT NULL,
    `ano` INT(11) NOT NULL,
    `duracao_horas` INT(11) NOT NULL,
    `duracao_minutos` INT(11) NOT NULL,
    `imdb` DECIMAL(3,1) DEFAULT NULL,
    `rotten_tomatoes` VARCHAR(10) DEFAULT NULL,
    `genero1` VARCHAR(20) NOT NULL,
    `genero2` VARCHAR(20) DEFAULT NULL,
    `genero3` VARCHAR(20) DEFAULT NULL,
    `ator1` VARCHAR(50) NOT NULL,
    `ator2` VARCHAR(50) DEFAULT NULL,
    `ator3` VARCHAR(50) DEFAULT NULL,
    `sinopse` TEXT NOT NULL,
    `preco` DECIMAL(5,2) NOT NULL DEFAULT 5.00,
    `foto` VARCHAR(255) DEFAULT NULL,
    `disponivel` TINYINT(1) NOT NULL DEFAULT 1,
    `idade_recomendada` INT(11) DEFAULT 0
) ENGINE=INNODB DEFAULT CHARSET=UTF8MB4 COLLATE=UTF8MB4_GENERAL_CI;


INSERT INTO `filme` (`id_filme`, `titulo`, `ano`, `duracao_horas`, `duracao_minutos`, `imdb`, `rotten_tomatoes`, `genero1`, `genero2`, `genero3`, `ator1`, `ator2`, `ator3`, `sinopse`, `preco`, `foto`, `disponivel`, `idade_recomendada`) VALUES
    (1, 'Dune: Part 2', 2024, 2, 48, 8.5, '95', 'Sci-Fi', 'Aventura', 'Ação', 'Timothée Chalamet', 'Zendaya', 'Rebecca Ferguson', 'Paul Atreides se une a Chani e aos Fremen enquanto busca vingança contra os conspiradores que destruíram sua família. Enfrentando uma escolha entre o amor de sua vida e o destino do universo, ele deve evitar um futuro terrível que só ele pode prever.', 5.00, 'imagens/Duna.jpg', 0, 16),
    (2, 'Interstellar', 2014, 2, 49, 8.0, '87', 'Épico', 'Sci-Fi', 'Drama', 'Matthew McConaughey', 'Anne Hathaway', 'Jessica Chastain', 'When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.', 4.50, 'imagens/71-uZ6tkY8L._AC_SL1500_.jpg', 0, 12),
    (3, 'Fight Club', 1999, 2, 19, 8.0, '96', 'Thriller', 'Crime', 'Drama', 'Edward Norton', 'Brad Pitt', 'Helena Bonham Carter', 'Um trabalhador de escritório e um fabricante de sabonetes formam um clube de luta clandestino que evolui para algo muito maior.', 6.00, 'imagens/fight club.jpeg', 1, 18),
    (4, 'La La Land', 2016, 2, 0, 8.0, '82', 'Musical', 'Drama', 'Comédia', 'Ryan Gosling', 'Emma Stone', '', 'Um pianista é uma frustrada actora se apaixonam enquanto tentam trabalhar por seus futuros.', 5.80, 'imagens/la la land.jpg', 0, 12),
    (5, 'DeadPool & Wolverine', 2024, 2, 40, 22.0, '22', 'asd', '', '', 'asd', '', '', 'dsa', 4.00, 'imagens/deadpool.jpeg', 1, 18),
    (6, 'Evil Dead 2', 1987, 1, 25, 7.0, '89', 'Terror', 'Comédia', '', 'Bruce Campbell', ' Sarah Berry', ' Denise Bixler', 'O único sobrevivente de uma avalanche de espíritos possuidores de carne entra em uma cabana com um grupo de estranhos, enquanto os demônios continuam seu ataque.', 4.00, 'imagens/EvilDead2.jpg', 1, 18),
    (7, 'A Minecraft Movie', 2025, 1, 41, 6.0, '88', 'Comédia', '', '', 'Jack Black', 'Jason Momoa', 'Danielle Brooks', 'Quatro desajustados são transportados para um bizarro país das maravilhas cúbico onde impera a imaginação. Para voltar para casa, eles terão que dominar este mundo enquanto embarcam em uma missão com um experiente construtor imprevisível.', 8.00, 'imagens/minecraft.jpg', 0, 3),
    (8, 'Pantera Negra', 2018, 2, 14, 7.0, '79', 'Super Heróis', 'Aventura', '', 'Chadwick Boseman', 'Michael B. Jordan', 'Lupita Nyong\'o', 'T\'Challa, herdeiro do reino oculto, mas avançado de Wakanda, deve dar um passo adiante para conduzir seu povo a um novo futuro e deve enfrentar um adversário do passado de seu país.', 4.00, 'imagens/Pantera Negra.jpg', 1, 14),
    (9, 'Baby Driver', 2017, 1, 53, 7.0, '92', 'Ação', 'Crime', 'Drama', 'Ansel Elgort', 'Jon Hamm', 'Lily James', 'Depois de ser obrigado a trabalhar para um chefe do crime, um jovem condutor se ve envolvido num assalto destinado a fracassar.', 4.90, 'imagens/Baby Driver.jpg', 1, 14),
    (10, 'Pulp Fiction', 1996, 2, 34, 8.9, '92', 'Crime', 'Humor Negro', 'Gangster', 'John Travolta', 'Samuel L Jackson', 'Bruce Willis', 'As vidas de dois assassinos da máfia, um boxeador, um gângster e sua esposa, e um par de bandidos se entrelaçam em quatro histórias de violência e redenção.', 6.50, 'imagens/Pulp_Fiction.jpg', 1, 18);

CREATE TABLE `funcionario` (
    `id_funcionario` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `nome_funcionario` VARCHAR(30) NOT NULL,
    `email_funcionario` VARCHAR(30) NOT NULL UNIQUE,
    `telefone` VARCHAR(20) DEFAULT NULL UNIQUE,
    `password_funcionario` VARCHAR(100) NOT NULL,
    `perm_leitura` TINYINT(1) DEFAULT 0,
    `perm_criacao` TINYINT(1) DEFAULT 0,
    `perm_edicao` TINYINT(1) DEFAULT 0,
    `id_tipo` INT(11) NOT NULL DEFAULT 2,
    `foto` VARCHAR(255) NOT NULL DEFAULT 'imagens/default.png'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `funcionario` (`id_funcionario`, `nome_funcionario`, `email_funcionario`, `telefone`, `password_funcionario`, `perm_leitura`, `perm_criacao`, `perm_edicao`,`id_tipo`,`foto`) VALUES
    (1, 'Afonso Neves Costa ', 'afonsocosta@gmail.com', '123456789', 'Afonso123', 0, 1, 1, 2, 'imagens/default.jpg'),
    (2, 'Eduardo Silva', 'eduardosilva.boss@gmail.com', '957 017 163', 'MarcTheKid', 1, 1, 1, 2, 'imagens/default.jpg'),
    (3, 'Bruno Fernandes', 'brunaocanhao@hotmail.com', '1223333333', 'cenas123', 1, 1, 1, 2, 'imagens/default.jpg'),
    (4, 'sdasd', 'adsdsa@ho', '2134432', 'asdasdasd', 0, 1, 0, 2, 'imagens/default.jpg');
    
CREATE TABLE `listadesejos` (
  `id_lista_desejo` int(11) AUTO_INCREMENT PRIMARY KEY,
  `id_cliente` int(11) NOT NULL,
  `id_filme` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `recomendacao`(
	`id_recomendacao` INT(11) AUTO_INCREMENT PRIMARY KEY,
    `id_funcionario` INT(11) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `recomendacao_filme`(
	`id` INT(11) AUTO_INCREMENT PRIMARY KEY,
    `id_recomendacao` INT(11) NOT NULL,
    `id_filme` INT(11) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


CREATE TABLE `tipo_utilizador` (
    `id_tipo` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `descricao` VARCHAR(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `tipo_utilizador` (`id_tipo`, `descricao`) VALUES
    (1, 'Administrador'),
    (2, 'Funcionário'),
    (3, 'Cliente');

ALTER TABLE `admin`
	ADD CONSTRAINT `admin_ibfk_1` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_utilizador`(`id_tipo`);
    
ALTER TABLE `aluguer`
    ADD CONSTRAINT `aluguer_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente`(`id_cliente`) ON DELETE CASCADE;

ALTER TABLE `aluguer_filme`
	ADD KEY (`id_aluguer`),
    ADD KEY (`id_filme`),
	ADD CONSTRAINT `aluguer_filme_ibfk_1` FOREIGN KEY (`id_aluguer`) REFERENCES `aluguer`(`id_aluguer`)  ON DELETE CASCADE,
	ADD CONSTRAINT `aluguer_filme_ibfk_2` FOREIGN KEY (`id_filme`) REFERENCES `filme`(`id_filme`)  ON DELETE CASCADE,
	ADD CONSTRAINT `aluguer_filmes` UNIQUE (`id_aluguer`, `id_filme`);
    
ALTER TABLE `carrinho`
	ADD CONSTRAINT `carrinho_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`)  ON DELETE CASCADE;

ALTER TABLE `carrinho_filme`
	ADD KEY (`id_carrinho`),
	ADD KEY (`id_filme`),
	ADD CONSTRAINT `carrinho_filme_ibfk_1` FOREIGN KEY (`id_carrinho`) REFERENCES `carrinho` (`id_carrinho`)  ON DELETE CASCADE,
	ADD CONSTRAINT `carrinho_filme_ibfk_2` FOREIGN KEY (`id_filme`) REFERENCES `filme` (`id_filme`) ON DELETE CASCADE;

 ALTER TABLE `cliente`
	ADD CONSTRAINT `cliente_ibfk_1` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_utilizador`(`id_tipo`);
    
 ALTER TABLE `funcionario`
	ADD CONSTRAINT `funcionario_ibfk_1` FOREIGN KEY (`id_tipo`) REFERENCES `tipo_utilizador`(`id_tipo`);

ALTER TABLE `listadesejos`
  ADD UNIQUE KEY `id_cliente` (`id_cliente`,`id_filme`),
  ADD CONSTRAINT `listadesejos_ibfk_1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`) ON DELETE CASCADE,
  ADD CONSTRAINT `listadesejos_ibfk_2` FOREIGN KEY (`id_filme`) REFERENCES `filme` (`id_filme`) ON DELETE CASCADE;
  
ALTER TABLE `recomendacao`
	ADD CONSTRAINT `recomendacao_ibfk_1` FOREIGN KEY (`id_funcionario`) REFERENCES `funcionario`(`id_funcionario`) ON DELETE CASCADE;
    
ALTER TABLE `recomendacao_filme`
	ADD CONSTRAINT `recomendacao_filme_ibfk_1`FOREIGN KEY (`id_recomendacao`) REFERENCES `recomendacao`(`id_recomendacao`) ON DELETE CASCADE,
    ADD CONSTRAINT `recomendacao_filme_ibfk_2`FOREIGN KEY (`id_filme`) REFERENCES `filme` (`id_filme`) ON DELETE CASCADE;
