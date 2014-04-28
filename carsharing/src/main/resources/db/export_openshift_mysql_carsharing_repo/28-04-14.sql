-- Member
INSERT INTO thorsten.`Member` (`id`, `carsize`, `city`, `email`, `firstname`, `name`, `phone_number`) VALUES
(14, 4, 'Grosskrotzenburg', NULL, 'Johann', 'Adam', NULL),
(13, 4, 'Grosskrotzenburg', NULL, 'Markus', 'Güntner', NULL),
(12, 4, 'Grosskrotzenburg', NULL, 'Jon', 'Goshi', NULL),
(6, 1, 'Grosskrotzenburg', NULL, 'Robin', 'Weitzel', NULL),
(5, 4, 'Grosskrotzenburg', NULL, 'Paul', 'Stober', NULL),
(3, 3, 'Grosskrotzenburg', NULL, 'Schlosser', 'Marius', NULL),
(2, 4, 'Grosskrotzenburg', NULL, 'Max', 'Albrecht', NULL),
(1, 4, 'Grosskrotzenburg', NULL, 'Moritz', 'Elfert', NULL);

-- TrainingDay
INSERT INTO `TrainingDay` (`weekday`, `location`, `pickUpLocationSource`, `pickUpLocationTarget`, `pickUpTimeSource`, `pickUpTimeTarget`, `timeFrom`, `timeTo`, `id`, `comment`) VALUES
(2, 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:10', '20:10', '18:30', '20:00', 1, 'mC Montag'),
(5, 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:55', '18:30', '19:45', 2, NULL),
(3, 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:50', '18:30', '19:30', 3, 'D1 und D2 Dienstags');

-- User
INSERT INTO `User` (`user_id`, `password`, `userName`) VALUES
(1, 'tet', 'tet');

--UserRole
INSERT INTO `UserRole` (`id`, `role`, `user_id`) VALUES
(1, 'admin', 1);

-- Team
INSERT INTO `Team` (`id`, `longName`, `shortName`) VALUES
(1, 'C PreagBerg', 'mC Jugend');

-- Sportsevent
INSERT INTO `SportsEvent` (`DTYPE`, `id`, `eventDate`, `location`, `pickUpLocationSource`, `pickUpLocationTarget`, `pickUpTimeSource`, `pickUpTimeTarget`, `timeFrom`, `timeTo`, `mapURL`, `opponent`, `trainingDay_id`) VALUES
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
('Training', 786, '2014-04-24 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:55', '18:30', '19:45', NULL, NULL, 2),
('Training', 777, '2014-04-17 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:55', '18:30', '19:45', NULL, NULL, 2),
('Training', 768, '2014-04-14 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:10', '20:10', '18:30', '20:00', NULL, NULL, 1),
('Training', 757, '2014-04-10 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:10', '19:55', '18:30', '19:45', NULL, NULL, 2),
('Training', 748, '2014-04-07 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:00', '20:10', '18:30', '20:00', NULL, NULL, 1),
('Training', 739, '2014-04-03 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Lindenauhalle', '18:00', '19:55', '18:30', '19:45', NULL, NULL, 2),
('Game', 679, '2014-02-22 00:00:00', 'Edith-Stein-Schule, Gravenbruchweg, OF-Rosenhöhe', 'GSS Großkrotzenburg', 'Edith-Stein-Schule, Gravenbruchweg, OF-Rosenhöhe', '13:45', '16:00', '15:00', '15:50', NULL, 'OFC', NULL),
('Game', 694, '2014-02-23 00:00:00', 'Klein Auheim, Fasaneriestraße', 'GSS Großkrotzenburg', 'Klein Auheim, Fasaneriestraße', '12:00', '14:00', '13:10', '14:00', NULL, 'TSV Klein Auheim', NULL),
('Game', 709, '2014-03-15 00:00:00', 'Waldsporthalle Oberau', 'Parkplatz der Großsporthalle in Großauheim', 'Waldsporthalle Oberau, Am Sportfeld 1, 63674 Altenstadt-Oberau', '13:00', '15:00', '14:00', '14:50', NULL, 'HSG Oberhessen III', NULL),
('Game', 724, '2014-03-15 00:00:00', 'Dietesheim', 'Parkplatz der Sporthalle der Geschwister-Scholl-Schule, Großkrotzenburg', 'Sporthalle am Sportzentrum, Anton-Dey-Str., 63165 Mühlheim am Main', '14:00', '16:30', '15:30', '16:20', NULL, 'SG Dietesheim/ Mühlheim', NULL),
('Training', 795, '2014-04-28 00:00:00', 'Grossauheim', 'Geschwister-Scholl Schule', 'Limesschule', '18:10', '20:10', '18:30', '20:00', NULL, NULL, 1);

CREATE TABLE IF NOT EXISTS `SportsEvent_Team` (
  `SportsEvent_id` bigint(20) NOT NULL
  KEY `FKA877620123F6ABD5` (`SportsEvent_id`),
  KEY `FKA8776201933E5EE6` (`teams_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

INSERT INTO `SportsEvent_Team` (`SportsEvent_id`, `teams_id`) VALUES
(739, 1),
(748, 1),
(757, 1),
(768, 1),
(777, 1),
(786, 1),
(795, 1);

-- News
INSERT INTO `News` (`id`, `activ`, `author`, `creationDate`, `text`, `mail`, `mailingList_id`) VALUES
(405, 0, 'Thorsten', '2014-01-31 18:42:21', 'Auf Peter''s Wunsch gibt''s ab sofort hier aktuelle Infos fÃ¼r Euch!Also: Immer mal wieder hier vorbeischauen...Gruß Thorsten', 0, NULL),
(406, 1, 'Thorsten', '2014-01-31 18:42:47', 'Auf Peter''s Wunsch gibt''s ab sofort hier aktuelle Infos fuer Euch! Also: Immer mal wieder hier vorbeischauen... Gruß Thorsten ', 0, NULL),
(407, 1, 'Thorsten', '2014-01-31 18:44:00', 'Ab sofort sind auch die Spiele hier hinterlegt!\r\nAchtung: Immer auch das jeweilige Team auswaehlen, da teilweise mehrere Teams am gleichen Tag spielen.', 0, NULL),
(408, 1, 'Peter', '2014-02-01 02:58:17', 'Am 19.2.14 steht ein Elternabend im Foyer der Auheimer Großsporthalle an. Los geht''s um 18.30 Uhr.Inhalt wird die Planung für die nächste Saison sein.', 0, NULL),
(766, 0, 'Thorsten', '2014-04-01 15:19:56', 'ACHTUNG!\r\nZur Zeit lÃ¤sst sich das Portal nur fÃ¼r eine Mannschaft nutzen. Ich arbeite daran, es auch fÃ¼r weitere Mannschaften nutzbar zu machen. \r\nAus diesem Grund ist vorerst nur die mÃ¤nnliche C-Jugend hinterlegt.', 0, NULL),
(767, 1, 'Thorsten', '2014-04-01 15:20:40', 'ACHTUNG! Zur Zeit laesst sich das Portal nur fuer eine Mannschaft nutzen. Ich arbeite daran, es auch fuer weitere Mannschaften nutzbar zu machen. Aus diesem Grund ist vorerst nur die maennliche C-Jugend hinterlegt. ', 0, NULL),
(804, 0, 'Thorsten', '2014-04-13 11:03:18', 'Abfahrstzeit fÃ¼r die mC-Jugend jetzt immer 18:10 von Grosskrotzenburg', 0, NULL),
(805, 1, 'Thorsten', '2014-04-13 11:03:12', 'Abfahrstzeit fuer die mC-Jugend jetzt immer 18:10 von Grosskrotzenburg', 0, NULL),
(806, 0, '', '2014-04-20 16:26:59', 'Ã?Ã?Ã?Ã¤Ã¤', 0, NULL);

-- ParticipationGroup_Member ForeignKey auf Member löschen!
-- Index members_id löschen!