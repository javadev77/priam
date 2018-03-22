package fr.sacem.priam.common;

public enum TypeLog {
    AFFECTATION_DESAFFECTATION ("Affectation / Désaffectation fichiers"),
    DESAFFECTATION ("Désaffectation fichiers"),
    ALL_DESAFFECTATION ("Tout désaffecter");

    private String evenement ;

    private TypeLog(String evenement) {
        this.evenement = evenement ;
    }

    public String getEvenement() {
        return this.evenement ;
    }
}
