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
  `TYPE_FICHIER` varchar(25) DEFAULT NULL,
  `STATUT_ENRICHISSEEMNT` VARCHAR(255),
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
-- Table structure for table `PRIAM_CATALOGUE_OCTAV`
--
DROP TABLE IF EXISTS `PRIAM_CATALOGUE_OCTAV`;
CREATE TABLE `PRIAM_CATALOGUE_OCTAV` (
  `ID` BIGINT NOT NULL AUTO_INCREMENT,
  `TYPE_CMS` VARCHAR(3)   NULL,
  `IDE12` BIGINT NULL,
  `TITRE` VARCHAR(255) NULL,
  `ROLE` VARCHAR(255) NULL,
  `PARTICIPANT` VARCHAR(255) NULL,
  PRIMARY KEY (`ID`)
) ENGINE = InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 6286965011, 'Titre1', 'CA', 'Participant1');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 3631472411, 'Titre2', 'CA', 'Participant2');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 8028354411, 'Titre3', 'CA', 'Participant3');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 2944409511, 'Titre4', 'CA', 'Participant4');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 2976498311, 'Titre5', 'CA', 'Participant5');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 9941821311, 'Titre6', 'CA', 'Participant6');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 9887883011, 'Titre7', 'CA', 'Participant7');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 8927933411, 'Titre8', 'CA', 'Participant8');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 8609247211, 'Titre9', 'CA', 'Participant9');
INSERT INTO `PRIAM_CATALOGUE_OCTAV` (`TYPE_CMS`, `IDE12`, `TITRE`, `ROLE`, `PARTICIPANT`) VALUES ('FR', 1111111111, 'Titre10', 'CA', 'Participant10');


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
  `TYPE_DROIT` VARCHAR (5),
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

DROP TABLE IF EXISTS `PRIAM_ROLE`;

CREATE TABLE `PRIAM_ROLE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'Identifiant technique',
  `EXTERNAL_ID` varchar(255) DEFAULT NULL COMMENT 'Code Role',
  `ROLE` varchar(255) DEFAULT NULL COMMENT 'Description',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `LIBFAMILTYPUTIL` varchar(300) NOT NULL COMMENT 'Libelle de la typeUtilisation de type dutilisation',
  `LIBABRGFAMILTYPUTIL` varchar(25) DEFAULT NULL COMMENT 'Libelle abrege de la typeUtilisation de type dutilisation',
  `DATCRE` datetime NOT NULL COMMENT 'DATETIME de creation',
  `USERCRE` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la creation',
  `DATMAJ` datetime NOT NULL COMMENT 'DATETIME de modification',
  `USERMAJ` varchar(60) NOT NULL COMMENT 'Utilisateur ayant effectue la modification',
  PRIMARY KEY (`CDELNG`,`CDEFAMILTYPUTIL`),
  KEY `SAREFTJ_LIBFA_LIBFAMILTYPUT_FK` (`CDEFAMILTYPUTIL`),
  CONSTRAINT `SAREFTJ_LIBFA_LIBFAMILTYPUT_FK` FOREIGN KEY (`CDEFAMILTYPUTIL`) REFERENCES `SAREFTR_FAMILTYPUTIL` (`CDEFAMILTYPUTIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Libelle dune typeUtilisation de type dutilisation';
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
  `FAMILREMPL` varchar(10) DEFAULT NULL COMMENT 'valeur de remplacement pour interrogation de la documentation si la typeUtilisation n¿est plus valide',
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


/*ALTER TABLE PRIAM_FICHIER
  ADD `TYPE_FICHIER` varchar(25) DEFAULT NULL COMMENT 'type de fichier peut etre CP CMD FV pour differencier l envoi';*/

DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_CMS`;
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
  `rionEffet` INT(11) DEFAULT NULL,
  `ID_OEUVRE_CTNU` INT(11) DEFAULT NULL,
  `labelValo` varchar(250) DEFAULT NULL,
  `dureeDeposee` int(11) DEFAULT NULL,
  `taxOri` double DEFAULT NULL,
  `indicRepart` tinyint(1) DEFAULT NULL,
  `genreOeuvre` varchar(250) DEFAULT NULL,
  `paysOri` int(4) DEFAULT NULL,
  `statut` varchar(250) DEFAULT NULL,
  `mtEdit` double DEFAULT NULL,
  `nbrDifEdit` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER_FV` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_FV` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

DROP TABLE IF EXISTS PRIAM_AYANT_DROIT_PERS;
CREATE TABLE PRIAM_AYANT_DROIT_PERS (
NUMPERS  INT(9) DEFAULT NULL,
PRENOM VARCHAR(250) DEFAULT NULL,
NOM VARCHAR(90) DEFAULT NULL,
ANNEE_NAISSANCE INT(4) DEFAULT NULL,
ANNEE_DECES INT(4) DEFAULT NULL,
INDICSACEM TINYINT(1) DEFAULT NULL,
SOUS_ROLE VARCHAR(250) DEFAULT NULL,
INDICDRTPERCUS TINYINT(1) DEFAULT NULL,
PRIMARY KEY (NUMPERS)
);


DROP TABLE IF EXISTS PRIAM_AYANT_DROIT;
CREATE TABLE PRIAM_AYANT_DROIT(
ID int(11) NOT NULL AUTO_INCREMENT,
IDE12REPCOAD  bigint(12) DEFAULT NULL,
CDETYPIDE12REPCOAD varchar(45) DEFAULT NULL,
ROLAD varchar(50) DEFAULT NULL,
COAD INT(10) DEFAULT NULL,
IDSTEAD VARCHAR(10) DEFAULT NULL,
CLEAD double DEFAULT NULL,
CDETYPPROTEC VARCHAR(5) DEFAULT NULL,
COADORIEDTR INT(8) DEFAULT NULL,
IDSTEORIEDTR bigint(15) DEFAULT NULL,
NUMCATAL INT(9) DEFAULT NULL,
NUMPERS INT(9) DEFAULT NULL,
ID_FV int null,
points double DEFAULT NULL,
PRIMARY KEY (ID),
constraint FK_ID_LIGNE_FV foreign key (ID_FV) references PRIAM_LIGNE_PROGRAMME_FV (id),
CONSTRAINT FK_NUMPERS FOREIGN KEY (NUMPERS) REFERENCES PRIAM_AYANT_DROIT_PERS (NUMPERS)
);


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
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','CINEMA','Cinema','Cinema','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','COPIEPRIV','Private copy','Private copy','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','DOCUM','Documentary','Documentary','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','DTSGEN','Live performance','Live performance','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','ETRANGER','Foreign revenues','Foreign revenues','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','FDSVAL','Support fund','Support fund','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','GESTSACEM','Sacem mangement','Sacem mangement','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','ONLINE','Digital reader provider','Digital reader provider','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','ONLINEACTE','Digital reader provider A','Digital reader provider','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','PHONO','French phonographic production','French phonographic produ','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','RADIO','Radio','Radio','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','TOUS','All','All','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','TV','Television','Television','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','UC','General rights (Recorded)','General rights (Recorded)','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('EN','VIDEO','Videogramm','Videogramm','2014-08-28 16:45:06','ZYSMAND','2014-08-28 16:45:06','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','CINEMA','Cinéma','Cinéma','2011-10-12 18:00:17','MASSONR','2013-12-11 09:37:38','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','COPIEPRIV','Copie Privée','Copie Privée','2009-02-03 09:41:13','MASSONR','2009-02-25 18:00:14','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','DOCUM','Documentaire','Doc','2006-11-17 13:28:42','BAUTISTAF','2007-04-17 13:28:10','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','DTSGEN','Droits Généraux','Droits Généraux','2011-10-12 18:00:17','MASSONR','2011-10-12 18:00:17','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','ETRANGER','Etranger','Etranger','2011-10-12 11:15:54','MASSONR','2011-10-12 11:15:54','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','FDSVAL','Valorisation','Valorisation','2013-01-03 08:33:57','MASSONR','2014-09-12 18:00:15','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','GESTSACEM','Gestion Sacem','Gestion Sacem','2011-10-13 11:00:41','MASSONR','2011-10-13 11:00:41','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','ONLINE','Online','Online','2006-11-17 00:00:00','BAUTISTAF','2006-11-17 13:16:32','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','ONLINEACTE','Online A','Online A','2008-10-20 17:08:05','GOLLIONB','2010-01-18 18:00:17','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','PHONO','Phono','Phono','2011-04-29 18:00:17','MASSONR','2011-10-07 16:03:57','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','RADIO','Radio','Radio','2006-11-17 13:14:32','BAUTISTAF','2011-10-07 13:36:20','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','TOUS','Tous','Tous','2010-10-19 18:00:20','MASSONR','2010-12-01 18:00:28','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','TV','Télévision','Télévision','2006-11-17 00:00:00','BAUTISTAF','2011-10-07 13:36:54','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','UC','Usagers Communs','Usagers Communs','2006-11-17 13:17:18','BAUTISTAF','2011-10-07 13:37:51','');
INSERT INTO `SAREFTJ_LIBFAMILTYPUTIL` VALUES ('FR','VIDEO','Vidéogramme','Vidéogramme','2012-07-17 14:43:04','MASSONR','2012-07-17 14:43:04','');


-- ---------------------------------------------------
-- ------ TABLE SAREFTJ_LIBTYPUTIL ------------------------
-- ---------------------------------------------------
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','AUTREDG','Other performance (Live)','Other performance (Live)','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','AUTREUC','Other performance (Live)','Other performance (Live)','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','BAL','Ball','Ball','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','BALCO','Ball (Sacem conductor)','Ball (Sacem conductor)','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','BALCONS','Ball (Non Sacem conductor)','Ball (Non Sacem conductor','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','BALLETDG','Ballet (Live)','Ballet (Live)','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','BALLETUC','Ballet (Recorded)','Ballet (Recorded)','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CENTRALISE','Central licensing','Central licensing','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CINEMA','Cinema','Cinema','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CINEPUB','Movie theater commercial','Movie theater commercial','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CIRQUEDG','Circus (Live)','Circus (Live)','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CIRQUEUC','Circus (Recorded)','Circus (Recorded)','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CONCERT','Concert','Concert','2014-08-29 09:09:36','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CONCERTSYM','Serious music concert','Serious music concert','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','COPRIVSON','Private copy sound','Private copy sound','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CPRIVAUDV','Private copy audiovisual','Private copy audiovisual','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CPRIVSONPH','Private copy sound (PHONO)','Private copy sound (PHONO','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','CPRIVSONRD','Private copy sound (Radio)','Private copy sound (Radio','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DG','Live performance','Live performance','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DISCOFIXE','Disco','Disco','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DIVERS','Various usage','Various usage','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOCUM','Documentation','Documentation','2014-08-29 09:09:36','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOWNMDBST','Download audiovisual DBST','Download audiovisual DBST','2014-08-28 17:52:10','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOWNMUSIC','Download','Download','2014-08-28 18:00:08','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOWNMUSICA','Download','Download','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOWNMUSICB','Download','Download','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOWNMUSICD','Download','Download','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOWNMUSICR','Download','Download','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOWNMUSICV','Download audiovisual','Download audiovisual','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DOWN_MUSIC','Download','Download','2008-11-18 18:00:15','MASSONR','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','DROITREAL','Director\'s rights','Director\'s rights','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','EDITEURS','Publishers','Publishers','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','EGLISE','Sacred building','Sacred building','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','ENCOURG','Support','Support','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','ETRANGER','Foreign revenues','Foreign revenues','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','EXCEDFRAIS','Operating surplus','Operating surplus','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','GRDETABL','Large venue','Large venue','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','GRDETOURNE','Major tour','Major tour','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','INDETERMIN','Unknown','Unknown','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','MUSIQELECT','Electronic music','Electronic music','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','MUSIQENREG','Recorded music','Recorded music','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','MUSIQSCENE','Stage music','Stage music','2014-08-29 09:06:33','ZYSMAND','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','PERCEPTDIV','Various revenues','Various revenues','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','PHONOFR','Phonographic production','Phonographic production','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','PLATFORMWB','UGC Platform','UGC Platform','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','PRIME','Bonus','Bonus','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','PRODUCTEUR','Producers','Producers','2014-08-29 09:09:36','ULYSS','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RADIOEI','Radio (Int. Exchanges)','Radio (Int. Exchanges)','2014-08-29 09:09:36','ULYSS','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RADIOEP','Radio Ads (int. exchange)','Radio Ads (int. exchange)','2014-08-29 09:09:36','ULYSS','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RADIOEPDR','Radio Ads (int. exch. Recorded only)','Radio Ads (int. exch. Rec','2014-08-29 09:09:36','BAUTISTAF','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RADIOPUB','Radio commercials','Radio commercials','2014-08-29 09:09:36','ULYSS','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RADLOC','Local Radio Channel','Local Radio Channel','2016-09-26 18:34:52','BOLJS','2016-09-26 18:34:52','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RDCAB','Cable Radio','Cable Radio','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RDHRZ','Radio channel','Radio channel','2016-09-26 18:33:55','BOLJS','2016-09-26 18:33:55','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','REDISTPH','Phonographic redistribution','Phonographic redistributi','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','REPADOL','IP prorated distribution','IP prorated distribution','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','REPADTV','IP TV prorated distribution','IP TV prorated distributi','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','REPRICABLE','Cable complementary','Cable complementary','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','REPRODDIV','Reproduction various','Reproduction various','2014-08-29 09:09:36','FRIESP','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RINGTONESA','Ringtones','Ringtones','2014-08-29 09:09:36','BAUTISTAF','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RINGTONESB','Ringtones','Ringtones','2014-08-29 09:09:36','LIETH','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','RINGTONESR','Ringtones','Ringtones','2014-08-29 09:09:36','LIETH','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','SEANCAUDIO','Audiovisual show','Audiovisual show','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','SEANOCCAS','Occasional performances','Occasional performances','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','SONLUMIERE','Sound and light show','Sound and light show','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','SPEHUMOUR','Humour','Humour','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','STREAMDBST','Streaming DBST','Streaming DBST','2014-08-29 09:09:36','LIETH','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','STREAMING','Streaming','Streaming','2008-11-18 18:00:15','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','STREAMINGA','Streaming','Streaming','2014-08-29 09:09:36','FRIESP','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','STREAMINGB','Streaming','Streaming','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','STREAMINGR','Streaming','Streaming','2014-08-29 09:09:36','LIETH','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','STREAMINGV','Streaming','Streaming','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','STREAMINGY','Streaming','Streaming','2014-08-29 09:09:36','LIETH','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TEMPOL','Temporaire On line','Temporaire On line','2014-08-29 09:09:36','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TOURMUSSCN','Tour stage music','Tour stage music','2014-08-29 09:09:36','FRIESP','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TOURNEE','Tour','Tour','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TOURSYMPHO','Tour serious music','Tour serious music','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TOUS','All','All','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TVABO','Television','Television','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TVADSL','TV ADSL','TV ADSL','2014-08-29 09:09:36','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TVCAB','TV Cable','TV Cable','2014-08-29 09:09:36','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TVHRZ','TV Air','TV Air','2014-08-29 09:09:36','ULYSS','2014-12-05 18:00:23','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TVPUB','TV commercial','TV commercial','2014-08-29 09:09:36','ULYSS','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TVTNT','TV TNT','TV TNT','2014-08-29 09:09:36','ULYSS','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','TVWEB','TV Web','TV Web','2014-08-29 09:09:36','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','UC','General rights (Recorded)','General rights (Recorded)','2014-08-29 09:09:36','ULYSS','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','VALORIS','Valorisation fund','Valorisation fund','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','VIDEOETR','Video (foreign)','Video (foreign)','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','VIDEOSESAM','Video (Sesam)','Video (Sesam)','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','VIDEOSTD','Video','Video','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('EN','VIDINSTIT','Video (Corporate)','Video (Corporate)','2014-08-29 09:09:36','MASSONR','2014-08-29 15:46:55','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','AUTREDG','Autres DG','Autres DG','2014-03-11 11:24:45','MASSONR','2015-12-08 11:04:43','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','AUTREUC','Autres UC','Autres UC','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','BAL','Bal','Bal','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','BALCO','Bal chef d\'orchestre sociétaire','Bal sociétaire','2009-07-29 14:02:09','GOLLIONB','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','BALCONS','Bal chef d\'orchestre non sociétaire','Bal non sociétaire','2009-07-29 14:04:00','GOLLIONB','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','BALLETDG','Ballet DG','Ballet DG','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','BALLETUC','Ballet UC','Ballet UC','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CENTRALISE','Centralisation','Centralisation','2011-04-29 18:00:17','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CINEMA','Cinéma','Cinéma','2011-10-12 18:00:17','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CINEPUB','Publicité cinéma','Publicité cinéma','2011-10-12 18:00:17','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CIRQUEDG','Cirque DG','Cirque DG','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CIRQUEUC','Cirque UC','Cirque UC','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CONCERT','Concert','Concert','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CONCERTSYM','Concert Musique Sérieuse','Concert Musique Sérieuse','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CONCERTVAR','Concerts de variétés','Concerts de variétés','2013-01-31 10:39:10','FRIESP','2013-01-31 10:39:10','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','COPRIVSON','Copie Privée Sonore','Copie Privée Sonore','2009-02-03 09:41:13','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CPRIVAUDPL','Copie Privée Audiovisuel - Part Littéraire','Copie privée Audiov Litt','2016-12-15 18:00:23','TASSINB','2016-12-15 18:00:23','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CPRIVAUDV','Copie Privée Audiovisuelle','Copie Privée Audiov.','2011-10-19 18:00:52','MASSONR','2017-03-09 18:00:12','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CPRIVSONPH','Copie privée sonore Phono','Copie Privée sonore Phono','2011-10-14 18:00:23','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','CPRIVSONRD','Copie Privée Sonore radio','Copie Privée sonore RD','2011-10-14 18:00:23','MASSONR','2017-03-09 18:00:12','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DG','Droits Généraux','Droits Généraux','2011-10-12 18:00:17','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DISCOFIXE','Discothèques Fixes','Discothèques','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DISCOPART','Calcul Rion Discothèques a la Part','Discopart','2016-08-23 17:27:15','FRIESP','2016-09-28 16:28:14','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DISCORETRO','Discothèques rétro et mobiles','Discothèques rétro','2017-01-19 10:04:16','TASSINB','2017-01-19 13:58:49','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DIVERS','Divers','Divers','2012-11-26 18:10:44','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOCUM','Documentation','Documentation','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOWNMDBST','Downmusic Vidéo Doubleur Soustitreur','Downmusic Vidéo DBST','2014-07-28 14:19:45','LIETH','2014-08-29 09:09:36','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOWNMUSIC','Téléchargement de musique','Téléchargement de musique','2009-07-08 11:16:43','GOLLIONB','2014-08-29 09:09:36','BAUTISTAF','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOWNMUSICA','Téléchargement de musique à l\'acte.','Téléch. musique à l\'acte','2008-10-20 17:13:58','GOLLIONB','2014-08-29 09:09:36','BAUTISTAF','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOWNMUSICB','Telechargement abonnement','Telechargement abonnement','2011-07-05 11:29:42','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOWNMUSICD','Téléch.musique à la durée','Téléch.musique à la durée','2013-02-14 18:13:22','LIETH','2014-08-29 09:09:36','BAUTISTAF','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOWNMUSICR','Téléchargement musique répertoire','Téléch musique repertoire','2013-10-30 12:22:49','LIETH','2014-08-29 09:09:36','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOWNMUSICV','Downmusic video','Downmusic video','2014-01-15 09:26:02','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DOWN_MUSIC','Téléchargement de musique (NPU)','Téléchargement (NPU)','2005-10-17 12:33:03','ULYSS','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','DROITREAL','Droits Réalisateurs','Droits Réalisateurs','2013-11-06 12:07:33','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','EDITEURS','Editeurs','Editeurs','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','EGLISE','Eglise','Eglise','2012-11-06 17:48:20','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','ENCOURG','Encouragement','Encouragement','2011-12-09 18:00:22','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','ETRAD','Droit Etranger AD sans oeuvre(HOMER)','ETRAD(HOMER)','2015-12-30 18:22:27','LIETH','2017-02-01 16:01:44','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','ETRADIN','Droit Etranger AD sans oeuvre retour de droit entrant(HOMER)','ETRADIN(HOMER)','2015-12-30 18:36:30','LIETH','2017-02-01 16:01:44','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','ETRANGER','Droits en provenance des sociétés soeurs','Etranger','2011-10-12 11:17:18','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','ETRANGERAD','Etranger AD sans oeuvre (hors Homer)','Etranger AD(hors Homer)','2016-07-07 14:39:11','LIETH','2016-07-07 14:39:11','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','ETROEUV','Droit Etranger avec oeuvre(HOMER)','ETROEUV(HOMER)','2015-12-30 18:27:25','LIETH','2017-02-01 16:01:44','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','ETROEUVIN','Droit Etranger retour de droit entrant avec oeuvre(HOMER)','ETROEUVIN(HOMER)','2015-12-30 18:29:33','LIETH','2017-02-01 16:01:44','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','EXCEDFRAIS','Excedent de gestion','Excedent de gestion','2011-10-13 11:00:41','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD01','Fonds d\'encouragement à la 1ère exécution publique musique symphonique','Encourg.1 Exec Music Sym','2017-09-22 18:00:12','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD02','Fonds d\'incitation au nouvel enregistrement de la musique symphonique','Nouvel.Enrg Mus.SYM','2017-09-22 18:00:12','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD03','Fonds de valorisation de la musique symphonique enregistrée','Val.Music Sym Enrg.','2017-09-27 11:43:37','TASSINB','2017-09-27 11:43:37','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD04','Fonds de valorisation de la musique symphonique','Val. Musique Sym.','2017-09-27 11:43:37','TASSINB','2017-09-27 11:43:38','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD05','Fonds d\'encouragement à la 1ere exécution publique musique JAZZ','JAZZ Encourg. 1Exec.','2017-09-27 11:43:38','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD06','Fonds de valorisation improvisation JAZZ','JAZZ Improvisation','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD07','Fonds d\'incitation au nouvel enregistrement de la musique de JAZZ','JAZZ Nvle.Enregistrement','2017-09-27 11:43:38','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD09','Fond d\'encouragement de la musique vivante de variétés (SMAC)','Encourg. Music Vivante','2017-09-27 11:43:38','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD10','Fonds de valorisation de la poésie enregistrée','Val. Poésie Enrg.','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD11','Fonds de valorisation de la poésie','Val. Poésie','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD12','Fonds de valorisation de la musique Corse','Val. Musique Corse','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD13','Complément Harmonie et Fanfares','Cplt. Harmonie Fanfares','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','ELYACOUBIA','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','FD14','Valorisation Documentaire','Val. Documentaire','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','GRDETABL','Grand Etablissement','Grand Etablissement','2013-11-06 11:27:03','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','GRDETOURNE','Grande tournée','Grande tournée','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','INDETERMIN','Indeterminé','Indeterminé','2012-06-20 11:31:31','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','MUSIQELECT','Musique électronique','Musique électronique','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','MUSIQENREG','Musique enregistrée','Musique enregistrée','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','MUSIQSCENE','Musique de scène','Musique de scène','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','OPO','OPO(GELDA)','Oeuvre par Oeuvre','2017-01-05 09:52:58','LIETH','2017-01-05 09:52:58','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','PERCEPTDIV','Perception divers','Perception divers','2011-10-13 11:00:41','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','PHONOFR','Phono France','Phono France','2011-10-12 18:00:17','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','PHONOFRAD','Phono France à l\'ayant droit sans oeuvre','Phono FRAD','2016-02-04 18:23:46','LIETH','2016-02-04 18:23:46','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','PLATFORMWB','Plateforme Web','Plateforme Web','2013-11-06 09:15:32','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','PLTFORMWBY','PLATFORM WEBY HUMORISTE','PLATFORM WEBY','2015-12-22 14:14:38','LIETH','2016-01-14 18:24:45','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','PRIME','Prime','Prime','2011-12-09 11:40:55','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','PRODUCTEUR','Producteurs','Producteurs','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOEI','Radio Echanges internationaux ','Radio Echanges internatio','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOEP','Radio Echanges Publicitaires','Radio Echanges Publicitai','2005-10-17 12:33:03','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOEPDR','Radio Ech.Pub DR uniquement','Radio Ech.Pub DR uniqueme','2006-06-30 00:00:00','BAUTISTAF','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOHAB','Radio Hertzienne Habillage',NULL,'2014-09-15 16:28:19','FRIESP','2014-09-15 16:28:19','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOHRZ','Radio hertzienne NPU','Radio hertzienne NPU','2015-08-17 10:47:58','CREUXA','2015-11-13 15:09:34','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOPUB','Radio publicité','Radio publicité','2005-08-30 12:23:25','VALENZAA','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOPUBDR','Radio Publicité DR Uniquement','Radio Publicité DR Unique','2006-06-30 00:00:00','BAUTISTAF','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOUC','Radio Usagers communs ','Radio Usagers communs ','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADIOWEB','Web Radio (Radio)','Web Radio (Radio)','2005-08-30 12:23:25','ULYSS','2016-06-01 14:53:41','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RADLOC','Radios Locales','Radios Locales','2005-10-17 12:32:28','ULYSS','2014-08-29 09:09:36','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RDCAB','Radio Cable','Radio Cable','2011-10-11 18:00:45','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RDHRZ','Radio Hertzienne','Radio Hertzienne','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','REDISTPH','Redistribution Phono','Redistribution Phono','2011-12-09 18:00:23','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','REPADOL','Repartition à l\'ayant droit','Repartition à l\'AD','2011-02-09 09:44:03','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','REPADTV','Repartition TV à l\'ayant droit','Repartition TV à l\'AD','2011-07-11 18:00:20','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','REPRICABLE','Reprise Câble','Reprise Câble','2011-11-02 09:29:02','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','REPRISONFR','Reprise Compte attente Online France','Reprise Attente ON FR','2016-04-13 09:43:57','LIETH','2016-05-12 16:05:18','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','REPRODDIV','Reproduction Divers','Reproduction Divers','2012-02-16 16:37:48','FRIESP','2014-08-29 09:09:36','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RINGTONES','Sonneries Telephoniques','Sonneries Telephoniques','2007-04-23 11:50:02','BAUTISTAF','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RINGTONESA','Sonnerie téléphonie mobile à l\'acte','Sonnerie Mobile à l\'acte','2008-10-20 17:15:54','GOLLIONB','2014-08-29 09:09:36','BAUTISTAF','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RINGTONESB','Sonneries téléphoniques à l\'abonnement','Sonneries telp. abonnemen','2013-07-26 09:33:53','LIETH','2014-08-29 09:09:36','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','RINGTONESR','Sonnerie téléphonique à répertoire','Sonnerie télp. répertoire','2013-11-04 12:14:53','LIETH','2014-08-29 09:09:36','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SEANCAUDIO','Séance audiovisuelle','Séance audiovisuelle','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SEANOCCAS','Séances occasionnelles','Séances occasionnelles','2012-09-11 12:51:34','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SONLUMIERE','Son et lumière','Son et lumière','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SONOANT','Musique de Sonorisation Antilles','Music.Sonorisat. Antilles','2017-09-29 07:55:36','TASSINB','2017-09-29 07:55:36','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SONOFRA','Musique de Sonorisation (CMS)','Musique de Sonorisation','2017-09-29 07:55:36','TASSINB','2017-09-29 07:55:36','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SONOPARTS','Sonorisation en part taxation','Sonoparts','2016-07-06 15:50:54','LIETH','2016-07-28 12:06:18','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SONORIS','Sonorisation','Sonorisation','2008-02-21 19:35:23','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SONOSECOND','Sonorisation en secondes','Sonosecond','2016-06-30 13:55:39','LIETH','2016-07-28 12:12:12','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','SPEHUMOUR','Humoriste','Humoriste','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','STREAMDBST','Streaming Vidéo Doubleur soustitreur','Streaming Vidéo DBST','2014-07-28 15:33:48','LIETH','2014-08-29 09:09:36','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','STREAMING','Streaming','Streaming','2008-07-23 18:00:17','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','STREAMINGA','Streaming à l\'acte','STREAMINGA','2010-07-02 13:37:33','FRIESP','2014-08-29 09:09:36','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','STREAMINGB','Streaming abonnement','Streaming abonnement','2012-07-26 11:22:25','FRIESP','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','STREAMINGR','Streaming à répertoire','STREAMINGR','2013-11-13 14:35:42','LIETH','2014-08-29 09:09:36','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','STREAMINGV','Streaming Video','Streaming Video','2014-01-15 09:26:02','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','STREAMINGY','Streaming réalisateur à l\'abonnement','Streaming réal. abo','2013-07-24 09:16:46','LIETH','2014-08-29 09:09:36','LIETH','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TEMPOL','Temporaire On line','Temporaire On line','2010-05-12 18:00:24','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TOURBLTDG','Tournée Ballet Droits Généraux','Tournée Ballet DG','2016-06-28 16:51:58','FRIESP','2016-08-23 17:54:15','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TOURBLTUC','Tournée Ballet Usagers Communs','Tournée Ballet UC','2016-06-28 16:51:58','FRIESP','2016-08-23 17:54:15','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TOURCRQDG','Tournée Cirque Droits Généraux','Tournée Cirque DG','2016-06-28 16:51:58','FRIESP','2016-08-23 17:54:15','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TOURCRQUC','Tournée Cirque Usagers Communs','Tournée Cirque UC','2016-06-28 16:51:58','FRIESP','2016-08-23 17:54:15','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TOURMUSSCN','Tournée Musique de scène','Tournée Musique de scène','2014-07-04 09:26:27','FRIESP','2014-08-29 09:09:36','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TOURNEE','Tournée','Tournée','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TOURSYMPHO','Tournée Musique Sérieuse','Tournée Musique Sérieuse','2014-03-11 11:24:45','MASSONR','2014-08-29 09:09:36','FRIESP','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TOUS','Tous','Tous','2010-09-29 18:00:21','MASSONR','2014-08-29 09:09:36','CREUXA','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TVABO','Television par abonnement','TV par abonnement','2011-10-11 18:00:45','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TVADSL','TV ADSL','TV ADSL','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TVCAB','TV Cable','TV Cable','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TVHRZ','TV Historique','TV Historique','2005-08-30 12:23:25','ULYSS','2014-12-04 18:00:11','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TVPUB','Pub Télévision','Publicité Télévision','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TVTNT','TV TNT','Télévision Numérique Terr','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','TVWEB','Télé Web (Télévision)','Télé Web (Télévision)','2005-08-30 12:23:25','ULYSS','2016-06-01 14:53:41','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','UC','Usagers Communs','Usagers Communs','2005-08-30 12:23:25','ULYSS','2014-08-29 09:09:36','ZYSMAND','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','VALORIS','Fonds de valorisation','Fonds de valorisation','2011-12-09 18:00:23','MASSONR','2014-09-12 18:00:16','TASSINB','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','VIDEOETR','Vidéo étranger','Vidéo étranger','2013-07-26 16:44:33','BAUTISTAF','2014-08-29 09:09:36','BOLJS','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','VIDEOSESAM','Video SESAM','Video SESAM','2012-11-06 15:21:45','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','VIDEOSTD','Video standard','Video STD','2012-11-06 11:33:07','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','VIDEOSTDAD','Vidéo Standard à l\'Ayant droit sans oeuvre','Vidéo STDAD','2016-01-27 17:49:42','LIETH','2016-02-01 10:46:36','BAUTISTAF','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','VIDINSTIT','Video institutionnelle','video instit.','2012-11-06 17:48:20','MASSONR','2014-08-29 09:09:36','MASSONR','');
INSERT INTO `SAREFTJ_LIBTYPUTIL` VALUES ('FR','WEBRADIO','Web Radio (OnLine)','Web Radio (OnLine)','2015-11-17 18:00:10','MASSONR','2016-06-01 14:53:41','TASSINB','');

-- ---------------------------------------------------
-- ------ TABLE SAREFTR_FAMILTYPUTIL -------------------
-- ---------------------------------------------------
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('CINEMA',28,1,NULL,'S','seconde',NULL,'2011-10-12 18:00:17','MASSONR','2017-06-29 11:01:52','BOLJS','1900-01-01 00:00:00',NULL,NULL,2.00,'CI',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('COPIEPRIV',9223372036854775807,1,NULL,NULL,'TAG',NULL,'2009-02-03 09:41:13','MASSONR','2012-10-16 10:31:22','TASSINB','2009-02-02 00:00:00',NULL,NULL,4.00,'CO',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('DOCUM',9223372036854775807,1,NULL,NULL,'seconde',NULL,'2006-11-17 13:28:12','BAUTISTAF','2012-03-21 16:27:41','BAUTISTAF','2006-11-17 00:00:00',NULL,'FORFAIT',NULL,'DO',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('DTSGEN',61,1,NULL,NULL,'seconde',NULL,'2011-10-12 18:00:17','MASSONR','2015-11-19 09:22:36','BOLJS','1900-01-01 00:00:00',NULL,NULL,NULL,'DG',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('ETRANGER',12,0,NULL,NULL,'seconde',NULL,'2011-10-12 11:15:54','MASSONR','2012-10-16 10:31:21','FRIESP','1900-01-01 00:00:00',NULL,NULL,6.00,'ET',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('FDSVAL',12,0,NULL,NULL,'seconde',NULL,'2013-01-03 08:33:57','MASSONR','2017-05-22 14:34:37','TASSINB','1900-01-01 00:00:00',NULL,NULL,NULL,'FS',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('GESTSACEM',4,0,NULL,NULL,'seconde',NULL,'2011-10-13 11:00:40','MASSONR','2012-03-21 16:27:41','BAUTISTAF','1900-01-01 00:00:00',NULL,NULL,NULL,'GE',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('ONLINE',9223372036854775807,1,'TOUS','T','TAG',NULL,'2006-11-17 00:00:00','BAUTISTAF','2014-01-08 17:20:23','FRIESP','2006-11-17 00:00:00',NULL,'FORFAIT',1.00,'OL',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('ONLINEACTE',495,1,'Famille pour le online dont la facturation dépend de la documentation',NULL,'TAG',NULL,'2008-10-20 17:08:05','GOLLIONB','2016-10-18 18:00:14','BESSONR','2010-01-18 00:00:00','2011-02-17 00:00:00','ACTE',NULL,'OA',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('PHONO',28,1,NULL,NULL,'4',NULL,'2011-04-29 18:00:16','MASSONR','2015-11-19 09:22:36','BOLJS','2011-04-29 00:00:00',NULL,NULL,11.00,'PH',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('RADIO',9223372036854775807,1,NULL,NULL,'seconde',NULL,'2006-11-17 00:00:00','BAUTISTAF','2012-10-16 10:31:22','TASSINB','2006-11-17 00:00:00',NULL,'FORFAIT',7.00,'RD',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('TOUS',8,0,NULL,NULL,'NA',NULL,'2010-10-19 18:00:19','MASSONR','2012-03-21 16:27:41','BAUTISTAF','2010-10-19 00:00:00',NULL,'FORFAIT',NULL,'TO',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('TV',9223372036854775807,1,NULL,'T','seconde',NULL,'2006-11-17 00:00:00','BAUTISTAF','2012-10-16 10:31:22','TASSINB','2006-11-17 00:00:00',NULL,'FORFAIT',22.00,'TV',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('UC',9223372036854775807,1,'Indicateur type diffusion audiovisuelle positionné sur Salle suite ULYSS-2310','T','TAG',NULL,'2006-11-17 00:00:00','BAUTISTAF','2016-02-17 11:16:49','BOLJS','2006-11-17 00:00:00',NULL,'FORFAIT',7.00,'UC',NULL);
INSERT INTO `SAREFTR_FAMILTYPUTIL` VALUES ('VIDEO',44,0,NULL,NULL,'seconde',NULL,'2012-07-17 14:43:04','MASSONR','2017-05-22 13:57:38','TASSINB','2000-01-01 00:00:00',NULL,NULL,2.00,'VD',NULL);


-- ---------------------------------------------------
-- ------ TABLE SAREFTR_TYPUTIL -------------------
-- ---------------------------------------------------
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('AUTREDG','DTSGEN',9223372036854775807,3,1,NULL,'.','2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('AUTREUC','UC',9223372036854775807,8,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('BAL','DTSGEN',0,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2016-10-18 18:00:15','BESSONR','2000-01-01 00:00:00','2000-01-02 00:00:00',90,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('BALCO','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2009-07-29 14:02:09','GOLLIONB','2017-01-18 19:02:27','BOLJS','2009-10-28 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('BALCONS','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2009-07-29 14:04:00','GOLLIONB','2017-08-21 15:44:04','LIETH','2009-10-28 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('BALLETDG','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('BALLETUC','UC',9223372036854775807,8,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CENTRALISE','PHONO',28,0,1,4,NULL,'2011-04-29 18:00:17','MASSONR','2017-05-16 15:33:16','FRIESP','2011-04-29 00:00:00',NULL,10,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CINEMA','CINEMA',28,1,1,NULL,NULL,'2011-10-12 18:00:17','MASSONR','2017-09-05 09:50:03','TASSINB','1900-01-01 00:00:00',NULL,80,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CINEPUB','CINEMA',4,0,0,NULL,NULL,'2011-10-12 18:00:17','MASSONR','2017-09-05 09:50:03','TASSINB','1900-01-01 00:00:00',NULL,80,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CIRQUEDG','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CIRQUEUC','UC',9223372036854775807,8,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CONCERT','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2016-10-18 18:00:15','BESSONR','2000-01-01 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CONCERTSYM','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2016-10-18 18:00:15','BESSONR','2000-01-01 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CONCERTVAR','DTSGEN',9223372036854775807,1,1,NULL,NULL,'2013-01-31 10:39:10','FRIESP','2016-09-06 16:28:39','TASSINB','2013-01-01 00:00:00','2013-01-02 00:00:00',50,NULL,1,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('COPRIVSON','COPIEPRIV',9223372036854775807,1,1,NULL,NULL,'2009-02-03 09:41:13','MASSONR','2016-10-18 18:00:15','BESSONR','2009-02-02 00:00:00','2011-10-15 00:00:00',30,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CPRIVAUDPL','COPIEPRIV',4,0,0,NULL,'Copie Privée Audiovisuel - Part Littéraire','2016-12-15 18:00:22','TASSINB','2017-02-09 15:28:16','ZYSMAND','1900-01-01 00:00:00',NULL,30,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CPRIVAUDV','COPIEPRIV',12,0,0,NULL,NULL,'2011-10-19 18:00:52','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,30,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CPRIVSONPH','COPIEPRIV',12,0,1,4,NULL,'2011-10-14 18:00:23','MASSONR','2017-02-09 15:28:16','ZYSMAND','1900-01-01 00:00:00',NULL,30,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('CPRIVSONRD','COPIEPRIV',12,0,0,NULL,NULL,'2011-10-14 18:00:23','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,30,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DG','DTSGEN',12,3,0,NULL,NULL,'2011-10-12 18:00:17','MASSONR','2017-09-05 09:50:03','TASSINB','1900-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DISCOFIXE','UC',9223372036854775807,8,1,1,NULL,'2005-08-30 11:55:15','ULYSS','2017-02-09 15:28:16','ZYSMAND','2005-01-01 00:00:00','2100-01-01 00:00:00',60,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DISCOPART','UC',4,8,1,NULL,'Calcul Rion Discothèques a la Part - Processus','2016-08-23 17:25:26','FRIESP','2016-09-28 16:29:49','TASSINB','2016-08-22 00:00:00',NULL,60,NULL,1,0,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DISCORETRO','UC',9223372036854775807,8,1,1,'Pour discothèques mobiles et rétro répartis à la Part','2017-01-16 16:04:14','TASSINB','2017-01-16 16:04:14','TASSINB','2017-01-01 00:00:00',NULL,60,NULL,NULL,1,1,'MN',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DIVERS','DTSGEN',4,3,0,NULL,NULL,'2012-11-26 18:10:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','1900-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOCUM','DOCUM',9223372036854775807,2,1,NULL,NULL,'2005-08-30 00:00:00','ULYSS','2015-11-19 09:22:37','BOLJS','2005-01-01 00:00:00','2100-01-01 00:00:00',90,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOWNMDBST','ONLINE',4,0,1,NULL,'DOWNMUSIC VOD DOUBLEUR SOUS-TITREUR','2014-07-28 14:19:43','LIETH','2017-02-09 15:28:16','ZYSMAND','2014-01-01 00:00:00',NULL,41,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOWNMUSIC','ONLINE',9223372036854775807,4,1,1,NULL,'2009-07-08 11:16:43','GOLLIONB','2016-10-18 18:00:15','BESSONR','2009-09-02 00:00:00',NULL,40,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOWNMUSICA','ONLINE',4,0,1,1,'Telechargement de musique dont la facturation dépend de la documentation','2008-10-20 17:13:58','GOLLIONB','2016-02-18 14:17:38','TASSINB','2010-01-18 00:00:00',NULL,40,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOWNMUSICB','ONLINE',4,1,0,NULL,NULL,'2011-07-05 11:29:42','MASSONR','2016-02-18 14:17:38','TASSINB','2000-01-01 00:00:00',NULL,40,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOWNMUSICD','ONLINE',4,1,1,NULL,NULL,'2013-02-14 18:13:22','LIETH','2016-02-18 14:17:38','TASSINB','2000-01-01 00:00:00',NULL,40,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOWNMUSICR','ONLINE',4,1,0,NULL,'Telechargement de music avec les Repertoires.','2013-07-15 14:13:48','MASSONR','2016-02-18 14:17:38','TASSINB','2013-01-01 00:00:00',NULL,40,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOWNMUSICV','ONLINE',4,0,1,NULL,NULL,'2014-01-15 09:26:02','MASSONR','2016-02-18 14:17:38','TASSINB','2014-01-14 00:00:00',NULL,41,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DOWN_MUSIC','ONLINE',9223372036854775807,12,0,1,NULL,'2005-10-24 17:21:20','ULYSS','2017-02-09 15:28:16','ZYSMAND','2005-01-01 00:00:00','2100-01-01 00:00:00',40,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('DROITREAL','TV',4,0,1,NULL,NULL,'2013-11-06 12:06:30','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,21,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('EDITEURS','DOCUM',9223372036854775807,2,1,NULL,NULL,'2005-08-30 11:55:15','ULYSS','2015-11-19 09:22:37','BOLJS','2005-01-01 00:00:00','2100-01-01 00:00:00',90,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('EGLISE','DTSGEN',12,3,0,NULL,'Code créé pour la RDO du MCP','2012-11-06 17:48:20','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('ENCOURG','FDSVAL',12,0,0,NULL,'Typutil créé pour la reprise de données MCP vers PENEF','2011-12-09 18:00:22','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('ETRAD','ETRANGER',4,0,1,NULL,'Homer AD sans oeuvre (FELIX)','2015-12-30 15:28:20','LIETH','2017-02-09 15:04:48','ELYACOUBIA','2015-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('ETRADIN','ETRANGER',4,0,1,NULL,'Homer AD sans oeuvre retour de droit entrant','2015-12-30 15:33:15','LIETH','2017-02-09 15:05:35','ELYACOUBIA','2015-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('ETRANGER','ETRANGER',12,0,0,NULL,NULL,'2011-10-12 11:17:18','MASSONR','2017-02-09 15:28:16','ZYSMAND','1900-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('ETRANGERAD','ETRANGER',4,0,0,2,'Répartition droit Etranger à l\'ayant droit','2016-07-07 14:39:11','LIETH','2017-02-16 18:00:13','ZYSMAND','2001-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('ETROEUV','ETRANGER',4,0,1,NULL,'Répartition oeuvre (HOMER)','2015-12-30 16:01:36','LIETH','2017-02-09 15:22:13','ELYACOUBIA','2015-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('ETROEUVIN','ETRANGER',4,0,1,NULL,'Etranger Retour de droit entrant avec oeuvre','2015-12-30 15:35:56','LIETH','2017-02-09 15:22:59','ELYACOUBIA','2015-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('EXCEDFRAIS','GESTSACEM',4,0,1,NULL,NULL,'2011-10-13 11:00:41','MASSONR','2013-12-31 11:13:09','MASSONR','1900-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD01','FDSVAL',9223372036854775807,0,0,NULL,'Fonds d\'encouragement à la 1ère exécution publique musique symphonique','2017-09-22 18:00:12','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD02','FDSVAL',9223372036854775807,0,0,NULL,'Fonds d\'incitation au nouvel enregistrement de la musique symphonique','2017-09-22 18:00:12','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD03','FDSVAL',9223372036854775807,0,0,NULL,'Fonds de valorisation de la musique symphonique enregistrée','2017-09-27 11:43:37','TASSINB','2017-09-27 11:43:37','TASSINB','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD04','FDSVAL',9223372036854775807,0,0,NULL,'Fonds de valorisation de la musique symphonique','2017-09-27 11:43:37','TASSINB','2017-09-27 11:43:37','TASSINB','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD05','FDSVAL',9223372036854775807,0,0,NULL,'Fonds d\'encouragement à la 1ere exécution publique musique JAZZ','2017-09-27 11:43:38','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD06','FDSVAL',9223372036854775807,0,0,NULL,'Fonds de valorisation improvisation JAZZ','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD07','FDSVAL',9223372036854775807,0,0,NULL,'Fonds d\'incitation au nouvel enregistrement de la musique de JAZZ','2017-09-27 11:43:38','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD09','FDSVAL',9223372036854775807,0,0,NULL,'Fond d\'encouragement de la musique vivante de variétés (SMAC)','2017-09-27 11:43:38','TASSINB','2017-09-27 15:07:19','ELYACOUBIA','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD10','FDSVAL',9223372036854775807,0,0,NULL,'Fonds de valorisation de la poésie enregistrée','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD11','FDSVAL',9223372036854775807,0,0,NULL,'Fonds de valorisation de la poésie','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD12','FDSVAL',9223372036854775807,0,0,NULL,'Fonds de valorisation de la musique Corse','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD13','FDSVAL',9223372036854775807,0,0,NULL,'Complément Harmonie et Fanfares','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','ELYACOUBIA','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('FD14','FDSVAL',9223372036854775807,0,0,NULL,'Valorisation Documentaire','2017-09-27 11:43:38','TASSINB','2017-09-27 11:43:38','TASSINB','2017-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('GRDETABL','DTSGEN',12,3,1,NULL,NULL,'2013-11-06 11:24:35','MASSONR','2017-08-21 14:01:31','LIETH','2000-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('GRDETOURNE','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('INDETERMIN','DTSGEN',4,3,1,NULL,NULL,'2012-06-20 11:31:30','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('MUSIQELECT','UC',9223372036854775807,8,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('MUSIQENREG','UC',9223372036854775807,8,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('MUSIQSCENE','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('OPO','PHONO',9223372036854775807,0,NULL,4,'uvre par uvre GELDA','2017-01-05 09:49:45','LIETH','2017-01-05 09:49:45','LIETH','2015-01-01 00:00:00',NULL,13,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('PERCEPTDIV','DTSGEN',4,3,0,NULL,NULL,'2011-10-13 11:00:41','MASSONR','2016-10-18 18:00:15','BESSONR','1900-01-01 00:00:00',NULL,90,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('PHONOFR','PHONO',44,0,0,NULL,NULL,'2011-10-12 18:00:17','MASSONR','2016-10-18 18:00:15','BESSONR','1900-01-01 00:00:00',NULL,10,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('PHONOFRAD','PHONO',4,0,0,4,'PHONO FRANCE à l\'ayant droit sans oeuvre','2016-02-04 18:23:46','LIETH','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('PLATFORMWB','ONLINE',9223372036854775807,4,1,4,NULL,'2013-08-28 11:58:02','MASSONR','2016-10-18 18:00:15','BESSONR','2000-01-01 00:00:00',NULL,45,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('PLTFORMWBY','ONLINE',4,0,1,NULL,'PLATFORM WEB MANUEL','2015-12-22 13:56:04','LIETH','2016-02-18 14:17:38','TASSINB','2000-01-01 00:00:00',NULL,45,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('PRIME','FDSVAL',4,0,0,NULL,NULL,'2011-12-09 11:40:55','MASSONR','2017-02-09 15:28:16','ZYSMAND','1900-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('PRODUCTEUR','DOCUM',9223372036854775807,2,1,NULL,NULL,'2005-08-30 11:55:15','ULYSS','2015-11-19 09:22:37','BOLJS','2005-01-01 00:00:00','2100-01-01 00:00:00',90,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOEI','RADIO',9223372036854775807,6,1,1,NULL,'2005-08-30 11:55:15','ULYSS','2017-02-09 15:28:16','ZYSMAND','2005-01-01 00:00:00','2100-01-01 00:00:00',20,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOEP','RADIO',9223372036854775807,6,1,2,NULL,'2005-10-17 12:30:38','ULYSS','2017-02-09 15:28:16','ZYSMAND','2005-01-01 00:00:00','2100-01-01 00:00:00',20,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOEPDR','RADIO',9223372036854775807,6,1,3,NULL,'2006-06-30 00:00:00','BAUTISTAF','2017-02-09 15:28:16','ZYSMAND','2006-06-30 00:00:00','2100-01-01 00:00:00',20,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOHAB','RADIO',4,1,0,3,NULL,'2014-09-15 16:19:47','FRIESP','2016-09-06 16:45:01','TASSINB','2012-09-01 00:00:00','2012-09-02 00:00:00',20,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOHRZ','RADIO',9223372036854775807,1,1,1,NULL,'2006-11-20 00:00:00','ULYSS','2016-10-18 18:00:15','BESSONR','2006-11-20 00:00:00','2015-01-01 00:00:00',20,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOPUB','RADIO',9223372036854775807,6,1,2,NULL,'2005-08-30 11:55:15','ULYSS','2017-02-09 15:28:16','ZYSMAND','2005-01-01 00:00:00','2100-01-01 00:00:00',20,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOPUBDR','RADIO',9223372036854775807,6,1,3,NULL,'2006-06-30 00:00:00','BAUTISTAF','2017-02-09 15:28:16','ZYSMAND','2006-06-30 00:00:00','2100-01-01 00:00:00',20,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOUC','RADIO',9223372036854775807,1,1,1,NULL,'2005-08-30 11:55:15','VALENZAA','2013-12-31 11:13:09','MASSONR','2005-01-01 00:00:00','2007-03-22 00:00:00',20,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADIOWEB','RADIO',9223372036854775807,1,1,1,NULL,'2005-08-30 11:55:15','VALENZAA','2016-10-18 18:00:15','BESSONR','2005-01-01 00:00:00','2007-03-22 00:00:00',44,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RADLOC','RADIO',9223372036854775807,1,1,1,NULL,'2005-10-17 12:31:50','VALENZAA','2017-02-09 15:28:16','ZYSMAND','2005-01-01 00:00:00','2007-03-22 00:00:00',20,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RDCAB','RADIO',12,0,0,NULL,NULL,'2011-10-11 18:00:45','MASSONR','2017-08-21 14:00:03','LIETH','1900-01-01 00:00:00',NULL,20,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RDHRZ','RADIO',9223372036854775807,6,1,1,NULL,'2005-08-30 11:55:15','ULYSS','2017-02-09 15:28:16','ZYSMAND','2005-01-01 00:00:00','2100-01-01 00:00:00',20,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('REDISTPH','PHONO',4,0,0,NULL,NULL,'2011-12-09 18:00:23','MASSONR','2017-02-09 15:28:16','ZYSMAND','1900-01-01 00:00:00',NULL,10,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('REPADOL','ONLINE',12,0,0,NULL,NULL,'2011-02-09 09:44:03','MASSONR','2017-05-22 10:13:40','TASSINB','2011-01-01 00:00:00',NULL,43,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('REPADTV','TV',4,0,1,NULL,NULL,'2011-07-11 18:00:20','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,21,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('REPRICABLE','TV',12,0,0,NULL,NULL,'2011-11-02 09:29:01','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,21,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('REPRISONFR','ONLINE',4,1,NULL,NULL,'REPRISE COMPTE ATTENTE ON-LINE FRANCE','2016-04-13 09:39:42','LIETH','2016-05-18 18:00:12','LIETH','2011-01-01 00:00:00',NULL,43,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('REPRODDIV','PHONO',9223372036854775807,5,1,4,NULL,'2012-02-16 16:37:48','FRIESP','2017-02-09 15:28:16','ZYSMAND','2012-02-13 00:00:00',NULL,10,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RINGTONES','ONLINE',9223372036854775807,4,1,1,NULL,'2005-10-24 17:21:20','ULYSS','2016-10-18 18:00:15','BESSONR','2005-01-01 00:00:00','2100-01-01 00:00:00',42,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RINGTONESA','ONLINE',4,0,0,NULL,'Sonneries Téléphoniques à l acte ','2008-10-20 17:15:54','GOLLIONB','2016-02-18 14:17:38','TASSINB','2000-01-01 00:00:00',NULL,42,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RINGTONESB','ONLINE',4,0,0,NULL,'Sonnerie télephonique à l\'abonnement','2013-07-26 09:31:17','LIETH','2016-02-18 14:17:38','TASSINB','2010-01-04 00:00:00',NULL,42,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('RINGTONESR','ONLINE',4,0,0,NULL,'Sonneries téléphones à Répertoire','2013-10-30 08:42:51','MASSONR','2016-02-18 14:17:38','TASSINB','2013-01-01 00:00:00',NULL,42,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SEANCAUDIO','UC',9223372036854775807,8,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SEANOCCAS','DTSGEN',4,3,1,NULL,NULL,'2012-09-11 12:51:34','MASSONR','2016-10-18 18:00:15','BESSONR','1900-01-01 00:00:00','1900-01-02 00:00:00',50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SONLUMIERE','UC',9223372036854775807,8,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,90,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SONOANT','UC',9223372036854775807,0,0,NULL,'Catalogue Musique de Sonorisation Antilles','2017-09-29 07:55:36','TASSINB','2017-09-29 07:55:36','TASSINB','2017-01-01 00:00:00',NULL,70,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SONOFRA','UC',9223372036854775807,0,0,NULL,'Catalogue Musique de Sonorisation','2017-09-29 07:55:36','TASSINB','2017-09-29 07:55:36','TASSINB','2017-01-01 00:00:00',NULL,70,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SONOPARTS','UC',9223372036854775807,0,0,1,'Sonorisation parts avec taxation','2016-07-06 15:36:49','LIETH','2017-04-25 09:37:11','TASSINB','2015-01-01 00:00:00',NULL,70,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SONORIS','UC',9223372036854775807,8,1,1,NULL,'2008-02-21 19:35:23','MASSONR','2017-04-25 09:37:11','TASSINB','2008-02-21 00:00:00',NULL,70,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SONOSECOND','UC',9223372036854775807,0,0,1,'Sonorisation en second','2016-06-30 13:52:08','LIETH','2017-04-25 09:37:11','TASSINB','2015-01-01 00:00:00',NULL,70,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('SPEHUMOUR','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:16','ZYSMAND','2000-01-01 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('STREAMDBST','ONLINE',4,0,1,NULL,'Streaming vidéo doubleur soustitreur','2014-07-28 15:33:48','LIETH','2016-02-18 14:17:38','TASSINB','2014-01-01 00:00:00',NULL,46,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('STREAMING','ONLINE',9223372036854775807,4,1,1,NULL,'2008-07-23 18:00:17','MASSONR','2016-10-18 18:00:15','BESSONR','2008-07-23 00:00:00',NULL,45,NULL,NULL,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('STREAMINGA','ONLINE',495,1,1,1,'Streaming dont la facturation dépend de la documentation','2010-07-02 13:37:33','FRIESP','2016-02-18 14:17:37','TASSINB','2010-07-01 00:00:00',NULL,45,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('STREAMINGB','ONLINE',4,0,0,NULL,NULL,'2012-07-26 11:22:25','FRIESP','2016-02-18 14:17:37','TASSINB','2000-01-01 00:00:00',NULL,45,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('STREAMINGR','ONLINE',4,0,0,NULL,'Streaming à Répertoire','2013-10-30 08:44:15','MASSONR','2016-02-18 14:17:37','TASSINB','2013-01-01 00:00:00',NULL,45,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('STREAMINGV','ONLINE',4,0,1,NULL,NULL,'2014-01-14 18:43:13','MASSONR','2016-02-18 14:17:37','TASSINB','2014-01-14 00:00:00',NULL,46,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('STREAMINGY','ONLINE',4,0,1,NULL,'Streaming à l\'abonnement youtube Réalisateur','2013-07-24 09:09:42','LIETH','2016-02-18 14:17:37','TASSINB','2010-01-04 00:00:00',NULL,45,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TEMPOL','ONLINE',4,0,1,NULL,NULL,'2010-05-12 18:00:24','MASSONR','2016-02-18 14:17:37','TASSINB','2010-05-12 00:00:00',NULL,40,NULL,NULL,1,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TOURBLTDG','DTSGEN',9223372036854775807,3,1,1,NULL,'2016-06-28 16:51:58','FRIESP','2017-02-09 15:28:17','ZYSMAND','2016-06-27 00:00:00',NULL,50,NULL,1,0,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TOURBLTUC','UC',9223372036854775807,8,1,1,NULL,'2016-06-28 16:51:58','FRIESP','2017-02-09 15:28:17','ZYSMAND','2016-06-27 00:00:00',NULL,50,NULL,1,0,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TOURCRQDG','DTSGEN',9223372036854775807,3,1,1,NULL,'2016-06-28 16:51:58','FRIESP','2017-02-09 15:28:17','ZYSMAND','2016-06-27 00:00:00',NULL,50,NULL,1,0,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TOURCRQUC','UC',9223372036854775807,8,1,1,NULL,'2016-06-28 16:51:58','FRIESP','2017-02-09 15:28:17','ZYSMAND','2016-06-27 00:00:00',NULL,50,NULL,1,0,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TOURMUSSCN','DTSGEN',9223372036854775807,3,1,1,NULL,'2014-06-16 11:44:32','FRIESP','2017-02-09 15:28:17','ZYSMAND','2014-07-04 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TOURNEE','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:17','ZYSMAND','2000-01-01 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TOURSYMPHO','DTSGEN',9223372036854775807,3,1,NULL,NULL,'2014-03-11 11:24:44','MASSONR','2017-02-09 15:28:17','ZYSMAND','2000-01-01 00:00:00',NULL,50,NULL,1,1,1,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TOUS',NULL,9,0,NULL,NULL,NULL,'2010-09-29 18:00:21','MASSONR','2013-12-31 11:13:09','MASSONR','2010-09-29 00:00:00',NULL,90,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TVABO','TV',12,0,0,NULL,NULL,'2011-10-11 18:00:45','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,21,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TVADSL','TV',9223372036854775807,1,1,1,NULL,'2005-08-30 11:55:15','VALENZAA','2016-10-18 18:00:15','BESSONR','2005-01-01 00:00:00','2007-03-22 00:00:00',21,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TVCAB','TV',9223372036854775807,7,1,1,NULL,'2005-08-30 11:55:15','ULYSS','2017-02-09 15:28:17','ZYSMAND','2005-01-01 00:00:00','2100-01-01 00:00:00',21,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TVHRZ','TV',9223372036854775807,7,1,1,NULL,'2005-08-30 11:55:15','ULYSS','2017-02-09 15:28:17','ZYSMAND','2005-01-01 00:00:00','2100-01-01 00:00:00',21,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TVPUB','TV',9223372036854775807,1,1,2,NULL,'2005-08-30 11:55:15','VALENZAA','2016-10-18 18:00:15','BESSONR','2005-01-01 00:00:00','2007-03-22 00:00:00',21,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TVTNT','TV',9223372036854775807,1,1,1,NULL,'2005-08-30 11:55:15','VALENZAA','2016-10-18 18:00:15','BESSONR','2005-01-01 00:00:00','2007-03-22 00:00:00',21,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('TVWEB','TV',9223372036854775807,1,1,1,NULL,'2005-08-30 11:55:15','VALENZAA','2016-10-18 18:00:15','BESSONR','2005-01-01 00:00:00','2007-03-22 00:00:00',47,NULL,NULL,0,NULL,NULL,NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('UC','UC',12,8,1,1,NULL,'2005-08-30 11:55:15','ULYSS','2017-04-25 09:37:11','TASSINB','2005-01-01 00:00:00','2100-01-01 00:00:00',70,NULL,1,1,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('VALORIS','FDSVAL',4,0,0,NULL,NULL,'2011-12-09 18:00:22','MASSONR','2017-02-09 15:28:17','ZYSMAND','1900-01-01 00:00:00',NULL,50,NULL,NULL,1,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('VIDEOETR','ETRANGER',12,0,0,NULL,NULL,'2013-07-09 17:21:50','MASSONR','2017-05-22 10:13:40','TASSINB','2000-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('VIDEOSESAM','VIDEO',12,0,0,NULL,'Code créé pour la RDO MCP','2012-11-06 15:21:45','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,11,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('VIDEOSTD','VIDEO',12,0,0,NULL,'Code créé pour la RDO MCP','2012-11-06 11:33:07','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,11,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('VIDEOSTDAD','VIDEO',4,0,0,NULL,'VIDEOSTD à l\'Ayant droit sans oeuvre','2016-01-27 17:48:09','LIETH','2017-02-09 15:28:17','ZYSMAND','2012-01-01 00:00:00',NULL,90,NULL,NULL,0,NULL,'MNF',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('VIDINSTIT','VIDEO',12,0,1,NULL,'Code créé pour la RDO MCP','2012-11-06 17:48:20','MASSONR','2017-05-22 10:13:40','TASSINB','1900-01-01 00:00:00',NULL,11,NULL,NULL,0,NULL,'MB',NULL);
INSERT INTO `SAREFTR_TYPUTIL` VALUES ('WEBRADIO','ONLINE',9223372036854775807,4,1,1,NULL,'2015-11-17 18:00:10','MASSONR','2017-02-09 15:28:17','ZYSMAND','2011-01-01 00:00:00',NULL,44,NULL,NULL,1,NULL,'MB',NULL);


-- ---------------------------------------------------
-- ------ TABLE PRIAM_FICHIER -------------------
-- ---------------------------------------------------

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 01', 'COPIEPRIV', 'COPRIVSON', '2017-02-04 17:15:14', NULL, 3000, 'EN_COURS', 1);

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 02', 'COPIEPRIV', 'COPRIVSON', '2017-02-03 17:15:14', NULL, 9500, 'EN_COURS', 1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 03', 'COPIEPRIV', 'COPRIVSON', '2017-02-01 17:15:14', NULL, 6500, 'EN_COURS', 1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 04', 'COPIEPRIV', 'COPRIVSON', '2017-04-01 17:15:14', NULL, 1478, 'EN_COURS', 1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 05', 'COPIEPRIV', 'COPRIVSON', '2017-05-01 17:10:14', NULL, 7451, 'EN_COURS', 1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 06', 'COPIEPRIV', 'COPRIVSON', '2017-05-02 18:15:14', NULL, 15000, 'EN_COURS', 1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 08', 'COPIEPRIV', 'COPRIVSON', '2017-02-01 17:15:14', NULL, 6500, 'EN_COURS', 1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 09', 'COPIEPRIV', 'COPRIVSON', '2017-04-01 17:15:14', '2017-04-01 22:10:11', 22000, 'CHARGEMENT_OK',1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 11', 'COPIEPRIV', 'COPRIVSON', '2017-05-01 17:10:14', '2017-05-02 01:10:00', 45789, 'CHARGEMENT_OK', 1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 12', 'COPIEPRIV', 'COPRIVSON', '2017-05-02 18:15:14', '2017-05-01 18:50:04', 15000, 'CHARGEMENT_KO', 1);


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 13', 'COPIEPRIV', 'COPRIVSON', '2017-05-04 18:15:14', '2017-05-04 22:57:04', 15000, 'CHARGEMENT_KO', 1);

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 15', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', 1);

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, SOURCE_AUTO)
VALUES ('Fichier 17', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'ABANDONNE', 1);

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
Insert into SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('640','2017-04-03','2018-07-05','4','0','Répartition Juillet 2017','2017-04-25','ROUCOULESG','2017-04-25','ROUCOULESG');
INSERT INTO SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) VALUES ('645', '2018-07-02', '2019-01-04', '9223372036854775807', '0', 'Repartition Janvier 2018', '2018-06-01', 'LIETH', '2018-07-02', 'LIETH');
INSERT INTO SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) VALUES ('646', '2019-02-26', '2019-04-05', '9223372036854775807',	'0','Répartition Avril 2019',	'2018-12-06', 'TASSINB', '2018-12-06', 'TASSINB');
INSERT INTO SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) VALUES ('647', '2019-05-28', '2019-07-05', '9223372036854775807',	'0',	'Répartition Juillet 2019',	'2018-12-06', 'TASSINB', '2018-12-06', 'TASSINB');
INSERT INTO SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) VALUES ('648',	'2019-08-27', '2019-10-04', '9223372036854775807', '0',	'Répartition Octobre 2019',	'2018-12-06', 'TASSINB', '2018-12-06',	'TASSINB');
INSERT INTO SAREFTR_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) VALUES ('649',	'2019-11-26', '2020-01-06', '9223372036854775807', '0',	'Répartition Janvier 2020',	'2018-12-06', 'TASSINB', '2018-12-06', 'TASSINB');


INSERT INTO `SAREFTJ_LIBUTIL` VALUES ('FR','0002','RIR','RIR','2009-07-08','GOLLIONB','2015-05-19','FRIESP',NULL);
INSERT INTO `SAREFTJ_LIBUTIL` VALUES ('FR','LU1','RTL',NULL,'2005-08-30','ULYSS','2013-05-21','ULYSS',NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('170001', 'Programme 01', 619, 'COPIEPRIV','CPRIVSONPH', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

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
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier 125', 'COPIEPRIV', 'CPRIVSONPH', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', '170001');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier 126', 'COPIEPRIV', 'CPRIVSONPH', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', '170001');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier 127', 'COPIEPRIV', 'CPRIVSONPH', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', '170001');
INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier 128', 'COPIEPRIV', 'CPRIVSONPH', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', '170001');

/*
INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeCisac, cdeTer, rionEffet, cdeFamilTypUtil, cdeModFac, numProg, keyLigPenel, cdeUtil, cdeTypUtil, cdeTypProg, cdeCompl, libProg, compLibProg, datDbtProg, datFinProg, hrDbt, hrFin, cdeGreDif, cdeModDif, cdeTypIde12, ide12, datDif, hrDif, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER)
VALUES ('58', 250, 639, 'COPIEPRIV', '', null, null, 'LU1', 'CPRIVAUDPL', 'PRINC', 'SANS', 'COPIEPRIV PRINC 639 250', '', '2017-05-24 22:57:04', '2017-05-24 22:57:04', null, null, '', '', 'COCV', 6829877211, null, null, null, 10, 71.52, '', '', null, '', '', null, 'MB', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125');
INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeCisac, cdeTer, rionEffet, cdeFamilTypUtil, cdeModFac, numProg, keyLigPenel, cdeUtil, cdeTypUtil, cdeTypProg, cdeCompl, libProg, compLibProg, datDbtProg, datFinProg, hrDbt, hrFin, cdeGreDif, cdeModDif, cdeTypIde12, ide12, datDif, hrDif, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER)
VALUES ('58', 250, 640, 'COPIEPRIV', '', null, null, 'RT2', 'CPRIVAUDPL', 'PRINC', 'SANS', 'COPIEPRIV PRINC 639 250', '', '2017-05-24 22:57:04', '2017-05-24 22:57:04', null, null, '', '', 'COCV', 8028354411, null, null, null, 2, 1.26, '', '', null, '', '', null, 'MB', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125');
INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeCisac, cdeTer, rionEffet, cdeFamilTypUtil, cdeModFac, numProg, keyLigPenel, cdeUtil, cdeTypUtil, cdeTypProg, cdeCompl, libProg, compLibProg, datDbtProg, datFinProg, hrDbt, hrFin, cdeGreDif, cdeModDif, cdeTypIde12, ide12, datDif, hrDif, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER)
VALUES ('58', 250, 641, 'COPIEPRIV', '', null, null, 'RIR', 'CPRIVAUDPL', 'PRINC', 'SANS', 'COPIEPRIV PRINC 639 250', '', '2017-05-24 22:57:04', '2017-05-24 22:57:04', null, null, '', '', 'COCV', 8028354411, null, null, null, 8, 1.74, '', '', null, '', '', null, 'MB', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125');
*/
INSERT INTO PRIAM_LIGNE_PROGRAMME_CP (cdeCisac, cdeFamilTypUtil, numProg, cdeUtil, cdeTypUtil, cdeGreDif, cdeModDif, cdeTypIde12, ide12, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, cdeGreIde12Cmplx, cdeGreIde12, titreOriCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreAltOeuvPereCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri,ID_FICHIER)
VALUES ('58', 'COPIEPRIV', '170001', '0002', 'CPRIVAUDPL', '', '', 'COCV', 6829877211, null,10, 71.52, '', '', null, '', '', null, 'MB', 19, null, null, null, null, null, 'Test Titre', null, null, null, null, null, null, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125'));

INSERT INTO PRIAM_LIGNE_PROGRAMME_CP (cdeCisac, cdeFamilTypUtil, numProg, cdeUtil, cdeTypUtil, cdeGreDif, cdeModDif, cdeTypIde12, ide12, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, cdeGreIde12Cmplx, cdeGreIde12, titreOriCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreAltOeuvPereCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri,ID_FICHIER)
VALUES ('58', 'COPIEPRIV', null, 'RT2', 'CPRIVAUDPL', '', '', 'COCV', 8028354411, null, 2,1.26, '', '', null, '', '', null, 'MB', 19, null, null, null, null, null, null, null, null, null, null, null, null, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125'));

INSERT INTO PRIAM_LIGNE_PROGRAMME_CP (cdeCisac, cdeFamilTypUtil, numProg, cdeUtil, cdeTypUtil, cdeGreDif, cdeModDif, cdeTypIde12, ide12, durDif, nbrDif, mt, ctna, paramCoefHor, durDifCtna, cdeLng, indDoubSsTit, tax, typMt, cdeGreIde12Cmplx, cdeGreIde12, titreOriCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreAltOeuvPereCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri,ID_FICHIER)
VALUES ('58', 'COPIEPRIV', null, 'RIR', 'CPRIVAUDPL', '', '', 'COCV', 8028354411,null, 8,1.74, '', '', null, '', '', null, 'MB', 19, null, null, null, null, null, null, null, null, null, null, null, null, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 125'));


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



-- USE priam_app;

INSERT INTO `PRIAM_ROLE` (`ID`, `EXTERNAL_ID`, `ROLE`) VALUES (4,'Gest_CP','Gest_CP');
INSERT INTO `PRIAM_ROLE` (`ID`, `EXTERNAL_ID`, `ROLE`) VALUES (5,'Gest_CMS','Gest_CMS');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'CRTPRG','Creation de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'CRTPRG','Creation de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'READPRG','Acces ecran programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'READPRG','Acces ecran programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'MDYPRG','Modification de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'MDYPRG','Modification de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'ABDPRG','Abandon de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'ABDPRG','Abandon de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'MSEREP','Mise en repartition');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'MSEREP','Mise en repartition');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'RECAFC','Enregistrement affectation');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'RECAFC','Enregistrement affectation');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'EDTAFC','Edition affectation');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'EDTAFC','Edition affectation');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'EDTSEL','Edition selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'EDTSEL','Edition selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'INVSEL','Invalider selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'INVSEL','Invalider selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'CLDSEL','Annuler selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'CLDSEL','Annuler selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'VLDSEL','Valider selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'VLDSEL','Valider selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'ABDCHGT','Abandon de fichier');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'ABDCHGT','Abandon de fichier');

DROP TABLE IF EXISTS `PRIAM_ELIGIBILITE_STATUT_PROGRAMME`;
CREATE TABLE `PRIAM_ELIGIBILITE_STATUT_PROGRAMME` (
  `CODE` varchar(50) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `PRIAM_ELIGIBILITE_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('EN_ATTENTE_ELIGIBILITE',' en attente eligibilité');
