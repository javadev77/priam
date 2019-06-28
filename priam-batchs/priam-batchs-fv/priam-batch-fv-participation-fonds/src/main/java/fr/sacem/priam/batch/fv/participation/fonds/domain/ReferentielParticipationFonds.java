package fr.sacem.priam.batch.fv.participation.fonds.domain;

public class ReferentielParticipationFonds {

    private Long ide12;
    private String cdeTypIde12;
    private String cdeFamilleTypeUtil;
    private String cdeTypUtil;
    private Integer rionPaiementMax;
    private Integer statut;
    private Integer lineNumber;
    private Exception exception;

    public ReferentielParticipationFonds() {
    }

    public ReferentielParticipationFonds(Exception exception) {
        this.exception = exception;
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

    public String getCdeFamilleTypeUtil() {
        return cdeFamilleTypeUtil;
    }

    public void setCdeFamilleTypeUtil(String cdeFamilleTypeUtil) {
        this.cdeFamilleTypeUtil = cdeFamilleTypeUtil;
    }

    public String getCdeTypUtil() {
        return cdeTypUtil;
    }

    public void setCdeTypUtil(String cdeTypUtil) {
        this.cdeTypUtil = cdeTypUtil;
    }

    public Integer getRionPaiementMax() {
        return rionPaiementMax;
    }

    public void setRionPaiementMax(Integer rionPaiementMax) {
        this.rionPaiementMax = rionPaiementMax;
    }

    public Integer getStatut() {
        return statut;
    }

    public void setStatut(Integer statut) {
        this.statut = statut;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (this.getClass() != obj.getClass())return false;
        ReferentielParticipationFonds referentielParticipationFonds = (ReferentielParticipationFonds)obj;
        if(this.hashCode()== referentielParticipationFonds.hashCode())return true;
        return false;
    }

    @Override
    public int hashCode() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.ide12);
        buffer.append(this.cdeTypUtil);
        return buffer.toString().hashCode();
    }
}
