package fr.sacem.priam.model.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by fandis on 02/08/2017.
 */
public class LigneProgrammeViewId implements Serializable {
    private Long ide12;
    private String numProg;

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }
}
