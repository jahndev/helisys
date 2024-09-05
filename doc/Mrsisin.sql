-- MySQL Script generated by MySQL Workbench
-- Fri Aug 30 17:53:00 2024
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema sisin
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema sisin
-- -----------------------------------------------------
DROP DATABASE IF EXISTS `sisin`;

CREATE SCHEMA IF NOT EXISTS `sisin` DEFAULT CHARACTER SET utf8 ;
-- Drop the user 'sisin' if it exists
DROP USER IF EXISTS 'sisin'@'localhost';

-- Create the user 'sisin' with password 'sisin'
CREATE USER 'sisin'@'localhost' IDENTIFIED BY 'sisin';

-- Grant all privileges on the 'sisin' schema to the 'sisin' user
GRANT ALL PRIVILEGES ON sisin.* TO 'sisin'@'localhost';

-- Apply the changes by flushing privileges
FLUSH PRIVILEGES;

-- -----------------------------------------------------
-- Schema sisin
-- -----------------------------------------------------
USE `sisin` ;

-- -----------------------------------------------------
-- Table `sisin`.`ALMACEN_ESTANTES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`ALMACEN_ESTANTES` (
  `amt_id` INT NOT NULL AUTO_INCREMENT,
  `amt_numero` INT NOT NULL,
  `amt_nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`amt_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`ALMACEN_REPISAS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`ALMACEN_REPISAS` (
  `amr_id` INT NOT NULL AUTO_INCREMENT,
  `amr_amt_id` INT NOT NULL,
  `amr_descripcion` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`amr_id`, `amr_amt_id`),
  CONSTRAINT `fk_ALMACEN_REPISAS_ALMACEN_ESTANTES1`
    FOREIGN KEY (`amr_amt_id`)
    REFERENCES `sisin`.`ALMACEN_ESTANTES` (`amt_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`GRADOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`GRADOS` (
  `gdo_id` INT NOT NULL AUTO_INCREMENT,
  `gdo_nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`gdo_id`),
  UNIQUE INDEX `gdo_nombre_UNIQUE` (`gdo_nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`BRIGADAS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`BRIGADAS` (
  `bga_id` INT NOT NULL AUTO_INCREMENT,
  `bga_nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`bga_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`UNIDADES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`UNIDADES` (
  `und_id` INT NOT NULL AUTO_INCREMENT,
  `und_bga_id` INT NOT NULL,
  `und_nombre` VARCHAR(45) NOT NULL,
  `und_telefono` VARCHAR(45) NOT NULL,
  `und_fax` VARCHAR(45) NOT NULL,
  `und_comandante_nombre` VARCHAR(45) NOT NULL,
  `und_direccion` VARCHAR(200) NOT NULL,
  `und_departamento` VARCHAR(45) NOT NULL,
  `und_provincia` VARCHAR(45) NOT NULL,
  `und_ciudad` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`und_id`, `und_bga_id`),
  CONSTRAINT `fk_UNIDADES_BRIGADAS1`
    FOREIGN KEY (`und_bga_id`)
    REFERENCES `sisin`.`BRIGADAS` (`bga_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`ESCUADRONES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`ESCUADRONES` (
  `edn_id` INT NOT NULL AUTO_INCREMENT,
  `edn_und_id` INT NOT NULL,
  `edn_nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`edn_id`, `edn_und_id`),
  CONSTRAINT `fk_ESCUADRONES_UNIDADES1`
    FOREIGN KEY (`edn_und_id`)
    REFERENCES `sisin`.`UNIDADES` (`und_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`USUARIOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`USUARIOS` (
  `usr_id` INT NOT NULL AUTO_INCREMENT,
  `usr_gdo_id` INT NOT NULL,
  `usr_edn_id` INT NOT NULL,
  `usr_ct_identidad` INT NOT NULL,
  `usr_ct_militar` INT NOT NULL,
  `usr_nombre` VARCHAR(45) NOT NULL,
  `usr_apellido` VARCHAR(45) NOT NULL,
  `usr_direccion` VARCHAR(225) NOT NULL,
  `usr_telefono` VARCHAR(45) NOT NULL,
  `usr_cargo` VARCHAR(45) NOT NULL,
  `usr_foto` VARCHAR(225) NOT NULL,
  `usr_user` VARCHAR(45) NOT NULL,
  `usr_password` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`usr_id`, `usr_gdo_id`),
  UNIQUE INDEX `usr_user_UNIQUE` (`usr_user` ASC) VISIBLE,
  UNIQUE INDEX `usr_password_UNIQUE` (`usr_password` ASC) VISIBLE,
  UNIQUE INDEX `usr_ct_identidad_UNIQUE` (`usr_ct_identidad` ASC) VISIBLE,
  UNIQUE INDEX `usr_ct_militar_UNIQUE` (`usr_ct_militar` ASC) VISIBLE,
  CONSTRAINT `fk_USUARIOS_GRADOS1`
    FOREIGN KEY (`usr_gdo_id`)
    REFERENCES `sisin`.`GRADOS` (`gdo_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_USUARIOS_ESCUADRONES1`
    FOREIGN KEY (`usr_edn_id`)
    REFERENCES `sisin`.`ESCUADRONES` (`edn_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`PROVEEDORES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`PROVEEDORES` (
  `pve_id` INT NOT NULL AUTO_INCREMENT,
  `pve_nombre` VARCHAR(45) NOT NULL,
  `pve_telefono` VARCHAR(45) NOT NULL,
  `pve_fax` VARCHAR(45) NOT NULL,
  `pve_email` VARCHAR(45) NOT NULL,
  `pve_direccion` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`pve_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`PEDIDOS_COMPRAS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`PEDIDOS_COMPRAS` (
  `pca_id` INT NOT NULL AUTO_INCREMENT,
  `pca_usr_id` INT NOT NULL,
  `pca_pve_id` INT NOT NULL,
  `pca_descripcion` VARCHAR(45) NOT NULL,
  `pca_fecha_pedido` DATE NOT NULL,
  `pca_fecha_envio` DATE NOT NULL,
  `pca_fecha_entrega` DATE NOT NULL,
  `pca_fecha_prometida` DATE NOT NULL,
  `pca_direccion_envio` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`pca_id`, `pca_usr_id`, `pca_pve_id`),
  CONSTRAINT `fk_PEDIDOS_COMPRAS_USUARIOS1`
    FOREIGN KEY (`pca_usr_id`)
    REFERENCES `sisin`.`USUARIOS` (`usr_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PEDIDOS_COMPRAS_PROVEEDORES1`
    FOREIGN KEY (`pca_pve_id`)
    REFERENCES `sisin`.`PROVEEDORES` (`pve_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`TIPO_PRODUCTOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`TIPO_PRODUCTOS` (
  `tpo_ido` INT NOT NULL AUTO_INCREMENT,
  `tpo_nombre_tipo` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`tpo_ido`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`PRODUCTOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`PRODUCTOS` (
  `pro_id` INT NOT NULL AUTO_INCREMENT,
  `pro_numero_parte` VARCHAR(45) NOT NULL,
  `pro_amr_id` INT NOT NULL,
  `pro_tpo_ido` INT NOT NULL,
  `pro_nombre` VARCHAR(45) NOT NULL,
  `pro_numero_parte_alterno` VARCHAR(45) NULL,
  `pro_numero_serie` VARCHAR(45) NULL,
  `pro_unidades` INT NOT NULL,
  `pro_fecha_vencimiento` DATE NOT NULL,
  `pro_tipo_documento` INT NULL,
  PRIMARY KEY (`pro_id`, `pro_numero_parte`, `pro_amr_id`, `pro_tpo_ido`),
  UNIQUE INDEX `pro_numero_serie_UNIQUE` (`pro_numero_serie` ASC) VISIBLE,
  UNIQUE INDEX `pro_numero_serie_alterno_UNIQUE` (`pro_numero_parte_alterno` ASC) VISIBLE,
  CONSTRAINT `fk_PRODUCTOS_ALMACEN_REPISAS1`
    FOREIGN KEY (`pro_amr_id`)
    REFERENCES `sisin`.`ALMACEN_REPISAS` (`amr_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PRODUCTOS_TIPO_PRODCUTOS1`
    FOREIGN KEY (`pro_tpo_ido`)
    REFERENCES `sisin`.`TIPO_PRODUCTOS` (`tpo_ido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`AERONAVES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`AERONAVES` (
  `anv_id` INT NOT NULL AUTO_INCREMENT,
  `anv_matricula` VARCHAR(45) NOT NULL,
  `anv_nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`anv_id`),
  UNIQUE INDEX `anv_matricula_UNIQUE` (`anv_matricula` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`CONTACTO_PROVEEDORES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`CONTACTO_PROVEEDORES` (
  `cve_id` INT NOT NULL AUTO_INCREMENT,
  `cpe_pve_id` INT NOT NULL,
  `cve_nombre` VARCHAR(45) NOT NULL,
  `cpe_telefono` VARCHAR(45) NOT NULL,
  `cpe_email` VARCHAR(45) NOT NULL,
  `cpe_url` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`cve_id`, `cpe_pve_id`),
  UNIQUE INDEX `cpe_email_UNIQUE` (`cpe_email` ASC) VISIBLE,
  CONSTRAINT `fk_CONTACTO_PROVEEDORES_PROVEEDORES1`
    FOREIGN KEY (`cpe_pve_id`)
    REFERENCES `sisin`.`PROVEEDORES` (`pve_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`TRASACCION_TIPO_EVENTO`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`TRASACCION_TIPO_EVENTO` (
  `tte_id` INT NOT NULL AUTO_INCREMENT,
  `tte_nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`tte_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`TRASACCION_EVENTOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`TRASACCION_EVENTOS` (
  `tvo_id` INT NOT NULL AUTO_INCREMENT,
  `tvo_tte_id` INT NOT NULL,
  `tvo_fecha` DATE NOT NULL,
  `AERONAVES_anv_id` INT NULL,
  PRIMARY KEY (`tvo_id`, `tvo_tte_id`),
  CONSTRAINT `fk_TRASACCION_EVENTOS_TRASACCION_TIPO_EVENTO1`
    FOREIGN KEY (`tvo_tte_id`)
    REFERENCES `sisin`.`TRASACCION_TIPO_EVENTO` (`tte_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TRASACCION_EVENTOS_AERONAVES1`
    FOREIGN KEY (`AERONAVES_anv_id`)
    REFERENCES `sisin`.`AERONAVES` (`anv_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`TRANSACCIONES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`TRANSACCIONES` (
  `tce_id` INT NOT NULL AUTO_INCREMENT,
  `tce_usr_id` INT NOT NULL,
  `tce_tvo_id` INT NOT NULL,
  `tce_fecha_transaccion` DATE NOT NULL,
  `tce_observaciones` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`tce_id`, `tce_usr_id`, `tce_tvo_id`),
  CONSTRAINT `fk_TRANSACCIONES_USUARIOS1`
    FOREIGN KEY (`tce_usr_id`)
    REFERENCES `sisin`.`USUARIOS` (`usr_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TRANSACCIONES_TRASACCION_EVENTOS1`
    FOREIGN KEY (`tce_tvo_id`)
    REFERENCES `sisin`.`TRASACCION_EVENTOS` (`tvo_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`TRANSACCIONES_PRODUCTOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`TRANSACCIONES_PRODUCTOS` (
  `tco_id` INT NOT NULL AUTO_INCREMENT,
  `tco_tce_id` INT NOT NULL,
  `tco_pro_id` INT NOT NULL,
  `tco_unidades` INT NOT NULL,
  PRIMARY KEY (`tco_id`, `tco_tce_id`, `tco_pro_id`),
  CONSTRAINT `fk_TRANSACCIONES_PRODUCTOS_TRANSACCIONES1`
    FOREIGN KEY (`tco_tce_id`)
    REFERENCES `sisin`.`TRANSACCIONES` (`tce_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TRANSACCIONES_PRODUCTOS_PRODUCTOS1`
    FOREIGN KEY (`tco_pro_id`)
    REFERENCES `sisin`.`PRODUCTOS` (`pro_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`PEDIDOS_PRODUCTOS`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`PEDIDOS_PRODUCTOS` (
  `ppt_id` INT NOT NULL AUTO_INCREMENT,
  `ppt_pca_id` INT NOT NULL,
  `ppt_pro_id` INT NOT NULL,
  `ppt_cantidad` INT NOT NULL,
  `ppt_precio_unitario` INT NOT NULL,
  PRIMARY KEY (`ppt_id`, `ppt_pca_id`, `ppt_pro_id`),
  CONSTRAINT `fk_PEDIDOS_PRODUCTOS_PEDIDOS_COMPRAS1`
    FOREIGN KEY (`ppt_pca_id`)
    REFERENCES `sisin`.`PEDIDOS_COMPRAS` (`pca_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_PEDIDOS_PRODUCTOS_PRODUCTOS1`
    FOREIGN KEY (`ppt_pro_id`)
    REFERENCES `sisin`.`PRODUCTOS` (`pro_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `sisin`.`MODELO_AERONAVES`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `sisin`.`MODELO_AERONAVES` (
  `mre` INT NOT NULL,
  `mre_anv_id` INT NOT NULL,
  `mre_nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`mre`, `mre_anv_id`),
  UNIQUE INDEX `mre_nombre_UNIQUE` (`mre_nombre` ASC) VISIBLE,
  CONSTRAINT `fk_MODELO_AERONAVES_AERONAVES1`
    FOREIGN KEY (`mre_anv_id`)
    REFERENCES `sisin`.`AERONAVES` (`anv_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
