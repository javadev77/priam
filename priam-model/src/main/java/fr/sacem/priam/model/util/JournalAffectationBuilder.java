package fr.sacem.priam.model.util;

import fr.sacem.priam.common.TypeLog;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.journal.JournalBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JournalAffectationBuilder {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public Journal create(String numProg, String listNomFichier, List<Fichier> listFichierAffecte, String userId) {
        List<SituationAvant> situationAvantList = new ArrayList<>();
        List<SituationApres> situationApresList = new ArrayList<>();

        if(!listNomFichier.isEmpty()) {
            List<String> listNomFichierAvantAffecte = Arrays.asList(listNomFichier.split("\\s*,\\s*"));
            listNomFichierAvantAffecte.forEach(nomFichierAvantAffecte -> {
                SituationAvant situationAvant = new SituationAvant();
                situationAvant.setSituation(nomFichierAvantAffecte);
                situationAvantList.add(situationAvant);
            });
        }

        listFichierAffecte.forEach(fichierAffecte ->{
            SituationApres situationApres = new SituationApres();
            situationApres.setSituation(fichierAffecte.getNomFichier() + " " + simpleDateFormat.format(fichierAffecte.getDateFinChargt()));
            situationApresList.add(situationApres);
        });

        JournalBuilder journalBuilder = new JournalBuilder(numProg,null,userId);
        Journal journal = journalBuilder.addEvenement(TypeLog.AFFECTATION_DESAFFECTATION.getEvenement()).build();

        if(!situationAvantList.isEmpty()) {
            journal.setListSituationAvant(situationAvantList);
        }

        if(!situationApresList.isEmpty()) {
            journal.setListSituationApres(situationApresList);
        }


        return journal;
    }



}
