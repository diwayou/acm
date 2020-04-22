CREATE TABLE `base`.`province` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `code` CHAR(12) NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `parent_id` INT UNSIGNED NOT NULL,
  `create_time` DATETIME NOT NULL,
  `modify_time` DATETIME NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `uniq_code` (`code` ASC) VISIBLE,
  INDEX `idx_parent` (`parent_id` ASC) VISIBLE);