INSERT INTO `PRIAM_ELIGIBILITE_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('EN_COURS_ELIGIBILITE','en cours eligibilité');
INSERT INTO `PRIAM_ELIGIBILITE_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('FIN_ELIGIBILITE','fin traitement eligibilité');


ALTER TABLE PRIAM_PROGRAMME
  ADD `STATUT_ELIGIBILITE` varchar(25) DEFAULT NULL COMMENT 'status d eligibilite';


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
    `pr`.`DATE_REPARTITION`                                                AS `DATE_REPARTITION`,
    `pr`.`STATUT_ELIGIBILITE`                                              AS `STATUT_ELIGIBILITE`,
    `pr`.`TYPE_DROIT`                                                      AS `TYPE_DROIT`
  FROM (`priam_app`.`PRIAM_PROGRAMME` `pr` LEFT JOIN `priam_app`.`PRIAM_FICHIER_FELIX` `ff`
      ON ((`ff`.`NUMPROG` = `pr`.`NUMPROG`)))
  GROUP BY `pr`.`NUMPROG`;


DROP TABLE IF EXISTS PRIAM_TRAITEMENT_ELIGIBILITE_CMS;
CREATE TABLE PRIAM_TRAITEMENT_ELIGIBILITE_CMS (
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  DATE_DEBUT_TMT DATETIME DEFAULT NULL,
  DATE_FIN_TMT DATETIME DEFAULT NULL,
  NUMPROG VARCHAR(10) DEFAULT NULL,
  STATUT_ELIGIBILITE VARCHAR(50) DEFAULT NULL,
  NB_OEUVRES_CATALOGUE BIGINT DEFAULT NULL,
  NB_OEUVRES_RETENUES BIGINT DEFAULT NULL,
  NB_OEUVRES_EXTRACT BIGINT DEFAULT NULL,
  SOMME_POINTS DOUBLE DEFAULT NULL,

  PRIMARY KEY (ID),


  CONSTRAINT `FK_TMT_NUMPROG` FOREIGN KEY (`NUMPROG`) REFERENCES `PRIAM_PROGRAMME`(`NUMPROG`),
  CONSTRAINT `FK_STATUT_ELIGIBILITE` FOREIGN KEY (`STATUT_ELIGIBILITE`) REFERENCES `PRIAM_ELIGIBILITE_STATUT_PROGRAMME`(`CODE`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `PRIAM_FICHIER_OCTAV`;
CREATE TABLE `PRIAM_FICHIER_OCTAV` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  `NOM` varchar(255) DEFAULT NULL COMMENT 'Nom du fichier',
  `CDEFAMILTYPUTIL` varchar(10) DEFAULT NULL COMMENT 'Famille',
  `CDETYPUTIL` varchar(10) DEFAULT NULL COMMENT 'Type utilisation',
  `DATE_DEBUT_CHGT` datetime DEFAULT NULL COMMENT 'Dete de Debut de chargement',
  `DATE_FIN_CHGT` datetime DEFAULT NULL COMMENT 'Dete de Fin de chargement',
  `NB_LIGNES` bigint(20) DEFAULT NULL COMMENT 'Nombre de lignes dans le fichier',
  `STATUT_CODE` varchar(255) DEFAULT NULL COMMENT 'Statut primut du fichier',
  `NUMPROG` varchar(8) DEFAULT NULL,
  TYPE_FICHIER VARCHAR(5) DEFAULT NULL,

  PRIMARY KEY (`ID`),
  KEY `FK_FAMILLE_2` (`CDEFAMILTYPUTIL`),
  KEY `FK_TYPE_UTIL_2` (`CDETYPUTIL`),
  KEY `FK_STATUT_CODE_2` (`STATUT_CODE`),
  KEY `PRIAM_FICHIER_OCTAV__index` (`ID`),
  KEY `FK_NUMPROG_2` (`NUMPROG`),
  CONSTRAINT `FK_FAMILLE_2` FOREIGN KEY (`CDEFAMILTYPUTIL`) REFERENCES `SAREFTR_FAMILTYPUTIL` (`CDEFAMILTYPUTIL`),
  CONSTRAINT `FK_TYPE_UTIL_2` FOREIGN KEY (`NUMPROG`) REFERENCES `PRIAM_PROGRAMME` (`NUMPROG`),
  CONSTRAINT `FK_STATUT_CODE_2` FOREIGN KEY (`STATUT_CODE`) REFERENCES `PRIAM_STATUT` (`CODE`),
  CONSTRAINT `FK_NUMPROG_2` FOREIGN KEY (`CDETYPUTIL`) REFERENCES `SAREFTR_TYPUTIL` (`CDETYPUTIL`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_CMS_COPY`;
CREATE TABLE `PRIAM_LIGNE_PROGRAMME_CMS_COPY` (
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
  KEY `FK_ID_FICHIER_CMS_COPY` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL_COPY` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_CMS_COPY` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `PRIAM_FICHIER_OCTAV_LOG`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_OCTAV_LOG`;
CREATE TABLE `PRIAM_FICHIER_OCTAV_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_FICHIER` bigint(20) DEFAULT NULL,
  `LOG` varchar(1024) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_FICHIER_OCTAV_FK` (`ID_FICHIER`),
  CONSTRAINT `ID_FICHIER_OCTAV_FK` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER_OCTAV` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_ETAT_OEUVRE;
CREATE TABLE PRIAM_ETAT_OEUVRE (
  CDE_ETAT VARCHAR(50) NOT NULL,
  LIB_ETAT VARCHAR(50) DEFAULT NULL,
  PRIMARY KEY (CDE_ETAT)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE PRIAM_LIGNE_PROGRAMME_CMS
  ADD CDE_ETAT varchar(50) DEFAULT NULL COMMENT 'Etat d une oeuvre';

ALTER TABLE PRIAM_LIGNE_PROGRAMME_CMS
  ADD CONSTRAINT FK_CDE_ETAT
FOREIGN KEY (CDE_ETAT) REFERENCES PRIAM_ETAT_OEUVRE(CDE_ETAT);

INSERT INTO PRIAM_ETAT_OEUVRE (CDE_ETAT, LIB_ETAT) VALUES ('AUTOMATIQUE', 'Automatique');
INSERT INTO PRIAM_ETAT_OEUVRE (CDE_ETAT, LIB_ETAT) VALUES ('MANUEL', 'Manuel');
INSERT INTO PRIAM_ETAT_OEUVRE (CDE_ETAT, LIB_ETAT) VALUES ('CORRIGE', 'Corrigé');

ALTER TABLE PRIAM_LIGNE_PROGRAMME_CP
  ADD `durDifEdit` int(11) DEFAULT NULL COMMENT  'Colonne qui sert pour le tableau editable dans ecran selection';

ALTER TABLE PRIAM_LIGNE_PROGRAMME_CP
  ADD `nbrDifEdit` int(11) DEFAULT NULL COMMENT  'Colonne qui sert pour le tableau editable dans ecran selection';


ALTER TABLE PRIAM_LIGNE_PROGRAMME_CMS
  ADD `mtEdit` double DEFAULT NULL COMMENT  'Colonne qui sert pour le tableau editable dans ecran selection';

ALTER TABLE PRIAM_LIGNE_PROGRAMME_CMS
  ADD `nbrDifEdit` int(11) DEFAULT NULL COMMENT  'Colonne qui sert pour le tableau editable dans ecran selection';


DROP TABLE IF EXISTS PRIAM_JOURNAL_EVENEMENT;
CREATE TABLE PRIAM_JOURNAL_EVENEMENT (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `numprog` varchar(8) NOT NULL,
  `evenement` varchar(100) DEFAULT NULL,
  `ide12` bigint(20) DEFAULT NULL,
  `date` datetime DEFAULT CURRENT_TIMESTAMP,
  `utilisateur` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_SITUATION_AVANT;
CREATE TABLE PRIAM_SITUATION_AVANT (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SITUATION` varchar(455) DEFAULT NULL,
  `ID_EVENEMENT` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_SITUATION_APRES;
CREATE TABLE PRIAM_SITUATION_APRES (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `SITUATION` varchar(455) DEFAULT NULL,
  `ID_EVENEMENT` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE PRIAM_SITUATION_AVANT
  ADD CONSTRAINT FK_JOURNAL_EVENEMENT
FOREIGN KEY (ID_EVENEMENT) REFERENCES PRIAM_JOURNAL_EVENEMENT(ID);

ALTER TABLE PRIAM_SITUATION_APRES
  ADD CONSTRAINT FK_JOURNAL_EVENEMENT2
FOREIGN KEY (ID_EVENEMENT) REFERENCES PRIAM_JOURNAL_EVENEMENT(ID);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('180001', 'Programme 180001', 619, 'UC','SONOFRA', 'OEUVRE', CURDATE(), 'CREE', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('180002', 'Programme 180002', 619, 'UC','SONOANT', 'OEUVRE', CURDATE(), 'CREE', NULL);

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier_FRA01', 'UC', 'SONOFRA', '2018-01-22 10:33:18', '2018-01-22 10:33:19', 5, 'CHARGEMENT_OK', NULL);

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier_ANT01', 'UC', 'SONOANT', '2018-01-22 10:33:18', '2018-01-22 10:33:19', 5, 'CHARGEMENT_OK', NULL);

INSERT INTO PRIAM_LIGNE_PROGRAMME_CMS (numProg, cdeTypUtil, ide12, nbrDif, ID_FICHIER, titreOeuvre, roleParticipant1, nomParticipant1, ajout)
VALUES ('180001', 'SONOFRA', 2000163011, 30, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier_FRA01'), 'Titre1', 'CA', 'Participant1', 'AUTOMATIQUE');

INSERT INTO PRIAM_LIGNE_PROGRAMME_CMS (numProg, cdeTypUtil, ide12, nbrDif, ID_FICHIER, titreOeuvre, roleParticipant1, nomParticipant1, ajout)
VALUES ('180002', 'SONOANT', 2002037711, 30, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier_ANT01'), 'Titre2', 'CA', 'Participant2', 'AUTOMATIQUE');

INSERT INTO PRIAM_FICHIER_FELIX(NUMPROG, FILENAME, DATE_CREATION, STATUT)
    VALUES ('170001', 'TEST-FF-0001.csv', '2018-03-15 10:33:18', 'EN_COURS');

INSERT INTO PRIAM_FICHIER_FELIX(NUMPROG, FILENAME, DATE_CREATION, STATUT)
  VALUES ('180001', 'TEST-FF-0001.csv', '2018-03-15 10:33:18', 'EN_COURS');

-- ALTER TABLE PRIAM_FICHIER
  -- ADD `version_num` int(11) NOT NULL;

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('180090', 'Programme 180090', 619, 'COPIEPRIV','CPRIVSONPH', 'OEUVRE', CURDATE(), 'CREE', NULL);




DROP TABLE IF EXISTS PRIAM_CATCMS_RDO;
CREATE TABLE PRIAM_CATCMS_RDO (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  TYPE_CMS VARCHAR(3) COMMENT 'FR poour France et ANF pour Antilles',
  IDE12 bigint(20) DEFAULT NULL COMMENT 'Code oeuvre RDO',
  TITRE VARCHAR(255) COMMENT 'titre oeuvre RDO',
  TYP_UTIL_GEN VARCHAR(255) COMMENT 'type utilisation RDO',
  DATE_CATAL DATE DEFAULT NULL COMMENT 'date catalogue RDO',
  ROLE VARCHAR(255) COMMENT 'role RDO',
  PARTICIPANT VARCHAR(255) COMMENT 'participant RDO',
  POURCENTAGE_DP TINYINT(4) COMMENT 'pourcentage DP',
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS PRIAM_CATCMS_STATUT;
CREATE TABLE PRIAM_CATCMS_STATUT (
  CODE varchar(255) NOT NULL DEFAULT 'EN_COURS',
  LIBELLE varchar(255) DEFAULT NULL,
  PRIMARY KEY (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_FICHIER;
CREATE TABLE PRIAM_CATCMS_FICHIER (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  NOM varchar(255) DEFAULT NULL COMMENT 'Nom du fichier',
  DATE_DEBUT_CHGT datetime DEFAULT NULL COMMENT 'Dete de Debut de chargement',
  DATE_FIN_CHGT datetime DEFAULT NULL COMMENT 'Dete de Fin de chargement',
  NB_LIGNES bigint(20) DEFAULT NULL COMMENT 'Nombre de lignes dans le fichier',
  STATUT_CODE varchar(255) DEFAULT NULL COMMENT 'Statut primut du fichier',
  TYPE_FICHIER varchar(25) DEFAULT NULL COMMENT 'type de fichier FR ou ANT pour differencier l envoi',
  PRIMARY KEY (ID),
  KEY FK_STATUT_CATCMS_CODE (STATUT_CODE),
  CONSTRAINT FK_STATUT_CATCMS_CODE FOREIGN KEY (STATUT_CODE) REFERENCES PRIAM_CATCMS_STATUT (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_FICHIER_LOG;
CREATE TABLE PRIAM_CATCMS_FICHIER_LOG (
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  ID_FICHIER bigint(20) DEFAULT NULL,
  LOG varchar(1024) DEFAULT NULL,
  DATE datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY ID_CATCMS_FICHIER_FK (ID_FICHIER),
  CONSTRAINT ID_CATCMS_FICHIER_FK FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_CATCMS_FICHIER (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_JOURNAL;
CREATE TABLE PRIAM_CATCMS_JOURNAL (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  EVENEMENT varchar(100) DEFAULT NULL COMMENT 'intitule evenement',
  IDE12 bigint(20) DEFAULT NULL COMMENT 'ide12',
  DATE datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'date evenement',
  UTILISATEUR varchar(255) DEFAULT NULL COMMENT 'id utilisateur',
  TYPE_CMS varchar(255) DEFAULT NULL COMMENT 'FR ou ANF',
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Ajout manuel oeuvre', 1234567890, CURRENT_TIMESTAMP(), 'idrissig', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Renouvellement manuel oeuvre', 1234567891, CURRENT_TIMESTAMP(), 'idrissig', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Suppression manuelle oeuvre', 1234567892, CURRENT_TIMESTAMP(), 'idrissig', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Mouvement de répartition - Entrée oeuvre', 1234567893, CURRENT_TIMESTAMP(), 'Batch Enrichissement', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Mouvement de répartition - Renouvellement oeuvre', 1234567894, CURRENT_TIMESTAMP(), 'Batch Enrichissement', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Sortie du catalogue', 1234567895, CURRENT_TIMESTAMP(), 'Batch Sortie', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Purge du catalogue', 1234567896, CURRENT_TIMESTAMP(), 'Batch Purge', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Sortie NPU', 1234567897, CURRENT_TIMESTAMP(), 'Batch RDO', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Création NPU', 1234567898, CURRENT_TIMESTAMP(), 'Batch RDO', 'FR');
INSERT INTO PRIAM_CATCMS_JOURNAL (EVENEMENT, IDE12, DATE, UTILISATEUR, TYPE_CMS) VALUES ('Migration RDO', 1234567899, CURRENT_TIMESTAMP(), 'Batch RDO', 'FR');

DROP TABLE IF EXISTS PRIAM_CATCMS_PENEF;
CREATE TABLE PRIAM_CATCMS_PENEF (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  CDE_TYP_IDE12 VARCHAR(255),
  CDE_TYP_UTIL_ORI VARCHAR(255),
  TITRE_OEUVRE VARCHAR(255),
  ROLE_PARTICIPANT1 VARCHAR(2) ,
  NOM_PARTICIPANT1 VARCHAR(255),
  ID_FICHIER bigint(20) DEFAULT NULL,

  PRIMARY KEY (ID),

  CONSTRAINT `FK_ID_FICHIER_PENEF` FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_CATCMS_FICHIER(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS PRIAM_CATCMS_PARTICIPANTS_TRTMT;
CREATE TABLE PRIAM_CATCMS_PARTICIPANTS_TRTMT (
  ID INT AUTO_INCREMENT,
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  ROLE VARCHAR(255),
  PARTICIPANT VARCHAR(255),
  STATUT BINARY(1) DEFAULT '1',
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_CATALOGUE;
CREATE TABLE PRIAM_CATCMS_CATALOGUE (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  TITRE VARCHAR(255),
  TYP_UTIL_GEN VARCHAR(255),
  DATE_ENTREE DATE DEFAULT NULL,
  TYPE_INSCRIPTION VARCHAR(255),
  DATE_RENOUVELLEMENT DATE DEFAULT NULL,
  DATE_SORTIE DATE DEFAULT NULL,
  TYPE_SORTIE VARCHAR(255),
  RAISON_SORTIE VARCHAR(255),
  PARTICIPANTS TEXT,
  ROLES VARCHAR(255),
  ELIGIBLE_CREATION TINYINT(0),
  PRIMARY KEY (ID),

  INDEX IDX_TYPECMS (TYPE_CMS ASC ),
  INDEX IDX_IDE12 (IDE12 ASC )
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE INDEX IDX_DATE_ENTREE ON PRIAM_CATCMS_CATALOGUE (DATE_ENTREE DESC);
CREATE INDEX IDX_TITRE ON PRIAM_CATCMS_CATALOGUE (TITRE ASC);
CREATE INDEX IDX_PARTICIPANTS ON PRIAM_CATCMS_CATALOGUE(PARTICIPANTS(50));


CREATE TABLE PRIAM_CATCMS_PARTICIPANTS (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  ROLE VARCHAR(2),
  PARTICIPANT VARCHAR(255),
  ID_CATALOGUE bigint(20),
  PRIMARY KEY (ID),

  CONSTRAINT `FK_ID_CATALOGUE` FOREIGN KEY (ID_CATALOGUE) REFERENCES PRIAM_CATCMS_CATALOGUE(ID)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2000163011, 'VIP', 'PHONOFR', 'C', 'YEYE CHRISTIAN', 1);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2002037711, 'AN TI ZAGRE', 'PHONOFR', 'C', 'VENDESTOC MARCEL', 1);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2002665711, 'PRISCA AVEC TOI JE VEUX VOYAGE', 'PHONOFR', 'CA', 'RAGALD JEAN MARI', 0);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2003256311, 'WAP', 'PHONOFR', 'CA', 'CLET VICTOR', 0);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2003257111, 'SAUCE', 'PHONOFR',  'C', 'BLANDY GERARD', 0);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2007277711, 'KEUR MWEN KA FE MWEN MAL', 'PHONOFR', 'AR', 'BONIFACE EDOUARD', 0);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2007278711, 'VIRE BO BONDIE', 'PHONOFR', 'AR', 'DURANTY JUDES', 0);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2007279511, 'OUI JEZ', 'PHONOFR', 'A', 'DURANTY JUDES', 0);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2007280311, 'PREPARONS NOUS DANS NOS FOYERS', 'PHONOFR', 'CA', 'HEJOAKA JEAN MIC', 0);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2007281411, 'VIV LES MAMANS', 'PHONOFR','CA', 'ANCINON MOLEON', 0);
INSERT INTO PRIAM_CATCMS_RDO (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, ROLE, PARTICIPANT, POURCENTAGE_DP) VALUES ('ANF', 2007282611, 'GLORIYE POU BONDYE', 'PHONOFR','C', 'DURANTY JUDES', 0);

/*OEUVRE TYPE ANF*/
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2000163011, 'OEUVRE NON ELIGIBLE 1', 'PHONOFR', '2018-01-01', 'Automatique', null, '2018-05-03', 'Automatique', null, 'PARTICIPANT 1', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2002037711, 'OEUVRE NON ELIGIBLE 2', 'PHONOFR', '2018-01-01', 'Automatique', null, '2018-05-03', 'Automatique', null, 'PARTICIPANT 2', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2002665711, 'PRISCA AVEC TOI JE VEUX VOYAGE', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 3', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2003256311, 'WAP', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 4', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2003257111, 'SAUCE', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 5', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2007277711, 'KEUR MWEN KA FE MWEN MAL', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 6', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2007278711, 'VIRE BO BONDIE', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 7', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2007279511, 'OUI JEZ', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 8', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2007280311, 'PREPARONS NOUS DANS NOS FOYERS', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 9', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2007281411, 'VIV LES MAMANS', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 10', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('ANF', 2007282611, 'GLORIYE POU BONDYE', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 11', 'C');

/*OEUVRE TYPE FR*/
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2000163011, 'OEUVRE NON ELIGIBLE 1', 'PHONOFR', '2018-01-01', 'Automatique', null, '2028-05-03', 'Automatique', null, 'PARTICIPANT 1', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2002037711, 'OEUVRE NON ELIGIBLE 2', 'PHONOFR', '2018-01-01', 'Automatique', null, '2018-02-03', 'Automatique', null, 'PARTICIPANT 2', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2002665711, 'PRISCA AVEC TOI JE VEUX VOYAGE', 'PHONOFR', '2018-05-07', 'Automatique', null, null, null, null, 'PARTICIPANT 3', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2003256311, 'WAP', 'PHONOFR', '2018-05-01', 'Automatique', null, null, null, null, 'PARTICIPANT 4', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2003257111, 'SAUCE', 'PHONOFR', '2018-05-01', 'Automatique', null, null, null, null, 'PARTICIPANT 5', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2007277711, 'KEUR MWEN KA FE MWEN MAL', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 6', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2007278711, 'VIRE BO BONDIE', 'PHONOFR', '2018-05-31', 'Automatique', null, null, null, null, 'PARTICIPANT 7', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2007279511, 'OUI JEZ', 'PHONOFR', '2017-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 8', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2007280311, 'PREPARONS NOUS DANS NOS FOYERS', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 9', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2007281411, 'VIV LES MAMANS', 'PHONOFR', '2018-01-01', 'Automatique', '2018-05-10', null, null, null, 'PARTICIPANT 10', 'C');
INSERT INTO PRIAM_CATCMS_CATALOGUE (TYPE_CMS, IDE12, TITRE, TYP_UTIL_GEN, DATE_ENTREE, TYPE_INSCRIPTION, DATE_RENOUVELLEMENT, DATE_SORTIE, TYPE_SORTIE, RAISON_SORTIE, PARTICIPANTS, ROLES) VALUES ('FR', 2007282611, 'GLORIYE POU BONDYE', 'PHONOFR', '2018-01-01', 'Automatique', null, null, null, null, 'PARTICIPANT 11', 'C');



/*INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000163011, 'A', 'AAAA');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2002037711, 'A', 'AAAA');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000001411, 'AR', 'ZZZZ');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000012511, 'AR', 'BBBB');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000014011, 'AR', 'BBBB');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 10000111, 'C', 'YYYY');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000001411, 'C', 'YYYY');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000002411, 'C', 'XXXX');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000012511, 'C', 'YYYY');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000014011, 'C', 'YYYY');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 10000111, 'CA', 'ZZZZ');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000001411, 'CA', 'BBBB');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000012511, 'CA', 'ZZZZ');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000014011, 'CA', 'ZZZZ');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000001411, 'SA', 'AAAA');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000012511, 'SA', 'CCCC');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000013811, 'SA', 'YYYY');
INSERT INTO PRIAM_CATCMS_PARTICIPANTS (TYPE_CMS, IDE12, ROLE, PARTICIPANT) VALUES ('FR', 2000014011, 'SA', 'CCCC');*/


DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_CP_COPY`;
CREATE TABLE `PRIAM_LIGNE_PROGRAMME_CP_COPY` (
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
  `ajout` varchar(45) DEFAULT 'AUTOMATIQUE',
  `selection` tinyint(1) DEFAULT '0',
  `idOeuvreManuel` int(11) DEFAULT NULL,
  `SEL_EN_COURS` tinyint(4) DEFAULT '1',
  `durDifEdit` int(11) DEFAULT NULL COMMENT  'Colonne qui sert pour le tableau editable dans ecran selection',
  `nbrDifEdit` int(11) DEFAULT NULL COMMENT  'Colonne qui sert pour le tableau editable dans ecran selection',
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER_CP_COPY` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL_CP_COPY` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_CP_COPY` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE PRIAM_CATCMS_STATISTIQUES (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  TYPE_CMS VARCHAR(3),
  DATE DATE DEFAULT NULL,
  NOM_FICHIER VARCHAR(255),
  NB_OEUVRES bigint(20),
  NB_CREATION bigint(20),
  NB_RENOUVELLEMENT bigint(20),
  NB_TOTAL_OEUVRES bigint(20),
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO PRIAM_CATCMS_STATISTIQUES (TYPE_CMS, DATE, NOM_FICHIER, NB_OEUVRES, NB_CREATION, NB_RENOUVELLEMENT, NB_TOTAL_OEUVRES)
VALUES ('FR', CURRENT_DATE, 'FF_PENEF_EXTRANA_CATALOGUE_FR_20180910160200.csv', 5, 1, 4, 1043);
INSERT INTO PRIAM_CATCMS_STATISTIQUES (TYPE_CMS, DATE, NOM_FICHIER, NB_OEUVRES, NB_CREATION, NB_RENOUVELLEMENT, NB_TOTAL_OEUVRES)
VALUES ('FR', CURRENT_DATE, 'FF_PENEF_EXTRANA_CATALOGUE_FR_20180910160200.csv', 5, 4, 1, 1048);
INSERT INTO PRIAM_CATCMS_STATISTIQUES (TYPE_CMS, DATE, NOM_FICHIER, NB_OEUVRES, NB_CREATION, NB_RENOUVELLEMENT, NB_TOTAL_OEUVRES)
VALUES ('ANF', CURRENT_DATE, 'FF_PENEF_EXTRANA_CATALOGUE_ANF_20180910160200.csv', 5, 1, 4, 1043);
INSERT INTO PRIAM_CATCMS_STATISTIQUES (TYPE_CMS, DATE, NOM_FICHIER, NB_OEUVRES, NB_CREATION, NB_RENOUVELLEMENT, NB_TOTAL_OEUVRES)
VALUES ('ANF', CURRENT_DATE, 'FF_PENEF_EXTRANA_CATALOGUE_ANF_20180910160200.csv', 5, 4, 1, 1048);

INSERT INTO PRIAM_PROGRAMME (NUMPROG,NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT, USERCRE, DATMAJ, USERMAJ, DATAFFECT, USERAFFECT, DATE_VALIDATION,USER_VALIDATION, DATE_DBT_PRG, DATE_FIN_PRG, CDE_TER, DATE_REPARTITION, STATUT_ELIGIBILITE, TYPE_DROIT)
VALUES (190001,'PROG FD06 TEST', 645, 'FDSVAL', 'FD06', 'OEUVRE', '2019-01-08 11:01:59', 'AFFECTE', null, 'Rachid EMBOUAZZA', null, null, null, null, null, null, '2019-01-15', '2019-01-31', 250, null, null, 'PH');

INSERT INTO PRIAM_FICHIER (NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG, SOURCE_AUTO, TYPE_FICHIER, STATUT_ENRICHISSEEMNT)
VALUES ('FF_PENEF_EXTRANA_FONDS06_20190108104659.csv', 'FDSVAL', 'FD06', '2019-01-03 14:21:06', '2019-01-03 14:21:06', 2, 'AFFECTE', 190001, 1, 'FV', null);

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV(numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt,  durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER, cdeGreIde12Cmplx, cdeGreIde12, titreAltOeuvPereCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreOriCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri, utilisateur, date_insertion, ajout, selection, idOeuvreManuel, SEL_EN_COURS, rionEffet, ID_OEUVRE_CTNU, labelValo, dureeDeposee, taxOri, indicRepart, genreOeuvre, paysOri, statut, mtEdit)
VALUES ( 190001 ,'FDSVAL', 'FD06', 2240093411, 48.75, 0, 'FR', null, 10, 'MB', (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS06_20190108104659.csv'), null, 'IJ', null, null, null, null, 'IMPRO JAZZ', null, null, 'C', 'MURCIA SARAH', 'CONCERT', 'DTSGEN', 'Batch Extraction', '2019-01-08 14:21:07', 'AUTOMATIQUE', 0, null, 1, null,null,null,null,null,null,null,null,null,48.75);

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV(numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt,  durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER, cdeGreIde12Cmplx, cdeGreIde12, titreAltOeuvPereCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreOriCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri, utilisateur, date_insertion, ajout, selection, idOeuvreManuel, SEL_EN_COURS, rionEffet, ID_OEUVRE_CTNU, labelValo, dureeDeposee, taxOri, indicRepart, genreOeuvre, paysOri, statut, mtEdit)
VALUES ( 190001 ,'FDSVAL', 'FD06', 2739083111, 51.25, 0, 'FR', null, 10, 'MB', (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS06_20190108104659.csv'), null, 'IJ', null, null, null, null, 'LA CORSE', null, null, 'C', 'MURCIA SARAH', 'CONCERT', 'DTSGEN', 'Batch Extraction', '2019-01-08 14:21:07', 'AUTOMATIQUE', 0, null, 1, null,null,null,null,null,null,null,null,null,51.25);

INSERT INTO PRIAM_PROGRAMME (NUMPROG,NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT, USERCRE, DATMAJ, USERMAJ, DATAFFECT, USERAFFECT, DATE_VALIDATION,USER_VALIDATION, DATE_DBT_PRG, DATE_FIN_PRG, CDE_TER, DATE_REPARTITION, STATUT_ELIGIBILITE, TYPE_DROIT)
VALUES (190002,'PROG FD06 TEST 2', 645, 'FDSVAL', 'FD06', 'OEUVRE', '2019-01-08 11:01:59', 'AFFECTE', null, 'Rachid EMBOUAZZA', null, null, null, null, null, null, '2019-01-15', '2019-01-31', 250, null, null, 'PH');

INSERT INTO PRIAM_FICHIER (NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG, SOURCE_AUTO, TYPE_FICHIER, STATUT_ENRICHISSEEMNT)
VALUES ('FF_PENEF_EXTRANA_FONDS06_20191401171045.csv', 'FDSVAL', 'FD06', '2019-01-03 14:21:06', '2019-01-03 14:21:06', 1, 'AFFECTE', 190002, 1, 'FV', null);

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV(numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt,  durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER, cdeGreIde12Cmplx, cdeGreIde12, titreAltOeuvPereCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreOriCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri, utilisateur, date_insertion, ajout, selection, idOeuvreManuel, SEL_EN_COURS, rionEffet, ID_OEUVRE_CTNU, labelValo, dureeDeposee, taxOri, indicRepart, genreOeuvre, paysOri, statut, mtEdit)
VALUES ( 190002 ,'FDSVAL', 'FD06', 2739083111, 51.25, 0, 'FR', null, 10, 'MB', (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS06_20191401171045.csv'), null, 'IJ', null, null, null, null, 'LA CORSE', null, null, 'C', 'MURCIA SARAH', 'CONCERT', 'DTSGEN', 'Batch Extraction', '2019-01-08 14:21:07', 'AUTOMATIQUE', 0, null, 1, null,null,null,null,null,null,null,null,null,51.25);

INSERT INTO PRIAM_PROGRAMME (NUMPROG,NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT, USERCRE, DATMAJ, USERMAJ, DATAFFECT, USERAFFECT, DATE_VALIDATION,USER_VALIDATION, DATE_DBT_PRG, DATE_FIN_PRG, CDE_TER, DATE_REPARTITION, STATUT_ELIGIBILITE, TYPE_DROIT)
VALUES (190004,'PROG FD01 TEST 3', 645, 'FDSVAL', 'FD01', 'OEUVRE_AD', '2019-01-08 11:01:59', 'AFFECTE', null, 'Rachid EMBOUAZZA', null, null, null, null, null, null, '2019-01-15', '2019-01-31', 250, null, null, 'PH');

INSERT INTO PRIAM_FICHIER (NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG, SOURCE_AUTO, TYPE_FICHIER, STATUT_ENRICHISSEEMNT)
VALUES ('FF_PENEF_EXTRANA_FONDS01_20191401171045.csv', 'FDSVAL', 'FD06', '2019-01-03 14:21:06', '2019-01-03 14:21:06', 1, 'AFFECTE', 190004, 1, 'FV', 'DONE_SRV_AD_INFO');

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV(numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt,  durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER, cdeGreIde12Cmplx, cdeGreIde12, titreAltOeuvPereCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreOriCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri, utilisateur, date_insertion, ajout, selection, idOeuvreManuel, SEL_EN_COURS, rionEffet, ID_OEUVRE_CTNU, labelValo, dureeDeposee, taxOri, indicRepart, genreOeuvre, paysOri, statut, mtEdit)
VALUES ( 190004 ,'FDSVAL', 'FD01', 2739083112, 51.25, 0, 'FR', null, 10, 'MB', (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS01_20191401171045.csv'), null, 'IJ', null, null, null, null, 'LA CORSE', null, null, 'C', 'MURCIA SARAH', 'CONCERT', 'DTSGEN', 'Batch Extraction', '2019-01-08 14:21:07', 'AUTOMATIQUE', 0, null, 1, null,null,null,null,null,null,null,null,null,51.25);

INSERT INTO PRIAM_AYANT_DROIT(IDE12REPCOAD, CDETYPIDE12REPCOAD,ROLAD,COAD,IDSTEAD,CLEAD,CDETYPPROTEC,COADORIEDTR,IDSTEORIEDTR,NUMCATAL,NUMPERS,ID_FV, POINTS)
VALUES (2739083112,'COCV','CA',536789,1101,0,'DP',536789,1101,345612,65478,(SELECT ID FROM PRIAM_LIGNE_PROGRAMME_FV WHERE ide12 = 2739083112),10);

INSERT INTO PRIAM_AYANT_DROIT_PERS(NUMPERS, PRENOM, NOM, ANNEE_NAISSANCE, ANNEE_DECES, INDICSACEM, SOUS_ROLE, INDICDRTPERCUS)
VALUES (65478,'JEAN','BOKY',1978,2015,0,'CA',null);

INSERT INTO PRIAM_PROGRAMME (NUMPROG,NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT, USERCRE, DATMAJ, USERMAJ, DATAFFECT, USERAFFECT, DATE_VALIDATION,USER_VALIDATION, DATE_DBT_PRG, DATE_FIN_PRG, CDE_TER, DATE_REPARTITION, STATUT_ELIGIBILITE, TYPE_DROIT)
VALUES (190005,'PROG FD12 TEST', 645, 'FDSVAL', 'FD12', 'OEUVRE', '2019-01-08 11:01:59', 'AFFECTE', null, 'Rachid EMBOUAZZA', null, null, null, null, null, null, '2019-01-15', '2019-01-31', 250, null, null, 'PH');

INSERT INTO PRIAM_FICHIER (NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG, SOURCE_AUTO, TYPE_FICHIER, STATUT_ENRICHISSEEMNT)
VALUES ('FF_PENEF_EXTRANA_FONDS12_20190108104659.csv', 'FDSVAL', 'FD12', '2019-01-03 14:21:06', '2019-01-03 14:21:06', 2, 'AFFECTE', 190005, 1, 'FV', null);

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV(numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt,  durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER, cdeGreIde12Cmplx, cdeGreIde12, titreAltOeuvPereCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreOriCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri, utilisateur, date_insertion, ajout, selection, idOeuvreManuel, SEL_EN_COURS, rionEffet, ID_OEUVRE_CTNU, labelValo, dureeDeposee, taxOri, indicRepart, genreOeuvre, paysOri, statut, mtEdit)
VALUES ( 190005 ,'FDSVAL', 'FD12', 2240093411, 48.75, 0, 'FR', null, 10, 'MB', (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS12_20190108104659.csv'), null, 'IJ', null, null, null, null, 'IMPRO JAZZ', null, null, 'C', 'MURCIA SARAH', 'CONCERT', 'DTSGEN', 'Batch Extraction', '2019-01-08 14:21:07', 'AUTOMATIQUE', 0, null, 1, null,null,null,null,null,null,null,null,null,48.75);

# ALTER TABLE PRIAM_PROGRAMME ADD TYPE_DROIT VARCHAR (5);

CREATE TABLE PRIAM_EXPORT_PROGRAMME_FV(
                                        `ID` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `NUMPROG` varchar(8) NOT NULL,
                                        `FILENAME` varchar(255) DEFAULT NULL,
                                        `DATE_CREATION` datetime DEFAULT NULL,
                                        `STATUT` varchar(20) DEFAULT NULL,
                                        PRIMARY KEY (`ID`),
                                        KEY `EXPORT_NUMPROG_FK` (`NUMPROG`),
                                        CONSTRAINT `EXPORT_NUMPROG_FK` FOREIGN KEY (`NUMPROG`) REFERENCES `PRIAM_PROGRAMME` (`NUMPROG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE PRIAM_IMPORT_PROGRAMME_FV(
                                        `ID` bigint(20) NOT NULL AUTO_INCREMENT,
                                        `NUMPROG` varchar(8) NOT NULL,
                                        `FILENAME` varchar(255) DEFAULT NULL,
                                        `DATE_CREATION` datetime DEFAULT NULL,
                                        `STATUT` varchar(20) DEFAULT NULL,
                                        CONTENT mediumblob null,
                                        PRIMARY KEY (`ID`),
                                        KEY `IMPORT_NUMPROG_FK` (`NUMPROG`),
                                        CONSTRAINT `IMPORT_NUMPROG_FK` FOREIGN KEY (`NUMPROG`) REFERENCES `PRIAM_PROGRAMME` (`NUMPROG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

alter view PRIAM_PROG_VIEW as
  SELECT DISTINCT
    pr.NUMPROG                                                         AS NUMPROG,
    pr.NOM                                                             AS NOM,
    pr.RION_THEORIQUE                                                  AS RION_THEORIQUE,
    pr.CDEFAMILTYPUTIL                                                 AS CDEFAMILTYPUTIL,
    pr.CDETYPUTIL                                                      AS CDETYPUTIL,
    pr.TYPE_REPART                                                     AS TYPE_REPART,
    pr.TYPE_DROIT                                                      AS TYPE_DROIT,
    pr.DATE_CREATION                                                   AS DATE_CREATION,
    pr.STATUT_PROG_CODE                                                AS STATUT_PROG_CODE,
    pr.RION_PAIEMENT                                                   AS RION_PAIEMENT,
    pr.USERCRE                                                         AS USERCRE,
    pr.DATMAJ                                                          AS DATMAJ,
    pr.USERMAJ                                                         AS USERMAJ,
    pr.DATAFFECT                                                       AS DATAFFECT,
    pr.USERAFFECT                                                      AS USERAFFECT,
    (SELECT count(f.NUMPROG)
     FROM PRIAM_FICHIER f
     WHERE ((pr.NUMPROG = f.NUMPROG) AND (f.SOURCE_AUTO = 1))) AS fichiers,
    pr.DATE_DBT_PRG                                                    AS DATEDBTPRG,
    pr.DATE_FIN_PRG                                                    AS DATEFINPRG,
    pr.CDE_TER                                                         AS CDETER,
    pr.USER_VALIDATION                                                 AS USERVALIDATION,
    pr.DATE_VALIDATION                                                 AS DATEVALIDATION,
    ff.STATUT                                                          AS STATUT_FICHIER_FELIX,
    pr.DATE_REPARTITION                                                AS DATE_REPARTITION,
    pr.STATUT_ELIGIBILITE                                              AS STATUT_ELIGIBILITE,
    epf.STATUT                                                         AS STATUT_EXPORT,
    ipf.STATUT                                                         AS STATUT_IMPORT
  FROM PRIAM_PROGRAMME pr LEFT JOIN PRIAM_FICHIER_FELIX ff
                                     ON ff.NUMPROG = pr.NUMPROG
                           left join PRIAM_EXPORT_PROGRAMME_FV epf on pr.NUMPROG = epf.NUMPROG
                          left join PRIAM_IMPORT_PROGRAMME_FV ipf ON pr.NUMPROG = ipf.NUMPROG
  GROUP BY pr.NUMPROG;


ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD datconslt DATE DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD datsitu DATE DEFAULT NULL;


commit;