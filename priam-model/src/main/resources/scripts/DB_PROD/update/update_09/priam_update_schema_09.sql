USE priam_app;

DROP TABLE IF EXISTS PRIAM_FICHIER_FV_ENRICHISSEMENT_LOG;
CREATE TABLE PRIAM_FICHIER_FV_ENRICHISSEMENT_LOG (
                                   ID bigint(20) NOT NULL AUTO_INCREMENT,
                                   ID_FICHIER bigint(20) DEFAULT NULL,
                                   STATUT varchar(250) DEFAULT NULL,
                                   DATE datetime DEFAULT NULL,
                                   PRIMARY KEY (ID),
                                   KEY ID_FICHIER_FV_FK (ID_FICHIER),
                                   CONSTRAINT ID_FICHIER_FV_FK FOREIGN KEY (ID_FICHIER) REFERENCES PRIAM_FICHIER (ID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;