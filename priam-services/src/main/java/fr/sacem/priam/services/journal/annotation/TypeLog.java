package fr.sacem.priam.services.journal.annotation;

public enum TypeLog {
    CREATION ("Création programme"),
    MODIFICATION ("Modification programme"),
    SUPPRESSION ("Suppression programme"),
    VALIDATION ("Validation programme"),
    INVALIDATION ("Invalidation programme"),
    ANNULATION ("Annuler la sélection"),
    REPARTITION ("Mise en répartition"),
    SUPPRESSIONOEUVRE ("Suppression oeuvre avec état ");

    private String evenement ;

    private TypeLog(String evenement) {
        this.evenement = evenement ;
    }

    public String getEvenement() {
        return this.evenement ;
    }
}
