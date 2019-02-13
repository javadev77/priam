USE priam_app;

ALTER TABLE PRIAM_LIGNE_PROGRAMME_TRAITEMENT_BATCH
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


ALTER TABLE PRIAM_FICHIER ADD STATUT_ENRICHISSEEMNT VARCHAR(255) DEFAULT NULL COMMENT 'STATUT ENRICHISSEMENT';

ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD ID_OEUVRE_CTNU INT(11) DEFAULT NULL COMMENT 'ID OEUVRE CONTENU';
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD rionEffet INT(11);
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD mtEdit double DEFAULT NULL COMMENT  'Colonne qui sert pour le tableau editable dans ecran selection';
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD nbrDifEdit int(11) DEFAULT NULL COMMENT  'Colonne qui sert pour le tableau editable dans ecran selection';
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD labelValo varchar(250) DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD dureeDeposee int(11) DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD taxOri double DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD indicRepart tinyint(1) DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV   ADD genreOeuvre varchar(250) DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV   ADD paysOri int(4) DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV   ADD statut varchar(250) DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV   ADD isOeuvreComplex tinyint(4) DEFAULT '0' COMMENT 'Flag qui indique si l''oeuvre est complexe';
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD durDifEdit int(11) DEFAULT NULL;



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
  IDSTEORIEDTR VARCHAR(10) DEFAULT NULL,
  NUMCATAL INT(9) DEFAULT NULL,
  NUMPERS INT(9) DEFAULT NULL,

  ID_FV int null,

  PRIMARY KEY (ID),

  constraint FK_ID_LIGNE_FV foreign key (ID_FV) references PRIAM_LIGNE_PROGRAMME_FV (id),
  CONSTRAINT FK_NUMPERS FOREIGN KEY (NUMPERS) REFERENCES PRIAM_AYANT_DROIT_PERS (NUMPERS)


);

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
  epf.STATUT                                                         AS STATUT_EXPORT
FROM (PRIAM_PROGRAMME pr LEFT JOIN PRIAM_FICHIER_FELIX ff
                                   ON ((ff.NUMPROG = pr.NUMPROG))
  left join PRIAM_EXPORT_PROGRAMME_FV epf on pr.NUMPROG = epf.NUMPROG)
GROUP BY pr.NUMPROG;

COMMIT;