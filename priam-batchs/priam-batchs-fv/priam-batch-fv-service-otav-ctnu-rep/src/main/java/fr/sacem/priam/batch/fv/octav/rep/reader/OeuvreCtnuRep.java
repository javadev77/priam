package fr.sacem.priam.batch.fv.octav.rep.reader;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class OeuvreCtnuRep {

    private String ide12cmplx;
    private String cdetypide12cmplx;
    private String ide12;
    private String cdetypide12;
    private String cdegenreide12cmplx;
    private String cdegenreide12;
    private String titreoriginalcmplx;
    private String titrealternatifprinpalcmplx;
    private String titreoriginaloeuvreperecmplx;
    private String titrealternatifoeuvreperecmplx;
    private String duree;
    private String cdepaysorigineiso4n;
    private String anneeproduction;
    private String realisateur;
    private String acteur;
    private String producteur;
    private String numepisode;
    private String seqcuesheet;
    private String dureecontenu;
    private Integer statut;
    private Integer lineNumber;
    private Exception exception;

    public OeuvreCtnuRep(final PriamValidationException e) {

    }

    public OeuvreCtnuRep() {

    }

    public Exception getException() {
        return exception;
    }

    public void setException(final Exception exception) {
        this.exception = exception;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(final Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getIde12cmplx() {
        return ide12cmplx;
    }

    public void setIde12cmplx(final String ide12cmplx) {
        this.ide12cmplx = ide12cmplx;
    }

    public String getCdetypide12cmplx() {
        return cdetypide12cmplx;
    }

    public void setCdetypide12cmplx(final String cdetypide12cmplx) {
        this.cdetypide12cmplx = cdetypide12cmplx;
    }

    public String getIde12() {
        return ide12;
    }

    public void setIde12(final String ide12) {
        this.ide12 = ide12;
    }

    public String getCdetypide12() {
        return cdetypide12;
    }

    public void setCdetypide12(final String cdetypide12) {
        this.cdetypide12 = cdetypide12;
    }

    public String getCdegenreide12cmplx() {
        return cdegenreide12cmplx;
    }

    public void setCdegenreide12cmplx(final String cdegenreide12cmplx) {
        this.cdegenreide12cmplx = cdegenreide12cmplx;
    }

    public String getCdegenreide12() {
        return cdegenreide12;
    }

    public void setCdegenreide12(final String cdegenreide12) {
        this.cdegenreide12 = cdegenreide12;
    }

    public String getTitreoriginalcmplx() {
        return titreoriginalcmplx;
    }

    public void setTitreoriginalcmplx(final String titreoriginalcmplx) {
        this.titreoriginalcmplx = titreoriginalcmplx;
    }

    public String getTitrealternatifprinpalcmplx() {
        return titrealternatifprinpalcmplx;
    }

    public void setTitrealternatifprinpalcmplx(final String titrealternatifprinpalcmplx) {
        this.titrealternatifprinpalcmplx = titrealternatifprinpalcmplx;
    }

    public String getTitreoriginaloeuvreperecmplx() {
        return titreoriginaloeuvreperecmplx;
    }

    public void setTitreoriginaloeuvreperecmplx(final String titreoriginaloeuvreperecmplx) {
        this.titreoriginaloeuvreperecmplx = titreoriginaloeuvreperecmplx;
    }

    public String getTitrealternatifoeuvreperecmplx() {
        return titrealternatifoeuvreperecmplx;
    }

    public void setTitrealternatifoeuvreperecmplx(final String titrealternatifoeuvreperecmplx) {
        this.titrealternatifoeuvreperecmplx = titrealternatifoeuvreperecmplx;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(final String duree) {
        this.duree = duree;
    }

    public String getCdepaysorigineiso4n() {
        return cdepaysorigineiso4n;
    }

    public void setCdepaysorigineiso4n(final String cdepaysorigineiso4n) {
        this.cdepaysorigineiso4n = cdepaysorigineiso4n;
    }

    public String getAnneeproduction() {
        return anneeproduction;
    }

    public void setAnneeproduction(final String anneeproduction) {
        this.anneeproduction = anneeproduction;
    }

    public String getRealisateur() {
        return realisateur;
    }

    public void setRealisateur(final String realisateur) {
        this.realisateur = realisateur;
    }

    public String getActeur() {
        return acteur;
    }

    public void setActeur(final String acteur) {
        this.acteur = acteur;
    }

    public String getProducteur() {
        return producteur;
    }

    public void setProducteur(final String producteur) {
        this.producteur = producteur;
    }

    public String getNumepisode() {
        return numepisode;
    }

    public void setNumepisode(final String numepisode) {
        this.numepisode = numepisode;
    }

    public String getSeqcuesheet() {
        return seqcuesheet;
    }

    public void setSeqcuesheet(final String seqcuesheet) {
        this.seqcuesheet = seqcuesheet;
    }

    public String getDureecontenu() {
        return dureecontenu;
    }

    public void setDureecontenu(final String dureecontenu) {
        this.dureecontenu = dureecontenu;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(final Integer statut) {
        this.statut = statut;
    }
}
