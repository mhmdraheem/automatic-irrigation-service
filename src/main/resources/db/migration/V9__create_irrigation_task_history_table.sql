CREATE TABLE `irrigation_job_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `slot_id` bigint NOT NULL,
  `task_end_time` datetime NOT NULL,
  `status` varchar(100) NOT NULL,
  `job_number` varchar(100) NOT NULL,
  `job_start_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `irrigation_task_history_FK` (`slot_id`),
  CONSTRAINT `irrigation_task_history_FK` FOREIGN KEY (`slot_id`) REFERENCES `plot_irrigation_slot` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;