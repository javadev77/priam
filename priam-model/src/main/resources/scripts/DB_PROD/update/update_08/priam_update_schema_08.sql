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
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD durDifEdit int(11) DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD datconslt DATE DEFAULT NULL;
ALTER TABLE PRIAM_LIGNE_PROGRAMME_FV  ADD datsitu DATE DEFAULT NULL;


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
  IDSTEORIEDTR BIGINT(15) DEFAULT NULL,
  NUMCATAL INT(9) DEFAULT NULL,
  NUMPERS INT(9) DEFAULT NULL,
  ID_FV int null,

  PRIMARY KEY (ID),

  constraint FK_ID_LIGNE_FV foreign key (ID_FV) references PRIAM_LIGNE_PROGRAMME_FV (id),
  CONSTRAINT FK_NUMPERS FOREIGN KEY (NUMPERS) REFERENCES PRIAM_AYANT_DROIT_PERS (NUMPERS)


);

DROP TABLE IF EXISTS PRIAM_EXPORT_PROGRAMME_FV;
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


DROP TABLE IF EXISTS PRIAM_IMPORT_PROGRAMME_FV;
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



DROP VIEW IF EXISTS EXPORT_FV_VIEW;
CREATE VIEW  EXPORT_FV_VIEW AS (
  SELECT PP.CDEFAMILTYPUTIL, PP.CDETYPUTIL, PP.NUMPROG as numProg, FV_CTN.rionEffet, FV_CTN.ide12,  FV_CTN.cdeTypIde12, PAD.IDE12REPCOAD, PAD.CDETYPIDE12REPCOAD, FV_CTN.datsitu, FV_CTN.datconslt, PAD.COAD,
    PAD.NUMPERS, PAD.NUMCATAL, PAD.IDSTEAD, PAD.ROLAD,
    CASE WHEN PP.TYPE_DROIT = 'DE_DR' THEN 'DE/DR'  ELSE PP.TYPE_DROIT END AS typeDroit, PAD.CLEAD, PAD.CDETYPPROTEC, PAD.COADORIEDTR, PAD.IDSTEORIEDTR, FV_CTN.tax, FV_CTN.durDif, FV_CTN.nbrDif,
    FV_CTN.typMt, FV_CTN.mt,
    FV.genreOeuvre, FV.titreOeuvre, FV.dureeDeposee, FV.taxOri, FV.paysOri, FV.indicRepart, PAD.POINTS, PP.NOM AS NOM_PRG, PERS.NOM, PERS.PRENOM, PERS.INDICSACEM,
    PERS.SOUS_ROLE, PERS.ANNEE_NAISSANCE, PERS.ANNEE_DECES, PERS.INDICDRTPERCUS
  FROM PRIAM_LIGNE_PROGRAMME_FV FV
    INNER JOIN PRIAM_FICHIER PF on FV.ID_FICHIER = PF.ID
    INNER JOIN PRIAM_PROGRAMME PP on PP.NUMPROG = PF.NUMPROG
    LEFT JOIN PRIAM_AYANT_DROIT PAD on FV.id = PAD.ID_FV
    LEFT JOIN PRIAM_AYANT_DROIT_PERS PERS on PAD.NUMPERS = PERS.NUMPERS
    INNER JOIN PRIAM_LIGNE_PROGRAMME_FV FV_CTN ON FV.ID_OEUVRE_CTNU=FV_CTN.id
  WHERE FV.isOeuvreComplex = 0 AND FV.ID_OEUVRE_CTNU IS NOT NULL
)
  UNION
