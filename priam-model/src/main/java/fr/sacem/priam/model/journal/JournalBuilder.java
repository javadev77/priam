package fr.sacem.priam.model.journal;

import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;

import java.util.Date;

/**
 * Created by benmerzoukah on 13/03/2018.
 */
public class JournalBuilder {

    private final Journal journal;
    private String numProg;
    private Long ide12;
    private String utilisateur;

    public JournalBuilder(String numProg, Long ide12, String utilisateur) {

        this.numProg = numProg;
        this.ide12 = ide12;
        this.utilisateur = utilisateur;
        this.journal = createJournal();
    }

    private Journal createJournal() {
        Journal journal = new Journal();

        journal.setNumProg(numProg);
        journal.setDate(new Date());
        journal.setIde12(ide12);
        journal.setUtilisateur(utilisateur);

        return journal;
    }

    public JournalBuilder addSituationAvant(SituationAvant situationAvant) {
        this.journal.getListSituationAvant().add(situationAvant);
        return this;
    }

    public JournalBuilder addSituationApres(SituationApres situationApres) {
        this.journal.getListSituationApres().add(situationApres);
        return this;
    }

    public JournalBuilder addEvenement(String event) {
        this.journal.setEvenement(event);
        return this;
    }

    public Journal build() {
        return this.journal;
    }



}
