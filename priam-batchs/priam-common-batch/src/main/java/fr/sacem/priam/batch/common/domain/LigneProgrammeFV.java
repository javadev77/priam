package fr.sacem.priam.batch.common.domain;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import java.time.LocalDate;

/**
 * Created by embouazzar on 22/11/2018.
 */
public class LigneProgrammeFV extends LigneProgramme {

    private String ide12Complx;
    private String cdetypide12cmplx;
    private Boolean insertIde12Complx;
    private Long idOeuvreCtnu;
    private String cdeTypDrtSacem;
    private LocalDate datConsult = LocalDate.now();
    private LocalDate datSitu = LocalDate.now();
    private String rionStatut;
    private String rionCalc;
    private String idRevend;
    private String cdeTer;

    private String rionEffet;
    private String labelValo;
    private Integer dureeDeposee;
    private Double taxOri;
    private Integer indicRepart;
    private String genreOeuvre;
    private String paysOri;
    private String statut;


    public LigneProgrammeFV() {
    }

    public LigneProgrammeFV(PriamValidationException e) {
    }


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

    public Boolean getInsertIde12Complx() {
        return insertIde12Complx;
    }

    public String getCdeTypDrtSacem() {
        return cdeTypDrtSacem;
    }

    public void setCdeTypDrtSacem(final String cdeTypDrtSacem) {
        this.cdeTypDrtSacem = cdeTypDrtSacem;
    }

    public LocalDate getDatConsult() {
        return datConsult;
    }

    public void setDatConsult(final LocalDate datConsult) {
        this.datConsult = datConsult;
    }

    public LocalDate getDatSitu() {
        return datSitu;
    }

    public void setDatSitu(final LocalDate datSitu) {
        this.datSitu = datSitu;
    }

    public String getRionStatut() {
        return rionStatut;
    }

    public void setRionStatut(final String rionStatut) {
        this.rionStatut = rionStatut;
    }

    public String getRionCalc() {
        return rionCalc;
    }

    public void setRionCalc(final String rionCalc) {
        this.rionCalc = rionCalc;
    }

    public String getIdRevend() {
        return idRevend;
    }

    public void setIdRevend(final String idRevend) {
        this.idRevend = idRevend;
    }

    public String getCdeTer() {
        return cdeTer;
    }

    public void setCdeTer(final String cdeTer) {
        this.cdeTer = cdeTer;
    }
}
