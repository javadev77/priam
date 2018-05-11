package fr.sacem.priam.batch.common.domain;

public class LigneCatalogueOctav  {
    private String typeCMS;
    private Long ide12;
    private String titre;
    private String participant;
    private String role;
    private String numProg;
    private int nbrDif;

    public LigneCatalogueOctav() {
    }

    public LigneCatalogueOctav(String typeCMS, Long ide12, String titre, String participant, String role, String numProg, int nbrDif) {
        this.typeCMS = typeCMS;
        this.ide12 = ide12;
        this.titre = titre;
        this.participant = participant;
        this.role = role;
        this.numProg = numProg;
        this.nbrDif = nbrDif;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public int getNbrDif() {
        return nbrDif;
    }

    public void setNbrDif(int nbrDif) {
        this.nbrDif = nbrDif;
    }
}
