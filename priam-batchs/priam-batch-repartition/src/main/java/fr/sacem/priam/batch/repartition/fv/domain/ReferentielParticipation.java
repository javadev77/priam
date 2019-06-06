package fr.sacem.priam.batch.repartition.fv.domain;

public class ReferentielParticipation {

    private Long ide12;
    private String cdeTypUtil;
    private Integer rionPaiementMax;
    private Boolean isPresent;

    public ReferentielParticipation() {
    }

    public ReferentielParticipation(Long ide12, String cdeTypUtil, Integer rionPaiementMax, Boolean isPresent) {
        this.ide12 = ide12;
        this.cdeTypUtil = cdeTypUtil;
        this.rionPaiementMax = rionPaiementMax;
        this.isPresent = isPresent;
    }

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
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

    public Boolean getPresent() {
        return isPresent;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }
}
