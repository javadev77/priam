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

    private String labelValo;

    private Integer dureeDeposee;

    private Double taxOri;

    private Integer indicRepart;

    private String genreOeuvre;

    private String paysOri;

    private String statut;

    public String getRionEffet() {
        return rionEffet;
    }

    public void setRionEffet(String rionEffet) {
        this.rionEffet = rionEffet;
    }

    public String getLabelValo() {
        return labelValo;
    }

    public void setLabelValo(String labelValo) {
        this.labelValo = labelValo;
    }

    public Integer getDureeDeposee() {
        return dureeDeposee;
    }

    public void setDureeDeposee(Integer dureeDeposee) {
        this.dureeDeposee = dureeDeposee;
    }

    public Double getTaxOri() {
        return taxOri;
    }

    public void setTaxOri(Double taxOri) {
        this.taxOri = taxOri;
    }

    public Integer getIndicRepart() {
        return indicRepart;
    }

    public void setIndicRepart(Integer indicRepart) {
        this.indicRepart = indicRepart;
    }

    public String getGenreOeuvre() {
        return genreOeuvre;
    }

    public void setGenreOeuvre(String genreOeuvre) {
        this.genreOeuvre = genreOeuvre;
    }

    public String getPaysOri() {
        return paysOri;
    }

    public void setPaysOri(String paysOri) {
        this.paysOri = paysOri;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
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
