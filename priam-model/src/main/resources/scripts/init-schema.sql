
-- Habib Benmerzouka
USE priam_app;

DROP TABLE IF EXISTS PRIAM_FICHIER_HISTORIQUE;
DROP TABLE IF EXISTS PRIAM_LIGNE_PROGRAMME;
DROP TABLE IF EXISTS PRIAM_FICHIER;
DROP TABLE IF EXISTS PRIAM_STATUT;
DROP TABLE IF EXISTS PRIAM_LIBFAMITYPUTIL;
DROP TABLE IF EXISTS PRIAM_LIBTYPUTIL;
DROP TABLE IF EXISTS PRIAM_TYPUTIL;
DROP TABLE IF EXISTS PRIAM_FAMILTYPUTIL;
DROP TABLE IF EXISTS PRIAM_STATUT_PROGRAMME;
DROP TABLE IF EXISTS PRIAM_RION;
DROP TABLE IF EXISTS PRIAM_PROGRAMME;
DROP TABLE IF EXISTS PRIAM_PARAMAPPLI;

CREATE TABLE PRIAM_PARAMAPPLI
(
  CDEPARAM VARCHAR(100),
  LIBSTAT VARCHAR(450),
  VAL VARCHAR(256),
  PRIMARY KEY(CDEPARAM)
);
CREATE TABLE PRIAM_PROGRAMME_SEQUENCE(

  ANNEE VARCHAR(4),
  CODESEQUENCE BIGINT NOT NULL ,
  PRIMARY KEY(CODESEQUENCE,ANNEE)
);

CREATE TABLE PRIAM_STATUT (
  CODE VARCHAR(255) NOT NULL  DEFAULT 'EN_COURS',
  LIBELLE VARCHAR(255),

  PRIMARY KEY(CODE)
);

CREATE TABLE PRIAM_FICHIER_HISTORIQUE (
  ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  ID_FICHIER BIGINT NOT NULL,
  CODE_STATUT VARCHAR(255) NOT NULL,

  PRIMARY KEY(ID),
  CONSTRAINT CODE_STATUT_FK FOREIGN KEY (CODE_STATUT) REFERENCES PRIAM_STATUT(CODE)
);

CREATE TABLE PRIAM_LIBFAMITYPUTIL (

  CODE VARCHAR(255) NOT NULL  COMMENT 'Code Famille',
  LIBELLE VARCHAR(255) COMMENT 'Libelle du code',
  CDELNG VARCHAR(2) COMMENT 'Traduction',


  PRIMARY KEY(CODE, CDELNG)
  -- CONSTRAINT LIBFAMITYPUTIL_CODE_FK FOREIGN KEY (CODE) REFERENCES PRIAM_FAMILTYPUTIL(CDEFAMILTYPUTIL)

);

CREATE TABLE PRIAM_LIBTYPUTIL (

  CODE VARCHAR(255) NOT NULL COMMENT 'Code Type Utilisation',
  CDELNG VARCHAR(2) COMMENT 'tradution',
  LIBELLE VARCHAR(255) COMMENT 'Libelle du code',

  PRIMARY KEY(CODE, CDELNG)
  -- CONSTRAINT CODE_FK FOREIGN KEY (CODE) REFERENCES PRIAM_TYPUTIL(CDETYPUTIL)

);
-- -------------------------------
-- Table PRIAM_FAMILTYPUTIL
-- ------------------------------
CREATE TABLE PRIAM_FAMILTYPUTIL (
  CDEFAMILTYPUTIL VARCHAR(10) NOT NULL COMMENT 'Code Famille Type Utilisation',
  DATDBTVLD DATE NOT NULL COMMENT 'Date debut validite',
  DATFINVLD DATE COMMENT 'Date de fin de validite',


  CONSTRAINT PRIAM_FAMILTYPUTIL_PK PRIMARY KEY (CDEFAMILTYPUTIL)
);

-- -------------------------------
-- Table PRIAM_TYPUTIL
-- ------------------------------
CREATE TABLE PRIAM_TYPUTIL (
  CDETYPUTIL VARCHAR(10) NOT NULL COMMENT 'Code type d utilisation',
  CDEFAMILTYPUTIL VARCHAR(10) NOT NULL COMMENT 'Code Famille Type Utilisation',
  DATDBTVLD DATE NOT NULL COMMENT 'Date debut validite',
  DATFINVLD DATE COMMENT 'Date de fin de validite',

  CONSTRAINT PRIAM_TYPUTIL_PK PRIMARY KEY (CDETYPUTIL),
  CONSTRAINT PRIAM_TYPUT_TYPUTILFAMILL_FK FOREIGN KEY (CDEFAMILTYPUTIL) REFERENCES PRIAM_FAMILTYPUTIL(CDEFAMILTYPUTIL)
);

