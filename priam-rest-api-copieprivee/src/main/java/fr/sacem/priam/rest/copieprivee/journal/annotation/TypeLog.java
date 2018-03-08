package fr.sacem.priam.rest.copieprivee.journal.annotation;

public enum TypeLog {
    AFFECTATION ("Affectation fichiers");

    private String evenement ;

    private TypeLog(String evenement) {
        this.evenement = evenement ;
    }

    public String getEvenement() {
        return this.evenement ;
    }
}
