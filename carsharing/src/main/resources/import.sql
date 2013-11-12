-- You can use this file to load seed data into the database using SQL statements
INSERT INTO `member` (`id`,`carsize`,`city`,`firstname`,`name`) VALUES (0,4,'Grosskrotzenburg','Johann','A.');
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
INSERT INTO `trainingday` (`weekday`,`location`,`pickUpLocationSource`,`pickUpLocationTarget`,`pickUpTimeSource`,`pickUpTimeTarget`,`timeFrom`,`timeTo`) VALUES (3,'Grossauheim','Geschwister-Scholl Schule','Lindenauhalle','18:00','19:55','17:00','18:30');
INSERT INTO `trainingday` (`weekday`,`location`,`pickUpLocationSource`,`pickUpLocationTarget`,`pickUpTimeSource`,`pickUpTimeTarget`,`timeFrom`,`timeTo`) VALUES (4,'Grossauheim','Geschwister-Scholl Schule','Lindenauhalle','17:00','19:10','17:00','18:30');

INSERT INTO `training` VALUES ('2013-11-12',3);
INSERT INTO `participation` VALUES (1,0,0,'2013-11-12',1,1,'2013-11-12');
INSERT INTO `training` VALUES ('2013-11-19',3);
INSERT INTO `participation` VALUES (1,0,0,'2013-11-12',1,1,'2013-11-19');

-- INSERT INTO `participation` (`id`,`drivingBack`,`drivingForth`,`lastChanged`,`notParticipating`,`participating`,`player_id`,`trainingItem_currentDate`)
-- VALUES (<{id: }>,
-- <{drivingBack: }>,
-- <{drivingForth: }>,
-- <{lastChanged: }>,
-- <{notParticipating: }>,
-- <{participating: }>,
-- <{player_id: }>,
-- <{trainingItem_currentDate: }>);

-- INSERT INTO `training` VALUES ('2013-11-13',3);
-- INSERT INTO `training` VALUES ('2013-10-15',3);
-- INSERT INTO `training` VALUES ('2013-10-29',3);
-- INSERT INTO `training` VALUES ('2013-09-12',4);
-- INSERT INTO `training` VALUES ('2013-10-08',4);
-- INSERT INTO `training` VALUES ('2013-10-17',4);


-- INSERT INTO `participation` VALUES (2,0,1,'2013-03-11',1,2,'2013-09-12');
-- INSERT INTO `participation` VALUES (3,0,1,'2013-03-11',1,1,'2013-09-12');
-- INSERT INTO `participation` VALUES (4,0,0,'2013-03-11',0,2,'2013-09-10');
-- INSERT INTO `participation` VALUES (5,0,0,'2013-03-11',1,0,'2013-09-10');
-- INSERT INTO `participation` VALUES (6,0,1,'2013-03-11',1,1,'2013-10-10');
-- INSERT INTO `participation` VALUES (7,0,0,'2013-03-11',1,2,'2013-10-10');
-- INSERT INTO `participation` VALUES (8,0,0,'2013-03-11',0,4,'2013-10-29');
-- INSERT INTO `participation` VALUES (9,0,0,'2013-03-11',0,5,'2013-10-29');
-- INSERT INTO `participation` VALUES (10,0,0,'2013-03-11',0,6,'2013-10-29');
-- INSERT INTO `participation` VALUES (11,0,0,'2013-03-11',0,7,'2013-10-29');
-- INSERT INTO `participation` VALUES (12,0,0,'2013-03-11',0,8,'2013-10-29');
