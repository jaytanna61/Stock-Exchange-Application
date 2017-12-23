CREATE DATABASE  IF NOT EXISTS `user` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `user`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: user
-- ------------------------------------------------------
-- Server version	5.7.19-log

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
-- Table structure for table `account_history`
--

DROP TABLE IF EXISTS `account_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `account_history` (
  `userid` varchar(45) DEFAULT NULL,
  `symbol` varchar(45) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `count` varchar(45) DEFAULT NULL,
  `managerid` varchar(45) DEFAULT NULL,
  `manager_commission` varchar(45) DEFAULT NULL,
  `transactionid` int(11) NOT NULL AUTO_INCREMENT,
  `buy_or_sell` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`transactionid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account_history`
--

LOCK TABLES `account_history` WRITE;
/*!40000 ALTER TABLE `account_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `account_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admin` (
  `adminid` varchar(45) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`adminid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin','admin');
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client_request`
--

DROP TABLE IF EXISTS `client_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client_request` (
  `requestid` int(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(45) DEFAULT NULL,
  `managerid` varchar(45) DEFAULT NULL,
  `symbol` varchar(45) DEFAULT NULL,
  `number` varchar(45) DEFAULT NULL,
  `buy_or_sell` varchar(45) DEFAULT NULL,
  `status` varchar(10) DEFAULT 'Pending',
  PRIMARY KEY (`requestid`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client_request`
--

LOCK TABLES `client_request` WRITE;
/*!40000 ALTER TABLE `client_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `client_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manager`
--

DROP TABLE IF EXISTS `manager`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manager` (
  `managerid` varchar(45) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `commission` varchar(45) DEFAULT NULL,
  `license` varchar(45) DEFAULT NULL,
  `activated` varchar(45) DEFAULT NULL,
  `account_balance` double DEFAULT '0',
  PRIMARY KEY (`managerid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manager`
--

LOCK TABLES `manager` WRITE;
/*!40000 ALTER TABLE `manager` DISABLE KEYS */;
INSERT INTO `manager` VALUES ('Jas','Jas','j@albay.edu','Jas','10','abc005','true',0),('Sags','Sagar','s@albany.edu','Sags','20','mkdakdla','false',0),('Shaun','Shaun','s@albany.edu','Shaun','15','abc123','false',0),('tj','TJ','j@alba.edu','tj','20','naksdn','true',0);
/*!40000 ALTER TABLE `manager` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stock_count`
--

DROP TABLE IF EXISTS `stock_count`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stock_count` (
  `userid` varchar(45) NOT NULL,
  `symbol` varchar(45) NOT NULL,
  `count` int(11) DEFAULT NULL,
  PRIMARY KEY (`userid`,`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stock_count`
--

LOCK TABLES `stock_count` WRITE;
/*!40000 ALTER TABLE `stock_count` DISABLE KEYS */;
/*!40000 ALTER TABLE `stock_count` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `symbols`
--

DROP TABLE IF EXISTS `symbols`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `symbols` (
  `symbol` varchar(45) NOT NULL,
  `cname` varchar(45) DEFAULT NULL,
  `price` varchar(45) DEFAULT NULL,
  `volume` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `symbols`
--

LOCK TABLES `symbols` WRITE;
/*!40000 ALTER TABLE `symbols` DISABLE KEYS */;
INSERT INTO `symbols` VALUES ('AAPL','Apple','173.9700','6751265'),('AMZN','Amazon LLC','1179.1400','814078'),('AR','Antero Resources','17.6600','198026'),('EBAY','Ebay','38.3700','4516584'),('FB','Facebook, Inc.','180.1800','5300958'),('GOLD','Gold','92.1300','66364'),('GOOGL','Google','1072.0000','596031'),('MSFT','Microsoft','86.8500','12977320'),('SLV','Silver','15.1500','124261'),('TWTR','Twitter, Inc.','22.2200','302279');
/*!40000 ALTER TABLE `symbols` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_table`
--

DROP TABLE IF EXISTS `user_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `userid` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `account_balance` double DEFAULT '100000',
  `managerid` varchar(45) DEFAULT 'not_set',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_table`
--

LOCK TABLES `user_table` WRITE;
/*!40000 ALTER TABLE `user_table` DISABLE KEYS */;
INSERT INTO `user_table` VALUES (1,'jay','jt@albany.edu','jaytanna61','33637371',100000,'Jas'),(9,'Sagar','s@albany.edu','sagar','12345',100000,'not_set'),(10,'Khusal','k@albany.edu','khus','khus',100000,'not_set');
/*!40000 ALTER TABLE `user_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_watchlist`
--

DROP TABLE IF EXISTS `user_watchlist`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_watchlist` (
  `symbol` varchar(45) NOT NULL,
  `userid` varchar(45) NOT NULL,
  PRIMARY KEY (`userid`,`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_watchlist`
--

LOCK TABLES `user_watchlist` WRITE;
/*!40000 ALTER TABLE `user_watchlist` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_watchlist` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-17 21:51:30
