delimiter $$

CREATE TABLE `member` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(25) NOT NULL,
  `phone_number` varchar(20) NOT NULL,
  `carsize` int(11) DEFAULT '0',
  `firstname` varchar(25) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Spielertabelle'$$

