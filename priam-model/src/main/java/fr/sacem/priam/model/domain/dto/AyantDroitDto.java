package fr.sacem.priam.model.domain.dto;

public class AyantDroitDto {

    private Long ide12;
    private String titre;
    private Long coad;
    private String role;
    private String participant;
    private Double points;

    public AyantDroitDto(Long ide12, String titre, Long coad, String role, String participant, Double points) {
        this.ide12 = ide12;
        this.titre = titre;
        this.coad = coad;
        this.role = role;
        this.participant = participant;
        this.points = points;
    }

    public AyantDroitDto(Long coad, String participant, Double points) {
        this.coad = coad;
        this.participant = participant;
        this.points = points;
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

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }
}
