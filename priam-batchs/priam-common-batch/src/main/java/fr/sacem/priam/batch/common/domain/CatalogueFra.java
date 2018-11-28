package fr.sacem.priam.batch.common.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;

public class CatalogueFra {
    private String typeCMS;
    private Long ide12;
    private String titre;
    private String typUtilGen;
    private String typeInscription;
    private Date dateSortie;
    private Date dateEntree;
    private String typeSortie;
    private String role;
    private String participant;
    private Date dateRenouvellement;

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

    public String getTypeInscription() {
        return typeInscription;
    }

    public void setTypeInscription(String typeInscription) {
        this.typeInscription = typeInscription;
    }

    public Date getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(Date dateSortie) {
        this.dateSortie = dateSortie;
    }

    public String getTypeSortie() {
        return typeSortie;
    }

    public void setTypeSortie(String typeSortie) {
        this.typeSortie = typeSortie;
    }

    public String getTypUtilGen() {
        return typUtilGen;
    }

    public void setTypUtilGen(String typUtilGen) {
        this.typUtilGen = typUtilGen;
    }

    public Date getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(Date dateEntree) {
        this.dateEntree = dateEntree;
    }

    public CatalogueFra() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public void setDateRenouvellement(Date dateRenouvellement) {
        this.dateRenouvellement = dateRenouvellement;
    }

    public Date getDateRenouvellement() {
        return dateRenouvellement;
    }
}