package fr.sacem.priam.model.domain.criteria;

public class AyantDroitCriteria {

    private String numProg;
    private Long ide12;
    private String titre;
    private Long coad;
    private String participant;

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
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

    public Long getCoad() {
        return coad;
    }

    public void setCoad(Long coad) {
        this.coad = coad;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
