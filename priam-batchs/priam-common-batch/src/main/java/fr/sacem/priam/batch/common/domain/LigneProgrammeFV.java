package fr.sacem.priam.batch.common.domain;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;

/**
 * Created by embouazzar on 22/11/2018.
 */
public class LigneProgrammeFV extends LigneProgramme {

    public LigneProgrammeFV() {
    }

    public LigneProgrammeFV(PriamValidationException e) {
    }
    private String rioneffet;

    public String getRioneffet() {
        return rioneffet;
    }

    public void setRioneffet(String rioneffet) {
        this.rioneffet = rioneffet;
    }
}
