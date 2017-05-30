-- Habib Benmerzouka


DROP TABLE IF EXISTS PRIAM_FICHIER;
DROP TABLE IF EXISTS PRIAM_STATUT;
DROP TABLE IF EXISTS PRIAM_LIBFAMITYPUTIL;
DROP TABLE IF EXISTS PRIAM_LIBTYPUTIL;
DROP TABLE IF EXISTS PRIAM_FAMILTYPUTIL;
DROP TABLE IF EXISTS PRIAM_TYPUTIL;
DROP TABLE IF EXISTS PRIAM_LIGNE_PROGRAMME;
DROP TABLE IF EXISTS PRIAM_FICHIER_HISTORIQUE;


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

);

CREATE TABLE PRIAM_LIBTYPUTIL (

  CODE VARCHAR(255) NOT NULL COMMENT 'Code Type Utilisation',
  CDELNG VARCHAR(2) COMMENT 'tradution',
  LIBELLE VARCHAR(255) COMMENT 'Libelle du code',

  PRIMARY KEY(CODE, CDELNG)

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
