-- You can use this file to load seed data into the database using SQL statements
insert into Member (id, firstname, name, email, phone_number,carsize, city) values (0, 'John', 'Smith', 'john.smith@mailinator.com', '2125551212', 5, 'Großkrotzenburg') ;
insert into Member (id, firstname, name, email, phone_number,carsize, city) values (1, 'Test1', 'Test2', 'test.test2@mailinator.com', '2125551212', 5, 'Test') ;
insert into Member (id, firstname, name, email, phone_number,carsize, city) values (2, 'Firstname', 'Lastname', 'firstname.lastname', '2125551212', 5, 'Test2') ;
INSERT INTO Trainingday (weekday, location, pickUpTimeSource, pickUpTimeTarget , timeFrom, timeTo) VALUES ('Dienstag','Großkrotzenburg','16:50','18:40','17:00','18:30') ;
INSERT INTO Trainingday (weekday, location, pickUpTimeSource, pickUpTimeTarget , timeFrom, timeTo) VALUES ('Mittwoch','Großkrotzenburg','16:50','18:40','17:00','18:30') ;
INSERT INTO Training (currentDate, calendarWeek, trainingDay_weekday) VALUES ('2013-09-10', '45', 'Dienstag') ;
INSERT INTO Training (currentDate, calendarWeek, trainingDay_weekday) VALUES ('2013-09-12', '45', 'Mittwoch') ;
INSERT INTO Participation (id, drivingBack, drivingForth, participating, player_id, trainingItem_currentDate)VALUES('1', '1', '1', '1', '0', '2013-09-10') ;
INSERT INTO Participation (id, drivingBack, drivingForth, participating, player_id, trainingItem_currentDate)VALUES('2', '0', '1', '1', '2', '2013-09-12') ;
