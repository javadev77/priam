package fr.sacem.priam.catcms.journal.annotation;

public enum TypeLog {
    AJOUT_OEUVRE("Ajout oeuvre");


    private String evenement ;

    private TypeLog(String evenement) {
        this.evenement = evenement ;
    }

    public String getEvenement() {
        return this.evenement ;
    }
}
