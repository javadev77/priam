package fr.sacem.priam.model.domain.dto;


import fr.sacem.priam.model.domain.cp.FichierCP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fandis on 03/07/2017.
 */
public class AffectationDto {
    private String numProg;
    private List<FichierCP> fichiers= new ArrayList<>();


    public AffectationDto(String numProg, List<FichierCP> fichiers) {
        this.numProg = numProg;
        this.fichiers = fichiers;
    }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public List<FichierCP> getFichiers() {
        return fichiers;
    }

    public void setFichiers(List<FichierCP> fichiers) {
        this.fichiers = fichiers;
    }

    public AffectationDto() {
    }
}