(
  SELECT PP.CDEFAMILTYPUTIL, PP.CDETYPUTIL, PP.NUMPROG as numProg, FV.rionEffet, FV.ide12,  FV.cdeTypIde12, PAD.IDE12REPCOAD, PAD.CDETYPIDE12REPCOAD, FV.datsitu, FV.datconslt, PAD.COAD,
    PAD.NUMPERS, PAD.NUMCATAL, PAD.IDSTEAD, PAD.ROLAD, CASE WHEN PP.TYPE_DROIT = 'DE_DR' THEN 'DE/DR'  ELSE PP.TYPE_DROIT END AS typeDroit, PAD.CLEAD, PAD.CDETYPPROTEC, PAD.COADORIEDTR, PAD.IDSTEORIEDTR, FV.tax, FV.durDif, FV.nbrDif,
    FV.typMt, FV.mt,
    FV.genreOeuvre, FV.titreOeuvre, FV.dureeDeposee, FV.taxOri, FV.paysOri, FV.indicRepart, PAD.POINTS, PP.NOM AS NOM_PRG, PERS.NOM, PERS.PRENOM, PERS.INDICSACEM,
    PERS.SOUS_ROLE, PERS.ANNEE_NAISSANCE, PERS.ANNEE_DECES, PERS.INDICDRTPERCUS
  FROM PRIAM_LIGNE_PROGRAMME_FV FV
    INNER JOIN PRIAM_FICHIER PF on FV.ID_FICHIER = PF.ID
    INNER JOIN PRIAM_PROGRAMME PP on PP.NUMPROG = PF.NUMPROG
    LEFT JOIN PRIAM_AYANT_DROIT PAD on FV.id = PAD.ID_FV
    LEFT JOIN PRIAM_AYANT_DROIT_PERS PERS on PAD.NUMPERS = PERS.NUMPERS
  WHERE FV.ID_OEUVRE_CTNU IS NULL);




DROP TABLE IF EXISTS `PRIAM_IMPORT_PROGRAMME_FV_LOG`;
CREATE TABLE `PRIAM_IMPORT_PROGRAMME_FV_LOG` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `ID_FICHIER` bigint(20) DEFAULT NULL,
  `LOG` varchar(1024) DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `ID_FICHIER_IMPORT_FK` (`ID_FICHIER`),
  CONSTRAINT `ID_FICHIER_IMPORT_FK` FOREIGN KEY (`ID_FICHIER`) REFERENCES `PRIAM_IMPORT_PROGRAMME_FV` (`ID`)
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
FROM PRIAM_PROGRAMME pr
  LEFT JOIN PRIAM_FICHIER_FELIX ff ON ff.NUMPROG = pr.NUMPROG
  left join PRIAM_EXPORT_PROGRAMME_FV epf ON pr.NUMPROG = epf.NUMPROG
  left join PRIAM_IMPORT_PROGRAMME_FV ipf ON pr.NUMPROG = ipf.NUMPROG
GROUP BY pr.NUMPROG;

DROP TABLE  IF EXISTS PRIAM_IMPORT_PROGRAMME_FV_DATA_BATCH;
create table PRIAM_IMPORT_PROGRAMME_FV_DATA_BATCH
(
	id int not null auto_increment
		primary key,
	cdeFamilTypUtil varchar(45) null,
	cdeTypUtil varchar(45) null,
	numProg int null,
	rionEffet int null,
	ide12 bigint null,
	cdeTypIde12 varchar(45) null,
	ide12RepCoad bigint null,
	cdeTypIde12RepCoad varchar(45) null,
	datsitu date null,
	datconslt date null,
	coad int(10) null,
	numPers int(9) null,
	numCatal int(9) null,
	idSteAd varchar(10) null,
	rolAd varchar(50) null,
	typeDroit varchar(5) null,
	cleAd double null,
	coadOriEdtr int(8) null,
	idSteOriEdtr BIGINT(15) null,
	tax double null,
	durDif int null,
	nbrDif int null,
	typMt varchar(45) null,
	mt double null,
	genreOeuvre varchar(250) null,
	titreOeuvre varchar(250) null,
	dureeDeposee int null,
	taxOri double null,
	labelValo varchar(250) null,
	paysOri int(4) null,
	indicRepart tinyint(1) null,
	nom varchar(90) null,
	prenom varchar(250) null,
	indicSacem tinyint(1) null,
	indicDrtPercus tinyint(1) null,
	ID_FICHIER bigint null,
	points double null,
	NOM_PRG varchar(50) null,
	cdeTypProtec varchar(5) null,
	SOUS_ROLE varchar(250) null,
	ANNEE_NAISSANCE int(4) null,
	ANNEE_DECES int(4) null
);


ALTER TABLE PRIAM_AYANT_DROIT ADD points double DEFAULT NULL;