CREATE TABLE PRIAM_FICHIER (
  ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  NOM varchar(255 ) DEFAULT NULL COMMENT 'Nom du fichier',
  CDEFAMILTYPUTIL VARCHAR (10) COMMENT 'Famille',
  CDETYPUTIL VARCHAR (10) COMMENT 'Type utilisation',
  DATE_DEBUT_CHGT DATETIME COMMENT 'Dete de Debut de chargement',
  DATE_FIN_CHGT DATETIME COMMENT 'Dete de Fin de chargement',
  NB_LIGNES BIGINT COMMENT 'Nombre de lignes dans le fichier',
  STATUT_CODE VARCHAR(255) COMMENT 'Statut primut du fichier',

  -- CONSTRAINT FK_STATUT FOREIGN KEY (STATUT_CODE) REFERENCES PRIAM_STATUT(CODE),
  CONSTRAINT FK_FAMILLE FOREIGN KEY (CDEFAMILTYPUTIL) REFERENCES PRIAM_FAMILTYPUTIL(CDEFAMILTYPUTIL),
  CONSTRAINT FK_TYPE_UTIL FOREIGN KEY (CDETYPUTIL) REFERENCES PRIAM_TYPUTIL(CDETYPUTIL),
  CONSTRAINT FK_STATUT_CODE FOREIGN KEY (STATUT_CODE) REFERENCES PRIAM_STATUT(CODE),
  PRIMARY KEY(ID)
);


CREATE TABLE PRIAM_LIGNE_PROGRAMME (
  `id` int(11) AUTO_INCREMENT NOT NULL,
  `cdeCisac` varchar(3) DEFAULT NULL,
  `cdeTer` bigint(20) DEFAULT NULL,
  `rionEffet` int(11) DEFAULT NULL,
  `cdeFamilTypUtil` varchar(45) DEFAULT NULL,
  `cdeModFac` varchar(45) DEFAULT NULL,
  `numProg` int(11) DEFAULT NULL,
  `keyLigPenel` int(11) DEFAULT NULL,
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
  `typMt` varchar(45) DEFAULT NULL,
  ID_FICHIER BIGINT NOT NULL,


  CONSTRAINT FK_ID_FICHIER FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_FICHIER(ID),
  PRIMARY KEY(id)
);

CREATE TABLE PRIAM_STATUT_PROGRAMME (
  CODE VARCHAR(50),
  LIBELLE VARCHAR(255),

  PRIMARY KEY(CODE)
);

CREATE TABLE PRIAM_RION (
  RION NUMERIC(4,0),
  DATCALC DATE,
  DATRGLMT DATE,
  FILTRE NUMERIC(20, 0),
  ORDAFF NUMERIC(5, 0) DEFAULT 0,
  COM VARCHAR(300),
  DATCRE DATETIME,
  USERCRE VARCHAR(60),
  DATMAJ DATETIME,
  USERMAJ VARCHAR(60)
);

ALTER TABLE PRIAM_RION ADD CONSTRAINT PRIAM_RION_PK PRIMARY KEY (RION);

create table priam_app.PRIAM_PROGRAMME
(
	NUMPROG varchar(8) not null primary key,
	NOM varchar(255) null,
	RION_THEORIQUE decimal(4) null,
	CDEFAMILTYPUTIL varchar(10) not null comment 'Code Famille Type Utilisation',
	CDETYPUTIL varchar(10) not null comment 'Code type d utilisation',
	TYPE_REPART varchar(45) null,
	DATE_CREATION datetime null comment 'Dete de creation du programme',
	STATUT_PROG_CODE varchar(50) null comment 'Statut du programme',
	RION_PAIEMENT decimal(4) null,
	USERCRE varchar(60) null,
	DATMAJ datetime null,
	USERMAJ varchar(60) null,
	DATAFFECT datetime null,
	USERAFFECT varchar(60) null,
	DATE_VALIDATION datetime null,
	USER_VALIDATION varchar(60) null,
	DATE_DBT_PRG date null,
	DATE_FIN_PRG date null,
	CDE_TER decimal(4) null,
	constraint FK_RION_THEORIQUE foreign key (RION_THEORIQUE) references priam_app.PRIAM_RION (RION),
	constraint FK_PROGRAMME_FAMILLE foreign key (CDEFAMILTYPUTIL) references priam_app.PRIAM_FAMILTYPUTIL (CDEFAMILTYPUTIL),
	constraint FK_PROGRAMME_TYPE_UTIL foreign key (CDETYPUTIL) references priam_app.PRIAM_TYPUTIL (CDETYPUTIL),
	constraint FK_STATUT_PROG_CODE foreign key (STATUT_PROG_CODE) references priam_app.PRIAM_STATUT_PROGRAMME (CODE),
	constraint FK_RION_PAIEMENT foreign key (RION_PAIEMENT) references priam_app.PRIAM_RION (RION)
);

