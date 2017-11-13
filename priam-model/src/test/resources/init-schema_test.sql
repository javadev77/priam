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


INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',4,'AFGHANISTAN','AFGHANISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',8,'ALBANIA','ALBANIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',10,'ANTARCTICA','ANTARCTICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',12,'ALGERIA','ALGERIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',16,'SAMOA USA','SAMOA Island United states','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',20,'ANDORRA','ANDORRA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',24,'ANGOLA','ANGOLA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',28,'ANTIGUA BARBUDA','ANTIGUA AND BARBUDA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',31,'AZERBAIJAN','AZERBAIJAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',32,'ARGENTINA','ARGENTINA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',36,'AUSTRALIA','AUSTRALIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',40,'AUSTRIA','AUSTRIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',44,'BAHAMAS','BAHAMAS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',48,'BAHRAIN','BAHRAIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',50,'BANGLADESH','BANGLADESH','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',51,'ARMENIA','ARMENIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',52,'BARBADOS','BARBADOS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',56,'BELGIUM','BELGIUM','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',60,'BERMUDA','BERMUDA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',64,'BHUTAN','BHUTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',68,'BOLIVIA','BOLIVIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',70,'BOSNIA HERZEGOVINA','BOSNIA AND HERZEGOVINA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',72,'BOTSWANA','BOTSWANA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',74,'BOUVET ISLAND','BOUVET ISLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',76,'BRAZIL','BRAZIL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',84,'BELIZE','BELIZE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',86,'BRIT.IND.OCEAN','BRITISH INDIAN OCEAN TERRITORY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',90,'SOLOMON ISLANDS','SOLOMON ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',92,'VIRGIN ISL.BRIT','VIRGIN ISLANDS, BRITISH','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',96,'BRUNEI DARUSSALAM','BRUNEI DARUSSALAM','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',100,'BULGARIA','BULGARIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',104,'MYANMAR','MYANMAR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',108,'BURUNDI','BURUNDI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',112,'BELARUS','BELARUS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',116,'CAMBODIA','CAMBODIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',120,'CAMEROON','CAMEROON','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',124,'CANADA','CANADA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',132,'CAPE VERDE','CAPE VERDE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',136,'CAYMAN ISLANDS','CAYMAN ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',140,'CENTRAL AFR REP','CENTRAL AFRICAN REPUBLIC','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',144,'SRI LANKA','SRI LANKA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',148,'CHAD','CHAD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',152,'CHILE','CHILE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',156,'CHINA','CHINA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',158,'TAIWAN','TAIWAN, PROVINCE OF CHINA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',162,'CHRISTMAS ISLAND','CHRISTMAS ISLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',166,'COCO ISL','COCOS (KEELING) ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',170,'COLOMBIA','COLOMBIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',174,'COMOROS','UNION OF THE COMOROS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',175,'MAYOTTE','MAYOTTE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',178,'CONGO','CONGO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',180,'CONGO DEMOCRATI','CONGO, THE DEMOCRATIC REPUBLIC OF THE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',184,'COOK ISLANDS','COOK ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',188,'COSTA RICA','COSTA RICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',191,'CROATIA','CROATIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',192,'CUBA','CUBA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',196,'CYPRUS','CYPRUS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',200,'CZECHOSLOVAKIA','CZECHOSLOVAKIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',203,'CZECH REPUBLIC','CZECH REPUBLIC','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',204,'BENIN','BENIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',208,'DENMARK','DENMARK','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',212,'DOMINICA','DOMINICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',214,'DOMINICAN REPUBLIC','DOMINICAN REPUBLIC','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',218,'ECUADOR','ECUADOR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',222,'EL SALVADOR','EL SALVADOR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',226,'EQUATORIAL GUINEA','EQUATORIAL GUINEA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',230,'ETHIOPIA (until 23-05-93)','ETHIOPIA (until 23-05-93)','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',231,'ETHIOPIA','ETHIOPIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',232,'ERITREA','ERITREA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',233,'ESTONIA','ESTONIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',234,'FAEROE ISLANDS','FAROE ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',238,'FALKLAND ISL','FALKLAND ISLANDS (MALVINAS)','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',239,'SOUTH GEORGIA','SOUTH GEORGIA AND THE SOUTH SANDWICH ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',242,'FIJI','FIJI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',246,'FINLAND','FINLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',248,'ALAND ISLANDS','ALAND ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',249,'FRANCE METRO','FRANCE METROPOLITAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',250,'FRANCE','FRANCE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',254,'FRENCH GUIANA','FRENCH GUIANA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',258,'FRENCH POLYNES.','FRENCH POLYNESIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',260,'FRENCH SOUTH TER','FRENCH SOUTHERN TERRITORIES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',262,'DJIBOUTI','DJIBOUTI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',266,'GABON','GABON','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',268,'GEORGIA','GEORGIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',270,'GAMBIA','GAMBIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',275,'Palestine','Palestine','2006-05-03','ARCHI_APP','2017-07-24',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',276,'GERMANY','GERMANY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',278,'GERMAN DEM.REP.','GERMAN DEMOCRATIC REPUBLIC','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',280,'GERMANY FED.REP','GERMANY, FEDERAL REPUBLIC OF','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',288,'GHANA','GHANA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',292,'GIBRALTAR','GIBRALTAR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',296,'KIRIBATI','KIRIBATI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',300,'GREECE','GREECE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',304,'GREENLAND','GREENLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',308,'GRENADA','GRENADA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',312,'GUADELOUPE','GUADELOUPE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',316,'GUAM','GUAM','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',320,'GUATEMALA','GUATEMALA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',324,'GUINEA','GUINEA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',328,'GUYANA','GUYANA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',332,'HAITI','HAITI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',334,'HEARD ISLMCDONALD ISL','HEARD ISLAND AND MCDONALD ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',336,'VATICAN','HOLY SEE (VATICAN CITY STATE)','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',340,'HONDURAS','HONDURAS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',344,'HONG KONG','HONG KONG','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',348,'HUNGARY','HUNGARY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',352,'ICELAND','ICELAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',356,'INDIA','INDIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',360,'INDONESIA','INDONESIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',364,'IRAN','IRAN, ISLAMIC REPUBLIC OF','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',368,'IRAQ','IRAQ','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',372,'IRELAND','IRELAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',376,'ISRAEL','ISRAEL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',380,'ITALY','ITALY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',384,'IVORY COAST','IVORY COAST','2006-05-03','ARCHI_APP','2017-02-10',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',388,'JAMAICA','JAMAICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',392,'JAPAN','JAPAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',398,'KAZAKHSTAN','KAZAKHSTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',400,'JORDAN','JORDAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',404,'KENYA','KENYA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',408,'KOREA (North)','KOREA (North)','2006-05-03','ARCHI_APP','2017-02-10',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',410,'KOREA (SOUTH)','KOREA (SOUTH)','2006-05-03','ARCHI_APP','2017-02-10',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',414,'KUWAIT','KUWAIT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',417,'KYRGYZSTAN','KYRGYZSTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',418,'LAOS','LAOS','2006-05-03','ARCHI_APP','2017-02-10',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',422,'LEBANON','LEBANON','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',426,'LESOTHO','LESOTHO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',428,'LATVIA','LATVIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',430,'LIBERIA','LIBERIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',434,'LIBYAN ARAB J','LIBYAN ARAB JAMAHIRIYA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',438,'LIECHTENSTEIN','LIECHTENSTEIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',440,'LITHUANIA','LITHUANIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',442,'LUXEMBOURG','LUXEMBOURG','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',446,'MACAO','MACAO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',450,'MADAGASCAR','MADAGASCAR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',454,'MALAWI','MALAWI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',458,'MALAYSIA','MALAYSIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',462,'MALDIVES','MALDIVES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',466,'MALI','MALI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',470,'MALTA','MALTA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',474,'MARTINIQUE','MARTINIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',478,'MAURITANIA','MAURITANIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',480,'MAURITIUS','MAURITIUS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',484,'MEXICO','MEXICO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',492,'MONACO','MONACO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',496,'MONGOLIA','MONGOLIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',498,'MOLDOVA','MOLDOVA, REPUBLIC OF','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',499,'MONTENEGRO','MONTENEGRO','2007-10-22','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',500,'MONTSERRAT','MONTSERRAT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',504,'MOROCCO','MOROCCO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',508,'MOZAMBIQUE','MOZAMBIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',512,'OMAN','OMAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',516,'NAMIBIA','NAMIBIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',520,'NAURU','NAURU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',524,'NEPAL','NEPAL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',528,'NETHERLANDS','NETHERLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',530,'NETH. ANTILLES','NETHERLANDS ANTILLES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',533,'ARUBA','ARUBA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',540,'NEW CALEDONIA','NEW CALEDONIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',548,'VANUATU','VANUATU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',554,'NEW ZEALAND','NEW ZEALAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',558,'NICARAGUA','NICARAGUA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',562,'NIGER','NIGER','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',566,'NIGERIA','NIGERIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',570,'NIUE','NIUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',574,'NORFOLK ISLAND','NORFOLK ISLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',578,'NORWAY','NORWAY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',580,'NTH.MARIANA ISL','NORTHERN MARIANA ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',581,'US.MINOR ISL.','UNITED STATES MINOR OUTLYING ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',583,'MICRONESIA','MICRONESIA, FEDERATED STATES OF','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',584,'MARSCHALL ISL.','MARSHALL ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',585,'PALAU','PALAU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',586,'PAKISTAN','PAKISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',591,'PANAMA','PANAMA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',598,'PAPUA N.G.','PAPUA NEW GUINEA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',600,'PARAGUAY','PARAGUAY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',604,'PERU','PERU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',608,'PHILIPPINES','PHILIPPINES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',612,'PITCAIRN','PITCAIRN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',616,'POLAND','POLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',620,'PORTUGAL','PORTUGAL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',624,'GUINEA-BISSAU','GUINEA-BISSAU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',626,'TIMOR LESTE','TIMOR LESTE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',630,'PUERTO RICO','PUERTO RICO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',634,'QATAR','QATAR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',638,'REUNION','REUNION','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',642,'ROMANIA','ROMANIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',643,'RUSSIAN','RUSSIAN FEDERATION','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',646,'RWANDA','RWANDA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',654,'SAINT HELENA','SAINT HELENA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',659,'ST.KITTS-NEVIS','SAINT KITTS AND NEVIS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',660,'ANGUILLA','ANGUILLA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',662,'SAINT LUCIA','SAINT LUCIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',666,'ST.PIERRE & MIQ','ST.PIERRE AND MIQUELON','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',670,'SAINT VINC. GREN.','SAINT VINCENT AND THE GRENADINES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',674,'SAN MARINO','SAN MARINO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',678,'SAO TOME PRINCP','SAO TOME AND PRINCIPE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',682,'SAUDI ARABIA','SAUDI ARABIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',686,'SENEGAL','SENEGAL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',688,'SERBIA','SERBIA','2007-10-22','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',690,'SEYCHELLES','SEYCHELLES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',694,'SIERRA LEONE','SIERRA LEONE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',702,'SINGAPORE','SINGAPORE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',703,'SLOVAKIA','SLOVAKIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',704,'VIET NAM','VIET NAM','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',705,'SLOVENIA','SLOVENIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',706,'SOMALIA','SOMALIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',710,'SOUTH AFRICA','SOUTH AFRICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',716,'ZIMBABWE','ZIMBABWE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',720,'YEMEN,DEMOCR.','YEMEN, DEMOCRATIC','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',724,'SPAIN','SPAIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',728,'SOUTH SUDAN','SOUTH SUDAN','2013-12-30','TASSINB','2013-12-30',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',729,'SUDAN','SUDAN','2013-12-30','TASSINB','2013-12-30',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',732,'WESTERN SAHARA','WESTERN SAHARA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',736,'SUDAN','SUDAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',740,'SURINAME','SURINAME','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',744,'SVALB  JAN MAY','SVALBARD AND JAN MAYEN Island','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',748,'SWAZILAND','SWAZILAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',752,'SWEDEN','SWEDEN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',756,'SWITZERLAND','SWITZERLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',760,'SYRIAN','SYRIAN ARAB REPUBLIC','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',762,'TAJIKISTAN','TAJIKISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',764,'THAILAND','THAILAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',768,'TOGO','TOGO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',772,'TOKELAU','TOKELAU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',776,'TONGA','TONGA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',780,'TRINIDAD TOBAGO','TRINIDAD AND TOBAGO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',784,'UNITED ARAB EMIR.','UNITED ARAB EMIRATES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',788,'TUNISIA','TUNISIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',792,'TURKEY','TURKEY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',795,'TURKMENISTAN','TURKMENISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',796,'TURKS CAICOS ISL','TURKS AND CAICOS ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',798,'TUVALU','TUVALU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',800,'UGANDA','UGANDA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',804,'UKRAINE','UKRAINE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',807,'MACEDONIA','MACEDONIA, THE FORMER YUGOSLAV REPUBLIC OF','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',810,'USSR','USSR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',818,'EGYPT','EGYPT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',826,'UNITED KINGDOM','UNITED KINGDOM OF GREAT BRITAIN AND NORTHERN IRELAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',831,'GUERNESEY','GUERNSEY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',832,'JERSEY','JERSEY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',833,'ISLE OF MAN','ISLE OF MAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',834,'TANZANIA','TANZANIA, UNITED REPUBLIC OF','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',840,'UNITED STATES','UNITED STATES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',850,'VIRGIN ISL.U.S.','VIRGIN ISLANDS, U.S.','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',854,'BURKINA FASO','BURKINA FASO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',858,'URUGUAY','URUGUAY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',860,'UZBEKISTAN','UZBEKISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',862,'VENEZUELA','VENEZUELA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',876,'WALLIS FUTUNA','WALLIS AND FUTUNA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',882,'SAMOA','SAMOA (INDEPENDANT STATES) ','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',886,'YEMEN','YEMEN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',887,'YEMEN','YEMEN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',890,'YUGOSLAVIA','YUGOSLAVIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',891,'SERBIA MONTENEGRO','SERBIA AND MONTENEGRO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',894,'ZAMBIA','ZAMBIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',947,'SOUTH VIETNAM','SOUTH VIETNAM','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2018,'EAST Hemisp.','Eastern hemisphere','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2019,'English speaking countrie','English speaking countries ','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2022,'FRENCH COM','FRENCH COMMUNITY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2025,'Antilles (Grandes)','Antilles (Grandes)','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2027,'LATINE AMERICA','LATINE AMERICA','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2029,'Lesser Antilles','Lesser Antilles','2013-12-31','LIETH','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2035,'STH.AFR+6Counties','SOUTH AFRICA + BOT.LES.NAM.SWA.BOP.TRA','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2038,'Spanish Countries','SPANISH COUNTRIES','2014-08-21','TASSINB','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2039,'USA Extended','United states and possessions','2014-08-21','TASSINB','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2040,'EUROPE OCC.','EUROPE OCCIDENTALE','2014-10-20','TASSINB','2014-10-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2041,'West Hemisph.','HEMISPHERE OCCIDENTAL ou OUEST','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2044,'UNKNOWN','UNKNOWN','2015-12-08','BOLJS','2015-12-08',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2046,'REPUBLIQUE FRANCAISE','REPUBLIQUE FRANCAISE (France + DOM TOM)','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2054,'French Indies ','French Indies ','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2055,'English ANTILLES','English ANTILLES','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2056,'Caraibean countries','Caraibean countries','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2100,'AFRICA','AFRICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2101,'AMERICAN SAMOA','AMERICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2102,'AMERICAN CONT.','AMERICAN CONTINENT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2103,'WEST INDIES','WEST INDIES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2104,'APEC STATES','APEC STATES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2105,'ASEAN STATES','ASEAN STATES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2106,'ASIA','ASIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2107,'AUSTRALASIA','AUSTRALASIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2108,'BALKANS','BALKANS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2109,'BALTIC STATES','BALTIC STATES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2110,'BENELUX','BENELUX COUNTRIES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2111,'BRTISH ISLANDS','BRITISH ISLANDS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2112,'BRIT WEST INDIES','BRITISH WEST INDIES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2113,'CENTRAL AMERICA','CENTRAL AMERICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2114,'COMMONWEALTH','COMMONWEALTH','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2115,'CW AFRICAN TERR','COMMONWEALTH AFRICAN TERRITORIES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2116,'CW ASIAN TERR.','COMMONWEALTH ASIAN TERRITORIES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2117,'CW AUSTRAL.TERR','COMMONWEALTH AUSTRALASIAN TERRITORIES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2118,'CEI','CEI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2119,'EAST EUROPEAN','EAST EUROPEAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2120,'EUROPA','EUROPA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2121,'EEC','EUROPEAN ECONOMIC SPACE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2122,'EUROPEAN CONTINENT','EUROPEAN CONTINENT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2123,'ECC','EUROPEAN ECONOMIC COMMUNITY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2124,'GERM+AUST+SWIT','GERMANY + AUSTRIA + SWITZERLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2125,'MIDDLE EAST','MIDDLE EAST','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2126,'NAFTA STATES','NAFTA STATES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2127,'NORDIC COUNTRIES','NORDIC COUNTRIES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2128,'NORTH AFRICA','NORTH AFRICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2129,'NORTH AMERICA','NORTH AMERICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2130,'OCEANIA',NULL,'2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2131,'SCANDINAVIA','SCANDINAVIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2132,'SOUTH AMERICA','SOUTH AMERICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2133,'SOUTH EAST ASIA','SOUTH EAST ASIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2134,'WEST INDIES','WEST INDIES (Former and present Britain terrotories)','2006-05-03','ARCHI_APP','2014-08-29',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',2136,'WORLD','WORLD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3000,'OTHER','OTHER','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3002,'AM CENTR.+MEX+HAIT+CUBA','AMERIQUE CENTRALE (Cont.) + MEXIQUE, HAITI, CUBA','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3003,'English countries','English countries except USA','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3012,'GG','GG','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3017,'SACEM TER','SACEM Territories','2006-05-03','ARCHI_APP','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3026,'Groupe 4','GROUPE 4 (AU CA GB IE IN NZ PK)','2014-08-25','TASSINB','2014-08-25',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3034,'CLE 90','CLE 90','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3035,'SOUTH AM EXC FR GUYANE','SOUTH AMERICA EXCEPT FR GUYANE','2014-08-21','TASSINB','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3039,'PRS TER','PRS Territories','2006-05-03','ARCHI_APP','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3040,'NCB TER','NCB TERRITORIES','2006-05-03','ARCHI_APP','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3041,'SABAM TER','SABAM Territories','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3042,'APRA TER','APRA Territories','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3043,'GEMA TER','GEMA Territories','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3044,'BUMA countries','BUMA Territories','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3045,'SAMRO TER','SAMRO Territories','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3046,'SGAE TER','SGAE Territories','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3047,'SPA TER','SPA Territoiries','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3048,'SIAE TER','SIAE Territories','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3049,'TONO TER','TONO Territories','2014-08-20','TASSINB','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3100,'ZOOM LANGUE','ZOOM LANGUE','2014-08-21','TASSINB','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3101,'ZOOM SOCIETE','ZOOM SOCIETE','2014-08-21','TASSINB','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3102,'Zoom organi. Internation','Zoom International bodies','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('EN',3104,'Zoom Various Group','Zoom Various Groups','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',4,'AFGHANISTAN','AFGHANISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',8,'ALBANIE','ALBANIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',10,'ANTARCTIQUE','ANTARCTIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',12,'ALGERIE','ALGERIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',16,'SAMOA USA','Iles SAMOA des Etats Unis','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',20,'ANDORRE','ANDORRE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',24,'ANGOLA','ANGOLA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',28,'ANTIGUA et BARBUDA','ANTIGUA-ET-BARBUDA','2006-05-03','ARCHI_APP','2016-09-29',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',31,'AZERBAIDJAN','AZERBAIDJAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',32,'ARGENTINE','ARGENTINE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',36,'AUSTRALIE','AUSTRALIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',40,'AUTRICHE','AUTRICHE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',44,'BAHAMAS','BAHAMAS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',48,'BAHREIN','BAHREIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',50,'BANGLADESH','BANGLADESH','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',51,'ARMENIE','ARMENIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',52,'BARBADE','BARBADE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',56,'BELGIQUE','BELGIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',60,'BERMUDES','BERMUDES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',64,'BHOUTAN','BHOUTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',68,'BOLIVIE','BOLIVIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',70,'BOSNIE-HERZEGOVINE','BOSNIE-HERZEGOVINE','2006-05-03','ARCHI_APP','2016-09-29',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',72,'BOTSWANA','BOTSWANA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',74,'ILE BOUVET','ILE BOUVET','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',76,'BRESIL','BRESIL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',84,'BELIZE','BELIZE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',86,'TER.BT O.INDIEN','OCÉAN INDIEN, TERRITOIRE BRITANNIQUE DE L\'','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',90,'ILES SALOMON','Iles SALOMON','2006-05-03','ARCHI_APP','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',92,'ILES V. BRITAN.','ILES VIERGES BRITANNIQUES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',96,'BRUNEI','BRUNEI DARUSSALAM','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',100,'BULGARIE','BULGARIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',104,'MYANMAR','BIRMANIE ou MYANMAR','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',108,'BURUNDI','BURUNDI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',112,'BELARUS','BELARUS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',116,'CAMBODGE','CAMBODGE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',120,'CAMEROUN','CAMEROUN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',124,'CANADA','CANADA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',132,'CAP-VERT','CAP-VERT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',136,'ILES CAIMANS','ILES CAIMANS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',140,'REP.CENTRAFRIC.','CENTRAFRICAINE, REPUBLIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',144,'SRI LANKA','SRI LANKA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',148,'TCHAD','TCHAD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',152,'CHILI','CHILI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',156,'CHINE','CHINE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',158,'TAIWAN','TAIWAN, PROVINCE DE CHINE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',162,'ILE CHRISTMAS','ILE CHRISTMAS','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',166,'ILES COCOS','ILES COCOS (KEELING)','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',170,'COLOMBIE','COLOMBIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',174,'COMORES','UNION DES COMORES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',175,'MAYOTTE','MAYOTTE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',178,'CONGO','CONGO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',180,'REP.DEMOC.CONGO','CONGO, LA REPUBLIQUE DEMOCRATIQUE DU (Ex. ZAIRE)','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',184,'ILES COOK','ILES COOK','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',188,'COSTA RICA','COSTA RICA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',191,'CROATIE','CROATIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',192,'CUBA','CUBA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',196,'CHYPRE','CHYPRE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',200,'TCHECOSLOVAQUIE','TCHECOSLOVAQUIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',203,'REP.TCHEQUE','TCHEQUE, REPUBLIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',204,'BENIN','BENIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',208,'DANEMARK','DANEMARK','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',212,'DOMINIQUE','DOMINIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',214,'REP.DOMINICAINE','DOMINICAINE, REPUBLIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',218,'EQUATEUR','EQUATEUR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',222,'EL SALVADOR','EL SALVADOR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',226,'GUINEE EQUATOR.','GUINEE EQUATORIALE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',230,'ETHIOPIE (jusqu\'en 93)','ETHIOPIE (jusqu\'au 23-05-1993)','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',231,'ETHIOPIE','ETHIOPIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',232,'ERYTHREE','ERYTHREE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',233,'ESTONIE','ESTONIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',234,'ILES FEROE','ILES FÉROÉ','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',238,'ILES FALKLAND','ILES FALKLAND (MALOUINES)','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',239,'GEORGIE SUD ','GEORGIE DU SUD ET ILES SANDWICH DU SUD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',242,'FIDJI','FIDJI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',246,'FINLANDE','FINLANDE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',248,'ILES D\'ALAND','ILES D\'ALAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',249,'FRANCE METRO.','FRANCE METROPOLITAINE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',250,'FRANCE','FRANCE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',254,'GUY.FRANCA.','GUYANE FRANCAISE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',258,'POLYNESIE FR.','POLYNESIE FRANCAISE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',260,'T.AUSTR.FRANC.','TERRES AUSTRALES FRANCAISES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',262,'DJIBOUTI','DJIBOUTI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',266,'GABON','GABON','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',268,'GEORGIE','GEORGIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',270,'GAMBIE','GAMBIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',275,'Palestine','Palestine','2006-05-03','ARCHI_APP','2017-07-24',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',276,'ALLEMAGNE','ALLEMAGNE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',278,'RDA','ALLEMANDE, REPUBLIQUE DEMOCRATIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',280,'ALLEMAGNE (RFA)','ALLEMAGNE (RFA)','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',288,'GHANA','GHANA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',292,'GIBRALTAR','GIBRALTAR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',296,'KIRIBATI','KIRIBATI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',300,'GRECE','GRECE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',304,'GROENLAND','GROENLAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',308,'GRENADE','GRENADE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',312,'GUADELOUPE','GUADELOUPE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',316,'GUAM','GUAM','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',320,'GUATEMALA','GUATEMALA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',324,'GUINEE','GUINEE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',328,'GUYANA','GUYANA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',332,'HAITI','HAITI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',334,'ILES H.M.DONALD','ILES HEARD, ILE ET MCDONALD','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',336,'VATICAN','VATICAN (ETAT DE LA CITE DU ) Saint siège','2006-05-03','ARCHI_APP','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',340,'HONDURAS','HONDURAS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',344,'HONG-KONG','HONG-KONG','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',348,'HONGRIE','HONGRIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',352,'ISLANDE','ISLANDE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',356,'INDE','INDE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',360,'INDONESIE','INDONESIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',364,'IRAN','IRAN, REPUBLIQUE ISLAMIQUE D\'','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',368,'IRAQ','IRAQ','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',372,'IRLANDE','IRLANDE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',376,'ISRAEL','ISRAEL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',380,'ITALIE','ITALIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',384,'COTE D\'IVOIRE','COTE D\'IVOIRE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',388,'JAMAIQUE','JAMAIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',392,'JAPON','JAPON','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',398,'KAZAKHSTAN','KAZAKHSTAN','2006-05-03','ARCHI_APP','2016-06-02',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',400,'JORDANIE','JORDANIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',404,'KENYA','KENYA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',408,'COREE DU NORD','COREE, REPUBLIQUE POPULAIRE DEMOCRATIQUE DE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',410,'COREE DU SUD','COREE DU SUD, REPUBLIQUE DE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',414,'KOWEIT','KOWEIT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',417,'KIRGHIZISTAN','KIRGHIZISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',418,'LAOS','LAOS, REPUBLIQUE DEMOCRATIQUE POPULAIRE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',422,'LIBAN','LIBAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',426,'LESOTHO','LESOTHO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',428,'LETTONIE','LETTONIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',430,'LIBERIA','LIBERIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',434,'LIBYE','LIBYENNE, JAMAHIRIYA ARABE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',438,'LIECHTENSTEIN','LIECHTENSTEIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',440,'LITUANIE','LITUANIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',442,'LUXEMBOURG','LUXEMBOURG','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',446,'MACAO','MACAO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',450,'MADAGASCAR','MADAGASCAR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',454,'MALAWI','MALAWI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',458,'MALAISIE','MALAISIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',462,'MALDIVES','MALDIVES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',466,'MALI','MALI','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',470,'MALTE','MALTE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',474,'MARTINIQUE','MARTINIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',478,'MAURITANIE','MAURITANIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',480,'MAURICE','MAURICE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',484,'MEXIQUE','MEXIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',492,'MONACO','MONACO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',496,'MONGOLIE','MONGOLIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',498,'MOLDAVIE','MOLDAVIE, REPUBLIQUE DE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',499,'MONTENEGRO','MONTENEGRO','2007-10-22','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',500,'MONTSERRAT','MONTSERRAT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',504,'MAROC','MAROC','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',508,'MOZAMBIQUE','MOZAMBIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',512,'OMAN','OMAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',516,'NAMIBIE','NAMIBIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',520,'NAURU','NAURU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',524,'NEPAL','NEPAL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',528,'PAYS-BAS','PAYS-BAS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',530,'ANT. NEERLAND.','ANTILLES Néerlandaises','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',533,'ARUBA','ARUBA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',540,'NVL CALEDONIE','NOUVELLE CALEDONIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',548,'VANUATU','VANUATU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',554,'NOUVELLE-ZELANDE','NOUVELLE-ZELANDE','2006-05-03','ARCHI_APP','2016-09-29',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',558,'NICARAGUA','NICARAGUA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',562,'NIGER','NIGER','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',566,'NIGERIA','NIGERIA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',570,'NIUE','NIUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',574,'ILE NORFOLK','ILE NORFOLK','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',578,'NORVEGE','NORVEGE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',580,'ILES MARI.NORD','ILES MARIANNES DU NORD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',581,'ILES MINR US','ILES MINEURES ÉLOIGNÉES DES ÉTATS-UNIS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',583,'MICRONESIE','MICRONESIE, ETATS FEDERES DE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',584,'ILES MARSHALL','Iles MARSHALL','2006-05-03','ARCHI_APP','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',585,'PALAOS','PALAOS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',586,'PAKISTAN','PAKISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',591,'PANAMA','PANAMA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',598,'PAP.NOUV.GUINEE','PAPOUASIE-NOUVELLE-GUINEE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',600,'PARAGUAY','PARAGUAY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',604,'PEROU','PEROU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',608,'PHILIPPINES','PHILIPPINES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',612,'PITCAIRN','PITCAIRN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',616,'POLOGNE','POLOGNE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',620,'PORTUGAL','PORTUGAL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',624,'GUINEE-BISSAU','GUINEE-BISSAU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',626,'TIMOR LESTE','TIMOR LESTE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',630,'PORTO RICO','PORTO RICO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',634,'QATAR','QATAR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',638,'REUNION','REUNION','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',642,'ROUMANIE','ROUMANIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',643,'RUSSIE','RUSSIE, FEDERATION DE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',646,'RWANDA','RWANDA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',654,'SAINTE HELENE','SAINTE HELENE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',659,'ST.KITTS+NEVIS','SAINT-KITTS-ET-NEVIS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',660,'ANGUILLA','ANGUILLA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',662,'STE.LUCIE','SAINTE-LUCIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',666,'S.P ET MIQUELON','SAINT PIERRE ET MIQUELON','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',670,'ST.VINCENT+GR','SAINT-VINCENT-ET-LES-GRENADINES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',674,'SAINT-MARIN','SAINT-MARIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',678,'S.TOME+PRINCIPE','SAO TOME-ET-PRINCIPE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',682,'ARABIE SAOUDITE','ARABIE SAOUDITE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',686,'SENEGAL','SENEGAL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',688,'SERBIE','SERBIE','2007-10-22','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',690,'SEYCHELLES','SEYCHELLES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',694,'SIERRA LEONE','SIERRA LEONE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',702,'SINGAPOUR','SINGAPOUR','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',703,'SLOVAQUIE','SLOVAQUIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',704,'VIET NAM','VIET NAM','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',705,'SLOVENIE','SLOVENIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',706,'SOMALIE','SOMALIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',710,'AFRIQUE DU SUD','AFRIQUE DU SUD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',716,'ZIMBABWE','ZIMBABWE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',720,'YEMEN DEMOCR.','YEMEN, DEMOCRATIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',724,'ESPAGNE','ESPAGNE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',728,'SOUDAN DU SUD','SOUDAN DU SUD','2013-12-30','TASSINB','2013-12-30',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',729,'SOUDAN','SOUDAN','2013-12-30','TASSINB','2013-12-30',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',732,'SAHARA OCCID.','SAHARA OCCIDENTAL','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',736,'SOUDAN','SOUDAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',740,'SURINAME','SURINAME','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',744,'Iles Svl+JANMAYEN','ILES SVALBARD ET JANMAYEN','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',748,'SWAZILAND','SWAZILAND','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',752,'SUEDE','SUEDE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',756,'SUISSE','SUISSE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',760,'SYRIE','SYRIENNE, REPUBLIQUE ARABE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',762,'TADJIKISTAN','TADJIKISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',764,'THAILANDE','THAILANDE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',768,'TOGO','TOGO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',772,'TOKELAO','TOKELAO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',776,'TONGA','TONGA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',780,'TRINITE+TOBAGO','TRINITE-ET-TOBAGO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',784,'EMIR.ARAB.UNIS','EMIRATS ARABES UNIS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',788,'TUNISIE','TUNISIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',792,'TURQUIE','TURQUIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',795,'TURKMENISTAN','TURKMENISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',796,'TURKS CAIQUES','TURKS ET CAIQUES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',798,'TUVALU','TUVALU','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',800,'OUGANDA','OUGANDA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',804,'UKRAINE','UKRAINE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',807,'MACEDOINE','MACEDOINE, L\'EX-REPUBLIQUE YOUGOSLAVE DE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',810,'URSS','URSS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',818,'EGYPTE','EGYPTE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',826,'ROYAUME-UNI','ROYAUME UNI DE GRANDE BRETAGNE ET D\'IRLANDE DU NORD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',831,'GUERNESEY','GUERNSEY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',832,'JERSEY','JERSEY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',833,'ISLE DE MAN','ILE DE MAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',834,'TANZANIE','TANZANIE, REPUBLIQUE-UNIE DE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',840,'ETATS-UNIS','ETATS-UNIS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',850,'IL. V. DES US','ILES VIERGES DES ETATS-UNIS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',854,'BURKINA FASO','BURKINA FASO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',858,'URUGUAY','URUGUAY','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',860,'OUZBEKISTAN','OUZBEKISTAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',862,'VENEZUELA','VENEZUELA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',876,'WAL. ET FUTUNA','WALLIS ET FUTUNA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',882,'SAMOA','SAMOA (Etats indépendants du )','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',886,'YEMEN','YEMEN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',887,'YEMEN','YEMEN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',890,'YOUGOSLAVIE','YOUGOSLAVIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',891,'SERB.MONTENEGRO','SERBIE-ET-MONTENEGRO','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',894,'ZAMBIE','ZAMBIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',900,'ANTARCTIQUE','ANTARCTIQUE (TERRITOIRE AUSTRALIEN DE L\')','2010-04-06','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',901,'ILE ASHMORE','ILE ASHMORE','2008-12-05','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',903,'ILE ASCENSION','ILE ASCENSION','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',904,'ACORES','ACORES','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',905,'BALEARES','BALEARES','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',907,'KALIMANTAN','KALIMANTAN','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',908,'BONAIRE','BONAIRE','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',909,'BOPHUTHATSWANA','BOPHUTHATSWANA','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',910,'ILE BEAR','ILE BEAR (BJORNOYA)','2009-12-07','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',911,'ANTARCTIQUE BRITANNIQUE','ANTARCTIQUE (TERRITOIRE BRITANNIQUE DE L)','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',912,'ILES CANARIES','ILES CANARIES','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',913,'SOULAWESI','SOULAWESI','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',914,'CANADA EXP FRANCE','CANADA D\'EXPRESSION FRANCAISE','2009-12-09','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',915,'ILES ANGLO-NORMANDES','ILES ANGLO-NORMANDES','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',916,'ILES DE LA LIGNE','ILES DE LA LIGNE (CENTRE ET SUD)','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',917,'ILE CARTIER','ILE CARTIER','2009-12-09','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',918,'CURACAO','CURACAO','2011-06-30','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',919,'ANGLETERRE','ANGLETERRE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',920,'TERRES AUSTRALES+ANT. FR.','TERRES AUSTRALES + ANTARCTIQUE FRANCAIS','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',921,'BELGIQUE D\'EXP. FRANCAISE','BELGIQUE D\'EXPRESSION FRANCAISE','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',922,'GRANDE-BRETAGNE','GRANDE-BRETAGNE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',923,'SUISSE D\'EXP. ITALIENNE','SUISSE D\'EXPRESSION ITALIENNE','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',924,'ILE ESPERANCE','ILE ESPERANCE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',925,'IRIAN BARAT','IRIAN BARAT','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',926,'REPUBLIQUE ITALIENNE ','REPUBLIQUE ITALIENNE ','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',927,'JAVA','JAVA','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',928,'MADERE','MADERE','2009-09-25','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',929,'ILE MACQUARIE','ILE MACQUARIE','2010-03-16','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',930,'MOLUQUES','MOLUQUES','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',931,'IRLANDE DU NORD','IRLANDE DU NORD','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',932,'VIETNAM DU NORD','VIETNAM DU NORD','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',933,'ZONE DU CANAL DE PANAMA','ZONE DU CANAL DE PANAMA','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',934,'REDONDA','REDONDA','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',936,'ROSS (DEPENDANCE DE)','ROSS (DEPENDANCE DE)','2010-02-04','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',937,'SABA','SABA','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',938,'SPITZBERG','SPITZBERG','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',939,'ECOSSE','ECOSSE','2009-12-09','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',940,'ILES DE LA SONDE','ILES DE LA SONDE','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',941,'ST.EUS-ST.MART.','SAINT-EUSTATINS ET SAINT-MARTIN','2011-06-30','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',943,'SIKKIM','SIKKIM','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',945,'SUMATRA','SUMATRA','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',946,'SUISSE D\'EXP. FRANCAISE','SUISSE D\'EXPRESSION FRANCAISE','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',947,'VIETNAM SUD','VIETNAM SUD','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',948,'ILES SANDWICH DU SUD','ILES SANDWICH DU SUD','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',949,'TRANSKEI','TRANSKEI','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',950,'TRISTAN DA CUNHA','TRISTAN DA CUNHA','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',952,'BELGIQUE D\'EXP. FLAMANDE','BELGIQUE D\'EXPRESSION FLAMANDE','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',953,'PAYS DE GALLES','PAYS DE GALLES','2010-02-04','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',954,'ILE DE MAN','ILE DE MAN','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',955,'SUISSE EXP ALLEMAN','SUISSE D\'EXPRESSION ALLEMANDE','2009-12-09','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',959,'VENDA','VENDA','2011-07-07','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2001,'AFRIQUE BRITANNIQUE','AFRIQUE BRITANNIQUE','2010-05-27','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2003,'CONT.AMERICAIN','CONT.AMERICAIN','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2006,'EXTREME-ORIENT','EXTREME-ORIENT','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2008,'COMMONWEALTH','COMMONWEALTH BRITANNIQUE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2012,'AMER.CENT.CONT.','AMERIQUE CENTRALE (CONTINENT)','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2018,'HEMIS.ORIENT','HEMISPHERE ORIENTAL ou EST','2009-08-25','CREUXA','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2019,'PAYS LANG.ANG.','Pays de langue ANGLAISE','2009-08-25','CREUXA','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2022,'FC','FRANCE COMMUNAUTE (France + PF + Pays Afr.)','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2024,'ALLEMAGNE EST/OUEST','ALLEMAGNE EST/OUEST','2011-03-22','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2025,'Antilles (Grandes)','Antilles (Grandes)','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2026,'PAYS LANG.ALLEM','PAYS DE LANGUE ALLEMANDE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2027,'AMER.LATINE','AMERIQUE LATINE','2009-08-25','CREUXA','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2029,'PETITES ANTILLES','Antilles (Petites)','2009-08-25','CREUXA','2014-08-29',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2033,'PROCHE-ORIENT','PROCHE-ORIENT','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2035,'AFR.SUD+6Pays','AFRIQUE du SUD + BOT.LES.NAM.SWA.BOP.TRA','2009-08-25','CREUXA','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2038,'PAYS LANG.ESP.','PAYS DE LANGUE ESPAGNOLE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2039,'ETATS UNIS + Poss','ETATS-UNIS (et leurs possessions)','2009-08-25','CREUXA','2014-10-17',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2040,'EUROPE OCC.','EUROPE OCCIDENTALE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2041,'HEMISPH.OCC.','HEMISPHERE OCCIDENTAL ou OUEST','2009-08-25','CREUXA','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2044,'PAYS INCONNU','PAYS INCONNU','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2045,'EUROPE CENTRALE','EUROPE CENTRALE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2046,'REPUBLIQUE FRANCAISE','REPUBLIQUE FRANCAISE (France + DOM TOM)','2009-08-25','CREUXA','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2050,'TERR.AFRIC.PRS','TERR.AFRICAINS PRS','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2054,'ANTILLES Francaises','ANTILLES Francaises','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2055,'ANTILLES Anglaises','ANTILLES Anglaises (Actuels)','2014-08-22','TASSINB','2014-08-29',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2056,'Caraibes','Territoires des Caraibes','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2100,'AFRIQUE','AFRIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2101,'AMERIQUE','AMERIQUE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2102,'CONT.AMERICAIN','CONTINENT AMERICAIN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2103,'ANTILLES','ANTILLES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2104,'APEC','ETATS APEC','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2105,'ASEAN','ETATS ASEAN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2106,'ASIE','ASIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2107,'AUSTRALASIE','AUSTRALASIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2108,'BALKANS','BALKANS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2109,'ETATS BALTES','ETATS BALTES','2006-05-03','ARCHI_APP','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2110,'BENELUX','BENELUX','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2111,'ILES BRITAN.','ILES BRITANNIQUES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2112,'INDES OCC.BRIT.','INDES OCCIDENTALES BRITANNIQUES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2113,'AMERIQUE CENT.','AMERIQUE CENTRALE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2114,'COMMONWEALTH','COMMONWEALTH','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2115,'CO AFRIQUE','COMMONWEALTH TERRITOIRES AFRICAINS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2116,'CO ASIE','COMMONWEALTH TERRITOIRES ASIATIQUES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2117,'CO AUSTRALASIE','COMMONWEALTH TERRITOIRES d\'OCEANIE','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2118,'CEI','COMMUNAUTE DES ETATS INDEPENDANTS','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2119,'EUROPE ORIENT.','EUROPE ORIENTALE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2120,'EUROPE','EUROPE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2121,'ESP.ECON.EURO','ESPACE ECONOMIQUE EUROPEEN','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2122,'CONT. EUROPEEN','CONTINENT EUROPEEN (GB IE IS MT exclus)','2006-05-03','ARCHI_APP','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2123,'CEE','COMMUNAUTE ECONOMIQUE EUROPEENNE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2124,'DE,AT,CH','ALLEMAGNE, AUTRICHE, SUISSE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2125,'MOYEN-ORIENT','MOYEN-ORIENT','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2126,'NAFTA','ETATS NAFTA','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2127,'PAYS NORDIQUES','PAYS NORDIQUES','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2128,'AFRIQUE DU NORD','AFRIQUE DU NORD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2129,'AMERIQUE D.NORD','AMERIQUE DU NORD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2130,'OCEANIE','OCEANIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2131,'SCANDINAVIE','SCANDINAVIE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2132,'AMERIQUE DU SUD','AMERIQUE DU SUD','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2133,'ASIE SUD EST','ASIE SUD EST','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2134,'Antilles Brit.','Antilles Britaniques (Territoires Brit. Anciens et actuels)','2006-05-03','ARCHI_APP','2014-08-29',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',2136,'MONDE','MONDE','2006-05-03','ARCHI_APP','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3000,'DIVERS','DIVERS','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3001,'GB NON UK','GB NON UK','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3002,'AM CENTR.+MEX+HAIT+CUBA','AMERIQUE CENTRALE (Cont.) + MEXIQUE, HAITI, CUBA','2009-08-25','CREUXA','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3003,'English countries','Pays de langue ANGLAISE sauf USA','2009-08-25','CREUXA','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3005,'VENDOME REPRESENTANT','VENDOME REPRESENTANT','2010-05-27','MASSONR','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3006,'WR','WORLD REPRESENTANT','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3007,'YY','PAYS PROTEGEANT - DE 50 ANS','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3008,'CC','TERR. SACEM JUSQU AU 1.01.90','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3009,'DD','TERR. SACEM SAUF LUX','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3010,'EE','EGYPTE LIBAN ANDORRE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3011,'HF','HARRY FOX','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3012,'GG','GG','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3013,'HH','PAYS LATINS + BENELUX','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3014,'PAYS SCANDINAVES','PAYS SCANDINAVES','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3016,'FK','SCAND.ISLA.FINL','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3017,' Pays SACEM','Pays SACEM (FR+LB+LU+MC)','2006-05-03','ARCHI_APP','2014-08-28',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3019,'NN','BENELUX + ZAIRE + BURUNDI','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3020,'LB','AMERIQUE DU SUD SAUF BRESIL','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3022,'QQ','SOUTHERN REP. VERS. FSE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3023,'AR','APRIL REPRESENTANT','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3024,'PAYS PROT 50 ANS AVEC DIV','PAYS PROTEGEANT 50 ANS AVEC DIVISIBILITE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3025,'QA','SOUTHERN REP. SAUF ALLEMAGNE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3026,'Groupe 4','GROUPE 4 (AU CA GB IE IN NZ PK)','2009-08-25','CREUXA','2014-08-25',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3027,'PAYS PROTEG 65 ANS ET +','PAYS PROTEGEANT 65 ANS ET +','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3028,'PAYS PROT. 57 ANS ET +','PAYS PROTEGEANT 57 ANS ET +','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3029,'PAYS PROT. 56 ANS ET +','PAYS PROTEGEANT 56 ANS ET +','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3030,'UZ','GROUPE 4 SAUF CANADA AUSTRALASIE','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3031,'PAYS PROTEG 30 ANS ET -','PAYS PROTEGEANT 30 ANS ET -','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3032,'SOC NFC REPRESENTANT','SOC NFC REPRESENTANT','2009-08-25','CREUXA','2014-01-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3034,'CLE 90','CLE 90','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3035,'AMERIQUE DU SUD SF GUYANE','AMERIQUE DU SUD SAUF GUYANE FR','2009-08-25','CREUXA','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3039,'PAYS PRS','TERRITOIRES PRS','2006-05-03','ARCHI_APP','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3040,'Pays NCB','TERRITOIRES NCB','2006-05-03','ARCHI_APP','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3041,'Pays SABAM','TERRITOIRES SABAM ','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3042,'Pays APRA','TERRITOIRES APRA ','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3043,'Pays GEMA','TERRITOIRES GEMA ','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3044,'Pays BUMA','TERRITOIRES BUMA','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3045,'Pays SAMRO','TERRITOIRES SAMRO','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3046,'Pays SGAE','TERRITOIRES SGAE ','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3047,'PAYS SPA','TERRITOIRES SPA','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3048,'Pays SIAE','TERRITOIRES SIAE ','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3049,'Pays TONO','TERRITOIRES TONO ','2009-08-25','CREUXA','2014-08-20',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3100,'ZOOM LANGUE','ZOOM LANGUE','2014-08-21','TASSINB','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3101,'ZOOM SOCIETE','ZOOM SOCIETE','2014-08-21','TASSINB','2014-08-21',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3102,'Zoom organi. Internation','Zoom Organisation Internationales','2014-08-22','TASSINB','2014-08-22',NULL);
INSERT INTO `SAREFTJ_LIBTER` VALUES ('FR',3104,'Zoom Groupes Divers','Zoom Groupes Divers','2014-08-22','TASSINB','2014-08-22',NULL);


INSERT INTO PRIAM_PARAMETRAGE VALUES ('GUEST', 'USER_PAGE_SIZE', '25');
INSERT INTO PRIAM_PARAMETRAGE VALUES ('GUEST', 'USER_FAMILLE', 'COPIEPRIV,Copie Privée');


commit;