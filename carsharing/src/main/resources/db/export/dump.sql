-- MySQL dump 10.13  Distrib 5.6.12, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.6.12-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (4);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `id` bigint(20) NOT NULL,
  `carsize` int(11) DEFAULT NULL,
  `city` varchar(25) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `firstname` varchar(25) DEFAULT NULL,
  `name` varchar(25) NOT NULL,
  `phone_number` varchar(12) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (0,5,'GroÃŸkrotzenburg','john.smith@mailinator.com','John','Smith','2125551212'),(1,5,'Test','test.test2@mailinator.com','Test1','Test2','2125551212'),(2,5,'Test2','firstname.lastname','Firstname','Lastname','2125551212'),(3,3,'HalloStadt','hallo@hallo.de','Hallo','hallo','06186201653'),(4,4,'Großkrotzenburg','johann@hallo.de','Johann','Adam','06186'),(5,4,'Großkrotzenburg','moritz@hallo.de','Moritz','Elfert',NULL),(6,3,'Großkrotzenburg','julius@hallo.de','Julius','Eltner',NULL),(7,0,'Großkrotzenburg','silas@hallo.de','Silas','N',NULL),(8,3,'Großkrotzenburg','simon@hallo.de','Simon','Schleiden',NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `participation`
--

DROP TABLE IF EXISTS `participation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `participation` (
  `id` bigint(20) NOT NULL,
  `drivingBack` tinyint(1) NOT NULL,
  `drivingForth` tinyint(1) NOT NULL,
  `participating` tinyint(1) NOT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  `trainingItem_currentDate` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE5A0BD21CE3A28F4` (`trainingItem_currentDate`),
  KEY `FKE5A0BD21655704D8` (`player_id`),
  CONSTRAINT `FKE5A0BD21655704D8` FOREIGN KEY (`player_id`) REFERENCES `member` (`id`),
  CONSTRAINT `FKE5A0BD21CE3A28F4` FOREIGN KEY (`trainingItem_currentDate`) REFERENCES `training` (`currentDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `participation`
--

LOCK TABLES `participation` WRITE;
/*!40000 ALTER TABLE `participation` DISABLE KEYS */;
INSERT INTO `participation` VALUES (1,0,0,1,1,'2013-09-10'),(2,0,1,1,2,'2013-09-12'),(3,0,1,1,1,'2013-09-12'),(4,0,0,0,2,'2013-09-10'),(5,0,0,1,0,'2013-09-10'),(6,0,1,1,1,'2013-10-10'),(7,0,0,1,2,'2013-10-10'),(8,0,0,0,4,'2013-10-29'),(9,0,0,0,5,'2013-10-29'),(10,0,0,0,6,'2013-10-29'),(11,0,0,0,7,'2013-10-29'),(12,0,0,0,8,'2013-10-29');
/*!40000 ALTER TABLE `participation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training`
--

DROP TABLE IF EXISTS `training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training` (
  `currentDate` date NOT NULL,
  `trainingDay_weekday` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`currentDate`),
  UNIQUE KEY `currentDate` (`currentDate`),
  KEY `FK4FEA6CFA9B2A4E8` (`trainingDay_weekday`),
  CONSTRAINT `FK4FEA6CFA9B2A4E8` FOREIGN KEY (`trainingDay_weekday`) REFERENCES `trainingday` (`weekday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training`
--

LOCK TABLES `training` WRITE;
/*!40000 ALTER TABLE `training` DISABLE KEYS */;
INSERT INTO `training` VALUES ('2013-09-10','Dienstag'),('2013-10-10','Dienstag'),('2013-10-15','Dienstag'),('2013-10-29','Dienstag'),('2013-09-12','Mittwoch'),('2013-10-08','Mittwoch'),('2013-10-17','Mittwoch');
/*!40000 ALTER TABLE `training` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trainingday`
--

DROP TABLE IF EXISTS `trainingday`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `trainingday` (
  `weekday` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `pickUpTimeSource` varchar(255) NOT NULL,
  `pickUpTimeTarget` varchar(255) NOT NULL,
  `timeFrom` varchar(255) NOT NULL,
  `timeTo` varchar(255) NOT NULL,
  PRIMARY KEY (`weekday`),
  UNIQUE KEY `weekday` (`weekday`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trainingday`
--

LOCK TABLES `trainingday` WRITE;
/*!40000 ALTER TABLE `trainingday` DISABLE KEYS */;
INSERT INTO `trainingday` VALUES ('Dienstag','GroÃŸkrotzenburg','16:50','18:40','17:00','18:30'),('Mittwoch','GroÃŸkrotzenburg','16:50','18:40','17:00','18:30');
/*!40000 ALTER TABLE `trainingday` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-02 10:56:27
