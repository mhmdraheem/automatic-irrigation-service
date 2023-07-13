CREATE TABLE `plot_irrigation_slot` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount_liters` int unsigned NOT NULL,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `plot_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `irrigation_FK` (`plot_id`),
  CONSTRAINT `irrigation_FK` FOREIGN KEY (`plot_id`) REFERENCES `plot` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;