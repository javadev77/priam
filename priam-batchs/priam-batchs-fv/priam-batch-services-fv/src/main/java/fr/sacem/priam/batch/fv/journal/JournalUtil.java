package fr.sacem.priam.batch.fv.journal;

import fr.sacem.priam.model.dao.jpa.JournalBatchDao;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.journal.JournalBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static fr.sacem.priam.batch.fv.journal.TypeLog.*;

@Component
public class JournalUtil {

    private static final String PRG_AFFECTE = "Affecté";
    private static final String PRG_EN_COURS = "En cours";
    private static final String PRG_VALIDE = "Validé";
    private static final String PRG_MIS_EN_REPART = "Mis en répartition";


    @Autowired
    JournalBatchDao journalBatchDao;

    public void saveJournal(String userId, String evenement, String numProg, Enum statutProgramme) {

        List<SituationAvant> situationAvantList = new ArrayList<>();
        List<SituationApres> situationApresList = new ArrayList<>();
        SituationAvant situationAvant = new SituationAvant();
        SituationApres situationApres = new SituationApres();
        Journal journal;

        JournalBuilder journalBuilder = new JournalBuilder(numProg,null, userId);
        if(EXPORT.getEvenement().equals(evenement)){
            journal = journalBuilder.addEvenement(EXPORT.getEvenement()).build();
            situationAvant.setSituation(PRG_AFFECTE);
            situationApres.setSituation(PRG_AFFECTE);
        } else if(TypeLog.IMPORT.getEvenement().equals(evenement)){
            journal = journalBuilder.addEvenement(TypeLog.IMPORT.getEvenement()).build();
            if(statutProgramme== StatutProgramme.AFFECTE){
                situationAvant.setSituation(PRG_AFFECTE);
            } else {
                situationAvant.setSituation(PRG_EN_COURS);
            }
            situationApres.setSituation(PRG_EN_COURS);
        } else {
            journal = journalBuilder.addEvenement(TypeLog.MISE_EN_REPART.getEvenement()).build();
            situationAvant.setSituation(PRG_VALIDE);
            situationApres.setSituation(PRG_MIS_EN_REPART);
        }
        situationAvantList.add(situationAvant);
        situationApresList.add(situationApres);
        journal.setListSituationAvant(situationAvantList);
        journal.setListSituationApres(situationApresList);

        long idJournal = journalBatchDao.saveJournal(journal);
        journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
        journalBatchDao.saveSituationApresJournal(journal.getListSituationApres(), idJournal);
    }

}
