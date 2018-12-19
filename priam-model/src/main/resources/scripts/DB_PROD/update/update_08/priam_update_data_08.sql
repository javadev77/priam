USE priam_app;

INSERT INTO `PRIAM_ROLE` (`ID`, `EXTERNAL_ID`, `ROLE`) VALUES (7,'Gest_FV','Gest_FV');


INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'CRTPRG','Creation de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'READPRG','Acces ecran programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'MDYPRG','Modification de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'ABDPRG','Abandon de programme');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'MSEREP','Mise en repartition');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'RECAFC','Enregistrement affectation');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'EDTAFC','Edition affectation');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'EDTSEL','Edition selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'INVSEL','Invalider selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'CLDSEL','Annuler selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'VLDSEL','Valider selection');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'ABDCHGT','Abandon de fichier');

INSERT INTO `PRIAM_ROLE_RIGHTS` (`ROLE_ID`, `RIGHTS`, `LIBELLE`) VALUES (7,'MENUCMS','Accès Menu Principal CMS');

UPDATE PRIAM_ROLE_RIGHTS SET RIGHTS='MENU_PRINCIPAL', LIBELLE='Accès Menu Principal' WHERE RIGHTS='MENUCMS' ;


UPDATE PRIAM_FICHIER SET STATUT_ENRICHISSEEMNT='TO_SRV_OCTAV_CTNU' WHERE STATUT_CODE='CHARGEMENT_OK' AND CDEFAMILTYPUTIL='FDSVAL';

COMMIT;