package fr.sacem.priam.batch.common.domain;


/**
 * Created by benmerzoukah on 26/01/2018.
 */
public class Programme {
    private String numProg;
    private String typeUtilisation;
    private String typeRepart;

    public Programme() {

    }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public String getTypeUtilisation() {
        return typeUtilisation;
    }

    public void setTypeUtilisation(String typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }

    public String getTypeRepart() {
        return typeRepart;
    }

    public void setTypeRepart(String typeRepart) {
        this.typeRepart = typeRepart;
    }
}
