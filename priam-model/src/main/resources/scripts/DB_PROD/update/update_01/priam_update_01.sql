USE priam_app;
create index IDX_SEL_EN_COURS on PRIAM_LIGNE_PROGRAMME (SEL_EN_COURS);

create index IDX_IDE12 on PRIAM_LIGNE_PROGRAMME (ide12);

create index IDX_durDif on PRIAM_LIGNE_PROGRAMME (durDif);

create index IDX_nbrDif on PRIAM_LIGNE_PROGRAMME (nbrDif);

create index IDX_idOeuvreManuel on PRIAM_LIGNE_PROGRAMME (idOeuvreManuel);

create index IDX_LIBUTIL on PRIAM_LIGNE_PROGRAMME (libelleUtilisateur);

commit;