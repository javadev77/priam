USE priam_app;

ALTER TABLE PRIAM_CATCMS_PARTICIPANTS
  ADD 'OEUVRE_SORTIE' TINYINT(0);

COMMIT;