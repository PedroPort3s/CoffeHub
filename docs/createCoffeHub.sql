-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema coffehub
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `coffehub` ;

-- -----------------------------------------------------
-- Schema coffehub
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `coffehub` DEFAULT CHARACTER SET utf8 ;
USE `coffehub` ;

-- -----------------------------------------------------
-- Table `coffehub`.`Categoria`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Categoria` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Categoria` (
  `cod` INT(5) UNSIGNED ZEROFILL NOT NULL,
  `nome` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`cod`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `coffehub`.`Pessoa`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Pessoa` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Pessoa` (
  `cod` INT NOT NULL AUTO_INCREMENT,
  `documento` VARCHAR(14) NOT NULL,
  `telefone` VARCHAR(45) NULL DEFAULT NULL,
  `nome` VARCHAR(45) NOT NULL,
  `endereco` VARCHAR(100) NULL DEFAULT NULL,
  `email` VARCHAR(100) NULL,
  PRIMARY KEY (`cod`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE UNIQUE INDEX `documento_UNIQUE` ON `coffehub`.`Pessoa` (`documento` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `coffehub`.`Cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Cliente` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Cliente` (
  `cod_Pessoa` INT NOT NULL,
  `data_nascimento` DATETIME NOT NULL,
  PRIMARY KEY (`cod_Pessoa`),
  CONSTRAINT `fk_Cliente_Pessoa1`
    FOREIGN KEY (`cod_Pessoa`)
    REFERENCES `coffehub`.`Pessoa` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `coffehub`.`Fornecedor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Fornecedor` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Fornecedor` (
  `cod_Pessoa` INT NOT NULL,
  `data_Contrato` DATETIME NOT NULL,
  PRIMARY KEY (`cod_Pessoa`),
  CONSTRAINT `fk_Fornecedor_Pessoa1`
    FOREIGN KEY (`cod_Pessoa`)
    REFERENCES `coffehub`.`Pessoa` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `coffehub`.`Funcionario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Funcionario` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Funcionario` (
  `cod_Pessoa` INT NOT NULL,
  `salario` DECIMAL NOT NULL,
  `data_Contratacao` DATETIME NOT NULL,
  `data_Demissao` DATETIME NULL,
  PRIMARY KEY (`cod_Pessoa`),
  CONSTRAINT `fk_Funcionario_Pessoa1`
    FOREIGN KEY (`cod_Pessoa`)
    REFERENCES `coffehub`.`Pessoa` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `coffehub`.`Compra`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Compra` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Compra` (
  `cod` INT(10) UNSIGNED ZEROFILL NOT NULL,
  `data_origem` DATE NOT NULL,
  `date_recebido` DATE NULL DEFAULT NULL,
  `valor_total` DECIMAL(18,2) NOT NULL,
  `status` VARCHAR(1) NOT NULL,
  `cod_Fornecedor` INT NOT NULL,
  `cod_Funcionario` INT NOT NULL,
  PRIMARY KEY (`cod`, `cod_Fornecedor`, `cod_Funcionario`),
  CONSTRAINT `fk_Compra_Fornecedor1`
    FOREIGN KEY (`cod_Fornecedor`)
    REFERENCES `coffehub`.`Fornecedor` (`cod_Pessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Compra_Funcionario1`
    FOREIGN KEY (`cod_Funcionario`)
    REFERENCES `coffehub`.`Funcionario` (`cod_Pessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Compra_Fornecedor1_idx` ON `coffehub`.`Compra` (`cod_Fornecedor` ASC) VISIBLE;

CREATE INDEX `fk_Compra_Funcionario1_idx` ON `coffehub`.`Compra` (`cod_Funcionario` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `coffehub`.`Acesso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Acesso` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Acesso` (
  `cod` INT(5) UNSIGNED ZEROFILL NOT NULL,
  `form` VARCHAR(45) NULL DEFAULT NULL,
  `funcao` VARCHAR(200) NULL DEFAULT NULL,
  PRIMARY KEY (`cod`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `coffehub`.`Produto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Produto` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Produto` (
  `cod_produto` INT(10) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `nome_produto` VARCHAR(45) NOT NULL,
  `valor_un` DECIMAL(5,2) NOT NULL,
  `qtd_atual` DOUBLE NOT NULL,
  `Categoria_cod` INT(5) UNSIGNED ZEROFILL NOT NULL,
  PRIMARY KEY (`cod_produto`),
  CONSTRAINT `fk_Produto_Categoria1`
    FOREIGN KEY (`Categoria_cod`)
    REFERENCES `coffehub`.`Categoria` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Produto_Categoria1_idx` ON `coffehub`.`Produto` (`Categoria_cod` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `coffehub`.`Venda`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`Venda` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`Venda` (
  `cod` INT(10) UNSIGNED ZEROFILL NOT NULL,
  `valor_total` DECIMAL(18,3) NOT NULL,
  `data_origem` DATE NOT NULL,
  `data_confirmacao` DATE NULL DEFAULT NULL,
  `status` VARCHAR(1) NOT NULL,
  `cod_Cliente` INT NOT NULL,
  `cod_Funcionario` INT NOT NULL,
  PRIMARY KEY (`cod`, `cod_Cliente`, `cod_Funcionario`),
  CONSTRAINT `fk_Venda_Cliente1`
    FOREIGN KEY (`cod_Cliente`)
    REFERENCES `coffehub`.`Cliente` (`cod_Pessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venda_Funcionario1`
    FOREIGN KEY (`cod_Funcionario`)
    REFERENCES `coffehub`.`Funcionario` (`cod_Pessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Venda_Cliente1_idx` ON `coffehub`.`Venda` (`cod_Cliente` ASC) VISIBLE;

CREATE INDEX `fk_Venda_Funcionario1_idx` ON `coffehub`.`Venda` (`cod_Funcionario` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `coffehub`.`FuncionarioAcesso`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`FuncionarioAcesso` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`FuncionarioAcesso` (
  `cod_Funcionario` INT NOT NULL,
  `cod_Acesso` INT(5) UNSIGNED ZEROFILL NOT NULL,
  `usuarioAcessa` VARCHAR(1) NOT NULL,
  PRIMARY KEY (`cod_Funcionario`, `cod_Acesso`),
  CONSTRAINT `fk_Funcionario_has_Acesso_Funcionario1`
    FOREIGN KEY (`cod_Funcionario`)
    REFERENCES `coffehub`.`Funcionario` (`cod_Pessoa`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Funcionario_has_Acesso_Acesso1`
    FOREIGN KEY (`cod_Acesso`)
    REFERENCES `coffehub`.`Acesso` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Funcionario_has_Acesso_Acesso1_idx` ON `coffehub`.`FuncionarioAcesso` (`cod_Acesso` ASC) VISIBLE;

CREATE INDEX `fk_Funcionario_has_Acesso_Funcionario1_idx` ON `coffehub`.`FuncionarioAcesso` (`cod_Funcionario` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `coffehub`.`VendaItem`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`VendaItem` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`VendaItem` (
  `cod_Venda` INT(10) UNSIGNED ZEROFILL NOT NULL,
  `cod_Produto` INT(10) UNSIGNED ZEROFILL NOT NULL,
  `qtdVenda` DECIMAL NOT NULL,
  `valor_venda` DECIMAL(18,2) NOT NULL,
  `num_item` INT NOT NULL,
  PRIMARY KEY (`cod_Venda`, `cod_Produto`),
  CONSTRAINT `fk_Venda_has_Produto_Venda1`
    FOREIGN KEY (`cod_Venda`)
    REFERENCES `coffehub`.`Venda` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Venda_has_Produto_Produto1`
    FOREIGN KEY (`cod_Produto`)
    REFERENCES `coffehub`.`Produto` (`cod_produto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Venda_has_Produto_Produto1_idx` ON `coffehub`.`VendaItem` (`cod_Produto` ASC) VISIBLE;

CREATE INDEX `fk_Venda_has_Produto_Venda1_idx` ON `coffehub`.`VendaItem` (`cod_Venda` ASC) VISIBLE;


-- -----------------------------------------------------
-- Table `coffehub`.`CompraProduto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `coffehub`.`CompraProduto` ;

CREATE TABLE IF NOT EXISTS `coffehub`.`CompraProduto` (
  `cod_Compra` INT(10) UNSIGNED ZEROFILL NOT NULL,
  `cod_Produto` INT(10) UNSIGNED ZEROFILL NOT NULL,
  `qtdVenda` VARCHAR(45) NOT NULL,
  `valor_venda` DECIMAL(18,2) NOT NULL,
  `num_item` INT NOT NULL,
  PRIMARY KEY (`cod_Compra`, `cod_Produto`),
  CONSTRAINT `fk_Compra_has_Produto_Compra1`
    FOREIGN KEY (`cod_Compra`)
    REFERENCES `coffehub`.`Compra` (`cod`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Compra_has_Produto_Produto1`
    FOREIGN KEY (`cod_Produto`)
    REFERENCES `coffehub`.`Produto` (`cod_produto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;

CREATE INDEX `fk_Compra_has_Produto_Produto1_idx` ON `coffehub`.`CompraProduto` (`cod_Produto` ASC) VISIBLE;

CREATE INDEX `fk_Compra_has_Produto_Compra1_idx` ON `coffehub`.`CompraProduto` (`cod_Compra` ASC) VISIBLE;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
