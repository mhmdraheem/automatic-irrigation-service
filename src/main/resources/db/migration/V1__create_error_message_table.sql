CREATE TABLE `error_message` (
  `error_type` varchar(255) NOT NULL,
  `error_message` varchar(255) NOT NULL,
  PRIMARY KEY (`error_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
