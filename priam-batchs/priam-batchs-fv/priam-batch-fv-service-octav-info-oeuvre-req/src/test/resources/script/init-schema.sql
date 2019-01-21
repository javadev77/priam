DROP TABLE IF EXISTS PRIAM_LIGNE_PROGRAMME_FV;
DROP TABLE IF EXISTS PRIAM_FICHIER;
DROP TABLE IF EXISTS PRIAM_STATUT;
DROP TABLE IF EXISTS SAREFTJ_LIBFAMITYPUTIL;
DROP TABLE IF EXISTS SAREFFT_LIBTYPUTIL;
DROP TABLE IF EXISTS SAREFFR_TYPUTIL;
DROP TABLE IF EXISTS SAREFTJ_LIBUTIL;


DROP TABLE IF EXISTS SAREFFT_FAMILTYPUTIL;
DROP TABLE IF EXISTS PRIAM_FICHIER_LOG;
DROP TABLE IF EXISTS PRIAM_LIGNE_PROGRAMME_TRAITEMENT_BATCH;
DROP TABLE IF EXISTS PRIAM_LIGNE_PROGRAMME_CMS;

CREATE TABLE PRIAM_FICHIER_LOG (
 ID  BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
 ID_FICHIER BIGINT DEFAULT NULL,
 LOG varchar(500) DEFAULT NULL,
 DATE datetime DEFAULT NULL,
 PRIMARY KEY (ID)
);
CREATE TABLE PRIAM_STATUT (
 CODE        VARCHAR(255) NOT NULL,
 LIBELLE     VARCHAR(255),
 DESCRIPTION VARCHAR(255),
 PRIMARY KEY (CODE)
);
CREATE TABLE SAREFTJ_LIBUTIL (
 `CDELNG` varchar(3) NOT NULL ,
 `CDEUTIL` varchar(10) NOT NULL ,
 `LIBUTIL` varchar(300) DEFAULT NULL,
 `LIBABRGUTIL` varchar(25) DEFAULT NULL,
 `DATCRE` date DEFAULT NULL,
 `USERCRE` varchar(60) DEFAULT NULL,
 `DATMAJ` date DEFAULT NULL,
 `USERMAJ` varchar(60) DEFAULT NULL ,
 `LIBFEUILLET` varchar(4) DEFAULT NULL,
 PRIMARY KEY (`CDELNG`,`CDEUTIL`)
);

CREATE TABLE SAREFTJ_LIBFAMITYPUTIL (

 CODE    VARCHAR(255) NOT NULL,
 LIBELLE VARCHAR(255),
 CDELNG  VARCHAR(2),


 PRIMARY KEY (CODE, CDELNG)

);

CREATE TABLE SAREFFT_LIBTYPUTIL (

 CODE    VARCHAR(255) NOT NULL,
 CDELNG  VARCHAR(2),
 LIBELLE VARCHAR(255),

 PRIMARY KEY (CODE, CDELNG)

);
-- -------------------------------
-- Table SAREFFT_FAMILTYPUTIL
-- ------------------------------
CREATE TABLE SAREFFT_FAMILTYPUTIL (
 CDEFAMILTYPUTIL VARCHAR(10) NOT NULL,
 DATDBTVLD       DATE        NOT NULL,
 DATFINVLD       DATE,

 CONSTRAINT SAREFFT_FAMILTYPUTIL_PK PRIMARY KEY (CDEFAMILTYPUTIL)
);

-- -------------------------------
-- Table SAREFFR_TYPUTIL
-- ------------------------------
CREATE TABLE SAREFTR_TYPUTIL (
 CDETYPUTIL      VARCHAR(10) NOT NULL,
 CDEFAMILTYPUTIL VARCHAR(10) NOT NULL,
 DATDBTVLD       DATE        NOT NULL,
 DATFINVLD       DATE,

 CONSTRAINT SAREFTR_TYPUTIL_PK PRIMARY KEY (CDETYPUTIL),
 CONSTRAINT PRIAM_TYPUT_TYPUTILFAMILL_FK FOREIGN KEY (CDEFAMILTYPUTIL) REFERENCES SAREFFT_FAMILTYPUTIL (CDEFAMILTYPUTIL)
);

CREATE TABLE PRIAM_FICHIER (
 ID              BIGINT NOT NULL GENERATED BY DEFAULT AS IDENTITY,
 NOM             VARCHAR(255) DEFAULT NULL,
 CDEFAMILTYPUTIL VARCHAR(10),
 CDETYPUTIL      VARCHAR(10),
 DATE_DEBUT_CHGT DATETIME,
 DATE_FIN_CHGT   DATETIME,
 NB_LIGNES       BIGINT,
 STATUT_CODE     VARCHAR(255),
 TYPE_FICHIER    VARCHAR (25),
 STATUT_ENRICHISSEEMNT VARCHAR(255) COMMENT 'STATUT ENRICHISSEMENT',

 -- CONSTRAINT FK_STATUT FOREIGN KEY (STATUT_CODE) REFERENCES PRIAM_STATUT(CODE),
 CONSTRAINT FK_FAMILLE FOREIGN KEY (CDEFAMILTYPUTIL) REFERENCES SAREFFT_FAMILTYPUTIL (CDEFAMILTYPUTIL),
 CONSTRAINT FK_TYPE_UTIL FOREIGN KEY (CDETYPUTIL) REFERENCES SAREFTR_TYPUTIL (CDETYPUTIL),
 PRIMARY KEY (ID)
);


CREATE TABLE PRIAM_LIGNE_PROGRAMME_FV
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
 `idOeuvreManuel` int(11) DEFAULT NULL,
 `SEL_EN_COURS` tinyint(4) DEFAULT '1',
 `LIBELLEUTILISATEUR` VARCHAR(45)  NULL,
 `isOeuvreComplex` tinyint(4) DEFAULT '0',
 CONSTRAINT FK_ID_FICHIER
 FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_FICHIER (ID)
);

CREATE TABLE PRIAM_LIGNE_PROGRAMME_TRAITEMENT_BATCH
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
 `idOeuvreManuel` int(11) DEFAULT NULL,
 `SEL_EN_COURS` tinyint(4) DEFAULT '1',
 LIBELLEUTILISATEUR VARCHAR(45)  NULL,
 `RION_N_ANT` varchar(7) NULL,
 CONSTRAINT FK_ID_FICHIER_TRAITEMENENT_BATCH
 FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_FICHIER (ID)
);