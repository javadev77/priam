package fr.sacem.priam.model.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by fandis on 22/06/2017.
 */
@Embeddable
public class ProgrammeKey implements Serializable {

    @Column(name = "ANNEE")
    private String annee;
    @Column(name = "CODESEQUENCE")
    private Long codeSequence;
    public Long getCodeSequence() {
        return codeSequence;
    }

    public void setCodeSequence(Long codeSequence) {
        this.codeSequence = codeSequence;
    }



    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public ProgrammeKey(String annee,Long codeSequence) {
        this.annee = annee;
        this.codeSequence=codeSequence;
    }

    public ProgrammeKey() {
    }
}
