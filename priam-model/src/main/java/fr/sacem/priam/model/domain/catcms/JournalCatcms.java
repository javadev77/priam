package fr.sacem.priam.model.domain.catcms;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.util.CustomDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PRIAM_CATCMS_JOURNAL")
public class JournalCatcms implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EVENEMENT")
    private String evenement;

    @Column(name = "IDE12")
    private Long ide12;

    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;

    @Column(name = "UTILISATEUR")
    private String utilisateur;

    @Column(name = "TYPE_CMS")
    private String typeCMS;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEvenement() {
        return evenement;
    }

    public void setEvenement(String evenement) {
        this.evenement = evenement;
    }

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getTypeCMS() {
        return typeCMS;
    }

    public void setTypeCMS(String typeCMS) {
        this.typeCMS = typeCMS;
    }

    public JournalCatcms() {
    }

    public JournalCatcms(String evenement, Long ide12, Date date, String utilisateur, String typeCMS) {
        this.evenement = evenement;
        this.ide12 = ide12;
        this.date = date;
        this.utilisateur = utilisateur;
        this.typeCMS = typeCMS;
    }
}
