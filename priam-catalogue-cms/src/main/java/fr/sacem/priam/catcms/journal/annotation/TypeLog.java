package fr.sacem.priam.catcms.journal.annotation;

public enum TypeLog {
    AJOUT_OEUVRE("Ajout manuel œuvre"),
    RENOUVELLEMENT_OEUVRE("Renouvellement manuel œuvre"),
    SUPPRESSION_OEUVRE("Suppression manuelle œuvre");


    private String evenement ;

    private TypeLog(String evenement) {
        this.evenement = evenement ;
    }

    public String getEvenement() {
        return this.evenement ;
    }
}
