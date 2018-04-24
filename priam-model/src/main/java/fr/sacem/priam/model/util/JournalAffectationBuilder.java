package fr.sacem.priam.model.util;

import fr.sacem.priam.common.TypeLog;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.journal.JournalBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class JournalAffectationBuilder {

    @Autowired
    private FichierDao fichierDao;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public Journal create(String numProg, String listNomFichier, List<Fichier> listFichierAffecte, String userId) {
        List<SituationAvant> situationAvantList = new ArrayList<>();
        List<SituationApres> situationApresList = new ArrayList<>();

        if(!listNomFichier.isEmpty()) {
            List<String> listNomFichierAvantAffecte = Arrays.asList(listNomFichier.split("\\s*,\\s*"));
            listNomFichierAvantAffecte.forEach(NomFichierAvantAffecte -> {
                SituationAvant situationAvant = new SituationAvant();
                situationAvant.setSituation(NomFichierAvantAffecte);
                situationAvantList.add(situationAvant);
            });
        }

        /*List<Fichier> listFichierAffecte = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);*/
        listFichierAffecte.forEach(fichierAffecte ->{
            SituationApres situationApres = new SituationApres();
            situationApres.setSituation(fichierAffecte.getNomFichier() + " " + simpleDateFormat.format(fichierAffecte.getDateFinChargt()));
            situationApresList.add(situationApres);
        });

        JournalBuilder journalBuilder = new JournalBuilder(numProg,null,userId);
        Journal journal = journalBuilder.addEvenement(TypeLog.AFFECTATION_DESAFFECTATION.getEvenement()).build();
        journal.setListSituationAvant(situationAvantList);
        journal.setListSituationApres(situationApresList);

        return journal;
    }



}
