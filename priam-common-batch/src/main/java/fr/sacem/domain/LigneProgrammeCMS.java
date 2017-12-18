package fr.sacem.domain;

import java.io.Serializable;

/**
 * Created by fandis on 03/05/2017.
 */

public class LigneProgrammeCMS implements Serializable {

    private Long idFichier;
    private String cdeCisac;
    private String cdeFamilTypUtil;
    private Long numProg;
    private String cdeUtil;
    private String cdeTypUtil;
    private String cdeGreDif;
    private String cdeModDif;
    private String cdeTypIde12;
    private Long ide12;
    private String durDif;
    private String nbrDif;
    private Double mt;
    private String ctna;
    private String paramCoefHor;
    private String durDifCtna;
    private String cdeLng;
    private String indDoubSsTit;
    private String tax;
    private String typMt;
    private String cdeGreIde12Cmplx;
    private String cdeGreIde12;
    private String titreOriCmplx;
    private String titreAltPppalCmplx;
    private String titreOriOeuvPereCmplx;
    private String titreAltOeuvPereCmplx;
    private String titreOeuvre;
    private String cdePaysOriIso4NCmplx;
    private String realisateurCmplx;
    private String roleParticipant1;
    private String nomParticipant1;
    private String cdeTypUtilOri;
    private String cdeFamilTypUtilOri;
    private String utilisateur;
    private String date_insertion;
    private String ajout;
    private String selection;
    private Integer lineNumber;
    private String libelleUtilisateur;
    private Exception exception;


    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getDate_insertion() {
        return date_insertion;
    }

    public void setDate_insertion(String date_insertion) {
        this.date_insertion = date_insertion;
    }

    public String getAjout() {
        return ajout;
    }

