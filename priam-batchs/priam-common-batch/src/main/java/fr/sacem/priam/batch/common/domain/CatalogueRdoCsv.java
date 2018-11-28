package fr.sacem.priam.batch.common.domain;

import java.time.LocalDate;

public class CatalogueRdoCsv {
    private String typeCMS;
    private Long ide12;
    private String titre;
    private String typUtilGen;
    private LocalDate dateCatal;
    private Short pourcentageDP;
    private String role;
    private String participant;
    private Integer lineNumber;
    private Exception exception;

    public String getTypeCMS() {
        return typeCMS;
    }

    public void setTypeCMS(String typeCMS) {
        this.typeCMS = typeCMS;
    }

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getTypUtilGen() {
        return typUtilGen;
    }

    public void setTypUtilGen(String typUtilGen) {
        this.typUtilGen = typUtilGen;
    }



    public Short getPourcentageDP() {
        return pourcentageDP;
    }

    public void setPourcentageDP(Short pourcentageDP) {
        this.pourcentageDP = pourcentageDP;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Exception getException() { return exception; }

    public void setException(Exception exception) { this.exception = exception; }


    public CatalogueRdoCsv() {
    }

    public CatalogueRdoCsv(Exception exception) {
        this.exception = exception;
    }

    public LocalDate getDateCatal() {
        return dateCatal;
    }

    public void setDateCatal(LocalDate dateCatal) {
        this.dateCatal = dateCatal;
    }
}
