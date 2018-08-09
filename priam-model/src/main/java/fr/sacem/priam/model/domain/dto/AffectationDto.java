package fr.sacem.priam.model.domain.dto;


import fr.sacem.priam.model.domain.Fichier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandis on 03/07/2017.
 */
public class AffectationDto {
    private String numProg;
    private List<Fichier> fichiers = new ArrayList<>();
    private List<Fichier> fichiersUnChecked = new ArrayList<>();
    private List<Long> fichersAvantAffectation = new ArrayList<>();


    public AffectationDto(String numProg, List<Fichier> fichiers) {
        this.numProg = numProg;
        this.fichiers = fichiers;
    }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public List<Fichier> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<Fichier> fichiers) {
        this.fichiers = fichiers;
    }

    public List<Long> getFichersAvantAffectation() {
        return fichersAvantAffectation;
    }

    public void setFichersAvantAffectation(List<Long> fichersAvantAffectation) {
        this.fichersAvantAffectation = fichersAvantAffectation;
    }

    public List<Fichier> getFichiersUnChecked() {
        return fichiersUnChecked;
    }

    public void setFichiersUnChecked(List<Fichier> fichiersUnChecked) {
        this.fichiersUnChecked = fichiersUnChecked;
    }

    public AffectationDto() {
    }
}
