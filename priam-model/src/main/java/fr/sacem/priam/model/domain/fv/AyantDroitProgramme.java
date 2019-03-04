package fr.sacem.priam.model.domain.fv;

import fr.sacem.priam.model.domain.Programme;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "AYANT_DROIT_PROGRAMME_VIEW")
public class AyantDroitProgramme implements Serializable {

    @Id
    @Column(name = "ID")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NUMPROG")
    private Programme programme;

    @Column(name = "ide12")
    private Long ide12;

    @Column(name = "titre")
    private String titre;

    @Column(name = "COAD")
    private Long coad;

    @Column(name = "role")
    private String role;

    @Column(name = "participant")
    private String participant;

    @Column(name = "points")
    private Double points;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Programme getProgramme() {
        return programme;
    }

    public void setProgramme(Programme programme) {
        this.programme = programme;
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
