-- Habib Benmerzouka


DROP TABLE IF EXISTS PRIAM_FICHIER;
DROP TABLE IF EXISTS PRIAM_STATUT;
DROP TABLE IF EXISTS SAREFTJ_LIBFAMITYPUTIL;
DROP TABLE IF EXISTS SAREFFT_LIBTYPUTIL;
DROP TABLE IF EXISTS SAREFFT_FAMILTYPUTIL;
DROP TABLE IF EXISTS SAREFFR_TYPUTIL;
DROP TABLE IF EXISTS PRIAM_LIGNE_PROGRAMME;
DROP TABLE IF EXISTS PRIAM_FICHIER_HISTORIQUE;
DROP TABLE IF EXISTS PRIAM_PARAMAPPLI;
DROP TABLE IF EXISTS PRIAM_PROGRAMME_SEQUENCE;

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

CREATE TABLE SAREFTJ_LIBFAMITYPUTIL (

  CODE VARCHAR(255) NOT NULL  COMMENT 'Code SareftrFamiltyputil',
  LIBELLE VARCHAR(255) COMMENT 'Libelle du code',
  CDELNG VARCHAR(2) COMMENT 'Traduction',


  PRIMARY KEY(CODE, CDELNG)

);

CREATE TABLE SAREFFT_LIBTYPUTIL (

  CODE VARCHAR(255) NOT NULL COMMENT 'Code Type Utilisation',
  CDELNG VARCHAR(2) COMMENT 'tradution',
  LIBELLE VARCHAR(255) COMMENT 'Libelle du code',

  PRIMARY KEY(CODE, CDELNG)

);
-- -------------------------------
-- Table SAREFFT_FAMILTYPUTIL
-- ------------------------------
CREATE TABLE SAREFFT_FAMILTYPUTIL (
  CDEFAMILTYPUTIL VARCHAR(10) NOT NULL COMMENT 'Code SareftrFamiltyputil Type Utilisation',
  DATDBTVLD DATE NOT NULL COMMENT 'Date debut validite',
  DATFINVLD DATE COMMENT 'Date de fin de validite',

  CONSTRAINT SAREFFT_FAMILTYPUTIL_PK PRIMARY KEY (CDEFAMILTYPUTIL)
);

-- -------------------------------
-- Table SAREFFR_TYPUTIL
-- ------------------------------
CREATE TABLE SAREFFR_TYPUTIL (
  CDETYPUTIL VARCHAR(10) NOT NULL COMMENT 'Code type d utilisation',
  CDEFAMILTYPUTIL VARCHAR(10) NOT NULL COMMENT 'Code SareftrFamiltyputil Type Utilisation',
  DATDBTVLD DATE NOT NULL COMMENT 'Date debut validite',
  DATFINVLD DATE COMMENT 'Date de fin de validite',

  CONSTRAINT SAREFFR_TYPUTIL_PK PRIMARY KEY (CDETYPUTIL),
  CONSTRAINT PRIAM_TYPUT_TYPUTILFAMILL_FK FOREIGN KEY (CDEFAMILTYPUTIL) REFERENCES SAREFFT_FAMILTYPUTIL(CDEFAMILTYPUTIL)
);

CREATE TABLE PRIAM_FICHIER (
  ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  NOM varchar(255 ) DEFAULT NULL COMMENT 'Nom du fichier',
  CDEFAMILTYPUTIL VARCHAR (10) COMMENT 'SareftrFamiltyputil',
  CDETYPUTIL VARCHAR (10) COMMENT 'Type utilisation',
  DATE_DEBUT_CHGT DATETIME COMMENT 'Dete de Debut de chargement',
  DATE_FIN_CHGT DATETIME COMMENT 'Dete de Fin de chargement',
  NB_LIGNES BIGINT COMMENT 'Nombre de lignes dans le fichier',
  STATUT_CODE VARCHAR(255) COMMENT 'Statut primut du fichier',

  -- CONSTRAINT FK_STATUT FOREIGN KEY (STATUT_CODE) REFERENCES PRIAM_STATUT(CODE),
  CONSTRAINT FK_FAMILLE FOREIGN KEY (CDEFAMILTYPUTIL) REFERENCES SAREFFT_FAMILTYPUTIL(CDEFAMILTYPUTIL),
  CONSTRAINT FK_TYPE_UTIL FOREIGN KEY (CDETYPUTIL) REFERENCES SAREFFR_TYPUTIL(CDETYPUTIL),
  CONSTRAINT FK_STATUT_CODE FOREIGN KEY (STATUT_CODE) REFERENCES PRIAM_STATUT(CODE),
  PRIMARY KEY(ID)
);


