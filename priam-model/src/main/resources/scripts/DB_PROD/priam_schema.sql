-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: msqdev08    Database: priam_app
-- ------------------------------------------------------
-- Server version	5.5.5-10.1.22-MariaDB-SACEM
USE priam_app;

--
-- Table structure for table `BATCH_JOB_INSTANCE`
--

DROP TABLE IF EXISTS `BATCH_JOB_INSTANCE`;
CREATE TABLE `BATCH_JOB_INSTANCE` (
  `JOB_INSTANCE_ID` bigint(20) NOT NULL,
  `VERSION` bigint(20) DEFAULT NULL,
  `JOB_NAME` varchar(100) NOT NULL,
  `JOB_KEY` varchar(32) NOT NULL,
  PRIMARY KEY (`JOB_INSTANCE_ID`),
  UNIQUE KEY `JOB_INST_UN` (`JOB_NAME`,`JOB_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `BATCH_JOB_EXECUTION`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION`;
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

--
-- Table structure for table `BATCH_JOB_EXECUTION_CONTEXT`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_CONTEXT`;
CREATE TABLE `BATCH_JOB_EXECUTION_CONTEXT` (
  `JOB_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text,
  PRIMARY KEY (`JOB_EXECUTION_ID`),
  CONSTRAINT `JOB_EXEC_CTX_FK` FOREIGN KEY (`JOB_EXECUTION_ID`) REFERENCES `BATCH_JOB_EXECUTION` (`JOB_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `BATCH_JOB_EXECUTION_PARAMS`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_PARAMS`;
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

--
-- Table structure for table `BATCH_JOB_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_EXECUTION_SEQ`;
CREATE TABLE `BATCH_JOB_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `BATCH_JOB_SEQ`
--

DROP TABLE IF EXISTS `BATCH_JOB_SEQ`;
CREATE TABLE `BATCH_JOB_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `BATCH_STEP_EXECUTION`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION`;
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

--
-- Table structure for table `BATCH_STEP_EXECUTION_CONTEXT`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_CONTEXT`;
CREATE TABLE `BATCH_STEP_EXECUTION_CONTEXT` (
  `STEP_EXECUTION_ID` bigint(20) NOT NULL,
  `SHORT_CONTEXT` varchar(2500) NOT NULL,
  `SERIALIZED_CONTEXT` text,
  PRIMARY KEY (`STEP_EXECUTION_ID`),
  CONSTRAINT `STEP_EXEC_CTX_FK` FOREIGN KEY (`STEP_EXECUTION_ID`) REFERENCES `BATCH_STEP_EXECUTION` (`STEP_EXECUTION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `BATCH_STEP_EXECUTION_SEQ`
--

DROP TABLE IF EXISTS `BATCH_STEP_EXECUTION_SEQ`;
CREATE TABLE `BATCH_STEP_EXECUTION_SEQ` (
  `ID` bigint(20) NOT NULL,
  `UNIQUE_KEY` char(1) NOT NULL,
  UNIQUE KEY `UNIQUE_KEY_UN` (`UNIQUE_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO BATCH_STEP_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_STEP_EXECUTION_SEQ);
INSERT INTO BATCH_JOB_EXECUTION_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_EXECUTION_SEQ);
INSERT INTO BATCH_JOB_SEQ (ID, UNIQUE_KEY) select * from (select 0 as ID, '0' as UNIQUE_KEY) as tmp where not exists(select * from BATCH_JOB_SEQ);


--
-- Table structure for table `SAREFTJ_LIBCISACROLE`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBCISACROLE`;
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

--
-- Table structure for table `SAREFTR_FAMILTYPUTIL`
--

DROP TABLE IF EXISTS `SAREFTR_FAMILTYPUTIL`;
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

--
-- Table structure for table `SAREFTJ_LIBFAMILTYPUTIL`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBFAMILTYPUTIL`;
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

--
-- Table structure for table `SAREFTJ_LIBTER`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBTER`;
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

--
-- Table structure for table `SAREFTJ_LIBTYPUTIL`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBTYPUTIL`;
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

--
-- Table structure for table `SAREFTJ_LIBUTIL`
--

DROP TABLE IF EXISTS `SAREFTJ_LIBUTIL`;
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


--
-- Table structure for table `SAREFTR_RION`
--

DROP TABLE IF EXISTS `SAREFTR_RION`;
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

--
-- Table structure for table `SAREFTR_TYPUTIL`
--

DROP TABLE IF EXISTS `SAREFTR_TYPUTIL`;
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

--
-- Table structure for table `PRIAM_STATUT`
--

DROP TABLE IF EXISTS `PRIAM_STATUT`;
CREATE TABLE `PRIAM_STATUT` (
  `CODE` varchar(255) NOT NULL DEFAULT 'EN_COURS',
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- Table structure for table `PRIAM_STATUT_PROGRAMME`
--

DROP TABLE IF EXISTS `PRIAM_STATUT_PROGRAMME`;
CREATE TABLE `PRIAM_STATUT_PROGRAMME` (
  `CODE` varchar(50) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `PRIAM_PROGRAMME`
--

DROP TABLE IF EXISTS `PRIAM_PROGRAMME`;
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

--
-- Table structure for table `PRIAM_FICHIER`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER`;
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

--
-- Table structure for table `PRIAM_FICHIER_FELIX`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_FELIX`;
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

--
-- Table structure for table `PRIAM_FICHIER_FELIX_LOG`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_FELIX_LOG`;
CREATE TABLE `PRIAM_FICHIER_FELIX_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_FICHIER_FELIX` bigint(20) DEFAULT NULL,
  `LOG` varchar(1024) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_FICHIER_FELIX_FK` (`ID_FICHIER_FELIX`),
  CONSTRAINT `ID_FICHIER_FELIX_FK` FOREIGN KEY (`ID_FICHIER_FELIX`) REFERENCES `PRIAM_FICHIER_FELIX` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `PRIAM_FICHIER_HISTORIQUE`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_HISTORIQUE`;
CREATE TABLE `PRIAM_FICHIER_HISTORIQUE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  `ID_FICHIER` bigint(20) NOT NULL,
  `CODE_STATUT` varchar(255) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `CODE_STATUT_FK` (`CODE_STATUT`),
  CONSTRAINT `CODE_STATUT_FK` FOREIGN KEY (`CODE_STATUT`) REFERENCES `PRIAM_STATUT` (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `PRIAM_FICHIER_LOG`
--

DROP TABLE IF EXISTS `PRIAM_FICHIER_LOG`;
CREATE TABLE `PRIAM_FICHIER_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_FICHIER` bigint(20) DEFAULT NULL,
  `LOG` varchar(1024) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_FICHIER_FK` (`ID_FICHIER`),
  CONSTRAINT `ID_FICHIER_FK` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `PRIAM_LIGNE_PREPREP`
--

DROP TABLE IF EXISTS `PRIAM_LIGNE_PREPREP`;
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

--
-- Table structure for table `PRIAM_LIGNE_PROGRAMME`
--

DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_CP`;
CREATE TABLE `PRIAM_LIGNE_PROGRAMME` (
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
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `PRIAM_PARAMAPPLI`
--

DROP TABLE IF EXISTS `PRIAM_PARAMAPPLI`;
CREATE TABLE `PRIAM_PARAMAPPLI` (
  `CDEPARAM` varchar(100) NOT NULL,
  `LIBSTAT` varchar(450) DEFAULT NULL,
  `VAL` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`CDEPARAM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Table structure for table `PRIAM_PARAMETRAGE`
--

DROP TABLE IF EXISTS `PRIAM_PARAMETRAGE`;
CREATE TABLE `PRIAM_PARAMETRAGE` (
  `USER_ID` varchar(127) NOT NULL,
  `PRIAM_KEY` varchar(60) NOT NULL,
  `PRIAM_VALUE` varchar(127) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`,`PRIAM_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Table structure for table `PRIAM_PROGRAMME_SEQUENCE`
--

DROP TABLE IF EXISTS `PRIAM_PROGRAMME_SEQUENCE`;
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
CREATE TABLE `PRIAM_ROLE_RIGHTS` (
  `ROLE_ID` bigint(20) NOT NULL COMMENT 'Role Id',
  `RIGHTS` varchar(255) DEFAULT NULL COMMENT 'Habilitation',
  `LIBELLE` varchar(255) DEFAULT NULL COMMENT 'Libelle habilitation',
  KEY `FK_2555xwvbf9ilta6fgul5al4ch` (`ROLE_ID`),
  CONSTRAINT `FK_2555xwvbf9ilta6fgul5al4ch` FOREIGN KEY (`ROLE_ID`) REFERENCES `PRIAM_ROLE` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Final view structure for view `PRIAM_PROG_VIEW`
--
DROP VIEW IF EXISTS PRIAM_PROG_VIEW ;
create view PRIAM_PROG_VIEW as
  SELECT DISTINCT
    pr.NUMPROG                                                         AS NUMPROG,
    pr.NOM                                                             AS NOM,
    pr.RION_THEORIQUE                                                  AS RION_THEORIQUE,
    pr.CDEFAMILTYPUTIL                                                 AS CDEFAMILTYPUTIL,
    pr.CDETYPUTIL                                                      AS CDETYPUTIL,
    pr.TYPE_REPART                                                     AS TYPE_REPART,
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
    pr.DATE_REPARTITION                                                AS DATE_REPARTITION
  FROM (PRIAM_PROGRAMME pr LEFT JOIN PRIAM_FICHIER_FELIX ff
      ON ((ff.NUMPROG = pr.NUMPROG)))
  GROUP BY pr.NUMPROG;
commit;