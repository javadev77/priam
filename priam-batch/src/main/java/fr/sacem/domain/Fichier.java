package fr.sacem.domain;

import org.springframework.batch.core.Entity;

import java.sql.Timestamp;

/**
 * Created by benmerzoukah on 09/05/2017.
 */

public class Fichier extends Entity {

    private Long id;

    private String nom;

    private String famille;

    private String typeUtilisation;


    private Timestamp dateDebutChargt;

    private Timestamp dateFinChargt;

    private Long nbLignes;


    private String statut;

    public Fichier() {

    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getFamille() {
        return famille;
    }

    public String getTypeUtilisation() {
        return typeUtilisation;
    }

    public Timestamp getDateDebutChargt() {
        return dateDebutChargt;
    }

    public Timestamp getDateFinChargt() {
        return dateFinChargt;
    }

    public Long getNbLignes() {
        return nbLignes;
    }

    public String getStatut() {
        return statut;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setFamille(String famille) {
        this.famille = famille;
    }

    public void setTypeUtilisation(String typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }

    public void setDateDebutChargt(Timestamp dateDebutChargt) {
        this.dateDebutChargt = dateDebutChargt;
    }

    public void setDateFinChargt(Timestamp dateFinChargt) {
        this.dateFinChargt = dateFinChargt;
    }

    public void setNbLignes(Long nbLignes) {
        this.nbLignes = nbLignes;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Fichier(String nom, String famille, String typeUtilisation, Timestamp dateDebutChargt, Timestamp dateFinChargt, Long nbLignes, String statut) {
        this.nom = nom;
        this.famille = famille;
        this.typeUtilisation = typeUtilisation;
        this.dateDebutChargt = dateDebutChargt;
        this.dateFinChargt = dateFinChargt;
        this.nbLignes = nbLignes;
        this.statut = statut;
    }


    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fichier that = (Fichier) o;

        return id == that.id;
    }
}