CREATE TABLE PRIAM_LIGNE_PROGRAMME
(
  id              INT          NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  cdeCisac        VARCHAR(3)   NULL,
  cdeFamilTypUtil VARCHAR(45)  NULL,
  numProg         INT          NULL,
  cdeUtil         VARCHAR(45)  NULL,
  cdeTypUtil      VARCHAR(45)  NULL,
  cdeGreDif       VARCHAR(45)  NULL,
  cdeModDif       VARCHAR(45)  NULL,
  cdeTypIde12     VARCHAR(45)  NULL,
  ide12           BIGINT       NULL,
  durDif          INT          NULL,
  nbrDif          INT          NULL,
  mt              DOUBLE       NULL,
  ctna            VARCHAR(45)  NULL,
  paramCoefHor    VARCHAR(10)  NULL,
  durDifCtna      INT          NULL,
  cdeLng          VARCHAR(45)  NULL,
  indDoubSsTit    VARCHAR(45)  NULL,
  tax             DOUBLE       NULL,
  typMt           VARCHAR(45)  NULL,
  ID_FICHIER      BIGINT       NOT NULL,
  cdeGreIde12Cmplx VARCHAR(45)  NULL,
  cdeGreIde12 VARCHAR(45)  NULL,
  titreOriCmplx VARCHAR(250)  NULL,
  titreAltPppalCmplx VARCHAR(250)  NULL,
  titreOriOeuvPereCmplx VARCHAR(250)  NULL,
  titreAltOeuvPereCmplx VARCHAR(250)  NULL,
  titreOeuvre VARCHAR(250)  NULL,
  cdePaysOriIso4NCmplx VARCHAR(45)  NULL,
  realisateurCmplx VARCHAR(45)  NULL,
  roleParticipant1 VARCHAR(45)  NULL,
  nomParticipant1 VARCHAR(45)  NULL,
  cdeTypUtilOri VARCHAR(45)  NULL,
  cdeFamilTypUtilOri VARCHAR(45)  NULL,
  date_insertion DATETIME NULL DEFAULT NOW(),
  utilisateur VARCHAR(45) NULL DEFAULT 'Batch Extraction',
  ajout VARCHAR(45) NULL DEFAULT 'Automatique',
  selection BOOLEAN DEFAULT FALSE,
  CONSTRAINT FK_ID_FICHIER
  FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_FICHIER (ID)
);


CREATE TABLE PRIAM_STATUT_PROGRAMME (
  CODE VARCHAR(50),
  LIBELLE VARCHAR(255),

  PRIMARY KEY(CODE)
);

CREATE TABLE SAREFTR_RION (
  RION NUMERIC(4,0) NOT NULL,
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

ALTER TABLE SAREFTR_RION ADD CONSTRAINT SAREFTR_RION_PK PRIMARY KEY (RION);

create table PRIAM_PROGRAMME
(
	NUMPROG varchar(8) not null
		primary key,
	NOM varchar(255) null,
	RION_THEORIQUE decimal(4) null,
	CDEFAMILTYPUTIL varchar(10) not null comment 'Code SareftrFamiltyputil Type Utilisation',
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
	constraint FK_RION_THEORIQUE foreign key (RION_THEORIQUE) references SAREFTR_RION (RION),
	constraint FK_PROGRAMME_FAMILLE foreign key (CDEFAMILTYPUTIL) references SAREFFT_FAMILTYPUTIL (CDEFAMILTYPUTIL),
	constraint FK_PROGRAMME_TYPE_UTIL foreign key (CDETYPUTIL) references SAREFFR_TYPUTIL (CDETYPUTIL),
	constraint FK_STATUT_PROG_CODE foreign key (STATUT_PROG_CODE) references PRIAM_STATUT_PROGRAMME (CODE),
	constraint FK_RION_PAIEMENT foreign key (RION_PAIEMENT) references SAREFTR_RION (RION)
);

ALTER TABLE PRIAM_FICHIER ADD COLUMN NUMPROG VARCHAR(8);
ALTER TABLE PRIAM_FICHIER ADD CONSTRAINT FK_NUMPROG FOREIGN KEY (NUMPROG) REFERENCES PRIAM_PROGRAMME(NUMPROG);

-- -------------------------------------
-- ----- PRIAM_PROG_VIEW ---------------
-- -------------------------------------

DROP VIEW IF EXISTS PRIAM_PROG_VIEW;

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

CREATE TABLE PRIAM_ROLE
(
    ID BIGINT NOT NULL AUTO_INCREMENT COMMENT 'Identifiant technique',
    EXTERNAL_ID VARCHAR(255) COMMENT 'Code Role',
    ROLE VARCHAR(255) COMMENT 'Description',
    PRIMARY KEY (ID)
);

CREATE TABLE PRIAM_ROLE_RIGHTS
(
    ROLE_ID BIGINT NOT NULL COMMENT 'Role Id',
    RIGHTS VARCHAR(255) COMMENT 'Habilitation',
    LIBELLE VARCHAR(255) COMMENT 'Libelle habilitation',
    CONSTRAINT FK_2555xwvbf9ilta6fgul5al4ch FOREIGN KEY (ROLE_ID) REFERENCES PRIAM_ROLE (ID)

);

CREATE INDEX FK_2555xwvbf9ilta6fgul5al4ch ON PRIAM_ROLE_RIGHTS (ROLE_ID);