-- Habib Benmerzouka
USE priam_app;

-- ------------------------------------------
-- -------- TABLE PRIAM_STATUT --------------
-- ------------------------------------------
INSERT INTO priam_app.PRIAM_STATUT
(CODE, LIBELLE, DESCRIPTION) VALUES
('EN_COURS', 'En cours', 'En cours de chargement');

INSERT INTO priam_app.PRIAM_STATUT
(CODE, LIBELLE, DESCRIPTION) VALUES
('OK', 'OK', 'Chargement OK');

INSERT INTO priam_app.PRIAM_STATUT
(CODE, LIBELLE, DESCRIPTION) VALUES
('KO', 'KO', 'Chargement KO');

INSERT INTO priam_app.PRIAM_STATUT
(CODE, LIBELLE, DESCRIPTION) VALUES
('AFFECTE', 'Affecte', 'Fichier Affecte');

INSERT INTO priam_app.PRIAM_STATUT
(CODE, LIBELLE, DESCRIPTION) VALUES
('ABONDONNE', 'Abondonne', 'Fichier Abondonne.');

-- ---------------------------------------------------
-- ------ TABLE PRIAM_FAMILLE_TYPUTIL --------------
-- -------------------------------------------------
INSERT INTO priam_app.PRIAM_FAMILLE_TYPUTIL (CODE, LIBELLE, DESCRIPTION) VALUES
('COPIEPRIV', 'Copie Privée', 'Copie Privée');

INSERT INTO priam_app.PRIAM_FAMILLE_TYPUTIL
(CODE, LIBELLE, DESCRIPTION) VALUES
('FDSVAL', 'Valorisation', 'Valorisation');

INSERT INTO priam_app.PRIAM_FAMILLE_TYPUTIL
(CODE, LIBELLE, DESCRIPTION) VALUES
('CMS', 'CMS', 'CMS');


-- ---------------------------------------------------
-- ------ TABLE PRIAM_TYPUTIL ------------------------
-- ---------------------------------------------------
INSERT INTO priam_app.PRIAM_TYPUTIL (CODE, CDE_FAMILLE, LIBELLE, DESCRIPTION)
VALUES ('CPRIVSONPH', 'COPIEPRIV', 'Copie privée sonore Phono', 'Copie privée sonore Phono');

INSERT INTO priam_app.PRIAM_TYPUTIL (CODE, CDE_FAMILLE, LIBELLE, DESCRIPTION)
VALUES ('CPRIVAUDV', 'COPIEPRIV', 'Copie Privée Audiovisuelle', 'Copie Privée Audiovisuelle');

INSERT INTO priam_app.PRIAM_TYPUTIL (CODE, CDE_FAMILLE, LIBELLE, DESCRIPTION)
VALUES ('CPRIVSONRD', 'COPIEPRIV', 'Copie Privée Sonore radio', 'Copie Privée Sonore radio');

INSERT INTO priam_app.PRIAM_TYPUTIL (CODE, CDE_FAMILLE, LIBELLE, DESCRIPTION)
VALUES ('COPRIVSON', 'COPIEPRIV', 'Copie Privée Sonore', 'Copie Privée Sonore');

INSERT INTO priam_app.PRIAM_TYPUTIL (CODE, CDE_FAMILLE, LIBELLE, DESCRIPTION)
VALUES ('CPRIVAUDPL', 'COPIEPRIV', 'Copie Privée Audiovisuel - Part Littéraire', 'Copie Privée Audiovisuel - Part Littéraire');

INSERT INTO priam_app.PRIAM_TYPUTIL (CODE, CDE_FAMILLE, LIBELLE, DESCRIPTION)
VALUES ('PRIME', 'FDSVAL', 'Prime', 'Prime');

INSERT INTO priam_app.PRIAM_TYPUTIL (CODE, CDE_FAMILLE, LIBELLE, DESCRIPTION)
VALUES ('VALORIS', 'FDSVAL', 'Fonds de valorisation', 'Fonds de valorisation');

INSERT INTO priam_app.PRIAM_TYPUTIL (CODE, CDE_FAMILLE, LIBELLE, DESCRIPTION)
VALUES ('ENCOURG', 'FDSVAL', 'Encouragement', 'Encouragement');





INSERT INTO priam_app.PRIAM_FICHIER
(NOM, FAMILLE, TYPE_UTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 01', 'COPIEPRIV', 'COPRIVSON', '2017-02-04 17:15:14', NULL, 3000, 'EN_COURS');

INSERT INTO priam_app.PRIAM_FICHIER
(NOM, FAMILLE, TYPE_UTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 02', 'COPIEPRIV', 'COPRIVSON', '2017-02-03 17:15:14', NULL, 9500, 'EN_COURS');


INSERT INTO priam_app.PRIAM_FICHIER
(NOM, FAMILLE, TYPE_UTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE)
VALUES ('Fichier 03', 'COPIEPRIV', 'COPRIVSON', '2017-02-01 17:15:14', NULL, 6500, 'EN_COURS');



