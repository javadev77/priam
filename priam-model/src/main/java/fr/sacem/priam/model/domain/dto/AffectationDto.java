package fr.sacem.priam.model.domain.dto;


import fr.sacem.priam.model.domain.Fichier;

import java.util.ArrayList;

/**
 * Created by fandis on 03/07/2017.
 */
public class AffectationDto {
    private String numProg;
    private ArrayList<Fichier> fichiers= new ArrayList<>();


    public AffectationDto(String numProg, ArrayList<Fichier> fichiers) {
        this.numProg = numProg;
        this.fichiers = fichiers;
    }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public ArrayList<Fichier> getFichiers() {
        return fichiers;
    }

    public void setFichiers(ArrayList<Fichier> fichiers) {
        this.fichiers = fichiers;
    }

    public AffectationDto() {
    }
}
