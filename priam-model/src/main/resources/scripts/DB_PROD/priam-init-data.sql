﻿-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
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
-- Dumping data for table `PRIAM_PARAMAPPLI`
--

LOCK TABLES `PRIAM_PARAMAPPLI` WRITE;
/*!40000 ALTER TABLE `PRIAM_PARAMAPPLI` DISABLE KEYS */;
INSERT INTO `PRIAM_PARAMAPPLI` (`CDEPARAM`, `LIBSTAT`, `VAL`) VALUES ('ANNEE_SEQ_PROGRAMME','represente annee en cours la valeur change pour reinitialiser la sequence de la table programme','17');
/*!40000 ALTER TABLE `PRIAM_PARAMAPPLI` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PRIAM_ROLE`
--

LOCK TABLES `PRIAM_ROLE` WRITE;
/*!40000 ALTER TABLE `PRIAM_ROLE` DISABLE KEYS */;
INSERT INTO `PRIAM_ROLE` (`ID`, `EXTERNAL_ID`, `ROLE`) VALUES (1,'ADM','ADM');
INSERT INTO `PRIAM_ROLE` (`ID`, `EXTERNAL_ID`, `ROLE`) VALUES (2,'GST','GST');
INSERT INTO `PRIAM_ROLE` (`ID`, `EXTERNAL_ID`, `ROLE`) VALUES (3,'INV','INV');
/*!40000 ALTER TABLE `PRIAM_ROLE` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PRIAM_ROLE_RIGHTS`
--

LOCK TABLES `PRIAM_ROLE_RIGHTS` WRITE;
/*!40000 ALTER TABLE `PRIAM_ROLE_RIGHTS` DISABLE KEYS */;
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'CRTPRG','Creation de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'CRTPRG','Creation de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (3,'READPRG','Acces ecran programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'READPRG','Acces ecran programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'READPRG','Acces ecran programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'MDYPRG','Modification de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'MDYPRG','Modification de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'ABDPRG','Abandon de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'ABDPRG','Abandon de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'MSEREP','Mise en repartition');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'MSEREP','Mise en repartition');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'RECAFC','Enregistrement affectation');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'RECAFC','Enregistrement affectation');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'EDTAFC','Edition affectation');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'EDTAFC','Edition affectation');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'EDTSEL','Edition selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'EDTSEL','Edition selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'INVSEL','Invalider selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'INVSEL','Invalider selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'CLDSEL','Annuler selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'CLDSEL','Annuler selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (1,'VLDSEL','Valider selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (2,'VLDSEL','Valider selection');
/*!40000 ALTER TABLE `PRIAM_ROLE_RIGHTS` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PRIAM_STATUT`
--

LOCK TABLES `PRIAM_STATUT` WRITE;
/*!40000 ALTER TABLE `PRIAM_STATUT` DISABLE KEYS */;
INSERT INTO `PRIAM_STATUT` (`CODE`, `LIBELLE`) VALUES ('ABANDONNE','Abandonné');
INSERT INTO `PRIAM_STATUT` (`CODE`, `LIBELLE`) VALUES ('AFFECTE','Affecté');
INSERT INTO `PRIAM_STATUT` (`CODE`, `LIBELLE`) VALUES ('CHARGEMENT_KO','Chargement KO');
INSERT INTO `PRIAM_STATUT` (`CODE`, `LIBELLE`) VALUES ('CHARGEMENT_OK','Chargement OK');
INSERT INTO `PRIAM_STATUT` (`CODE`, `LIBELLE`) VALUES ('EN_COURS','En cours');
/*!40000 ALTER TABLE `PRIAM_STATUT` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `PRIAM_STATUT_PROGRAMME`
--

LOCK TABLES `PRIAM_STATUT_PROGRAMME` WRITE;
/*!40000 ALTER TABLE `PRIAM_STATUT_PROGRAMME` DISABLE KEYS */;
INSERT INTO `PRIAM_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('ABANDONNE','Abandonné');
INSERT INTO `PRIAM_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('AFFECTE','Affecté');
INSERT INTO `PRIAM_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('CREE','Créé');
INSERT INTO `PRIAM_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('EN_COURS','En cours');
INSERT INTO `PRIAM_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('MIS_EN_REPART','Mis en répartition');
INSERT INTO `PRIAM_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('REPARTI','Réparti');
INSERT INTO `PRIAM_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('VALIDE','Validé');
/*!40000 ALTER TABLE `PRIAM_STATUT_PROGRAMME` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-09-13 11:18:52
commit;