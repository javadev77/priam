USE priam_app;

INSERT INTO priam_app.SAREFTR_RION (RION, DATCALC, DATRGLMT, FILTRE, ORDAFF, COM, DATCRE, USERCRE, DATMAJ, USERMAJ)
VALUES (644, '2018-07-02', '2018-10-05', 9223372036854775807, 0, 'Repartition October 2018', '2018-06-01', 'LIETH', '2018-07-02', 'LIETH');

INSERT INTO PRIAM_PROGRAMME (NUMPROG,NOM, RION_THEORIQUE, CDEFAMILTYPUTIL, CDETYPUTIL, TYPE_REPART, DATE_CREATION, STATUT_PROG_CODE, RION_PAIEMENT, USERCRE, DATMAJ, USERMAJ, DATAFFECT, USERAFFECT, DATE_VALIDATION,USER_VALIDATION, DATE_DBT_PRG, DATE_FIN_PRG, CDE_TER, DATE_REPARTITION, STATUT_ELIGIBILITE, TYPE_DROIT)
VALUES (190003,'PROG FD06 TEST 3', 644, 'FDSVAL', 'FD06', 'OEUVRE', '2019-01-08 11:01:59', 'AFFECTE', null, 'Rachid EMBOUAZZA', null, null, null, null, null, null, '2019-01-15', '2019-01-31', 250, null, null, 'PH');

INSERT INTO PRIAM_FICHIER (NOM, CDEFAMILTYPUTIL, CDETYPUTIL, DATE_DEBUT_CHGT, DATE_FIN_CHGT, NB_LIGNES, STATUT_CODE, NUMPROG, SOURCE_AUTO, TYPE_FICHIER, STATUT_ENRICHISSEEMNT)
VALUES ('FF_PENEF_EXTRANA_FONDS06_20190115103841.csv', 'FDSVAL', 'FD06', '2019-01-03 14:21:06', '2019-01-03 14:21:06', 2, 'AFFECTE', 190003, 1, 'FV', null);

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV(numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt,  durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER, cdeGreIde12Cmplx, cdeGreIde12, titreAltOeuvPereCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreOriCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri, utilisateur, date_insertion, ajout, selection, idOeuvreManuel, SEL_EN_COURS, rionEffet, ID_OEUVRE_CTNU, labelValo, dureeDeposee, taxOri, indicRepart, genreOeuvre, paysOri, statut, mtEdit)
VALUES ( 190003 ,'FDSVAL', 'FD06', 2240093411, 48.75, 0, 'FR', null, 10, 'MB', (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS06_20190115103841.csv'), null, 'IJ', null, null, null, null, 'IMPRO JAZZ', null, null, 'C', 'MURCIA SARAH', 'CONCERT', 'DTSGEN', 'Batch Extraction', '2019-01-08 14:21:07', 'AUTOMATIQUE', 0, null, 1, null,null,null,null,null,null,null,null,null,48.75);

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV(numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt,  durDifCtna, cdeLng, indDoubSsTit, tax, typMt, ID_FICHIER, cdeGreIde12Cmplx, cdeGreIde12, titreAltOeuvPereCmplx, titreAltPppalCmplx, titreOriOeuvPereCmplx, titreOriCmplx, titreOeuvre, cdePaysOriIso4NCmplx, realisateurCmplx, roleParticipant1, nomParticipant1, cdeTypUtilOri, cdeFamilTypUtilOri, utilisateur, date_insertion, ajout, selection, idOeuvreManuel, SEL_EN_COURS, rionEffet, ID_OEUVRE_CTNU, labelValo, dureeDeposee, taxOri, indicRepart, genreOeuvre, paysOri, statut, mtEdit)
VALUES ( 190003 ,'FDSVAL', 'FD06', 2739083111, 51.25, 0, 'FR', null, 10, 'MB', (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS06_20190115103841.csv'), null, 'IJ', null, null, null, null, 'LA CORSE', null, null, 'C', 'MURCIA SARAH', 'CONCERT', 'DTSGEN', 'Batch Extraction', '2019-01-08 14:21:07', 'AUTOMATIQUE', 0, null, 1, null,null,null,null,null,null,null,null,null,51.25);

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV (numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt, ID_FICHIER, titreOeuvre, roleParticipant1, nomParticipant1, ajout, SEL_EN_COURS, mtEdit)
VALUES (190003, 'FDSVAL', 'FD06', 2018300111, 30.68, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS06_20190115103841.csv'), 'Titre7', 'CA', 'Participant7', 'CORRIGE', 1, 30.68);

INSERT INTO PRIAM_LIGNE_PROGRAMME_FV (numProg, cdeFamilTypUtil, cdeTypUtil, ide12, mt, ID_FICHIER, titreOeuvre, roleParticipant1, nomParticipant1, ajout, SEL_EN_COURS, mtEdit)
VALUES (190003, 'FDSVAL', 'FD06', 2018290111, 20.12, (SELECT ID FROM PRIAM_FICHIER WHERE NOM ='FF_PENEF_EXTRANA_FONDS06_20190115103841.csv'), 'Titre8', 'CA', 'Participant8', 'MANUEL', 1, 20.12);

COMMIT;