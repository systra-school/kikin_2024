SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `kikin_db` DEFAULT CHARACTER SET utf8 ;
USE `kikin_db`;

-- -----------------------------------------------------
-- Table `kikin_db`.`m_base_shift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kikin_db`.`m_base_shift` (
  `employee_id` CHAR(6) NOT NULL,
  `monday` CHAR(6) DEFAULT NULL,
  `tuesday` CHAR(6) DEFAULT NULL,
  `wednesday` CHAR(6) DEFAULT NULL,
  `thursday` CHAR(6) DEFAULT NULL,
  `friday` CHAR(6) DEFAULT NULL,
  `saturday` CHAR(6) DEFAULT NULL,
  `sunday` CHAR(6) DEFAULT NULL,
  `creator_employee_id` CHAR(6) DEFAULT NULL,
  `creation_datetime` TIMESTAMP NULL DEFAULT NULL,
  `updater_employee_id` CHAR(6) DEFAULT NULL,
  `update_datetime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
ROW_FORMAT=DYNAMIC COMMENT='基本シフトマスタ';


-- -----------------------------------------------------
-- Table `kikin_db`.`m_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kikin_db`.`m_category` (
  `category_id` CHAR(6) NOT NULL,
  `code` VARCHAR(10) NOT NULL,
  `name` VARCHAR(40) DEFAULT NULL,
  `display_order` VARCHAR(4) DEFAULT NULL,
  `display` TINYINT(1) DEFAULT NULL,
  `creator_employee_id` CHAR(6) DEFAULT NULL,
  `creation_datetime` TIMESTAMP NULL DEFAULT NULL,
  `updater_employee_id` CHAR(6) DEFAULT NULL,
  `update_datetime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`category_id`,`code`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
ROW_FORMAT=DYNAMIC COMMENT='分類マスタ';


-- -----------------------------------------------------
-- Table `kikin_db`.`m_employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kikin_db`.`m_employee` (
  `employee_id` CHAR(6) NOT NULL,
  `password` VARCHAR(6) NOT NULL,
  `employee_name` VARCHAR(20) NOT NULL,
  `employee_name_kana` VARCHAR(20) DEFAULT NULL,
  `authority_id` CHAR(2) NOT NULL,
  `creator_employee_id` CHAR(6) DEFAULT NULL,
  `creation_datetime` TIMESTAMP NULL DEFAULT NULL,
  `updater_employee_id` CHAR(6) DEFAULT NULL,
  `update_datetime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`employee_id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
ROW_FORMAT=DYNAMIC COMMENT='社員マスタ';


-- -----------------------------------------------------
-- Table `kikin_db`.`m_shift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kikin_db`.`m_shift` (
  `shift_id` CHAR(6) NOT NULL,
  `shift_name` VARCHAR(20) NOT NULL,
  `symbol` VARCHAR(10) NOT NULL,
  `start_time` CHAR(5) DEFAULT NULL,
  `end_time` CHAR(5) DEFAULT NULL,
  `break_time` CHAR(5) DEFAULT NULL,
  `creator_employee_id` CHAR(6) DEFAULT NULL,
  `creation_datetime` TIMESTAMP NULL DEFAULT NULL,
  `updater_employee_id` CHAR(6) DEFAULT NULL,
  `update_datetime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`shift_id`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
ROW_FORMAT=DYNAMIC COMMENT='シフトマスタ';


-- -----------------------------------------------------
-- Table `kikin_db`.`m_public_holiday`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kikin_db`.`m_public_holiday` (
  `year_month_day` CHAR(8) NOT NULL,
  `public_holiday_name` VARCHAR(40) NOT NULL,
  `creator_employee_id` CHAR(6) DEFAULT NULL,
  `creation_datetime` TIMESTAMP NULL DEFAULT NULL,
  `updater_employee_id` CHAR(6) DEFAULT NULL,
  `update_datetime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`year_month_day`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
ROW_FORMAT=DYNAMIC COMMENT='祝日マスタ';


-- -----------------------------------------------------
-- Table `kikin_db`.`t_work_record`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kikin_db`.`t_work_record` (
  `employee_id` CHAR(6) NOT NULL,
  `work_day` CHAR(8) NOT NULL,
  `start_time` CHAR(5) DEFAULT NULL,
  `end_time` CHAR(5) DEFAULT NULL,
  `break_time` CHAR(5) DEFAULT NULL,
  `actual_work_time` CHAR(5) DEFAULT NULL,
  `over_time` CHAR(5) DEFAULT NULL,
  `holiday_work_time` CHAR(5) DEFAULT NULL,
  `remark` VARCHAR(200) DEFAULT NULL,
  `creator_employee_id` CHAR(6) DEFAULT NULL,
  `creation_datetime` TIMESTAMP NULL DEFAULT NULL,
  `updater_employee_id` CHAR(6) DEFAULT NULL,
  `update_datetime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`employee_id`,`work_day`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
ROW_FORMAT=DYNAMIC COMMENT='勤務実績テーブル';


-- -----------------------------------------------------
-- Table `kikin_db`.`t_shift`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `kikin_db`.`t_shift` (
  `employee_id` CHAR(6) NOT NULL,
  `year_month_day` CHAR(8) NOT NULL,
  `shift_id` CHAR(6) DEFAULT NULL,
  `request_shift_id` CHAR(6) DEFAULT NULL,
  `remark` VARCHAR(200) DEFAULT NULL,
  `creator_employee_id` CHAR(6) DEFAULT NULL,
  `creation_datetime` TIMESTAMP NULL DEFAULT NULL,
  `updater_employee_id` CHAR(6) DEFAULT NULL,
  `update_datetime` TIMESTAMP NULL DEFAULT NULL,
  PRIMARY KEY (`employee_id`,`year_month_day`)
) ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
ROW_FORMAT=DYNAMIC COMMENT='シフトテーブル';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
