package fr.sacem.priam.services.journal.annotation;

public enum TypeLog {
    CREATION ("Création programme"),
    MODIFICATION ("Modification programme"),
    SUPPRESSION ("Suppression programme"),
    VALIDATION ("Validation programme"),
    INVALIDATION ("Invalidation programme"),
    ANNULATION ("Annulation de la sélection"),
    REPARTITION ("Mise en répartition"),
    AJOUT_OEUVRE("Ajout oeuvre"),
    MODIFIER_OEUVRE("Modification oeuvre"),
    SUPPRESSION_OEUVRE ("Suppression oeuvre avec état "),
    SELECTION ("Sélection"),
    DESELECTION ("Désélection");


    private String evenement ;

    private TypeLog(String evenement) {
        this.evenement = evenement ;
    }

    public String getEvenement() {
        return this.evenement ;
    }
}
