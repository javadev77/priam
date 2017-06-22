package fr.sacem.priam.model.domain;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by fandis on 22/06/2017.
 */
@Embeddable
public class ProgrammeKey implements Serializable {
    @Column(name = "PREFIX")
    private String prefix;
    @Column(name = "ANNEE")
    private String annee;
    @Column(name = "CODESEQUENCE")
    private Integer codeSequence;
    public Integer getCodeSequence() {
        return codeSequence;
    }

    public void setCodeSequence(Integer codeSequence) {
        this.codeSequence = codeSequence;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public ProgrammeKey(String prefix, String annee) {
        this.prefix = prefix;
        this.annee = annee;
    }

    public ProgrammeKey() {
    }
}
