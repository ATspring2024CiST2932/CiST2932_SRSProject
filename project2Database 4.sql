CREATE DATABASE  IF NOT EXISTS `myproject2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `myproject2`;
-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: myproject2
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mentorassignments`
--

DROP TABLE IF EXISTS `mentorassignments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mentorassignments` (
  `mentorID` int NOT NULL,
  `menteeID` int NOT NULL,
  `AssignmentID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`AssignmentID`),
  UNIQUE KEY `menteeID_UNIQUE` (`menteeID`),
  UNIQUE KEY `AssignmentID_UNIQUE` (`AssignmentID`),
  KEY `fk_mentorID` (`mentorID`),
  KEY `fk_menteeID` (`menteeID`),
  CONSTRAINT `fk_menteeID` FOREIGN KEY (`menteeID`) REFERENCES `newhireinfo` (`EmployeeID`),
  CONSTRAINT `fk_mentorID` FOREIGN KEY (`mentorID`) REFERENCES `newhireinfo` (`EmployeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mentorassignments`
--

LOCK TABLES `mentorassignments` WRITE;
/*!40000 ALTER TABLE `mentorassignments` DISABLE KEYS */;
INSERT INTO `mentorassignments` VALUES (1,5,1),(1,6,2),(1,7,3),(2,8,4),(2,9,5),(2,10,6),(3,4,7),(3,12,8),(3,13,9),(4,14,10),(4,15,11),(4,16,12);
/*!40000 ALTER TABLE `mentorassignments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newhireinfo`
--

DROP TABLE IF EXISTS `newhireinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `newhireinfo` (
  `EmployeeID` int NOT NULL,
  `Name` varchar(50) DEFAULT NULL,
  `EmploymentType` varchar(50) DEFAULT NULL,
  `Mentor` bit(1) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`EmployeeID`),
  KEY `idx_name` (`Name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newhireinfo`
--

LOCK TABLES `newhireinfo` WRITE;
/*!40000 ALTER TABLE `newhireinfo` DISABLE KEYS */;
INSERT INTO `newhireinfo` VALUES (1,'John Mentor','Full Time',_binary '','JohnMentor@example.com'),(2,'Bob Mentor','Contractor',_binary '','BobMentor@example.com'),(3,'Eve Mentor','Full Time',_binary '','EveMentor@example.com'),(4,'Grace Mentor','Full Time',_binary '','GraceMentor@example.com'),(5,'Alice Mentee','Full Time',_binary '\0','AliceMentee@example.com'),(6,'Charlie Mentee','Full Time',_binary '\0','CharlieMentee@example.com'),(7,'David Mentee','Contractor',_binary '\0','DavidMentee@example.com'),(8,'Frank Mentee','Full Time',_binary '\0','FrankMentee@example.com'),(9,'Emily Mentee','Full-Time',_binary '\0','EmilyMentee@example.com'),(10,'Daniel Mentee','Part-Time',_binary '\0','DanielMentee@example.com'),(11,'Grace Mentee','Contractor',_binary '\0','GraceMentee@example.com'),(12,'Isaac Mentee','Full-Time',_binary '\0','IsaacMentee@example.com'),(13,'Hannah Mentee','Part-Time',_binary '\0','HannahMentee@example.com'),(14,'Jacob Mentee','Full-Time',_binary '\0','JacobMentee@example.com'),(15,'Lily Mentee','Contractor',_binary '\0','LilyMentee@example.com'),(16,'Olivia Mentee','Part-Time',_binary '\0','OliviaMentee@example.com');
/*!40000 ALTER TABLE `newhireinfo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `peercodingtasks`
--

DROP TABLE IF EXISTS `peercodingtasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `peercodingtasks` (
  `task_id` int NOT NULL AUTO_INCREMENT,
  `task_url` varchar(255) DEFAULT NULL,
  `task_number` varchar(50) DEFAULT NULL,
  `task_type` varchar(50) DEFAULT NULL,
  `total_hours` decimal(38,2) DEFAULT NULL,
  `employeeID` int DEFAULT NULL,
  PRIMARY KEY (`task_id`),
  KEY `fk_employeeID` (`employeeID`),
  CONSTRAINT `fk_employeeID` FOREIGN KEY (`employeeID`) REFERENCES `newhireinfo` (`EmployeeID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `peercodingtasks`
--

LOCK TABLES `peercodingtasks` WRITE;
/*!40000 ALTER TABLE `peercodingtasks` DISABLE KEYS */;
INSERT INTO `peercodingtasks` VALUES (1,'http://example.com/task1','T123','UI',10.50,1),(2,'http://example.com/task2','T124','Cypress',8.75,2),(3,'http://example.com/task3','T125','Service',15.25,3),(4,'http://example.com/task4','T126','Junit',12.00,4),(5,'example.com','t126','Java',2.10,5),(6,'example.com','t126','Java2',2.10,11),(7,'example.com','t126','Java2',2.10,16),(8,'example.com','t126','Java2',2.10,12);
/*!40000 ALTER TABLE `peercodingtasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'myproject2'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-04-29 21:13:34
