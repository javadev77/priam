package fr.sacem.priam.batch.participants.rep.domain;

import java.time.LocalDateTime;

public class Participant {
    private Integer id;
    private String typeCMS;
    private Long ide12;
    private String cdeTypUtilIde12;
    private LocalDateTime dateConslt;
    private String rolPart;
    private String nomPart;
    private Integer statut;
    private Long idCatalogue;
    private Integer lineNumber;
    private Exception exception;

    public Participant() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getCdeTypUtilIde12() {
        return cdeTypUtilIde12;
    }

    public void setCdeTypUtilIde12(String cdeTypUtilIde12) {
        this.cdeTypUtilIde12 = cdeTypUtilIde12;
    }

    public LocalDateTime getDateConslt() {
        return dateConslt;
    }

    public void setDateConslt(LocalDateTime dateConslt) {
        this.dateConslt = dateConslt;
    }

    public String getRolPart() {
        return rolPart;
    }

    public void setRolPart(String rolPart) {
        this.rolPart = rolPart;
    }

    public String getNomPart() {
        return nomPart;
    }

    public void setNomPart(String nomPart) {
        this.nomPart = nomPart;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
    }

    public Long getIdCatalogue() {
        return idCatalogue;
    }

    public void setIdCatalogue(Long idCatalogue) {
        this.idCatalogue = idCatalogue;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Participant(Exception exception) {
        this.exception = exception;
    }


}