SET FOREIGN_KEY_CHECKS=0;

-- -------------------------------------
-- ----- PRIAM_PROG_VIEW ---------------
-- -------------------------------------

DROP VIEW IF EXISTS PRIAM_PROG_VIEW;
DROP VIEW IF EXISTS PRIAM_LIGNE_PROG_VIEW;



create view PRIAM_PROG_VIEW as
SELECT DISTINCT
    `pr`.`NUMPROG`                           AS `NUMPROG`,
    `pr`.`NOM`                               AS `NOM`,
    `pr`.`RION_THEORIQUE`                    AS `RION_THEORIQUE`,
    `pr`.`CDEFAMILTYPUTIL`                   AS `CDEFAMILTYPUTIL`,
    `pr`.`CDETYPUTIL`                        AS `CDETYPUTIL`,
    `pr`.`TYPE_REPART`                       AS `TYPE_REPART`,
    `pr`.`DATE_CREATION`                     AS `DATE_CREATION`,
    `pr`.`STATUT_PROG_CODE`                  AS `STATUT_PROG_CODE`,
    `pr`.`RION_PAIEMENT`                     AS `RION_PAIEMENT`,
    `pr`.`USERCRE`                           AS `USERCRE`,
    `pr`.`DATMAJ`                            AS `DATMAJ`,
    `pr`.`USERMAJ`                           AS `USERMAJ`,
    `pr`.`DATAFFECT`                         AS `DATAFFECT`,
    `pr`.`USERAFFECT`                        AS `USERAFFECT`,
    (SELECT count(`f`.`NUMPROG`)
     FROM PRIAM_FICHIER `f`
     WHERE (`pr`.`NUMPROG` = `f`.`NUMPROG`)) AS `fichiers`,
    `pr`.`DATE_DBT_PRG`                      AS `DATEDBTPRG`,
    `pr`.`DATE_FIN_PRG`                      AS `DATEFINPRG`,
    `pr`.`CDE_TER`                           AS `CDETER`,
    `pr`.`USER_VALIDATION`                   AS `USERVALIDATION`,
    `pr`.`DATE_VALIDATION`                   AS `DATEVALIDATION`
  FROM PRIAM_PROGRAMME `pr`
  GROUP BY `pr`.`NUMPROG`;



CREATE OR REPLACE VIEW PRIAM_LIGNE_PROG_VIEW AS
  SELECT lpr1.ide12 as IDE12,f.numProg as NUMPROG,count(lpr1.ide12) as QUANTITE ,sum(lpr1.durDif) as DURDIF
  FROM PRIAM_LIGNE_PROGRAMME  lpr1,PRIAM_FICHIER f
  WHERE lpr1.ID_FICHIER = f.ID
        AND lpr1.ide12 is NOT  NULL
        AND f.numProg is NOT  NULL
  GROUP BY lpr1.ide12,lpr1.numProg;


CREATE TABLE PRIAM_LIGNE_PREPREP (
  `id` int(11) AUTO_INCREMENT NOT NULL,
  `cdeCisac` varchar(3) DEFAULT NULL,
  `cdeTer` bigint(20) DEFAULT NULL,
  `rionEffet` int(11) DEFAULT NULL,
  `cdeFamilTypUtil` varchar(45) DEFAULT NULL,
  `cdeModFac` varchar(45) DEFAULT NULL,
  `numProg` int(11) DEFAULT NULL,
  `keyLigPenel` int(11) DEFAULT NULL,
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

  PRIMARY KEY(id)

);



