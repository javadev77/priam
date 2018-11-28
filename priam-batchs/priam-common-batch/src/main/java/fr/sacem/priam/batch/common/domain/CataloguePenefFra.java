package fr.sacem.priam.batch.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by benmerzoukah on 17/05/2018.
 */
public class CataloguePenefFra {
    String typeCMS;
    Long ide12;
    String cdeTypIde12;
    String cdeTypUtilOri;
    String titreOeuvre;
    String nomParticipant1;
    String roleParticipant1;
    Long idFichier;

    private Exception exception;
    private int lineNumber;

    public CataloguePenefFra() {

    }

    public CataloguePenefFra(Exception ex) {
        this.exception = ex;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
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

    public String getCdeTypIde12() {
        return cdeTypIde12;
    }

    public void setCdeTypIde12(String cdeTypIde12) {
        this.cdeTypIde12 = cdeTypIde12;
    }

    public String getCdeTypUtilOri() {
        return cdeTypUtilOri;
    }

    public void setCdeTypUtilOri(String cdeTypUtilOri) {
        this.cdeTypUtilOri = cdeTypUtilOri;
    }

    public String getTitreOeuvre() {
        return titreOeuvre;
    }

    public void setTitreOeuvre(String titreOeuvre) {
        this.titreOeuvre = titreOeuvre;
    }

    public String getNomParticipant1() {
        return nomParticipant1;
    }

    public void setNomParticipant1(String nomParticipant1) {
        this.nomParticipant1 = nomParticipant1;
    }

    public String getRoleParticipant1() {
        return roleParticipant1;
    }

    public void setRoleParticipant1(String roleParticipant1) {
        this.roleParticipant1 = roleParticipant1;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Long getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(Long idFichier) {
        this.idFichier = idFichier;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
