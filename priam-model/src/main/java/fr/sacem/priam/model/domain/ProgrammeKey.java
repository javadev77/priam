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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    
        ProgrammeKey that = (ProgrammeKey) o;
    
        if (annee != null ? !annee.equals(that.annee) : that.annee != null) return false;
        if (codeSequence != null ? !codeSequence.equals(that.codeSequence) : that.codeSequence != null) return false;
    
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = annee != null ? annee.hashCode() : 0;
        result = 31 * result + (codeSequence != null ? codeSequence.hashCode() : 0);
        return result;
    }
}
