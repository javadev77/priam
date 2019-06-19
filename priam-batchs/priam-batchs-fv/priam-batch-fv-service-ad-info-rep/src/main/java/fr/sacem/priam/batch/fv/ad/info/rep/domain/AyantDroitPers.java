package fr.sacem.priam.batch.fv.ad.info.rep.domain;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;

import java.io.Serializable;

public class AyantDroitPers implements Serializable {

    private Long numPers;
    private String prenom;
    private String nom;
    private Integer anneeNaissance;
    private Integer anneeDeces;
    private Boolean indicSacem;
    private String sousRole;
    private String statut;
    private Exception exception;
    private Integer lineNumber;

    public AyantDroitPers() {
    }

    public AyantDroitPers(Long numPers, String prenom, String nom, Integer anneeNaissance, Integer anneeDeces, Boolean indicSacem, String sousRole, String statut) {
        this.numPers = numPers;
        this.prenom = prenom;
        this.nom = nom;
        this.anneeNaissance = anneeNaissance;
        this.anneeDeces = anneeDeces;
        this.indicSacem = indicSacem;
        this.sousRole = sousRole;
        this.statut = statut;
    }

    public AyantDroitPers(PriamValidationException e) {
    }

    public Long getNumPers() {
        return numPers;
    }

    public void setNumPers(Long numPers) {
        this.numPers = numPers;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getAnneeNaissance() {
        return anneeNaissance;
    }

    public void setAnneeNaissance(Integer anneeNaissance) {
        this.anneeNaissance = anneeNaissance;
    }

    public Integer getAnneeDeces() {
        return anneeDeces;
    }

    public void setAnneeDeces(Integer anneeDeces) {
        this.anneeDeces = anneeDeces;
    }

    public Boolean getIndicSacem() {
        return indicSacem;
    }

    public void setIndicSacem(Boolean indicSacem) {
        this.indicSacem = indicSacem;
    }

    public String getSousRole() {
        return sousRole;
    }

    public void setSousRole(String sousRole) {
        this.sousRole = sousRole;
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

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
