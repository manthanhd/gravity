-- MySQL dump 10.13  Distrib 5.5.35, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: webdb
-- ------------------------------------------------------
-- Server version	5.5.35-0ubuntu0.12.04.2

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
-- Table structure for table `App_Custom`
--

DROP TABLE IF EXISTS `App_Custom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `App_Custom` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `tokenid` varchar(50) DEFAULT NULL,
  `jar_file` varchar(200) DEFAULT NULL,
  `main_class` varchar(45) DEFAULT NULL,
  `jre_version` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



--
-- Table structure for table `Apps`
--

DROP TABLE IF EXISTS `Apps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Apps` (
  `app_id` varchar(50) NOT NULL,
  `app_name` varchar(45) DEFAULT NULL,
  `app_description` varchar(200) DEFAULT NULL,
  `dev_id` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `Request`
--

DROP TABLE IF EXISTS `Request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Request` (
  `tokenid` varchar(50) NOT NULL,
  `app_name` varchar(45) DEFAULT NULL,
  `input_file` varchar(100) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `progress_percent` varchar(45) DEFAULT NULL,
  `output_file` varchar(100) DEFAULT NULL,
  `output_file_hdfs` varchar(100) DEFAULT NULL,
  `expired` bit(1) DEFAULT NULL,
  `requested_on` datetime DEFAULT NULL,
  `started_on` datetime DEFAULT NULL,
  `completed_on` datetime DEFAULT NULL,
  `prepTime` int(11) DEFAULT NULL,
  `hdfsInTime` int(11) DEFAULT NULL,
  `mapreduceTime` int(11) DEFAULT NULL,
  `hdfsOutTime` int(11) DEFAULT NULL,
  `cleanupTime` int(11) DEFAULT NULL,
  PRIMARY KEY (`tokenid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `Request_Archived`
--

DROP TABLE IF EXISTS `Request_Archived`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Request_Archived` (
  `tokenid` varchar(50) NOT NULL,
  `app_name` varchar(45) DEFAULT NULL,
  `input_file` varchar(100) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `progress_percent` varchar(45) DEFAULT NULL,
  `output_file` varchar(100) DEFAULT NULL,
  `output_file_hdfs` varchar(100) DEFAULT NULL,
  `expired` bit(1) DEFAULT NULL,
  `requested_on` datetime DEFAULT NULL,
  `started_on` datetime DEFAULT NULL,
  `completed_on` datetime DEFAULT NULL,
  `prepTime` int(11) DEFAULT NULL,
  `hdfsInTime` int(11) DEFAULT NULL,
  `mapreduceTime` int(11) DEFAULT NULL,
  `hdfsOutTime` int(11) DEFAULT NULL,
  `cleanupTime` int(11) DEFAULT NULL,
  PRIMARY KEY (`tokenid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;



-- Dump completed on 2014-05-02 23:00:34
