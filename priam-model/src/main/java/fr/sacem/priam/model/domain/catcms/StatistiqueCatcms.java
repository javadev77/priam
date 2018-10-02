package fr.sacem.priam.model.domain.catcms;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.util.CustomDateSerializer;
import fr.sacem.priam.model.util.SimpleDateSerializer;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by embouazzar on 28/09/2018.
 */
@Entity
@Table(name = "PRIAM_CATCMS_STATISTIQUES")
public class StatistiqueCatcms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TYPE_CMS")
    private String typeCMS;

    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date date;

    @Column(name = "NOM_FICHIER")
    private String nomFichier;

    @Column(name = "NB_OEUVRES")
    private Long nbOeuvres;

    @Column(name = "NB_CREATION")
    private Long nbCreation;

    @Column(name = "NB_RENOUVELLEMENT")
    private Long nbRenouvellement;

    @Column(name = "NB_TOTAL_OEUVRES")
    private Long nbTotalOeuvres;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public Long getNbOeuvres() {
        return nbOeuvres;
    }

    public void setNbOeuvres(Long nbOeuvres) {
        this.nbOeuvres = nbOeuvres;
    }

    public Long getNbCreation() {
        return nbCreation;
    }

    public void setNbCreation(Long nbCreation) {
        this.nbCreation = nbCreation;
    }

    public Long getNbRenouvellement() {
        return nbRenouvellement;
    }

    public void setNbRenouvellement(Long nbRenouvellement) {
        this.nbRenouvellement = nbRenouvellement;
    }

    public Long getNbTotalOeuvres() {
        return nbTotalOeuvres;
    }

    public void setNbTotalOeuvres(Long nbTotalOeuvres) {
        this.nbTotalOeuvres = nbTotalOeuvres;
    }

    public StatistiqueCatcms() {
    }
}
