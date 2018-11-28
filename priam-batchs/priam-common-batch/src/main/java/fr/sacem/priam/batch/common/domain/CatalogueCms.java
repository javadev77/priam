package fr.sacem.priam.batch.common.domain;

import java.time.LocalDate;

public class CatalogueCms {
    private Long id;
    private String typeCMS;
    private Long ide12;
    private String titre;
    private String typUtilGen;
    private LocalDate dateEntree;
    private LocalDate dateRenouvellement;
    private String typeInscription;
    private LocalDate dateSortie;
    private String typeSortie;
    /*private String role;
    private String participant;*/

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

    public String getTypUtilGen() {
        return typUtilGen;
    }

    public void setTypUtilGen(String typUtilGen) {
        this.typUtilGen = typUtilGen;
    }


    /*public String getRole() {
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
    }*/

    public String getTypeInscription() {
        return typeInscription;
    }

    public void setTypeInscription(String typeInscription) {
        this.typeInscription = typeInscription;
    }

    public void setDateRenouvellement(LocalDate dateRenouvellement) {
        this.dateRenouvellement = dateRenouvellement;
    }

    public String getTypeSortie() {
        return typeSortie;
    }

    public void setTypeSortie(String typeSortie) {
        this.typeSortie = typeSortie;
    }

    public LocalDate getDateEntree() {
        return dateEntree;
    }

    public void setDateEntree(LocalDate dateEntree) {
        this.dateEntree = dateEntree;
    }

    public LocalDate getDateSortie() {
        return dateSortie;
    }

    public void setDateSortie(LocalDate dateSortie) {
        this.dateSortie = dateSortie;
    }

    public CatalogueCms() {
    }


}
