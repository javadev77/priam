USE priam_app;

ALTER TABLE PRIAM_LIGNE_PROGRAMME_TRAITEMENT_BATCH
  ADD rionEffet INT(11);

ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV
  ADD rionEffet INT(11);

CREATE TABLE `PRIAM_LIGNE_PROGRAMME_FV_COPY` (
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
  rionEffet INT(11),
  PRIMARY KEY (`id`),
  KEY `FK_ID_FICHIER_FV_COPY` (`ID_FICHIER`),
  KEY `IDX_CDE_UTIL_COPY` (`cdeUtil`),
  CONSTRAINT `FK_ID_FICHIER_FV_COPY` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_FICHIER` (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE PRIAM_PROGRAMME ADD TYPE_DROIT VARCHAR (5);


ALTER TABLE PRIAM_FICHIER ADD STATUT_ENRICHISSEEMNT VARCHAR(255) NOT NULL COMMENT 'STATUT ENRICHISSEMENT';

COMMIT;