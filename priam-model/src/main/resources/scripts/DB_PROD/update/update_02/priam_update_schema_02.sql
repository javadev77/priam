USE priam_app;

ALTER TABLE PRIAM_FICHIER
ADD `TYPE_FICHIER` varchar(25) DEFAULT NULL COMMENT 'type de fichier peut etre CP CMD FV pour differencier l envoi';

RENAME TABLE `PRIAM_LIGNE_PROGRAMME` TO `PRIAM_LIGNE_PROGRAMME_CP`;

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
  `ajout` varchar(45) DEFAULT 'AUTOMATIQUE',
  `selection` tinyint(1) DEFAULT '0',
  `idOeuvreManuel` int(11) DEFAULT NULL,
  `SEL_EN_COURS` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER_CMS` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_CMS` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_TRAITEMENT_BATCH`;
CREATE TABLE `PRIAM_LIGNE_PROGRAMME_TRAITEMENT_BATCH` (
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
  KEY `FK_ID_FICHIER_CMS` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_BATCH_TRAITMENT` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `PRIAM_LIGNE_PROGRAMME_FV`;
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
  `ajout` varchar(45) DEFAULT 'AUTOMATIQUE',
  `selection` tinyint(1) DEFAULT '0',
  `idOeuvreManuel` int(11) DEFAULT NULL,
  `SEL_EN_COURS` tinyint(4) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER_FV` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_FV` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS PRIAM_CATALOGUE_OCTAV;
CREATE TABLE PRIAM_CATALOGUE_OCTAV (
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  TITRE VARCHAR(255),
  ROLE VARCHAR(255),
  PARTICIPANT VARCHAR(255),

  PRIMARY KEY (ID)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE PRIAM_PROGRAMME
ADD `STATUT_ELIGIBILITE` varchar(25) DEFAULT NULL COMMENT 'status d eligibilite';


DROP TABLE IF EXISTS `PRIAM_ELIGIBILITE_STATUT_PROGRAMME`;
CREATE TABLE `PRIAM_ELIGIBILITE_STATUT_PROGRAMME` (
  `CODE` varchar(50) NOT NULL,
  `LIBELLE` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CODE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


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
    pr.DATE_REPARTITION                                                AS DATE_REPARTITION,
    pr.STATUT_ELIGIBILITE                                              AS STATUT_ELIGIBILITE
  FROM (PRIAM_PROGRAMME pr LEFT JOIN PRIAM_FICHIER_FELIX ff
      ON ((ff.NUMPROG = pr.NUMPROG)))
  GROUP BY pr.NUMPROG;


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
  `ajout` varchar(45) DEFAULT 'AUTOMATIQUE',
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

ALTER TABLE `PRIAM_LIGNE_PROGRAMME_CMS`
ADD `RION_N_ANT` varchar(7) DEFAULT NULL COMMENT 'rion contenu dans le fichier PENEF CMS ANT pour le calcul des points';

ALTER TABLE `PRIAM_LIGNE_PROGRAMME_CMS_COPY`
  ADD `RION_N_ANT` varchar(7) DEFAULT NULL COMMENT 'rion contenu dans le fichier PENEF CMS ANT pour le calcul des points';

DROP TABLE IF EXISTS PRIAM_CATALOGUE_OCTAV_ANT;
CREATE TABLE PRIAM_CATALOGUE_OCTAV_ANT (
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  TITRE VARCHAR(255),
  ROLE VARCHAR(255),
  PARTICIPANT VARCHAR(255),
  NUMPROG VARCHAR(8) DEFAULT NULL,
  NBRDIF int(11) DEFAULT NULL,
  PRIMARY KEY (ID)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


commit;