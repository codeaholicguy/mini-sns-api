SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema n9_mini_sns
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `n9_mini_sns` ;
CREATE SCHEMA IF NOT EXISTS `n9_mini_sns` DEFAULT CHARACTER SET utf8 ;
USE `n9_mini_sns` ;

-- -----------------------------------------------------
-- Table `n9_mini_sns`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `n9_mini_sns`.`account` ;

CREATE TABLE IF NOT EXISTS `n9_mini_sns`.`account` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(256) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `screen_name` VARCHAR(128) NULL DEFAULT NULL,
  `avatar` VARCHAR(512) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `n9_mini_sns`.`image`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `n9_mini_sns`.`image` ;

CREATE TABLE IF NOT EXISTS `n9_mini_sns`.`image` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `url` VARCHAR(512) NOT NULL,
  `account_id` INT(11) NOT NULL,
  `create_time` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_image_account1_idx` (`account_id` ASC),
  CONSTRAINT `fk_image_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `n9_mini_sns`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `n9_mini_sns`.`feed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `n9_mini_sns`.`feed` ;

CREATE TABLE IF NOT EXISTS `n9_mini_sns`.`feed` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `image_id` INT(11) NULL DEFAULT NULL,
  `status` TEXT NULL DEFAULT NULL,
  `create_time` INT(11) NOT NULL,
  `url` TEXT NULL DEFAULT NULL,
  `account_id` INT(11) NULL DEFAULT NULL,
  `like_count` INT(11) NULL DEFAULT NULL,
  `liker` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_feed_image1_idx` (`image_id` ASC),
  INDEX `fk_feed_account_idx` (`account_id` ASC),
  CONSTRAINT `fk_feed_account`
    FOREIGN KEY (`account_id`)
    REFERENCES `n9_mini_sns`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_feed_image1`
    FOREIGN KEY (`image_id`)
    REFERENCES `n9_mini_sns`.`image` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `n9_mini_sns`.`comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `n9_mini_sns`.`comment` ;

CREATE TABLE IF NOT EXISTS `n9_mini_sns`.`comment` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `feed_id` INT(11) NOT NULL,
  `account_id` INT(11) NOT NULL,
  `text` TEXT NOT NULL,
  `create_time` INT(11) NOT NULL,
  `like_count` INT(11) NULL DEFAULT NULL,
  `liker` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_account1_idx` (`account_id` ASC),
  INDEX `fk_comment_feed1_idx` (`feed_id` ASC),
  CONSTRAINT `fk_comment_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `n9_mini_sns`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_feed1`
    FOREIGN KEY (`feed_id`)
    REFERENCES `n9_mini_sns`.`feed` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `n9_mini_sns`.`follow`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `n9_mini_sns`.`follow` ;

CREATE TABLE IF NOT EXISTS `n9_mini_sns`.`follow` (
  `followed_id` INT(11) NOT NULL,
  `follower_id` INT(11) NOT NULL,
  INDEX `fk_follow_account1_idx` (`followed_id` ASC),
  INDEX `fk_follow_account2_idx` (`follower_id` ASC),
  CONSTRAINT `fk_follow_account1`
    FOREIGN KEY (`followed_id`)
    REFERENCES `n9_mini_sns`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_follow_account2`
    FOREIGN KEY (`follower_id`)
    REFERENCES `n9_mini_sns`.`account` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `n9_mini_sns`.`tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `n9_mini_sns`.`tag` ;

CREATE TABLE IF NOT EXISTS `n9_mini_sns`.`tag` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(512) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `n9_mini_sns`.`image_tag`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `n9_mini_sns`.`image_tag` ;

CREATE TABLE IF NOT EXISTS `n9_mini_sns`.`image_tag` (
  `image_id` INT(11) NOT NULL,
  `tag_id` INT(11) NOT NULL,
  INDEX `fk_image_tag_image1_idx` (`image_id` ASC),
  INDEX `fk_image_tag_tag1_idx` (`tag_id` ASC),
  CONSTRAINT `fk_image_tag_image1`
    FOREIGN KEY (`image_id`)
    REFERENCES `n9_mini_sns`.`image` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_image_tag_tag1`
    FOREIGN KEY (`tag_id`)
    REFERENCES `n9_mini_sns`.`tag` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
