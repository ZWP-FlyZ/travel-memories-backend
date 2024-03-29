-- MySQL Script generated by MySQL Workbench
-- Tue Dec 10 12:11:31 2019
-- Model: New Model    Version: 1.3
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema travel_memories
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema travel_memories
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `travel_memories` DEFAULT CHARACTER SET utf8 ;
USE `travel_memories` ;

-- -----------------------------------------------------
-- Table `travel_memories`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_memories`.`user` (
  `u_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `u_name` VARCHAR(45) CHARACTER SET 'utf8' NOT NULL COMMENT '用户名',
  `u_password` VARCHAR(60) CHARACTER SET 'utf8' NOT NULL,
  `u_role` CHAR(10) NOT NULL,
  `u_status` INT(1) NOT NULL DEFAULT 0,
  `u_regtime` BIGINT(13) NOT NULL COMMENT '注册时间',
  `u_lasttime` BIGINT(13) NOT NULL COMMENT '最近登录时间',
  PRIMARY KEY (`u_id`),
  UNIQUE INDEX `u_name_UNIQUE` (`u_name` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_memories`.`event_point`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_memories`.`event_point` (
  `ep_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
  `u_id` INT(10) NOT NULL,
  `ep_title` VARCHAR(50) NOT NULL DEFAULT '' COMMENT '标题',
  `ep_addr` VARCHAR(64) NOT NULL DEFAULT '' COMMENT '地址',
  `ep_lng` DECIMAL(10,7) NOT NULL DEFAULT 0.0 COMMENT '经度',
  `ep_lat` DECIMAL(10,7) NOT NULL DEFAULT 0.0 COMMENT '纬度',
  `ep_type` INT(1) NOT NULL DEFAULT 0,
  `ep_time` BIGINT(13) NOT NULL DEFAULT 0 COMMENT '事件发生时间',
  `ep_cretime` BIGINT(13) NOT NULL DEFAULT 0 COMMENT '事件点创建时间',
  `ep_status` INT(2) NOT NULL DEFAULT 0 COMMENT '事件点状态0-正常 1-锁定  -1-失效',
  PRIMARY KEY (`ep_id`),
  INDEX `fk_event_point_user_idx` (`u_id` ASC) VISIBLE,
  CONSTRAINT `fk_event_point_user`
    FOREIGN KEY (`u_id`)
    REFERENCES `travel_memories`.`user` (`u_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_memories`.`event_point_text_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_memories`.`event_point_text_info` (
  `ep_ti_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '事件点文本信息主键,注意这个主键会因为频繁更新用完',
  `ep_id` BIGINT(11) NOT NULL,
  `u_id` INT(10) NOT NULL,
  `ep_ti_lasttime` BIGINT(13) NOT NULL DEFAULT 0 COMMENT '最近更改时间',
  `ep_ti_text` TEXT(2000) CHARACTER SET 'utf8' NULL COMMENT '事件点文本信息',
  PRIMARY KEY (`ep_ti_id`),
  INDEX `fk_event_point_text_info_user1_idx` (`u_id` ASC) VISIBLE,
  INDEX `fk_event_point_text_info_event_point1_idx` (`ep_id` ASC) VISIBLE,
  UNIQUE INDEX `ep_id_UNIQUE` (`ep_id` ASC) VISIBLE,
  CONSTRAINT `fk_event_point_text_info_user1`
    FOREIGN KEY (`u_id`)
    REFERENCES `travel_memories`.`user` (`u_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_event_point_text_info_event_point1`
    FOREIGN KEY (`ep_id`)
    REFERENCES `travel_memories`.`event_point` (`ep_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `travel_memories`.`event_point_media_info`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `travel_memories`.`event_point_media_info` (
  `ep_mi_id` BIGINT(15) NOT NULL AUTO_INCREMENT,
  `ep_mi_type` INT(2) NOT NULL DEFAULT 0 COMMENT '媒体信息类型',
  `u_id` INT(10) NOT NULL,
  `ep_id` BIGINT(11) NOT NULL,
  `ep_mi_desc` VARCHAR(50) NOT NULL DEFAULT '',
  `ep_mi_path` VARCHAR(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`ep_mi_id`),
  INDEX `fk_event_point_media_info_event_point1_idx` (`ep_id` ASC) VISIBLE,
  INDEX `fk_event_point_media_info_user1_idx` (`u_id` ASC) VISIBLE,
  CONSTRAINT `fk_event_point_media_info_event_point1`
    FOREIGN KEY (`ep_id`)
    REFERENCES `travel_memories`.`event_point` (`ep_id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
  CONSTRAINT `fk_event_point_media_info_user1`
    FOREIGN KEY (`u_id`)
    REFERENCES `travel_memories`.`user` (`u_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
