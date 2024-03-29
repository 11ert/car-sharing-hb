-- You can use this file to load seed data into the database using SQL statements
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (1,4,'Grosskrotzenburg','Moritz','E.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (2,3,'Grosskrotzenburg','Julius','E.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (3,3,'Grosskrotzenburg','Silas','N.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (4,3,'Grosskrotzenburg','Simon','S.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (5,3,'Grosskrotzenburg','Paul','S.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (6,1,'Grosskrotzenburg','Robin','W.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (7,1,'Grosskrotzenburg','Oliver','Z.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (8,5,'Grosskrotzenburg','Lovis','W.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (9,4,'Grosskrotzenburg','Paula','S.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (10,1,'Grosskrotzenburg','Yunus','T.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (11,1,'Grosskrotzenburg','Marie-Ann','W.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (12,4,'Grosskrotzenburg','Peter','S.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (13,4,'Grosskrotzenburg','Jon','G.');
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (14,4,'Grosskrotzenburg','Johann','A.');
INSERT INTO `trainingday` (`id`,`weekday`,`location`,`pickUpLocationSource`,`pickUpLocationTarget`,`pickUpTimeSource`,`pickUpTimeTarget`,`timeFrom`,`timeTo`) VALUES (1,3,'Grossauheim','Geschwister-Scholl Schule','Lindenauhalle','18:00','19:55','17:00','18:30');
INSERT INTO `trainingday` (`id`,`weekday`,`location`,`pickUpLocationSource`,`pickUpLocationTarget`,`pickUpTimeSource`,`pickUpTimeTarget`,`timeFrom`,`timeTo`) VALUES (2,4,'Grossauheim','Geschwister-Scholl Schule','Lindenauhalle','17:00','19:10','17:00','18:30');
INSERT INTO `team` (`id`,`shortName`,`longName`) VALUES (1, 'D1','D1 PreagBerg');
INSERT INTO `team` (`id`,`shortName`,`longName`) VALUES (2, 'D2','D2 PreagBerg');
-- evtl. das hier ausführen!!!
-- update hibernate_sequence set next_val = 20;



-- Für openshift:
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (0,4,'Grosskrotzenburg','Johann','A.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (1,4,'Grosskrotzenburg','Moritz','E.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (2,3,'Grosskrotzenburg','Julius','E.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (3,3,'Grosskrotzenburg','Silas','N.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (4,3,'Grosskrotzenburg','Simon','S.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (5,3,'Grosskrotzenburg','Paul','S.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (6,1,'Grosskrotzenburg','Robin','W.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (7,1,'Grosskrotzenburg','Oliver','Z.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (8,5,'Grosskrotzenburg','Lovis','W.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (9,4,'Grosskrotzenburg','Paula','S.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (10,1,'Grosskrotzenburg','Yunus','T.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (11,1,'Grosskrotzenburg','Marie-Ann','W.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (12,4,'Grosskrotzenburg','Peter','S.');
-- INSERT INTO carsharing.`Member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (13,4,'Grosskrotzenburg','Jon','G.');
-- INSERT INTO carsharing.`TrainingDay` (`weekday`,`location`,`pickUpLocationSource`,`pickUpLocationTarget`,`pickUpTimeSource`,`pickUpTimeTarget`,`timeFrom`,`timeTo`) VALUES (3,'Grossauheim','Geschwister-Scholl Schule','Lindenauhalle','18:00','19:55','17:00','18:30');
-- INSERT INTO carsharing.`TrainingDay` (`weekday`,`location`,`pickUpLocationSource`,`pickUpLocationTarget`,`pickUpTimeSource`,`pickUpTimeTarget`,`timeFrom`,`timeTo`) VALUES (4,'Grossauheim','Geschwister-Scholl Schule','Lindenauhalle','17:00','19:10','17:00','18:30');