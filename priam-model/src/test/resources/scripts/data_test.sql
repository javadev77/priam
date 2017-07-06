
-- ------------------------------------------
-- -------- TABLE PRIAM_STATUT --------------
-- ------------------------------------------
INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
('EN_COURS', 'En cours');

INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
('CHARGEMENT_OK', 'Chargement OK');

INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
('CHARGEMENT_KO', 'Chargement KO');

INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
('AFFECTE', 'Affecté');

INSERT INTO PRIAM_STATUT
(CODE, LIBELLE) VALUES
('ABANDONNE', 'Abandonné');
INSERT INTO PRIAM_PROGRAMME_SEQUENCE(PREFIX,ANNEE,CODESEQUENCE) VALUES ('BR','2017',1);
INSERT INTO PRIAM_PROGRAMME_SEQUENCE(PREFIX,ANNEE,CODESEQUENCE) VALUES ('BR','2017',2);
INSERT INTO PRIAM_PARAMAPPLI(CDEPARAM,LIBSTAT,VAL)
VALUES ('ANNEE_SEQ_PROGRAMME','année en cours utilisé pour generer id programme','2017');
VALUES ('PREFIX','prefix','BR');

-- ---------------------------------------------------
-- ------ TABLE PRIAM_LIBFAMITYPUTIL --------------
-- -------------------------------------------------
INSERT INTO PRIAM_LIBFAMITYPUTIL (CODE, LIBELLE, CDELNG) VALUES
('COPIEPRIV', 'Copie Privée', 'FR');

INSERT INTO PRIAM_LIBFAMITYPUTIL
(CODE, LIBELLE, CDELNG) VALUES
('FDSVAL', 'Valorisation', 'FR');

INSERT INTO PRIAM_LIBFAMITYPUTIL
(CODE, LIBELLE, CDELNG) VALUES
('CMS', 'CMS', 'FR');


-- ---------------------------------------------------
-- ------ TABLE PRIAM_LIBTYPUTIL ------------------------
-- ---------------------------------------------------
INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('CPRIVSONPH', 'Copie privée sonore Phono', 'FR');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('CPRIVAUDV', 'Copie Privée Audiovisuelle', 'FR');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('CPRIVSONRD', 'Copie Privée Sonore radio', 'FR');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('COPRIVSON', 'Copie Privée Sonore', 'FR');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('CPRIVAUDPL', 'Copie Privée Audiovisuel - Part Littéraire', 'FR');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('CPRIVSONPH', 'Private copy sound (PHONO)', 'EN');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('CPRIVAUDV',  'Private copy audiovisual', 'EN');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('CPRIVSONRD', 'Private copy sound', 'EN');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('COPRIVSON', 'Copie Privée Sonore', 'EN');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('PRIME', 'Prime', 'FR');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('VALORIS', 'Fonds de valorisation', 'FR');

INSERT INTO PRIAM_LIBTYPUTIL (CODE, LIBELLE, CDELNG)
VALUES ('ENCOURG', 'Encouragement', 'FR');

-- ---------------------------------------------------
-- ------ TABLE PRIAM_FAMILTYPUTIL -------------------
-- ---------------------------------------------------
INSERT INTO PRIAM_FAMILTYPUTIL(CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
    VALUES ('COPIEPRIV', CURDATE(), NULL);

INSERT INTO PRIAM_FAMILTYPUTIL(CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('FDSVAL', CURDATE(), NULL);

INSERT INTO PRIAM_FAMILTYPUTIL(CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CMS', CURDATE(), NULL);


-- ---------------------------------------------------
-- ------ TABLE PRIAM_TYPUTIL -------------------
-- ---------------------------------------------------
INSERT INTO PRIAM_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CPRIVSONPH','COPIEPRIV', CURDATE(), NULL);

INSERT INTO PRIAM_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CPRIVAUDV','COPIEPRIV', CURDATE(), NULL);

INSERT INTO PRIAM_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CPRIVSONRD','COPIEPRIV', CURDATE(), NULL);

INSERT INTO PRIAM_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('COPRIVSON','COPIEPRIV', CURDATE(), '2017-05-04');

INSERT INTO PRIAM_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('CPRIVAUDPL','COPIEPRIV', CURDATE(), NULL);

INSERT INTO PRIAM_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('PRIME','FDSVAL', CURDATE(), NULL);

INSERT INTO PRIAM_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('VALORIS','FDSVAL', CURDATE(), NULL);


INSERT INTO PRIAM_TYPUTIL(CDETYPUTIL, CDEFAMILTYPUTIL, DATDBTVLD, DATFINVLD)
VALUES ('ENCOURG','CMS', CURDATE(), NULL);


-- ---------------------------------------------------
-- ------ TABLE PRIAM_FICHIER -------------------
-- ---------------------------------------------------

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 01', 'COPIEPRIV', 'COPRIVSON', '2017-02-04 17:15:14', NULL, 3000, 'EN_COURS');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 02', 'COPIEPRIV', 'COPRIVSON', '2017-02-03 17:15:14', NULL, 9500, 'EN_COURS');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 03', 'COPIEPRIV', 'COPRIVSON', '2017-02-01 17:15:14', NULL, 6500, 'EN_COURS');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 04', 'COPIEPRIV', 'COPRIVSON', '2017-04-01 17:15:14', NULL, 1478, 'EN_COURS');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 05', 'COPIEPRIV', 'COPRIVSON', '2017-05-01 17:10:14', NULL, 7451, 'EN_COURS');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 06', 'COPIEPRIV', 'COPRIVSON', '2017-05-02 18:15:14', NULL, 15000, 'EN_COURS');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 08', 'COPIEPRIV', 'COPRIVSON', '2017-02-01 17:15:14', NULL, 6500, 'EN_COURS');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 09', 'COPIEPRIV', 'COPRIVSON', '2017-04-01 17:15:14', '2017-04-01 22:10:11', 22000, 'CHARGEMENT_OK');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 11', 'COPIEPRIV', 'COPRIVSON', '2017-05-01 17:10:14', '2017-05-02 01:10:00', 45789, 'CHARGEMENT_OK');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 12', 'COPIEPRIV', 'COPRIVSON', '2017-05-02 18:15:14', '2017-05-01 18:50:04', 15000, 'CHARGEMENT_KO');


INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 13', 'COPIEPRIV', 'COPRIVSON', '2017-05-04 18:15:14', '2017-05-04 22:57:04', 15000, 'CHARGEMENT_KO');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 15', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 17', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'ABANDONNE');




-- ---------------------------------------------------
-- ------ TABLE PRIAM_LIGNE_PROGRAMME -------------------
-- ---------------------------------------------------

INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeFamilTypUtil, ID_FICHIER)
    VALUES ('COPIEPRIV', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 01');

INSERT INTO PRIAM_LIGNE_PROGRAMME (cdeFamilTypUtil, ID_FICHIER)
VALUES ('COPIEPRIV', SELECT ID FROM PRIAM_FICHIER WHERE NOM ='Fichier 02');


INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
    ('EN_COURS', 'En cours');

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
    ('CREE', 'Créé');

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
    ('AFFECTE', 'Affecté');

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
    ('VALIDE', 'Validé');

INSERT INTO PRIAM_STATUT_PROGRAMME
(CODE, LIBELLE) VALUES
    ('ABANDONNE', 'Abandonné');



Insert into PRIAM_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ)
values ('619','2012-03-29', '2012-07-05', '9223372036854775807','0','Répartition 2ème Trimestre 2012.', '2011-11-17','bautistaf', '2012-04-08','BUISSONF');

Insert into PRIAM_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('600','2013-07-23','2013-07-05','9223372036854775807','0','Rion 600','2013-07-23','GUEST','2013-07-23','GUEST');
Insert into PRIAM_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('629','2014-11-19','2015-01-05','9223372036854775807','0','rion 629','2014-11-19','ROMEIBRIGITTE','2014-11-19','ROMEIBRIGITTE');
Insert into PRIAM_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('630','2015-01-06','2015-04-06','9223372036854775807','0','Répartition Avril 2015','2015-01-06','LIETH','2015-01-06','LIETH');
Insert into PRIAM_RION (RION,DATCALC,DATRGLMT,FILTRE,ORDAFF,COM,DATCRE,USERCRE,DATMAJ,USERMAJ) values ('639','2017-04-03','2018-07-05','4','0','Répartition Juillet 2017','2017-04-25','ROUCOULESG','2017-04-25','ROUCOULESG');


INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('PR170001', 'Programme 01', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('PR170002', 'Programme 02', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('PR170003', 'Programme 03', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);


INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('PR170004', 'Programme 04', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('PR170005', 'Programme 05', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('PR170006', 'Programme 01', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', CURDATE(), 'EN_COURS', NULL);

/*INSERT INTO PRIAM_PROGRAMME(NUMPROG, NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT)
VALUES ('PR170008', 'Programme 01', 619, 'COPIEPRIV','CPRIVAUDPL', 'OEUVRE', '2017-06-19 00:00:00', 'EN_COURS', NULL);*/

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier 125', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', 'PR170001');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier 126', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', 'PR170001');

INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier 127', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', 'PR170001');
INSERT INTO PRIAM_FICHIER
(NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG)
VALUES ('Fichier 128', 'COPIEPRIV', 'COPRIVSON', '2017-05-24 16:00:14', '2017-05-24 22:57:04', 150780, 'AFFECTE', 'PR170001');
