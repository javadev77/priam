package fr.sacem.priam.batch.felix.journal;

public enum TypeLog {

    EXPORT ("Export"),
    IMPORT ("Import"),
    MISE_EN_REPART ("Mise en répartition");

    private String evenement ;

    TypeLog(final String evenement) {
        this.evenement = evenement ;
    }

    public String getEvenement() {
        return this.evenement ;
    }

}