    public void setAjout(String ajout) {
        this.ajout = ajout;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public LigneProgrammeCMS() {
    }

    public LigneProgrammeCMS(String cdeCisac, String cdeFamilTypUtil, Long numProg, String cdeUtil, String cdeTypUtil, String cdeGreDif, String cdeModDif, String cdeTypIde12, Long ide12, String durDif, String nbrDif, Double mt, String ctna, String paramCoefHor, String durDifCtna, String cdeLng, String indDoubSsTit, String tax, String typMt, String cdeGreIde12Cmplx, String cdeGreIde12, String titreOriCmplx, String titreAltPppalCmplx, String titreOriOeuvPereCmplx, String titreAltOeuvPereCmplx, String titreOeuvre, String cdePaysOriIso4NCmplx, String realisateurCmplx, String roleParticipant1, String nomParticipant1, String cdeTypUtilOri, String cdeFamilTypUtilOri) {
        this.cdeCisac = cdeCisac;
        this.cdeFamilTypUtil = cdeFamilTypUtil;
        this.numProg = numProg;
        this.cdeUtil = cdeUtil;
        this.cdeTypUtil = cdeTypUtil;
        this.cdeGreDif = cdeGreDif;
        this.cdeModDif = cdeModDif;
        this.cdeTypIde12 = cdeTypIde12;
        this.ide12 = ide12;
        this.durDif = durDif;
        this.nbrDif = nbrDif;
        this.mt = mt;
        this.ctna = ctna;
        this.paramCoefHor = paramCoefHor;
        this.durDifCtna = durDifCtna;
        this.cdeLng = cdeLng;
        this.indDoubSsTit = indDoubSsTit;
        this.tax = tax;
        this.typMt = typMt;
        this.cdeGreIde12Cmplx = cdeGreIde12Cmplx;
        this.cdeGreIde12 = cdeGreIde12;
        this.titreOriCmplx = titreOriCmplx;
        this.titreAltPppalCmplx = titreAltPppalCmplx;
        this.titreOriOeuvPereCmplx = titreOriOeuvPereCmplx;
        this.titreAltOeuvPereCmplx = titreAltOeuvPereCmplx;
        this.titreOeuvre = titreOeuvre;
        this.cdePaysOriIso4NCmplx = cdePaysOriIso4NCmplx;
        this.realisateurCmplx = realisateurCmplx;
        this.roleParticipant1 = roleParticipant1;
        this.nomParticipant1 = nomParticipant1;
        this.cdeTypUtilOri = cdeTypUtilOri;
        this.cdeFamilTypUtilOri = cdeFamilTypUtilOri;
    }

    public LigneProgrammeCMS(Exception e) {
        this.exception = e;
    }

    public Long getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(Long idFichier) {
        this.idFichier = idFichier;
    }

    public String getCdeCisac() {
        return cdeCisac;
    }

    public void setCdeCisac(String cdeCisac) {
        this.cdeCisac = cdeCisac;
    }

    public String getCdeFamilTypUtil() {
        return cdeFamilTypUtil;
    }

    public void setCdeFamilTypUtil(String cdeFamilTypUtil) {
        this.cdeFamilTypUtil = cdeFamilTypUtil;
    }

    public Long getNumProg() {
        return numProg;
    }

    public void setNumProg(Long numProg) {
        this.numProg = numProg;
    }

    public String getCdeUtil() {
        return cdeUtil;
    }

    public void setCdeUtil(String cdeUtil) {
        this.cdeUtil = cdeUtil;
    }

    public String getCdeTypUtil() {
        return cdeTypUtil;
    }

    public void setCdeTypUtil(String cdeTypUtil) {
        this.cdeTypUtil = cdeTypUtil;
    }

    public String getCdeGreDif() {
        return cdeGreDif;
    }

    public void setCdeGreDif(String cdeGreDif) {
        this.cdeGreDif = cdeGreDif;
    }

    public String getCdeModDif() {
        return cdeModDif;
    }

    public void setCdeModDif(String cdeModDif) {
        this.cdeModDif = cdeModDif;
    }

    public String getCdeTypIde12() {
        return cdeTypIde12;
    }

    public void setCdeTypIde12(String cdeTypIde12) {
        this.cdeTypIde12 = cdeTypIde12;
    }

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public String getDurDif() {
        return durDif;
    }

    public void setDurDif(String durDif) {
        this.durDif = durDif;
    }

    public String getNbrDif() {
        return nbrDif;
    }

    public void setNbrDif(String nbrDif) {
        this.nbrDif = nbrDif;
    }

    public Double getMt() {
        return mt;
    }

    public void setMt(Double mt) {
        this.mt = mt;
    }

    public String getCtna() {
        return ctna;
    }

    public void setCtna(String ctna) {
        this.ctna = ctna;
    }

    public String getParamCoefHor() {
        return paramCoefHor;
    }

    public void setParamCoefHor(String paramCoefHor) {
        this.paramCoefHor = paramCoefHor;
    }

    public String getDurDifCtna() {
        return durDifCtna;
    }

    public void setDurDifCtna(String durDifCtna) {
        this.durDifCtna = durDifCtna;
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

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getTypMt() {
        return typMt;
    }

    public void setTypMt(String typMt) {
        this.typMt = typMt;
    }

    public String getCdeGreIde12Cmplx() {
        return cdeGreIde12Cmplx;
    }

    public void setCdeGreIde12Cmplx(String cdeGreIde12Cmplx) {
        this.cdeGreIde12Cmplx = cdeGreIde12Cmplx;
    }

    public String getCdeGreIde12() {
        return cdeGreIde12;
    }

    public void setCdeGreIde12(String cdeGreIde12) {
        this.cdeGreIde12 = cdeGreIde12;
    }

    public String getTitreOriCmplx() {
        return titreOriCmplx;
    }

    public void setTitreOriCmplx(String titreOriCmplx) {
        this.titreOriCmplx = titreOriCmplx;
    }

    public String getTitreAltPppalCmplx() {
        return titreAltPppalCmplx;
    }

    public void setTitreAltPppalCmplx(String titreAltPppalCmplx) {
        this.titreAltPppalCmplx = titreAltPppalCmplx;
    }

    public String getTitreOriOeuvPereCmplx() {
        return titreOriOeuvPereCmplx;
    }

    public void setTitreOriOeuvPereCmplx(String titreOriOeuvPereCmplx) {
        this.titreOriOeuvPereCmplx = titreOriOeuvPereCmplx;
    }

    public String getTitreAltOeuvPereCmplx() {
        return titreAltOeuvPereCmplx;
    }

    public void setTitreAltOeuvPereCmplx(String titreAltOeuvPereCmplx) {
        this.titreAltOeuvPereCmplx = titreAltOeuvPereCmplx;
    }

    public String getTitreOeuvre() {
        return titreOeuvre;
    }

    public void setTitreOeuvre(String titreOeuvre) {
        this.titreOeuvre = titreOeuvre;
    }

    public String getCdePaysOriIso4NCmplx() {
        return cdePaysOriIso4NCmplx;
    }

    public void setCdePaysOriIso4NCmplx(String cdePaysOriIso4NCmplx) {
        this.cdePaysOriIso4NCmplx = cdePaysOriIso4NCmplx;
    }

    public String getRealisateurCmplx() {
        return realisateurCmplx;
    }

    public void setRealisateurCmplx(String realisateurCmplx) {
        this.realisateurCmplx = realisateurCmplx;
    }

    public String getRoleParticipant1() {
        return roleParticipant1;
    }

    public void setRoleParticipant1(String roleParticipant1) {
        this.roleParticipant1 = roleParticipant1;
    }

    public String getNomParticipant1() {
        return nomParticipant1;
    }

    public void setNomParticipant1(String nomParticipant1) {
        this.nomParticipant1 = nomParticipant1;
    }

    public String getCdeTypUtilOri() {
        return cdeTypUtilOri;
    }

    public void setCdeTypUtilOri(String cdeTypUtilOri) {
        this.cdeTypUtilOri = cdeTypUtilOri;
    }

    public String getCdeFamilTypUtilOri() {
        return cdeFamilTypUtilOri;
    }

    public void setCdeFamilTypUtilOri(String cdeFamilTypUtilOri) {
        this.cdeFamilTypUtilOri = cdeFamilTypUtilOri;
    }

    public Integer getLineNumber() { return lineNumber; }

    public void setLineNumber(Integer lineNumber) { this.lineNumber = lineNumber; }

    public Exception getException() { return exception; }

    public void setException(Exception exception) { this.exception = exception; }

    public String getLibelleUtilisateur() {
        return libelleUtilisateur;
    }

    public void setLibelleUtilisateur(String libelleUtilisateur) {
        this.libelleUtilisateur = libelleUtilisateur;
    }
}
