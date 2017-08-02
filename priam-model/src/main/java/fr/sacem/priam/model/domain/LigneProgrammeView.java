package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by fandis on 02/08/2017.
 */
@Entity
@IdClass(LigneProgrammeViewId.class)
@Table(name = "PRIAM_LIGNE_PROG_VIEW")
public class LigneProgrammeView implements Serializable{
    @Id
    @Column(name = "IDE12")
    private Long ide12;
    @Id
    @Column(name = "NUMPROG")
    private String numProg;
    @Column(name = "DURDIF")
    private Long durDif;
    @Column(name = "QUANTITE")
    private Long quantite;

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

    public Long getDurDif() {
        return durDif;
    }

    public void setDurDif(Long durDif) {
        this.durDif = durDif;
    }

    public Long getQuantite() {
        return quantite;
    }

    public void setQuantite(Long quantite) {
        this.quantite = quantite;
    }

    public LigneProgrammeView() {
    }
}
