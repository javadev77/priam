package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PRIAM_CATALOGUE_OCTAV")
public class CatalogueOctav implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE_CMS")
    private String typeCMS;

    @Column(name = "IDE12")
    private Long ide12;

    @Column(name = "TITRE")
    private String titre;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "PARTICIPANT")
    private String participant;

    public CatalogueOctav() {
    }

    public CatalogueOctav(String typeCMS, Long ide12, String titre, String role, String participant) {
        this.typeCMS = typeCMS;
        this.ide12 = ide12;
        this.titre = titre;
        this.role = role;
        this.participant = participant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
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
}
