delimiter $$

CREATE TABLE `participation` (
  `id` bigint(20) NOT NULL,
  `drivingBack` tinyint(1) NOT NULL,
  `drivingForth` tinyint(1) NOT NULL,
  `participating` tinyint(1) NOT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  `trainingDay_weekday` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE5A0BD219B2A4E8` (`trainingDay_weekday`),
  KEY `FKE5A0BD21655704D8` (`player_id`),
  CONSTRAINT `FKE5A0BD21655704D8` FOREIGN KEY (`player_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKE5A0BD219B2A4E8` FOREIGN KEY (`trainingDay_weekday`) REFERENCES `trainingday` (`weekday`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1$$

