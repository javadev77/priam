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
    private String rionEffet;

    public String getRionEffet() {
        return rionEffet;
    }

    public void setRionEffet(String rionEffet) {
        this.rionEffet = rionEffet;
    }
}
