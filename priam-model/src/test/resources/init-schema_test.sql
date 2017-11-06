-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: msqdev08    Database: priam_app
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.22-MariaDB-SACEM
USE priam_app;
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
-- Table structure for table `BATCH_JOB_EXECUTION`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `CREATE_TIME` datetime NOT NULL,
  `START_TIME` datetime DEFAULT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  `JOB_CONFIGURATION_LOCATION` varchar(2500) DEFAULT NULL,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  KEY `JOB_INST_EXEC_FK` (`JOB_INSTANCE_ID`),
  CONSTRAINT `JOB_INST_EXEC_FK` FOREIGN KEY (`JOB_INSTANCE_ID`) REFERENCES `BATCH_JOB_INSTANCE` (`JOB_INSTANCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_EXECUTION_CONTEXT`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_CONTEXT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_CONTEXT` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_EXECUTION_PARAMS`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_PARAMS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_PARAMS` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `TYPE_CD` varchar(6) NOT NULL,
  `KEY_NAME` varchar(100) NOT NULL,
  `STRING_VAL` varchar(250) DEFAULT NULL,
  `DATE_VAL` datetime DEFAULT NULL,
  `LONG_VAL` bigint(20) DEFAULT NULL,
  `DOUBLE_VAL` double DEFAULT NULL,
  `IDENTIFYING` char(1) NOT NULL,
  KEY `JOB_EXEC_PARAMS_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_PARAMS_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_INSTANCE`
--

DROP TABLE IF EXISTS `BATCH_JOB_INSTANCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_INSTANCE` (
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_KEY` varchar(32) NOT NULL,
  PRIMARY KEY (`JOB_INSTANCE_ID`),
  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_JOB_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_JOB_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_STEP_EXECUTION`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) NOT NULL,
  `STEP_NAME` varchar(100) NOT NULL,
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `START_TIME` datetime NOT NULL,
  `END_TIME` datetime DEFAULT NULL,
  `STATUS` varchar(10) DEFAULT NULL,
  `COMMIT_COUNT` bigint(20) DEFAULT NULL,
  `READ_COUNT` bigint(20) DEFAULT NULL,
  `FILTER_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_COUNT` bigint(20) DEFAULT NULL,
  `READ_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `WRITE_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `PROCESS_SKIP_COUNT` bigint(20) DEFAULT NULL,
  `ROLLBACK_COUNT` bigint(20) DEFAULT NULL,
  `EXIT_CODE` varchar(2500) DEFAULT NULL,
  `EXIT_MESSAGE` varchar(2500) DEFAULT NULL,
  `LAST_UPDATED` datetime DEFAULT NULL,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  KEY `JOB_EXEC_STEP_FK` (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_STEP_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_STEP_EXECUTION_CONTEXT`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_CONTEXT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION_CONTEXT` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `BATCH_STEP_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_SEQ`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;



INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);
INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);
INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);


--
-- Table structure for table `PRIAM_FICHIER`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_FICHIER` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  `NOM` varchar(255) DEFAULT NULL COMMENT 'Nom du fichier',
  `CDEFAMILTYPUTIL` varchar(10) DEFAULT NULL COMMENT 'Famille',
  `CDETYPUTIL` varchar(10) DEFAULT NULL COMMENT 'Type utilisation',
  `DATE_DEBUT_CHGT` datetime DEFAULT NULL COMMENT 'Dete de Debut de chargement',
  `DATE_FIN_CHGT` datetime DEFAULT NULL COMMENT 'Dete de Fin de chargement',
  `NB_LIGNES` bigint(20) DEFAULT NULL COMMENT 'Nombre de lignes dans le fichier',
  `STATUT_CODE` varchar(255) DEFAULT NULL COMMENT 'Statut primut du fichier',
  `NUMPROG` varchar(8) DEFAULT NULL,
  `SOURCE_AUTO` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`),
  KEY `FK_FAMILLE` (`CDEFAMILTYPUTIL`),
  KEY `FK_TYPE_UTIL` (`CDETYPUTIL`),
  KEY `FK_STATUT_CODE` (`STATUT_CODE`),
  KEY `PRIAM_FICHIER_ID__index` (`ID`),
  KEY `FK_NUMPROG` (`NUMPROG`),
  CONSTRAINT `FK_FAMILLE` FOREIGN KEY (`CDEFAMILTYPUTIL`) REFERENCES `SAREFTR_FAMILTYPUTIL` (`CDEFAMILTYPUTIL`),
  CONSTRAINT `FK_NUMPROG` FOREIGN KEY (`NUMPROG`) REFERENCES `PRIAM_PROGRAMME` (`NUMPROG`),
  CONSTRAINT `FK_STATUT_CODE` FOREIGN KEY (`STATUT_CODE`) REFERENCES `PRIAM_STATUT` (`CODE`),
  CONSTRAINT `FK_TYPE_UTIL` FOREIGN KEY (`CDETYPUTIL`) REFERENCES `SAREFTR_TYPUTIL` (`CDETYPUTIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_FICHIER_FELIX`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_FELIX`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_FICHIER_FELIX` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NUMPROG` varchar(8) NOT NULL,
  `FILENAME` varchar(255) DEFAULT NULL,
  `ERROR_LIST` varchar(512) DEFAULT NULL,
  `CONTENT` mediumblob,
  `DATE_CREATION` datetime DEFAULT NULL,
  `STATUT` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `NUMPROG_FK` (`NUMPROG`),
  CONSTRAINT `NUMPROG_FK` FOREIGN KEY (`NUMPROG`) REFERENCES `PRIAM_PROGRAMME` (`NUMPROG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_FICHIER_FELIX_LOG`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_FELIX_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_FICHIER_FELIX_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_FICHIER_FELIX` bigint(20) DEFAULT NULL,
  `LOG` varchar(1024) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_FICHIER_FELIX_FK` (`ID_FICHIER_FELIX`),
  CONSTRAINT `ID_FICHIER_FELIX_FK` FOREIGN KEY (`ID_FICHIER_FELIX`) REFERENCES `PRIAM_FICHIER_FELIX` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_FICHIER_HISTORIQUE`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_HISTORIQUE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_FICHIER_HISTORIQUE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  `ID_FICHIER` bigint(20) NOT NULL,
  `CODE_STATUT` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `CODE_STATUT_FK` (`CODE_STATUT`),
  CONSTRAINT `CODE_STATUT_FK` FOREIGN KEY (`CODE_STATUT`) REFERENCES `PRIAM_STATUT` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_FICHIER_LOG`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_LOG`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_FICHIER_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_FICHIER` bigint(20) DEFAULT NULL,
  `LOG` varchar(1024) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_FICHIER_FK` (`ID_FICHIER`),
  CONSTRAINT `ID_FICHIER_FK` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_LIGNE_PREPREP`
--

DROP TABLE IF EXISTS `PRIAM_LIGNE_PREPREP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_LIGNE_PREPREP` (
  `cdeCisac` varchar(3) DEFAULT NULL,
  `cdeTer` bigint(20) DEFAULT NULL,
  `rionEffet` int(11) DEFAULT NULL,
  `cdeFamilTypUtil` varchar(45) DEFAULT NULL,
  `cdeModFac` varchar(45) DEFAULT NULL,
  `numProg` int(11) DEFAULT NULL,
  `keyLigPenel` int(11) NOT NULL AUTO_INCREMENT,
  `cdeUtil` varchar(45) DEFAULT NULL,
  `cdeTypUtil` varchar(45) DEFAULT NULL,
  `cdeTypProg` varchar(45) DEFAULT NULL,
  `cdeCompl` varchar(45) DEFAULT NULL,
  `libProg` varchar(100) DEFAULT NULL,
  `compLibProg` varchar(100) DEFAULT NULL,
  `datDbtProg` datetime DEFAULT NULL,
  `datFinProg` datetime DEFAULT NULL,
  `hrDbt` int(11) DEFAULT NULL,
  `hrFin` int(11) DEFAULT NULL,
  `cdeGreDif` varchar(45) DEFAULT NULL,
  `cdeModDif` varchar(45) DEFAULT NULL,
  `cdeTypIde12` varchar(45) DEFAULT NULL,
  `ide12` bigint(20) DEFAULT NULL,
  `datDif` datetime DEFAULT NULL,
  `hrDif` int(11) DEFAULT NULL,
  `durDif` int(11) DEFAULT NULL,
  `nbrDif` int(11) DEFAULT NULL,
  `mt` double DEFAULT NULL,
  `ctna` varchar(45) DEFAULT NULL,
  `paramCoefHor` varchar(10) DEFAULT NULL,
  `durDifCtna` int(11) DEFAULT NULL,
  `cdeLng` varchar(45) DEFAULT NULL,
  `indDoubSsTit` varchar(45) DEFAULT NULL,
  `tax` double DEFAULT NULL,
  PRIMARY KEY (`keyLigPenel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_PARAMAPPLI`
--

DROP TABLE IF EXISTS `PRIAM_PARAMAPPLI`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_PARAMAPPLI` (
  `CDEPARAM` varchar(100) NOT NULL,
  `LIBSTAT` varchar(450) DEFAULT NULL,
  `VAL` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`CDEPARAM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_PARAMETRAGE`
--

DROP TABLE IF EXISTS `PRIAM_PARAMETRAGE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_PARAMETRAGE` (
  `USER_ID` varchar(127) NOT NULL,
  `PRIAM_KEY` varchar(60) NOT NULL,
  `PRIAM_VALUE` varchar(127) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`,`PRIAM_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_PROGRAMME`
--

DROP TABLE IF EXISTS `PRIAM_PROGRAMME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_PROGRAMME` (
  `NUMPROG` varchar(8) NOT NULL,
  `NOM` varchar(255) DEFAULT NULL,
  `RION_THEORIQUE` int(11) DEFAULT NULL,
  `CDEFAMILTYPUTIL` varchar(10) NOT NULL COMMENT 'Code Famille Type Utilisation',
  `CDETYPUTIL` varchar(10) NOT NULL COMMENT 'Code type d utilisation',
  `TYPE_REPART` varchar(45) DEFAULT NULL,
  `DATE_CREATION` datetime DEFAULT NULL COMMENT 'Dete de creation du programme',
  `STATUT_PROG_CODE` varchar(50) DEFAULT NULL COMMENT 'Statut du programme',
  `RION_PAIEMENT` int(11) DEFAULT NULL,
  `USERCRE` varchar(60) DEFAULT NULL,
  `DATMAJ` datetime DEFAULT NULL,
  `USERMAJ` varchar(60) DEFAULT NULL,
  `DATAFFECT` datetime DEFAULT NULL,
  `USERAFFECT` varchar(60) DEFAULT NULL,
  `DATE_VALIDATION` datetime DEFAULT NULL,
  `USER_VALIDATION` varchar(60) DEFAULT NULL,
  `DATE_DBT_PRG` date DEFAULT NULL,
  `DATE_FIN_PRG` date DEFAULT NULL,
  `CDE_TER` decimal(4,0) DEFAULT NULL,
  `DATE_REPARTITION` datetime DEFAULT NULL,
  PRIMARY KEY (`NUMPROG`),
  KEY `FK_PROGRAMME_FAMILLE` (`CDEFAMILTYPUTIL`),
  KEY `FK_PROGRAMME_TYPE_UTIL` (`CDETYPUTIL`),
  KEY `FK_STATUT_PROG_CODE` (`STATUT_PROG_CODE`),
  KEY `PRIAM_PROGRAMME_NUMPROG__index` (`NUMPROG`),
  KEY `FK_PRG_RION_THEORIQUE` (`RION_THEORIQUE`),
  KEY `FK_PRG_RION_PAIEMENT` (`RION_PAIEMENT`),
  CONSTRAINT `FK_PRG_RION_PAIEMENT` FOREIGN KEY (`RION_PAIEMENT`) REFERENCES `SAREFTR_RION` (`RION`),
  CONSTRAINT `FK_PRG_RION_THEORIQUE` FOREIGN KEY (`RION_THEORIQUE`) REFERENCES `SAREFTR_RION` (`RION`),
  CONSTRAINT `FK_PROGRAMME_FAMILLE` FOREIGN KEY (`CDEFAMILTYPUTIL`) REFERENCES `SAREFTR_FAMILTYPUTIL` (`CDEFAMILTYPUTIL`),
  CONSTRAINT `FK_PROGRAMME_TYPE_UTIL` FOREIGN KEY (`CDETYPUTIL`) REFERENCES `SAREFTR_TYPUTIL` (`CDETYPUTIL`),
  CONSTRAINT `FK_STATUT_PROG_CODE` FOREIGN KEY (`STATUT_PROG_CODE`) REFERENCES `PRIAM_STATUT_PROGRAMME` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_PROGRAMME_SEQUENCE`
--

DROP TABLE IF EXISTS `PRIAM_PROGRAMME_SEQUENCE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_PROGRAMME_SEQUENCE` (
  `ANNEE` varchar(4) NOT NULL,
  `CODESEQUENCE` bigint(20) NOT NULL,
  PRIMARY KEY (`CODESEQUENCE`,`ANNEE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

create view PRIAM_PROG_VIEW as
  SELECT DISTINCT
    `pr`.`NUMPROG`                                                         AS `NUMPROG`,
    `pr`.`NOM`                                                             AS `NOM`,
    `pr`.`RION_THEORIQUE`                                                  AS `RION_THEORIQUE`,
    `pr`.`CDEFAMILTYPUTIL`                                                 AS `CDEFAMILTYPUTIL`,
    `pr`.`CDETYPUTIL`                                                      AS `CDETYPUTIL`,
    `pr`.`TYPE_REPART`                                                     AS `TYPE_REPART`,
    `pr`.`DATE_CREATION`                                                   AS `DATE_CREATION`,
    `pr`.`STATUT_PROG_CODE`                                                AS `STATUT_PROG_CODE`,
    `pr`.`RION_PAIEMENT`                                                   AS `RION_PAIEMENT`,
    `pr`.`USERCRE`                                                         AS `USERCRE`,
    `pr`.`DATMAJ`                                                          AS `DATMAJ`,
    `pr`.`USERMAJ`                                                         AS `USERMAJ`,
    `pr`.`DATAFFECT`                                                       AS `DATAFFECT`,
    `pr`.`USERAFFECT`                                                      AS `USERAFFECT`,
    (SELECT count(`f`.`NUMPROG`)
     FROM `priam_app`.`PRIAM_FICHIER` `f`
     WHERE ((`pr`.`NUMPROG` = `f`.`NUMPROG`) AND (`f`.`SOURCE_AUTO` = 1))) AS `fichiers`,
    `pr`.`DATE_DBT_PRG`                                                    AS `DATEDBTPRG`,
    `pr`.`DATE_FIN_PRG`                                                    AS `DATEFINPRG`,
    `pr`.`CDE_TER`                                                         AS `CDETER`,
    `pr`.`USER_VALIDATION`                                                 AS `USERVALIDATION`,
    `pr`.`DATE_VALIDATION`                                                 AS `DATEVALIDATION`,
    `ff`.`STATUT`                                                          AS `STATUT_FICHIER_FELIX`,
    `pr`.`DATE_REPARTITION`                                                AS `DATE_REPARTITION`
  FROM (`priam_app`.`PRIAM_PROGRAMME` `pr` LEFT JOIN `priam_app`.`PRIAM_FICHIER_FELIX` `ff`
      ON ((`ff`.`NUMPROG` = `pr`.`NUMPROG`)))
  GROUP BY `pr`.`NUMPROG`;
DROP TABLE IF EXISTS `PRIAM_ROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_ROLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Identifiant technique',
  `EXTERNAL_ID` varchar(255) DEFAULT NULL COMMENT 'Code Role',
  `ROLE` varchar(255) DEFAULT NULL COMMENT 'Description',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_ROLE_RIGHTS`
--

DROP TABLE IF EXISTS `PRIAM_ROLE_RIGHTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_ROLE_RIGHTS` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT 'Role Id',
  `RIGHTS` varchar(255) DEFAULT NULL COMMENT 'Habilitation',
  `LIBELLE` varchar(255) DEFAULT NULL COMMENT 'Libelle habilitation',
  KEY `FK_2555xwvbf9ilta6fgul5al4ch` (`ROLE_ID`),
  CONSTRAINT `FK_2555xwvbf9ilta6fgul5al4ch` FOREIGN KEY (`ROLE_ID`) REFERENCES `PRIAM_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_STATUT`
--

DROP TABLE IF EXISTS `PRIAM_STATUT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_STATUT` (
  `CODE` varchar(255) NOT NULL DEFAULT 'EN_COURS',
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PRIAM_STATUT_PROGRAMME`
--

DROP TABLE IF EXISTS `PRIAM_STATUT_PROGRAMME`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_STATUT_PROGRAMME` (
  `CODE` varchar(50) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `SAREFTJ_LIBCISACROLE`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBCISACROLE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SAREFTJ_LIBCISACROLE` (
  `CDELNG` varchar(3) NOT NULL COMMENT 'Code langue ISO2A de la langue utilisée dans les environements sacem',
  `CDEROLCISAC` varchar(2) NOT NULL,
  `LIB` varchar(300) DEFAULT NULL COMMENT 'Libellé long du type de découpage commune canton',
  `LIBABRG` varchar(25) DEFAULT NULL COMMENT 'Libellé court du type de découpage communes canton',
  `DATCRE` date DEFAULT NULL COMMENT 'Date de création',
  `USERCRE` varchar(60) DEFAULT NULL COMMENT 'Créés par',
  `DATMAJ` date DEFAULT NULL COMMENT 'Modifiée le',
  `USERMAJ` varchar(60) DEFAULT NULL COMMENT 'Modifiée par',
  PRIMARY KEY (`CDELNG`,`CDEROLCISAC`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='libellé des rôle des Participants (CISAC)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SAREFTJ_LIBFAMILTYPUTIL`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBFAMILTYPUTIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SAREFTJ_LIBFAMILTYPUTIL` (
  `CDELNG` varchar(3) NOT NULL COMMENT 'Code langue ISO2A de la langue utilisee dans les environements sacem',
  `CDEFAMILTYPUTIL` varchar(10) NOT NULL COMMENT 'Code Famille Type dUtilisation',
  `LIBFAMILTYPUTIL` varchar(300) NOT NULL COMMENT 'Libelle de la famille de type dutilisation',
  `LIBABRGFAMILTYPUTIL` varchar(25) DEFAULT NULL COMMENT 'Libelle abrege de la famille de type dutilisation',
  `DATCRE` datetime NOT NULL COMMENT 'DATETIME de creation',
  `USERCRE` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la creation',
  `DATMAJ` datetime NOT NULL COMMENT 'DATETIME de modification',
  `USERMAJ` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la modification',
  PRIMARY KEY (`CDELNG`,`CDEFAMILTYPUTIL`),
  KEY `SAREFTJ_LIBFA_LIBFAMILTYPUT_FK` (`CDEFAMILTYPUTIL`),
  CONSTRAINT `SAREFTJ_LIBFA_LIBFAMILTYPUT_FK` FOREIGN KEY (`CDEFAMILTYPUTIL`) REFERENCES `SAREFTR_FAMILTYPUTIL` (`CDEFAMILTYPUTIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Libelle dune famille de type dutilisation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SAREFTJ_LIBTER`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBTER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SAREFTJ_LIBTER` (
  `CDELNG` varchar(3) NOT NULL COMMENT 'Code langue ISO2A de la langue utilisee dans les environements sacem',
  `CDEPAYSISO4N` int(4) NOT NULL COMMENT 'Code pays suivant la norme 3366 - ISO 4N',
  `NOMPAYSABR` varchar(25) DEFAULT NULL COMMENT 'TERRI-ABRFRA Nom abrege du territoire Attention, multilinguisme. Obligatoire pour le Francais',
  `NOMPAYS` varchar(60) DEFAULT NULL COMMENT 'TERRI-NOMFRA Nom du territoire Attention, multilinguisme. Obligatoire pour le Francais',
  `DATCRE` date DEFAULT NULL COMMENT 'Date de creation',
  `USERCRE` varchar(60) DEFAULT NULL COMMENT 'Utilisateur ayant cree lentree',
  `DATMAJ` date DEFAULT NULL COMMENT 'Date de modification',
  `USERMAJ` varchar(60) DEFAULT NULL COMMENT 'Utilisateur ayant effectue la derniere mise a jour',
  PRIMARY KEY (`CDELNG`,`CDEPAYSISO4N`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='LIBELLE TERRITOIRE (RLATE';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SAREFTJ_LIBTYPUTIL`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBTYPUTIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SAREFTJ_LIBTYPUTIL` (
  `CDELNG` varchar(3) NOT NULL COMMENT 'Code langue ISO2A de la langue utilisee dans les environements sacem',
  `CDETYPUTIL` varchar(10) NOT NULL COMMENT 'Code type d utilisation',
  `LIBTYPUTIL` varchar(300) NOT NULL COMMENT 'Libelle type dutilisation',
  `LIBABRGTYPUTIL` varchar(25) DEFAULT NULL COMMENT 'Libelle abrege du type dutilisation',
  `DATCRE` datetime NOT NULL COMMENT 'DATETIME de creation',
  `USERCRE` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la creation',
  `DATMAJ` datetime NOT NULL COMMENT 'DATETIME de modification',
  `USERMAJ` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la modification',
  `LIBFEUILTYPUTIL` varchar(300) NOT NULL COMMENT ' Libelle feuille type dutilisation',
  PRIMARY KEY (`CDELNG`,`CDETYPUTIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Type d utilisation a comme libelle';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SAREFTJ_LIBUTIL`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBUTIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SAREFTJ_LIBUTIL` (
  `CDELNG` varchar(3) NOT NULL COMMENT 'Code langue ISO2A de la langue utilisee dans les environements sacem',
  `CDEUTIL` varchar(10) NOT NULL COMMENT 'Code utilisateur',
  `LIBUTIL` varchar(300) DEFAULT NULL COMMENT 'Libelle utilisateur',
  `LIBABRGUTIL` varchar(25) DEFAULT NULL COMMENT 'Libelle abrege Utilisateur',
  `DATCRE` date DEFAULT NULL COMMENT 'Date de creation',
  `USERCRE` varchar(60) DEFAULT NULL COMMENT 'Utilisateur ayant effectue la creation',
  `DATMAJ` date DEFAULT NULL COMMENT 'Date de modification',
  `USERMAJ` varchar(60) DEFAULT NULL COMMENT 'Utilisateur ayant effectue la modification',
  `LIBFEUILLET` varchar(4) DEFAULT NULL COMMENT 'Libellé du feuillet',
  PRIMARY KEY (`CDELNG`,`CDEUTIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Libelle dun utilisateur';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SAREFTR_FAMILTYPUTIL`
--

DROP TABLE IF EXISTS `SAREFTR_FAMILTYPUTIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SAREFTR_FAMILTYPUTIL` (
  `CDEFAMILTYPUTIL` varchar(10) NOT NULL COMMENT 'Code Famille Type dUtilisation',
  `FILTRE` bigint(20) NOT NULL COMMENT 'Filtrage des donnees de la table',
  `ORDAFF` int(11) NOT NULL COMMENT 'Ordre affichage',
  `COM` varchar(300) DEFAULT NULL COMMENT 'Commentaires libres',
  `INDLIEUDIF` varchar(1) DEFAULT NULL COMMENT 'Indicateur du type de diffusion audiovisuelle (TV )',
  `TYPRION` varchar(10) NOT NULL COMMENT 'Type de repartition',
  `TYPDIFAUDV` varchar(10) DEFAULT NULL COMMENT 'Type de diffusion audiovisuel',
  `DATCRE` datetime NOT NULL COMMENT 'Date de creation',
  `USERCRE` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la creation',
  `DATMAJ` datetime NOT NULL COMMENT 'Date de modification',
  `USERMAJ` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la modification',
  `DATDBTVLD` datetime NOT NULL COMMENT 'Date de debut de validite',
  `DATFINVLD` datetime DEFAULT NULL COMMENT 'Date de fin d evalidite',
  `CDETYPPROCESS` varchar(10) DEFAULT NULL,
  `POIDSOF` decimal(8,2) DEFAULT NULL,
  `CDEFAMILPART` char(2) NOT NULL,
  `FAMILREMPL` varchar(10) DEFAULT NULL COMMENT 'valeur de remplacement pour interrogation de la documentation si la famille n¿est plus valide',
  PRIMARY KEY (`CDEFAMILTYPUTIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Famille de type dutilisation';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SAREFTR_RION`
--

DROP TABLE IF EXISTS `SAREFTR_RION`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SAREFTR_RION` (
  `RION` int(11) NOT NULL COMMENT 'No Repartition',
  `DATCALC` datetime DEFAULT NULL COMMENT 'Date de calcul',
  `DATRGLMT` datetime DEFAULT NULL COMMENT 'Date Reglement',
  `FILTRE` bigint(20) NOT NULL COMMENT 'Filtre',
  `ORDAFF` int(11) NOT NULL COMMENT 'Ordre affichage',
  `COM` varchar(300) DEFAULT NULL COMMENT 'commentaire',
  `DATCRE` datetime NOT NULL COMMENT 'Date de creation',
  `USERCRE` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la creation',
  `DATMAJ` datetime NOT NULL COMMENT 'Modifie le',
  `USERMAJ` varchar(60) NOT NULL COMMENT 'Modifie par',
  PRIMARY KEY (`RION`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Table qui regroupe les numero de Rion de repartition';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SAREFTR_TYPUTIL`
--

DROP TABLE IF EXISTS `SAREFTR_TYPUTIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SAREFTR_TYPUTIL` (
  `CDETYPUTIL` varchar(10) NOT NULL COMMENT 'Code type d utilisation',
  `CDEFAMILTYPUTIL` varchar(10) DEFAULT NULL COMMENT 'Code Famille Type dUtilisation',
  `FILTRE` bigint(20) NOT NULL COMMENT 'Filtrage des donnees de la table',
  `ORDAFF` int(11) NOT NULL COMMENT 'Ordre affichage',
  `FLGULYSS` smallint(6) DEFAULT NULL COMMENT 'indique si la valeur est a traiter par lapplication ULYSS',
  `TYPDRT` smallint(6) DEFAULT NULL COMMENT 'Type de droit',
  `COM` varchar(300) DEFAULT NULL COMMENT 'Commentaires libres',
  `DATCRE` datetime NOT NULL COMMENT 'Date de creation',
  `USERCRE` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la creation',
  `DATMAJ` datetime NOT NULL COMMENT 'Date de modification',
  `USERMAJ` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la modification',
  `DATDBTVLD` datetime NOT NULL COMMENT 'Date de debut de validite',
  `DATFINVLD` datetime DEFAULT NULL COMMENT 'Date de fin d evalidite',
  `CDEDISTRBCATEGTYP` smallint(6) NOT NULL COMMENT 'code type catégorie distribution CISAC',
  `TXFRAIS` varchar(3) DEFAULT NULL COMMENT 'code Taux de frais',
  `FLGSUIDG` smallint(6) DEFAULT NULL COMMENT 'Flag de suivi des droits généraux',
  `FLGIGNOREDP` smallint(6) DEFAULT NULL COMMENT 'Flag Ignorer le Domaine Public',
  `FLGTAX` smallint(6) DEFAULT NULL COMMENT 'Indicateur de traitement a  la taxation',
  `TYPMT` varchar(3) DEFAULT NULL COMMENT 'Type de montant par défaut à utiliser si absent dans penef et pour application des frais',
  `TYPUTILREMPL` varchar(10) DEFAULT NULL COMMENT 'valeur de remplacement pour interrogation de la documentation si le type d¿utilisation n¿est plus valide',
  PRIMARY KEY (`CDETYPUTIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Type d utilisation';


ALTER TABLE PRIAM_FICHIER
  ADD `TYPE_FICHIER` varchar(25) DEFAULT NULL COMMENT 'type de fichier peut etre CP CMD FV pour differencier l envoi';

DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_CMS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_LIGNE_PROGRAMME_CMS` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cdeCisac` varchar(3) DEFAULT NULL,
  `cdeFamilTypUtil` varchar(45) DEFAULT NULL,
  `numProg` int(11) DEFAULT NULL,
  `cdeUtil` varchar(45) DEFAULT NULL,
  `cdeTypUtil` varchar(45) DEFAULT NULL,
  `cdeGreDif` varchar(45) DEFAULT NULL,
  `cdeModDif` varchar(45) DEFAULT NULL,
  `cdeTypIde12` varchar(45) DEFAULT NULL,
  `ide12` bigint(20) DEFAULT NULL,
  `durDif` int(11) DEFAULT NULL,
  `nbrDif` int(11) DEFAULT NULL,
  `libelleUtilisateur` varchar(255) DEFAULT NULL,
  `mt` double DEFAULT NULL,
  `ctna` varchar(45) DEFAULT NULL,
  `paramCoefHor` varchar(10) DEFAULT NULL,
  `durDifCtna` int(11) DEFAULT NULL,
  `cdeLng` varchar(45) DEFAULT NULL,
  `indDoubSsTit` varchar(45) DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `typMt` varchar(45) DEFAULT NULL,
  `ID_FICHIER` bigint(20) DEFAULT NULL,
  `cdeGreIde12Cmplx` varchar(45) DEFAULT NULL,
  `cdeGreIde12` varchar(45) DEFAULT NULL,
  `titreAltOeuvPereCmplx` varchar(250) DEFAULT NULL,
  `titreAltPppalCmplx` varchar(250) DEFAULT NULL,
  `titreOriOeuvPereCmplx` varchar(250) DEFAULT NULL,
  `titreOriCmplx` varchar(250) DEFAULT NULL,
  `titreOeuvre` varchar(250) DEFAULT NULL,
  `cdePaysOriIso4NCmplx` varchar(45) DEFAULT NULL,
  `realisateurCmplx` varchar(250) DEFAULT NULL,
  `roleParticipant1` varchar(250) DEFAULT NULL,
  `nomParticipant1` varchar(250) DEFAULT NULL,
  `cdeTypUtilOri` varchar(45) DEFAULT NULL,
  `cdeFamilTypUtilOri` varchar(45) DEFAULT NULL,
  `utilisateur` varchar(250) DEFAULT 'Batch Extraction',
  `date_insertion` datetime DEFAULT CURRENT_TIMESTAMP,
  `ajout` varchar(45) DEFAULT 'Automatique',
  `selection` tinyint(1) DEFAULT '0',
  `idOeuvreManuel` int(11) DEFAULT NULL,
  `SEL_EN_COURS` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER_CMS` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_CMS` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_CP`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_LIGNE_PROGRAMME_CP` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cdeCisac` varchar(3) DEFAULT NULL,
  `cdeFamilTypUtil` varchar(45) DEFAULT NULL,
  `numProg` int(11) DEFAULT NULL,
  `cdeUtil` varchar(45) DEFAULT NULL,
  `cdeTypUtil` varchar(45) DEFAULT NULL,
  `cdeGreDif` varchar(45) DEFAULT NULL,
  `cdeModDif` varchar(45) DEFAULT NULL,
  `cdeTypIde12` varchar(45) DEFAULT NULL,
  `ide12` bigint(20) DEFAULT NULL,
  `durDif` int(11) DEFAULT NULL,
  `nbrDif` int(11) DEFAULT NULL,
  `libelleUtilisateur` varchar(255) DEFAULT NULL,
  `mt` double DEFAULT NULL,
  `ctna` varchar(45) DEFAULT NULL,
  `paramCoefHor` varchar(10) DEFAULT NULL,
  `durDifCtna` int(11) DEFAULT NULL,
  `cdeLng` varchar(45) DEFAULT NULL,
  `indDoubSsTit` varchar(45) DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `typMt` varchar(45) DEFAULT NULL,
  `ID_FICHIER` bigint(20) DEFAULT NULL,
  `cdeGreIde12Cmplx` varchar(45) DEFAULT NULL,
  `cdeGreIde12` varchar(45) DEFAULT NULL,
  `titreAltOeuvPereCmplx` varchar(250) DEFAULT NULL,
  `titreAltPppalCmplx` varchar(250) DEFAULT NULL,
  `titreOriOeuvPereCmplx` varchar(250) DEFAULT NULL,
  `titreOriCmplx` varchar(250) DEFAULT NULL,
  `titreOeuvre` varchar(250) DEFAULT NULL,
  `cdePaysOriIso4NCmplx` varchar(45) DEFAULT NULL,
  `realisateurCmplx` varchar(250) DEFAULT NULL,
  `roleParticipant1` varchar(250) DEFAULT NULL,
  `nomParticipant1` varchar(250) DEFAULT NULL,
  `cdeTypUtilOri` varchar(45) DEFAULT NULL,
  `cdeFamilTypUtilOri` varchar(45) DEFAULT NULL,
  `utilisateur` varchar(250) DEFAULT 'Batch Extraction',
  `date_insertion` datetime DEFAULT CURRENT_TIMESTAMP,
  `ajout` varchar(45) DEFAULT 'Automatique',
  `selection` tinyint(1) DEFAULT '0',
  `idOeuvreManuel` int(11) DEFAULT NULL,
  `SEL_EN_COURS` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER_CP` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_CP` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_FV`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PRIAM_LIGNE_PROGRAMME_FV` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cdeCisac` varchar(3) DEFAULT NULL,
  `cdeFamilTypUtil` varchar(45) DEFAULT NULL,
  `numProg` int(11) DEFAULT NULL,
  `cdeUtil` varchar(45) DEFAULT NULL,
  `cdeTypUtil` varchar(45) DEFAULT NULL,
  `cdeGreDif` varchar(45) DEFAULT NULL,
  `cdeModDif` varchar(45) DEFAULT NULL,
  `cdeTypIde12` varchar(45) DEFAULT NULL,
  `ide12` bigint(20) DEFAULT NULL,
  `durDif` int(11) DEFAULT NULL,
  `nbrDif` int(11) DEFAULT NULL,
  `libelleUtilisateur` varchar(255) DEFAULT NULL,
  `mt` double DEFAULT NULL,
  `ctna` varchar(45) DEFAULT NULL,
  `paramCoefHor` varchar(10) DEFAULT NULL,
  `durDifCtna` int(11) DEFAULT NULL,
  `cdeLng` varchar(45) DEFAULT NULL,
  `indDoubSsTit` varchar(45) DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `typMt` varchar(45) DEFAULT NULL,
  `ID_FICHIER` bigint(20) DEFAULT NULL,
  `cdeGreIde12Cmplx` varchar(45) DEFAULT NULL,
  `cdeGreIde12` varchar(45) DEFAULT NULL,
  `titreAltOeuvPereCmplx` varchar(250) DEFAULT NULL,
  `titreAltPppalCmplx` varchar(250) DEFAULT NULL,
  `titreOriOeuvPereCmplx` varchar(250) DEFAULT NULL,
  `titreOriCmplx` varchar(250) DEFAULT NULL,
  `titreOeuvre` varchar(250) DEFAULT NULL,
  `cdePaysOriIso4NCmplx` varchar(45) DEFAULT NULL,
  `realisateurCmplx` varchar(250) DEFAULT NULL,
  `roleParticipant1` varchar(250) DEFAULT NULL,
  `nomParticipant1` varchar(250) DEFAULT NULL,
  `cdeTypUtilOri` varchar(45) DEFAULT NULL,
  `cdeFamilTypUtilOri` varchar(45) DEFAULT NULL,
  `utilisateur` varchar(250) DEFAULT 'Batch Extraction',
  `date_insertion` datetime DEFAULT CURRENT_TIMESTAMP,
  `ajout` varchar(45) DEFAULT 'Automatique',
  `selection` tinyint(1) DEFAULT '0',
  `idOeuvreManuel` int(11) DEFAULT NULL,
  `SEL_EN_COURS` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER_FV` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_FV` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;


INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
  ('EN_COURS', 'En cours');

INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
  ('CHARGEMENT_OK', 'Chargement OK');

INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
  ('CHARGEMENT_KO', 'Chargement KO');

INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
  ('AFFECTE', 'Affecté');

INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
  ('ABANDONNE', 'Abandonné');
INSERT INTO PRIAM_PROGRAMME_SEQUENCE(ANNEE,CODESEQUENCE) VALUES ('2017',1);
INSERT INTO PRIAM_PROGRAMME_SEQUENCE(ANNEE,CODESEQUENCE) VALUES ('2017',2);
INSERT INTO PRIAM_PARAMAPPLI(CDEPARAM,LIBSTAT,VAL)
VALUES ('ANNEE_SEQ_PROGRAMME','année en cours utilisé pour generer id programme','2017');

-- ---------------------------------------------------
-- ------ TABLE SAREFTJ_LIBFAMITYPUTIL --------------
-- -------------------------------------------------
INSERT INTO SAREFTJ_LIBFAMILTYPUTIL (CDEFAMILTYPUTIL, LIBABRGFAMILTYPUTIL, CDELNG) VALUES
  ('COPIEPRIV', 'Copie Privée', 'FR');

INSERT INTO SAREFTJ_LIBFAMILTYPUTIL (CDEFAMILTYPUTIL, LIBABRGFAMILTYPUTIL, CDELNG) VALUES
  ('FDSVAL', 'Valorisation', 'FR');

INSERT INTO SAREFTJ_LIBFAMILTYPUTIL (CDEFAMILTYPUTIL, LIBABRGFAMILTYPUTIL, CDELNG) VALUES
  ('CMS', 'CMS', 'FR');


-- ---------------------------------------------------
-- ------ TABLE SAREFTJ_LIBTYPUTIL ------------------------
-- ---------------------------------------------------
INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('CPRIVSONPH', 'Copie privée sonore Phono', 'FR');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('CPRIVAUDV', 'Copie Privée Audiovisuelle', 'FR');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('CPRIVSONRD', 'Copie Privée Sonore radio', 'FR');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('COPRIVSON', 'Copie Privée Sonore', 'FR');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('CPRIVAUDPL', 'Copie Privée Audiovisuel - Part Littéraire', 'FR');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('CPRIVSONPH', 'Private copy sound (PHONO)', 'EN');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('CPRIVAUDV',  'Private copy audiovisual', 'EN');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('CPRIVSONRD', 'Private copy sound', 'EN');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('COPRIVSON', 'Copie Privée Sonore', 'EN');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('PRIME', 'Prime', 'FR');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('VALORIS', 'Fonds de valorisation', 'FR');

INSERT INTO SAREFTJ_LIBTYPUTIL (CDETYPUTIL, LIBABRGTYPUTIL, CDELNG)
VALUES ('ENCOURG', 'Encouragement', 'FR');

-- ---------------------------------------------------
-- ------ TABLE SAREFTR_FAMILTYPUTIL -------------------
-- ---------------------------------------------------
INSERT INTO SAREFTR_FAMILTYPUTIL(CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('COPIEPRIV', CURDATE(), NULL);

INSERT INTO SAREFTR_FAMILTYPUTIL(CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('FDSVAL', CURDATE(), NULL);

INSERT INTO SAREFTR_FAMILTYPUTIL(CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CMS', CURDATE(), NULL);


-- ---------------------------------------------------
-- ------ TABLE SAREFTR_TYPUTIL -------------------
-- ---------------------------------------------------
INSERT INTO SAREFTR_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CPRIVSONPH','COPIEPRIV', CURDATE(), NULL);

INSERT INTO SAREFTR_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CPRIVAUDV','COPIEPRIV', CURDATE(), NULL);

INSERT INTO SAREFTR_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CPRIVSONRD','COPIEPRIV', CURDATE(), NULL);

INSERT INTO SAREFTR_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('COPRIVSON','COPIEPRIV', CURDATE(), '2017-05-04');

INSERT INTO SAREFTR_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CPRIVAUDPL','COPIEPRIV', CURDATE(), NULL);

INSERT INTO SAREFTR_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('PRIME','FDSVAL', CURDATE(), NULL);

INSERT INTO SAREFTR_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('VALORIS','FDSVAL', CURDATE(), NULL);


INSERT INTO SAREFTR_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('ENCOURG','CMS', CURDATE(), NULL);


-- ---------------------------------------------------
-- ------ TABLE PRIAM_FICHIER -------------------
-- ---------------------------------------------------

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 01', 'COPIEPRIV', 'COPRIVSON', '2017-02-04 17:15:14', NULL, 3000, 'EN_COURS', 1,'CP');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 02', 'COPIEPRIV', 'COPRIVSON', '2017-02-03 17:15:14', NULL, 9500, 'EN_COURS', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 03', 'COPIEPRIV', 'COPRIVSON', '2017-02-01 17:15:14', NULL, 6500, 'EN_COURS', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 04', 'COPIEPRIV', 'COPRIVSON', '2017-04-01 17:15:14', NULL, 1478, 'EN_COURS', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 05', 'COPIEPRIV', 'COPRIVSON', '2017-05-01 17:10:14', NULL, 7451, 'EN_COURS', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 06', 'COPIEPRIV', 'COPRIVSON', '2017-05-02 18:15:14', NULL, 15000, 'EN_COURS', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 08', 'COPIEPRIV', 'COPRIVSON', '2017-02-01 17:15:14', NULL, 6500, 'EN_COURS', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 09', 'COPIEPRIV', 'COPRIVSON', '2017-04-01 17:15:14', '2017-04-01 22:10:11', 22000, 'CHARGEMENT_OK', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 11', 'COPIEPRIV', 'COPRIVSON', '2017-05-01 17:10:14', '2017-05-02 01:10:00', 45789, 'CHARGEMENT_OK', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 12', 'COPIEPRIV', 'COPRIVSON', '2017-05-02 18:15:14', '2017-05-01 18:50:04', 15000, 'CHARGEMENT_KO', 1,'CP');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 13', 'COPIEPRIV', 'COPRIVSON', '2017-05-04 18:15:14', '2017-05-04 22:57:04', 15000, 'CHARGEMENT_KO', 1,'CP');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 15', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', 1,'CP');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO,TYPE_FICHIER)
VALUES ('Fichier 17', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'ABANDONNE', 1,'CP');




-- ---------------------------------------------------
-- ------ TABLE PRIAM_LIGNE_PROGRAMME -------------------
-- ---------------------------------------------------
/*
INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeFamilTypUtil, ID_FICHIER)
    VALUES ('COPIEPRIV', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 01');

INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeFamilTypUtil, ID_FICHIER)
VALUES ('COPIEPRIV', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 02');
*/

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
  ('EN_COURS', 'En cours');

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
  ('CREE', 'Créé');

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
  ('AFFECTE', 'Affecté');

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
  ('VALIDE', 'Validé');

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
  ('ABANDONNE', 'Abandonné');



Insert into SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ)
values ('619','2012-03-29', '2012-07-05', '9223372036854775807','0','Répartition 2ème Trimestre 2012.', '2011-11-17','bautistaf', '2012-04-08','BUISSONF');

Insert into SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('600','2013-07-23','2013-07-05','9223372036854775807','0','SareftrRion 600','2013-07-23','GUEST','2013-07-23','GUEST');
Insert into SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('629','2014-11-19','2015-01-05','9223372036854775807','0','sareftrRion 629','2014-11-19','ROMEIBRIGITTE','2014-11-19','ROMEIBRIGITTE');
Insert into SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('630','2015-01-06','2015-04-06','9223372036854775807','0','Répartition Avril 2015','2015-01-06','LIETH','2015-01-06','LIETH');
Insert into SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('639','2017-04-03','2018-07-05','4','0','Répartition Juillet 2017','2017-04-25','ROUCOULESG','2017-04-25','ROUCOULESG');

INSERT INTO `SAREFTJ_LIBUTIL` VALUES ('FR','0002','RIR','RIR','2009-07-08','GOLLIONB','2015-05-19','FRIESP',NULL);


INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('170001', 'Programme 01', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'VALIDE', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('170002', 'Programme 02', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('170003', 'Programme 03', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);


INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('170004', 'Programme 04', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('170005', 'Programme 05', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('170006', 'Programme 01', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

/*INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('PR170008', 'Programme 01', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', '2017-06-19 00:00:00', 'EN_COURS', NULL);*/

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG,TYPE_FICHIER)
VALUES ('Fichier 125', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', '170001','CP');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG,TYPE_FICHIER)
VALUES ('Fichier 126', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', '170001','CP');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG,TYPE_FICHIER)
VALUES ('Fichier 127', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', '170001','CP');
INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG,TYPE_FICHIER)
VALUES ('Fichier 128', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', '170001','CP');

/*
INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeCisac, cdeTer, rionEffet, cdeFamilTypUtil, cdeModFac, numProg, keyLigPenel, cdeUtil, cdeTypUtil, cdeTypProg, cdeCompl, libProg, compLibProg, datDbtProg, datFinProg, hrDbt, hrFin, cdeGreDif, cdeModDif, cdeTypIde12, ide12, datDif, hrDif, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER)
VALUES ('58', 250, 639, 'COPIEPRIV', '', null, null, 'LU1', 'CPRIVAUDPL', 'PRINC', 'SANS', 'COPIEPRIV PRINC 639 250', '', '2017-05-24 22:57:04', '2017-05-24 22:57:04', null, null, '', '', 'COCV', 6829877211, null, null, null, 10, 71.52, '', '', null, '', '', null, 'MB', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125');
INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeCisac, cdeTer, rionEffet, cdeFamilTypUtil, cdeModFac, numProg, keyLigPenel, cdeUtil, cdeTypUtil, cdeTypProg, cdeCompl, libProg, compLibProg, datDbtProg, datFinProg, hrDbt, hrFin, cdeGreDif, cdeModDif, cdeTypIde12, ide12, datDif, hrDif, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER)
VALUES ('58', 250, 640, 'COPIEPRIV', '', null, null, 'RT2', 'CPRIVAUDPL', 'PRINC', 'SANS', 'COPIEPRIV PRINC 639 250', '', '2017-05-24 22:57:04', '2017-05-24 22:57:04', null, null, '', '', 'COCV', 8028354411, null, null, null, 2, 1.26, '', '', null, '', '', null, 'MB', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125');
INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeCisac, cdeTer, rionEffet, cdeFamilTypUtil, cdeModFac, numProg, keyLigPenel, cdeUtil, cdeTypUtil, cdeTypProg, cdeCompl, libProg, compLibProg, datDbtProg, datFinProg, hrDbt, hrFin, cdeGreDif, cdeModDif, cdeTypIde12, ide12, datDif, hrDif, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER)
VALUES ('58', 250, 641, 'COPIEPRIV', '', null, null, 'RIR', 'CPRIVAUDPL', 'PRINC', 'SANS', 'COPIEPRIV PRINC 639 250', '', '2017-05-24 22:57:04', '2017-05-24 22:57:04', null, null, '', '', 'COCV', 8028354411, null, null, null, 8, 1.74, '', '', null, '', '', null, 'MB', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125');
*/
INSERT INTO PRIAM_LIGNE_PROGRAMME_CP (cdeCisac, cdeFamilTypUtil, numProg, cdeUtil, cdeTypUtil, cdeGreDif, cdeModDif, cdeTypIde12, ide12, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, cdeGreIde12Cmplx, cdeGreIde12, titreOriCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreAltOeuvPereCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri,ID_FICHIER) VALUES ('58', 'COPIEPRIV', '170001', '0002', 'CPRIVAUDPL', '', '', 'COCV', 6829877211, null,10, 71.52, '', '', null, '', '', null, 'MB', 19, null, null, null, null, null, 'Test Titre', null, null, null, null, null, null, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125'));
INSERT INTO PRIAM_LIGNE_PROGRAMME_CP (cdeCisac, cdeFamilTypUtil, numProg, cdeUtil, cdeTypUtil, cdeGreDif, cdeModDif, cdeTypIde12, ide12, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, cdeGreIde12Cmplx, cdeGreIde12, titreOriCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreAltOeuvPereCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri,ID_FICHIER) VALUES ('58', 'COPIEPRIV', null, 'RT2', 'CPRIVAUDPL', '', '', 'COCV', 8028354411, null, 2,1.26, '', '', null, '', '', null, 'MB', 19, null, null, null, null, null, null, null, null, null, null, null, null, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125'));
INSERT INTO PRIAM_LIGNE_PROGRAMME_CP (cdeCisac, cdeFamilTypUtil, numProg, cdeUtil, cdeTypUtil, cdeGreDif, cdeModDif, cdeTypIde12, ide12, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, cdeGreIde12Cmplx, cdeGreIde12, titreOriCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreAltOeuvPereCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri,ID_FICHIER) VALUES ('58', 'COPIEPRIV', null, 'RIR', 'CPRIVAUDPL', '', '', 'COCV', 8028354411,null, 8,1.74, '', '', null, '', '', null, 'MB', 19, null, null, null, null, null, null, null, null, null, null, null, null, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125'));

-- ---------------------------------------------------
-- ------ TABLE PRIAM_ROLE -------------------
-- ---------------------------------------------------
INSERT INTO PRIAM_ROLE
(EXTERNAL_ID, ROLE)
values ('ADM','ADM');

INSERT INTO PRIAM_ROLE
(EXTERNAL_ID, ROLE)
values ('GST','GST');

INSERT INTO PRIAM_ROLE
(EXTERNAL_ID, ROLE)
values ('INV','INV');

-- ---------------------------------------------------
-- ------ TABLE PRIAM_ROLE_RIGHTS -------------------
-- ---------------------------------------------------
INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (1,'CRTPRG','Creation de programme');

INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (1,'READPRG','Acces ecran programme');

INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (1,'MDYPRG','Modification de programme');

INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (1,'ABDPRG','Abandon de programme');

INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (2,'CRTPRG','Creation de programme');

INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (2,'READPRG','Acces ecran programme');

INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (2,'MDYPRG','Modification de programme');

INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (2,'ABDPRG','Abandon de programme');

INSERT INTO PRIAM_ROLE_RIGHTS
(ROLE_ID,RIGHTS,LIBELLE)
VALUES (3,'READPRG','Acces ecran programme');


commit;