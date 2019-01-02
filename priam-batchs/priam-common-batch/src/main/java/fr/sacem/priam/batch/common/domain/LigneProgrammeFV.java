package fr.sacem.priam.batch.common.domain;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;

/**
 * Created by embouazzar on 22/11/2018.
 */
public class LigneProgrammeFV extends LigneProgramme {

    private String ide12Complx;
    private String cdetypide12cmplx;
    private Boolean insertIde12Complx;
    private Long idOeuvreCtnu;

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

    public String getIde12Complx() {
        return ide12Complx;
    }

    public void setIde12Complx(final String ide12Complx) {
        this.ide12Complx = ide12Complx;
    }

    public String getCdetypide12cmplx() {
        return cdetypide12cmplx;
    }

    public void setCdetypide12cmplx(final String cdetypide12cmplx) {
        this.cdetypide12cmplx = cdetypide12cmplx;
    }

    public Boolean isInsertIde12Complx() {
        return insertIde12Complx;
    }

    public void setInsertIde12Complx(final Boolean toInsertIde12Complx) {
        this.insertIde12Complx = toInsertIde12Complx;
    }

    public Long getIdOeuvreCtnu() {
        return idOeuvreCtnu;
    }

    public void setIdOeuvreCtnu(final Long idOeuvreCtnu) {
        this.idOeuvreCtnu = idOeuvreCtnu;
    }
}