DROP VIEW IF EXISTS AYANT_DROIT_PROGRAMME_VIEW;
CREATE VIEW AYANT_DROIT_PROGRAMME_VIEW AS (
  select AD.ID AS ID, PP.NUMPROG, LPF.ide12, LPF.titreOeuvre as titre, AD.COAD,
         AD.ROLAD as role, concat(ADP.NOM, ' ', ADP.PRENOM) AS participant, AD.points
  from PRIAM_AYANT_DROIT AD
         inner join PRIAM_LIGNE_PROGRAMME_FV LPF on AD.ID_FV = LPF.id
         inner join PRIAM_AYANT_DROIT_PERS ADP on AD.NUMPERS = ADP.NUMPERS
         inner join PRIAM_FICHIER PF on LPF.ID_FICHIER = PF.ID
         inner join PRIAM_PROGRAMME PP on PF.NUMPROG = PP.NUMPROG
  );


-- Ajout des tables SAREF pour la validation des champs import
DROP TABLE IF EXISTS SAREFTR_STE;
create table SAREFTR_STE
(
  IDSTE              BIGINT                                NOT NULL
    PRIMARY KEY COMMENT 'Code Societe (Interne) pour OSCAR',
  FILTRE             DECIMAL(20) NOT NULL
  COMMENT 'Filtrage des donnees de la table',
  ORDAFF             INT DEFAULT 0                         NOT NULL
  COMMENT 'Ordre affichage',
  COM                VARCHAR(300) COMMENT 'Le champ commentaire  n',
  CDESTECISAC        VARCHAR(3),
  LIBSTE             VARCHAR(300)                          NOT NULL,
  SIGLESTE           VARCHAR(12),
  CISAC              DOUBLE                                NOT NULL,
  STEGESTIONSACEM    DOUBLE                                NOT NULL,
  INDADRSTE          TINYINT,
  INDCPTBENE         TINYINT,
  REPART_SACEM       DOUBLE DEFAULT 0                      NOT NULL,
  DATCRE             DATETIME                              NOT NULL,
  USERCRE            VARCHAR(60)                           NOT NULL,
  DATMAJ             DATETIME           NOT NULL,
  USERMAJ            VARCHAR(60)                           NOT NULL,
  DATDBTVLD          DATETIME                              NOT NULL,
  DATFINVLD          DATETIME,
  TERRESSTE          SMALLINT,
  ORIEDTRRAES        TINYINT DEFAULT 2                     NOT NULL,
  LOGOENTET          VARCHAR(80),
  LOGOADR1A          VARCHAR(100),
  LOGOADR1B          VARCHAR(100),
  LOGOADR1C          VARCHAR(100),
  LOGOADR1D          VARCHAR(100),
  LOGOADR1E          VARCHAR(100),
  BIEM               TINYINT DEFAULT 0,
  FLGPREREPART       TINYINT DEFAULT 0                     NOT NULL,
  FLGCOOP            TINYINT DEFAULT 0                     NOT NULL,
  CDEDEVISE          VARCHAR(3),
  FLGSTEHORSRION     TINYINT DEFAULT 0,
  FLGRIONREALISSACEM TINYINT DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS SAREFTR_CATEGADSACEM;
create table SAREFTR_CATEGADSACEM
(
  CDECATEGADSACEM VARCHAR(10) not null
    primary key comment 'Code identifiant AD Sacem',
  FILTRE DECIMAL(20) not null comment 'Filtrage des donnees de la table',
  ORDAFF INT default 0  not null comment 'Ordre affichage',
  COM VARCHAR(300) COMMENT 'Commentaires libres',
  DATCRE DATETIME not null comment 'Date de creation',
  USERCRE VARCHAR(60) not null comment 'Utilisateur ayant effectue la creation',
  DATMAJ DATETIME  not null comment 'Date de modification',
  USERMAJ VARCHAR(60) not null comment 'Utilisateur ayant effectue la modification',
  DATDBTVLD DATETIME not null comment 'Date de debut de validite',
  DATFINVLD DATETIME COMMENT 'Date de fin d evalidite',
  NONAD CHAR default '0',
  ADCRE CHAR default '0' COMMENT 'ind. Ayant droit de type createur',
  CDEROLCISAC VARCHAR(2)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS SAREFTR_TYPDRTSACEM;
create table SAREFTR_TYPDRTSACEM
(
  CDETYPDRTSACEM VARCHAR(3) not null
    primary key comment 'Ce code permet de specifier le type de Droit SACEM (DE, DR ou PH par exemple)',
  FILTRE DECIMAL(20) not null comment 'Filtrage des donnees de la table',
  ORDAFF INT default 0  not null comment 'Ordre affichage',
  COM VARCHAR(300) COMMENT 'Le champ commentaire  n',
  DATCRE DATETIME not null,
  USERCRE VARCHAR(60) not null,
  DATMAJ DATETIME not null,
  USERMAJ VARCHAR(60) not null,
  DATDBTVLD DATETIME not null,
  DATFINVLD DATETIME,
  FLGIGNOREDP TINYINT default 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS SAREFTR_TYPPROTEC;
create table SAREFTR_TYPPROTEC
(
  CDETYPPROTEC VARCHAR(3) not null primary key comment 'Code type protection des oeuvres',
  FILTRE DECIMAL(20) not null comment 'Filtrage des donnees de la table',
  ORDAFF INT default 0  not null comment 'Ordre affichage',
  COM VARCHAR(300) COMMENT 'Commentaires libres',
  DATCRE DATETIME not null comment 'Date de creation',
  USERCRE VARCHAR(60) not null comment 'Utilisateur ayant cree l',
  DATMAJ DATETIME not null,
  USERMAJ VARCHAR(60) not null,
  DATDBTVLD DATETIME not null,
  DATFINVLD DATETIME,
  FLGCPTEATT CHAR default 'N'  not null
)  ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS SAREFTR_GREOEUV;
create table SAREFTR_GREOEUV
(
  CDEGREOEUV VARCHAR(3) not null primary key,
  FILTRE DECIMAL(20) not null,
  ORDAFF INT default 0  not null,
  COM VARCHAR(300),
  DATCRE DATETIME not null,
  USERCRE VARCHAR(60) not null,
  DATMAJ DATETIME  not null,
  USERMAJ VARCHAR(60) not null,
  DATDBTVLD DATETIME not null,
  DATFINVLD DATETIME,
  CDETYPIDE12 VARCHAR(10) not null,
  INDCMPLX CHAR default 'O'  not null,
  REGPMTGRE VARCHAR(3) not null,
  CDEMWCATEG VARCHAR(3) default 'UNC'  not null,
  CDEGREDIF VARCHAR(10),
  CDESSTYPIDE12 VARCHAR(3)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_LIGNE_PREPREP_FV;
CREATE TABLE PRIAM_LIGNE_PREPREP_FV (
                                     id int(11) NOT NULL AUTO_INCREMENT,
                                     typRepart varchar(10),
                                     cdeCisac varchar(10) DEFAULT NULL,
                                     cdeTer bigint(20) DEFAULT NULL,
                                     rionEffet int(11) DEFAULT NULL,
                                     cdeFamilTypUtil varchar(45) DEFAULT NULL,
                                     numProg int(11) DEFAULT NULL,
                                     cdeUtil varchar(45) DEFAULT NULL,
                                     cdeTypUtil varchar(45) DEFAULT NULL,
                                     cdeModFac varchar(45) DEFAULT NULL,
                                     cdeTypProg varchar(45) DEFAULT NULL,
                                     cdeCompl varchar(45) DEFAULT NULL,
                                     libProg varchar(100) DEFAULT NULL,
                                     datDbtProg datetime DEFAULT NULL,
                                     datFinProg datetime DEFAULT NULL,
                                     ide12 bigint(20) DEFAULT NULL,
                                     cdeTypIde12 varchar(45) DEFAULT NULL,
                                     datSitu DATE DEFAULT NULL,
                                     datConslt DATE DEFAULT NULL,
                                     durDif int(11) DEFAULT NULL,
                                     nbrDif int(11) DEFAULT NULL,
                                     typMt varchar(3) DEFAULT NULL,
                                     mt double DEFAULT NULL,
                                     cdeTypDrtSacem varchar(10) DEFAULT NULL,
                                     coadPayer int(10) null,
                                     idSteAd varchar(10) null,
                                     rolAd varchar(50) null,
                                     cleAd double null,
                                     cdeTypProtec varchar(5) null,
                                     coadOriEdtr INT(8) DEFAULT NULL,
                                     idSteOriEdtr BIGINT(15) DEFAULT NULL,
                                     numCatal INT(9) DEFAULT NULL,
                                     points double DEFAULT NULL,
                                     numPers INT(11) DEFAULT NULL;
                                     PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

COMMIT;