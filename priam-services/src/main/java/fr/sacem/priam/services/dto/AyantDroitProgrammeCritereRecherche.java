package fr.sacem.priam.services.dto;

public class AyantDroitProgrammeCritereRecherche {

    private String numProg;
    private Long ide12;
    private Long coad;
    private String titre;
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

    public Long getCoad() {
        return coad;
    }

    public void setCoad(Long coad) {
        this.coad = coad;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }
}
