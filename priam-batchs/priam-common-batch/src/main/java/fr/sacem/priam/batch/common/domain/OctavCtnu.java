package fr.sacem.priam.batch.common.domain;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;

import java.io.Serializable;

public class OctavCtnu implements Serializable {

    private String cdeCisac;
    private String cdeTer;
    private String cdeFamilTypUtilOri;
    private String cdeTypUtilOri;
    private String cdeUtil;
    private String ide12;
    private String cdeTypIde12;
    private String datConsult;
    private String datSitu;
    private String rionStatut;
    private String rionCalc;
    private String cdeLng;
    private String indDoubSsTit;

    private String filler;
    private String ide12Ctnu;
    private String cdeTypIde12Ctnu;
    private String indCmplxCtnu;
    private String seqOeuvCtnu;
    private String seqOeuvPere;
    private String durDsOeuvPere;
    private String taxatDsOeuvPere;
    private String cdeGreDifDsOeuvPere;
    private String oeuvPreExist;
    private String pctDvaltn;
    private String numOrd;
    private String indDroitTotEnAttente;
    private String statut;

    private String cdeGreValorisationDoc;
    private String oeuvreSynchrone;
    private String indVOCueSheet;
    private String cdeLngCtnu;
    private String flagImpose;

    private Integer lineNumber;
    private Exception exception;

    public OctavCtnu() {
    }

    public OctavCtnu(final PriamValidationException e) {
    }

    public String getCdeCisac() {
        return cdeCisac;
    }

    public void setCdeCisac(String cdeCisac) {
        this.cdeCisac = cdeCisac;
    }

    public String getCdeTer() {
        return cdeTer;
    }

    public void setCdeTer(String cdeTer) {
        this.cdeTer = cdeTer;
    }

    public String getCdeFamilTypUtilOri() {
        return cdeFamilTypUtilOri;
    }

    public void setCdeFamilTypUtilOri(String cdeFamilTypUtilOri) {
        this.cdeFamilTypUtilOri = cdeFamilTypUtilOri;
    }

    public String getCdeTypUtilOri() {
        return cdeTypUtilOri;
    }

    public void setCdeTypUtilOri(String cdeTypUtilOri) {
        this.cdeTypUtilOri = cdeTypUtilOri;
    }

    public String getCdeUtil() {
        return cdeUtil;
    }

    public void setCdeUtil(String cdeUtil) {
        this.cdeUtil = cdeUtil;
    }

    public String getIde12() {
        return ide12;
    }

    public void setIde12(String ide12) {
        this.ide12 = ide12;
    }

    public String getCdeTypIde12() {
        return cdeTypIde12;
    }

    public void setCdeTypIde12(String cdeTypIde12) {
        this.cdeTypIde12 = cdeTypIde12;
    }

    public String getDatConsult() {
        return datConsult;
    }

    public void setDatConsult(String datConsult) {
        this.datConsult = datConsult;
    }

    public String getDatSitu() {
        return datSitu;
    }

    public void setDatSitu(String datSitu) {
        this.datSitu = datSitu;
    }

    public String getRionStatut() {
        return rionStatut;
    }

    public void setRionStatut(String rionStatut) {
        this.rionStatut = rionStatut;
    }

    public String getRionCalc() {
        return rionCalc;
    }

    public void setRionCalc(String rionCalc) {
        this.rionCalc = rionCalc;
    }

    public String getCdeLng() {
        return cdeLng;
    }

    public void setCdeLng(String cdeLng) {
        this.cdeLng = cdeLng;
    }

    public String getIndDoubSsTit() {
        return indDoubSsTit;
    }

    public void setIndDoubSsTit(String indDoubSsTit) {
        this.indDoubSsTit = indDoubSsTit;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getIde12Ctnu() {
        return ide12Ctnu;
    }

    public void setIde12Ctnu(String ide12Ctnu) {
        this.ide12Ctnu = ide12Ctnu;
    }

    public String getCdeTypIde12Ctnu() {
        return cdeTypIde12Ctnu;
    }

    public void setCdeTypIde12Ctnu(String cdeTypIde12Ctnu) {
        this.cdeTypIde12Ctnu = cdeTypIde12Ctnu;
    }

    public String getIndCmplxCtnu() {
        return indCmplxCtnu;
    }

    public void setIndCmplxCtnu(String indCmplxCtnu) {
        this.indCmplxCtnu = indCmplxCtnu;
    }

    public String getSeqOeuvCtnu() {
        return seqOeuvCtnu;
    }

    public void setSeqOeuvCtnu(String seqOeuvCtnu) {
        this.seqOeuvCtnu = seqOeuvCtnu;
    }

    public String getSeqOeuvPere() {
        return seqOeuvPere;
    }

    public void setSeqOeuvPere(String seqOeuvPere) {
        this.seqOeuvPere = seqOeuvPere;
    }

    public String getDurDsOeuvPere() {
        return durDsOeuvPere;
    }

    public void setDurDsOeuvPere(String durDsOeuvPere) {
        this.durDsOeuvPere = durDsOeuvPere;
    }

    public String getTaxatDsOeuvPere() {
        return taxatDsOeuvPere;
    }

    public void setTaxatDsOeuvPere(String taxatDsOeuvPere) {
        this.taxatDsOeuvPere = taxatDsOeuvPere;
    }

    public String getCdeGreDifDsOeuvPere() {
        return cdeGreDifDsOeuvPere;
    }

    public void setCdeGreDifDsOeuvPere(String cdeGreDifDsOeuvPere) {
        this.cdeGreDifDsOeuvPere = cdeGreDifDsOeuvPere;
    }

    public String getOeuvPreExist() {
        return oeuvPreExist;
    }

    public void setOeuvPreExist(String oeuvPreExist) {
        this.oeuvPreExist = oeuvPreExist;
    }

    public String getPctDvaltn() {
        return pctDvaltn;
    }

    public void setPctDvaltn(String pctDvaltn) {
        this.pctDvaltn = pctDvaltn;
    }

    public String getNumOrd() {
        return numOrd;
    }

    public void setNumOrd(String numOrd) {
        this.numOrd = numOrd;
    }

    public String getIndDroitTotEnAttente() {
        return indDroitTotEnAttente;
    }

    public void setIndDroitTotEnAttente(String indDroitTotEnAttente) {
        this.indDroitTotEnAttente = indDroitTotEnAttente;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCdeGreValorisationDoc() {
        return cdeGreValorisationDoc;
    }

    public void setCdeGreValorisationDoc(String cdeGreValorisationDoc) {
        this.cdeGreValorisationDoc = cdeGreValorisationDoc;
    }

    public String getOeuvreSynchrone() {
        return oeuvreSynchrone;
    }

    public void setOeuvreSynchrone(String oeuvreSynchrone) {
        this.oeuvreSynchrone = oeuvreSynchrone;
    }

    public String getIndVOCueSheet() {
        return indVOCueSheet;
    }

    public void setIndVOCueSheet(String indVOCueSheet) {
        this.indVOCueSheet = indVOCueSheet;
    }

    public String getCdeLngCtnu() {
        return cdeLngCtnu;
    }

    public void setCdeLngCtnu(String cdeLngCtnu) {
        this.cdeLngCtnu = cdeLngCtnu;
    }

    public String getFlagImpose() {
        return flagImpose;
    }

    public void setFlagImpose(String flagImpose) {
        this.flagImpose = flagImpose;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
