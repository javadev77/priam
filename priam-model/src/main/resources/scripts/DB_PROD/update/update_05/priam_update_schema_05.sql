USE priam_app;

DROP TABLE IF EXISTS PRIAM_CATCMS_RDO;
CREATE TABLE PRIAM_CATCMS_RDO (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  TYPE_CMS VARCHAR(3) COMMENT 'FR poour France et ANF pour Antilles',
  IDE12 bigint(20) DEFAULT NULL COMMENT 'Code oeuvre RDO',
  TITRE VARCHAR(255) COMMENT 'titre oeuvre RDO',
  TYP_UTIL_GEN VARCHAR(255) COMMENT 'type utilisation RDO',
  DATE_CATAL DATE DEFAULT NULL COMMENT 'date catalogue RDO',
  ROLE VARCHAR(255) COMMENT 'role RDO',
  PARTICIPANT VARCHAR(255) COMMENT 'participant RDO',
  POURCENTAGE_DP TINYINT(4) COMMENT 'pourcentage DP',
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_STATUT;
CREATE TABLE PRIAM_CATCMS_STATUT (
  CODE varchar(255) NOT NULL DEFAULT 'EN_COURS',
  LIBELLE varchar(255) DEFAULT NULL,
  PRIMARY KEY (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_FICHIER;
CREATE TABLE PRIAM_CATCMS_FICHIER (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  NOM varchar(255) DEFAULT NULL COMMENT 'Nom du fichier',
  DATE_DEBUT_CHGT datetime DEFAULT NULL COMMENT 'Dete de Debut de chargement',
  DATE_FIN_CHGT datetime DEFAULT NULL COMMENT 'Dete de Fin de chargement',
  NB_LIGNES bigint(20) DEFAULT NULL COMMENT 'Nombre de lignes dans le fichier',
  STATUT_CODE varchar(255) DEFAULT NULL COMMENT 'Statut primut du fichier',
  TYPE_FICHIER varchar(25) DEFAULT NULL COMMENT 'type de fichier FR ou ANT pour differencier l envoi',
  PRIMARY KEY (ID),
  KEY FK_STATUT_CATCMS_CODE (STATUT_CODE),
  CONSTRAINT FK_STATUT_CATCMS_CODE FOREIGN KEY (STATUT_CODE) REFERENCES PRIAM_CATCMS_STATUT (CODE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_FICHIER_LOG;
CREATE TABLE PRIAM_CATCMS_FICHIER_LOG (
  ID bigint(20) NOT NULL AUTO_INCREMENT,
  ID_FICHIER bigint(20) DEFAULT NULL,
  LOG varchar(1024) DEFAULT NULL,
  DATE datetime DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY ID_CATCMS_FICHIER_FK (ID_FICHIER),
  CONSTRAINT ID_CATCMS_FICHIER_FK FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_CATCMS_FICHIER (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_JOURNAL;
CREATE TABLE PRIAM_CATCMS_JOURNAL (
  ID int(11) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  EVENEMENT varchar(100) DEFAULT NULL COMMENT 'intitule evenement',
  IDE12 bigint(20) DEFAULT NULL COMMENT 'ide12',
  DATE datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'date evenement',
  UTILISATEUR varchar(255) DEFAULT NULL COMMENT 'id utilisateur',
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS PRIAM_CATCMS_PENEF;
CREATE TABLE PRIAM_CATCMS_PENEF (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  CDE_TYP_IDE12 VARCHAR(255),
  CDE_TYP_UTIL_ORI VARCHAR(255),
  TITRE_OEUVRE VARCHAR(255),
  ROLE_PARTICIPANT1 VARCHAR(255) ,
  NOM_PARTICIPANT1 VARCHAR(255),
  ID_FICHIER bigint(20) DEFAULT NULL,

  PRIMARY KEY (ID),

  CONSTRAINT `FK_ID_FICHIER_PENEF` FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_CATCMS_FICHIER(ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS PRIAM_CATCMS_PARTICIPANTS;
CREATE TABLE PRIAM_CATCMS_PARTICIPANTS (
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  ROLE VARCHAR(255),
  PARTICIPANT VARCHAR(255),
  PRIMARY KEY (IDE12, ROLE, TYPE_CMS)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_PARTICIPANTS_TRTMT;
CREATE TABLE PRIAM_CATCMS_PARTICIPANTS_TRTMT (
  ID INT AUTO_INCREMENT,
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  ROLE VARCHAR(255),
  PARTICIPANT VARCHAR(255),
  STATUT BINARY(1) DEFAULT '1',
  PRIMARY KEY (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS PRIAM_CATCMS_CATALOGUE;
CREATE TABLE PRIAM_CATCMS_CATALOGUE (
  ID bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'identifiant technique',
  TYPE_CMS VARCHAR(3),
  IDE12 bigint(20) DEFAULT NULL,
  TITRE VARCHAR(255),
  TYP_UTIL_GEN VARCHAR(255),
  DATE_ENTREE DATE DEFAULT NULL,
  TYPE_INSCRIPTION VARCHAR(255),
  DATE_RENOUVELLEMENT DATE DEFAULT NULL,
  DATE_SORTIE DATE DEFAULT NULL,
  TYPE_SORTIE VARCHAR(255),
  RAISON_SORTIE VARCHAR(255),
  PRIMARY KEY (ID),
  CONSTRAINT `FK_IDE12` FOREIGN KEY (IDE12, TYPE_CMS) REFERENCES PRIAM_CATCMS_PARTICIPANTS(IDE12, TYPE_CMS)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

