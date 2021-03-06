USE priam_app;

update PRIAM_FICHIER SET TYPE_FICHIER ='CP';

INSERT INTO `PRIAM_ROLE` (`ID`, `EXTERNAL_ID`, `ROLE`) VALUES (4,'Gest_CP','Gest_CP');
INSERT INTO `PRIAM_ROLE` (`ID`, `EXTERNAL_ID`, `ROLE`) VALUES (5,'Gest_CMS','Gest_CMS');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'CRTPRG','Creation de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'CRTPRG','Creation de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'READPRG','Acces ecran programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'READPRG','Acces ecran programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'MDYPRG','Modification de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'MDYPRG','Modification de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'ABDPRG','Abandon de programme');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'ABDPRG','Abandon de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'MSEREP','Mise en repartition');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'MSEREP','Mise en repartition');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'RECAFC','Enregistrement affectation');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'RECAFC','Enregistrement affectation');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'EDTAFC','Edition affectation');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'EDTAFC','Edition affectation');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'EDTSEL','Edition selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'EDTSEL','Edition selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'INVSEL','Invalider selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'INVSEL','Invalider selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'CLDSEL','Annuler selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'CLDSEL','Annuler selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'VLDSEL','Valider selection');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'VLDSEL','Valider selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (4,'ABDCHGT','Abandon de fichier');
INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (5,'ABDCHGT','Abandon de fichier');


INSERT INTO `PRIAM_ELIGIBILITE_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('EN_ATTENTE_ELIGIBILITE',' en attente eligibilité');
INSERT INTO `PRIAM_ELIGIBILITE_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('EN_COURS_ELIGIBILITE','en cours eligibilité');
INSERT INTO `PRIAM_ELIGIBILITE_STATUT_PROGRAMME` (`CODE`, `LIBELLE`) VALUES ('FIN_ELIGIBILITE','fin traitement eligibilité');


commit;
