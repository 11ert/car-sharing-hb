-- phpMyAdmin SQL Dump
-- version 4.0.5
-- http://www.phpmyadmin.net
--
-- Host: 127.10.9.130:3306
-- Erstellungszeit: 02. Mai 2014 um 11:16
-- Server Version: 5.5.36
-- PHP-Version: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


use carsharing;


-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE IF NOT EXISTS `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(9);

--
-- Tabellenstruktur für Tabelle `UserRole`
--

DROP TABLE IF EXISTS `UserRole`;
CREATE TABLE IF NOT EXISTS `UserRole` (
  `id` bigint(20) NOT NULL,
  `role` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKF3F76701EDE0721F` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
-- --------------------------------------------------------
--
-- Tabellenstruktur für Tabelle `User`
--

DROP TABLE IF EXISTS `User`;
CREATE TABLE IF NOT EXISTS `User` (
  `user_id` bigint(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `userName` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `User`
--

INSERT INTO `User` (`user_id`, `password`, `userName`) VALUES
(1, 'tet', 'tet');

-- --------------------------------------------------------


--
-- Daten für Tabelle `UserRole`
--

INSERT INTO `UserRole` (`id`, `role`, `user_id`) VALUES
(1, 'admin', 1);

--
-- Tabellenstruktur für Tabelle `MailConfig`
--

DROP TABLE IF EXISTS `MailConfig`;
CREATE TABLE IF NOT EXISTS `MailConfig` (
  `id` bigint(20) NOT NULL,
  `postConfiguredText` varchar(255) DEFAULT NULL,
  `preConfiguredText` varchar(255) DEFAULT NULL,
  `recipient` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `signature` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tabellenstruktur für Tabelle `News`
--

DROP TABLE IF EXISTS `News`;
CREATE TABLE IF NOT EXISTS `News` (
  `id` bigint(20) NOT NULL,
  `activ` tinyint(1) NOT NULL,
  `author` varchar(255) DEFAULT NULL,
  `creationDate` datetime DEFAULT NULL,
  `mail` tinyint(1) NOT NULL,
  `text` varchar(255) DEFAULT NULL,
  `mailingList_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK24FEF391C43155` (`mailingList_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `News`
--

INSERT INTO `News` (`id`, `activ`, `author`, `creationDate`, `mail`, `text`, `mailingList_id`) VALUES
(405, 0, 'Thorsten', '2014-01-31 18:42:21', 0, 'Auf Peter''s Wunsch gibt''s ab sofort hier aktuelle Infos fÃ¼r Euch!Also: Immer mal wieder hier vorbeischauen...Gruß Thorsten', NULL),
(406, 1, 'Thorsten', '2014-01-31 18:42:47', 0, 'Auf Peter''s Wunsch gibt''s ab sofort hier aktuelle Infos fuer Euch! Also: Immer mal wieder hier vorbeischauen... Gruß Thorsten ', NULL),
(407, 1, 'Thorsten', '2014-01-31 18:44:00', 0, 'Ab sofort sind auch die Spiele hier hinterlegt!\r\nAchtung: Immer auch das jeweilige Team auswaehlen, da teilweise mehrere Teams am gleichen Tag spielen.', NULL),
(408, 1, 'Peter', '2014-02-01 02:58:17', 0, 'Am 19.2.14 steht ein Elternabend im Foyer der Auheimer Großsporthalle an. Los geht''s um 18.30 Uhr.Inhalt wird die Planung für die nächste Saison sein.', NULL),
(766, 0, 'Thorsten', '2014-04-01 15:19:56', 0, 'ACHTUNG!\r\nZur Zeit lÃ¤sst sich das Portal nur fÃ¼r eine Mannschaft nutzen. Ich arbeite daran, es auch fÃ¼r weitere Mannschaften nutzbar zu machen. \r\nAus diesem Grund ist vorerst nur die mÃ¤nnliche C-Jugend hinterlegt.', NULL),
(767, 1, 'Thorsten', '2014-04-01 15:20:40', 0, 'ACHTUNG! Zur Zeit laesst sich das Portal nur fuer eine Mannschaft nutzen. Ich arbeite daran, es auch fuer weitere Mannschaften nutzbar zu machen. Aus diesem Grund ist vorerst nur die maennliche C-Jugend hinterlegt. ', NULL),
(804, 0, 'Thorsten', '2014-04-13 11:03:18', 0, 'Abfahrstzeit fÃ¼r die mC-Jugend jetzt immer 18:10 von Grosskrotzenburg', NULL),
(805, 1, 'Thorsten', '2014-04-13 11:03:12', 0, 'Abfahrstzeit fuer die mC-Jugend jetzt immer 18:10 von Grosskrotzenburg', NULL),
(806, 0, '', '2014-04-20 16:26:59', 0, 'Ã?Ã?Ã?Ã¤Ã¤', NULL);

-- --------------------------------------------------------

-- --------------------------------------------------------
--
-- Tabellenstruktur für Tabelle `MailingList_eMailAdresses`
--

DROP TABLE IF EXISTS `MailingList_eMailAdresses`;
CREATE TABLE IF NOT EXISTS `MailingList_eMailAdresses` (
  `MailingList_id` bigint(20) NOT NULL,
  `eMailAdresses` varchar(255) DEFAULT NULL,
  KEY `FK942065AA91C43155` (`MailingList_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------
--
-- Tabellenstruktur für Tabelle `mailinglist`
--

DROP TABLE IF EXISTS `mailinglist`;
CREATE TABLE IF NOT EXISTS `mailinglist` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `description` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ParticipationGroup_Member`
--

DROP TABLE IF EXISTS `ParticipationGroup_Member`;
CREATE TABLE IF NOT EXISTS `ParticipationGroup_Member` (
  `ParticipationGroup_id` bigint(20) NOT NULL,
  `members_id` bigint(20) NOT NULL,
  KEY `FKACA7683B85098BA0` (`members_id`),
  KEY `FKACA7683B27D3085F` (`ParticipationGroup_id`),
  KEY `members_id` (`members_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- --------------------------------------------------------
--
-- Tabellenstruktur für Tabelle `Participation`
--

DROP TABLE IF EXISTS `Participation`;
CREATE TABLE IF NOT EXISTS `Participation` (
  `id` bigint(20) NOT NULL,
  `drivingBack` tinyint(1) NOT NULL,
  `drivingBicycle` tinyint(1) NOT NULL,
  `drivingForth` tinyint(1) NOT NULL,
  `lastChanged` datetime DEFAULT NULL,
  `notParticipating` tinyint(1) NOT NULL,
  `participating` tinyint(1) NOT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  `trainingItem_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE5A0BD21655704D8` (`player_id`),
  KEY `FKE5A0BD2152C4FA23` (`trainingItem_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tabellenstruktur für Tabelle `SportsEvent_Team`
--

DROP TABLE IF EXISTS `SportsEvent_Team`;
CREATE TABLE IF NOT EXISTS `SportsEvent_Team` (
  `SportsEvent_id` bigint(20) NOT NULL,
  `teams_id` bigint(20) NOT NULL,
  KEY `FKA877620123F6ABD5` (`SportsEvent_id`),
  KEY `FKA8776201933E5EE6` (`teams_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
--
-- Tabellenstruktur für Tabelle `SportsEvent`
--

DROP TABLE IF EXISTS `SportsEvent`;
CREATE TABLE IF NOT EXISTS `SportsEvent` (
  `DTYPE` varchar(31) NOT NULL,
  `id` bigint(20) NOT NULL,
  `eventDate` datetime NOT NULL,
  `location` varchar(255) NOT NULL,
  `pickUpLocationSource` varchar(255) DEFAULT NULL,
  `pickUpLocationTarget` varchar(255) DEFAULT NULL,
  `pickUpTimeSource` varchar(255) NOT NULL,
  `pickUpTimeTarget` varchar(255) NOT NULL,
  `timeFrom` varchar(255) NOT NULL,
  `timeTo` varchar(255) NOT NULL,
  `mapURL` varchar(255) DEFAULT NULL,
  `opponent` varchar(255) DEFAULT NULL,
  `trainingDay_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK72CD3A3BB04B1275` (`trainingDay_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Tabellenstruktur für Tabelle `Member`
--

DROP TABLE IF EXISTS `Member`;
CREATE TABLE IF NOT EXISTS `Member` (
  `id` bigint(20) NOT NULL,
  `carsize` int(11) DEFAULT NULL,
  `city` varchar(25) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(25) DEFAULT NULL,
  `name` varchar(25) NOT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Daten für Tabelle `Member`
--

INSERT INTO `Member` (`id`, `carsize`, `city`, `email`, `firstname`, `name`, `phone_number`) VALUES
(1, 4, 'Grosskrotzenburg', NULL, 'Moritz', 'Elfert', NULL),
(2, 4, 'Grosskrotzenburg', NULL, 'Max', 'Albrecht', NULL),
(3, 3, 'Grosskrotzenburg', NULL, 'Schlosser', 'Marius', NULL),
(4, 3, 'Grosskrotzenburg', '', 'Silas', 'Neyer', ''),
(5, 4, 'Grosskrotzenburg', NULL, 'Paul', 'Stober', NULL),
(6, 1, 'Grosskrotzenburg', NULL, 'Robin', 'Weitzel', NULL),
(12, 4, 'Grosskrotzenburg', NULL, 'Jon', 'Goshi', NULL),
(13, 4, 'Grosskrotzenburg', NULL, 'Markus', 'Güntner', NULL),
(14, 4, 'Grosskrotzenburg', NULL, 'Johann', 'Adam', NULL);

-- --------------------------------------------------------




-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ParticipationGroup`
--

DROP TABLE IF EXISTS `ParticipationGroup`;
CREATE TABLE IF NOT EXISTS `ParticipationGroup` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `ParticipationGroup`
--

INSERT INTO `ParticipationGroup` (`id`, `description`) VALUES
(1, 'mC Jugend Training'),
(6, 'D Jugend Training');

-- --------------------------------------------------------



--
-- Daten für Tabelle `ParticipationGroup_Member`
--

INSERT INTO `ParticipationGroup_Member` (`ParticipationGroup_id`, `members_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 5),
(1, 6),
(1, 12),
(1, 13),
(1, 14);


--
-- Daten für Tabelle `SportsEvent`
--

INSERT INTO `SportsEvent` (`DTYPE`, `id`, `eventDate`, `location`, `pickUpLocationSource`, `pickUpLocationTarget`, `pickUpTimeSource`, `pickUpTimeTarget`, `timeFrom`, `timeTo`, `mapURL`, `opponent`, `trainingDay_id`) VALUES
('Training', 7, '2014-05-06 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:50', '18:30', '19:30', NULL, NULL, 3),
('Training', 225, '2014-01-21 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 240, '2014-01-22 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Training', 255, '2014-01-28 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 270, '2014-01-29 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Game', 285, '2014-01-19 00:00:00', 'Lindenauhalle Großauheim', 'GSS Großkrotzenburg', 'Lindenauhalle Großauheim', '10:15', '13:00', '10:30', '12:45', NULL, 'SG Bruchköbel', NULL),
('Game', 300, '2014-01-25 00:00:00', 'Dreieich', 'GSS Großkrotzenburg', 'Hans-Meudt-Halle, Breslauer Str. 20, Dreieich', '13:45', '16:30', '13:45', '16:20', NULL, 'Dreieich', NULL),
('Game', 315, '2014-01-26 00:00:00', 'Gelnhausen', 'GSS Großkrotzenburg', 'Großsporthalle, Am Schwimmbad, Gelnhausen', '10:45', '13:00', '11:15', '12:50', NULL, 'Gelnhausen', NULL),
('Training', 330, '2014-02-04 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 345, '2014-02-05 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Training', 360, '2014-02-11 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 375, '2014-02-12 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Game', 390, '2014-02-01 00:00:00', 'Offenbach', 'GSS Großkrotzenburg', 'Offenbach', '14:30', '17:00', '15:50', '16:40', NULL, 'Offenbach', NULL),
('Training', 409, '2014-02-18 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 424, '2014-02-19 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Training', 439, '2014-02-25 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 454, '2014-02-26 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Training', 469, '2014-03-04 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 484, '2014-03-05 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Training', 499, '2014-03-11 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 514, '2014-03-12 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Training', 529, '2014-03-18 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 544, '2014-03-19 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Training', 559, '2014-03-25 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 1),
('Training', 574, '2014-03-26 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '17:00', '19:10', '17:30', '19:00', NULL, NULL, 2),
('Game', 679, '2014-02-22 00:00:00', 'Edith-Stein-Schule, Gravenbruchweg, OF-Rosenhöhe', 'GSS Großkrotzenburg', 'Edith-Stein-Schule, Gravenbruchweg, OF-Rosenhöhe', '13:45', '16:00', '15:00', '15:50', NULL, 'OFC', NULL),
('Game', 694, '2014-02-23 00:00:00', 'Klein Auheim, Fasaneriestraße', 'GSS Großkrotzenburg', 'Klein Auheim, Fasaneriestraße', '12:00', '14:00', '13:10', '14:00', NULL, 'TSV Klein Auheim', NULL),
('Game', 709, '2014-03-15 00:00:00', 'Waldsporthalle Oberau', 'Parkplatz der Großsporthalle in Großauheim', 'Waldsporthalle Oberau, Am Sportfeld 1, 63674 Altenstadt-Oberau', '13:00', '15:00', '14:00', '14:50', NULL, 'HSG Oberhessen III', NULL),
('Game', 724, '2014-03-15 00:00:00', 'Dietesheim', 'Parkplatz der Sporthalle der Geschwister-Scholl-Schule, Großkrotzenburg', 'Sporthalle am Sportzentrum, Anton-Dey-Str., 63165 Mühlheim am Main', '14:00', '16:30', '15:30', '16:20', NULL, 'SG Dietesheim/ Mühlheim', NULL),
('Training', 739, '2014-04-03 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 2),
('Training', 748, '2014-04-07 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '20:10', '18:30', '20:00', NULL, NULL, 1),
('Training', 757, '2014-04-10 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:55', '18:30', '19:45', NULL, NULL, 2),
('Training', 768, '2014-04-14 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:10', '20:10', '18:30', '20:00', NULL, NULL, 1),
('Training', 777, '2014-04-17 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:55', '18:30', '19:45', NULL, NULL, 2),
('Training', 786, '2014-04-24 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:55', '18:30', '19:45', NULL, NULL, 2),
('Training', 795, '2014-04-28 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:10', '20:10', '18:30', '20:00', NULL, NULL, 1);

-- --------------------------------------------------------





-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `Team`
--

DROP TABLE IF EXISTS `Team`;
CREATE TABLE IF NOT EXISTS `Team` (
  `id` bigint(20) NOT NULL,
  `longName` varchar(255) DEFAULT NULL,
  `shortName` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `Team`
--

INSERT INTO `Team` (`id`, `longName`, `shortName`) VALUES
(1, 'C PreagBerg', 'mC Jugend'),
(2, 'D1 PreagBerg', 'D1 Jugend'),
(3, 'D2 PreagBerg', 'D2 Jugend');

--
-- Daten für Tabelle `SportsEvent_Team`
--

INSERT INTO `SportsEvent_Team` (`SportsEvent_id`, `teams_id`) VALUES
(739, 1),
(748, 1),
(757, 1),
(768, 1),
(777, 1),
(786, 1),
(795, 1);
-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `TrainingDay`
--

DROP TABLE IF EXISTS `TrainingDay`;
CREATE TABLE IF NOT EXISTS `TrainingDay` (
  `id` bigint(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `location` varchar(255) NOT NULL,
  `pickUpLocationSource` varchar(255) DEFAULT NULL,
  `pickUpLocationTarget` varchar(255) DEFAULT NULL,
  `pickUpTimeSource` varchar(255) NOT NULL,
  `pickUpTimeTarget` varchar(255) NOT NULL,
  `timeFrom` varchar(255) DEFAULT NULL,
  `timeTo` varchar(255) NOT NULL,
  `weekday` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `weekday` (`weekday`,`location`,`timeFrom`,`timeTo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Daten für Tabelle `TrainingDay`
--

INSERT INTO `TrainingDay` (`id`, `comment`, `location`, `pickUpLocationSource`, `pickUpLocationTarget`, `pickUpTimeSource`, `pickUpTimeTarget`, `timeFrom`, `timeTo`, `weekday`) VALUES
(1, 'mC Montag', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:10', '20:10', '18:30', '20:00', 2),
(2, 'mc Donnerstag', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:55', '18:30', '19:45', 5),
(3, 'D1 und D2 Dienstags', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:50', '18:30', '19:30', 3);

-- --------------------------------------------------------


--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `MailingList_eMailAdresses`
--
ALTER TABLE `MailingList_eMailAdresses`
  ADD CONSTRAINT `FK942065AA91C43155` FOREIGN KEY (`MailingList_id`) REFERENCES `mailinglist` (`id`);

--
-- Constraints der Tabelle `News`
--
ALTER TABLE `News`
  ADD CONSTRAINT `FK24FEF391C43155` FOREIGN KEY (`mailingList_id`) REFERENCES `mailinglist` (`id`);

--
-- Constraints der Tabelle `Participation`
--
ALTER TABLE `Participation`
  ADD CONSTRAINT `FKE5A0BD2152C4FA23` FOREIGN KEY (`trainingItem_id`) REFERENCES `SportsEvent` (`id`),
  ADD CONSTRAINT `FKE5A0BD21655704D8` FOREIGN KEY (`player_id`) REFERENCES `Member` (`id`);

--
-- Constraints der Tabelle `ParticipationGroup_Member`
--
ALTER TABLE `ParticipationGroup_Member`
  ADD CONSTRAINT `FKACA7683B27D3085F` FOREIGN KEY (`ParticipationGroup_id`) REFERENCES `ParticipationGroup` (`id`),
  ADD CONSTRAINT `FKACA7683B85098BA0` FOREIGN KEY (`members_id`) REFERENCES `Member` (`id`);

--
-- Constraints der Tabelle `SportsEvent`
--
ALTER TABLE `SportsEvent`
  ADD CONSTRAINT `FK72CD3A3BB04B1275` FOREIGN KEY (`trainingDay_id`) REFERENCES `TrainingDay` (`id`);

--
-- Constraints der Tabelle `UserRole`
--
ALTER TABLE `UserRole`
  ADD CONSTRAINT `FKF3F76701EDE0721F` FOREIGN KEY (`user_id`) REFERENCES `User` (`user_id`);
COMMIT;
