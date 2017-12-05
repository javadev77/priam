package fr.sacem.priam.batch.affectation.domain;

/**
 * Created by benmerzoukah on 01/12/2017.
 */
public class CatalogueOctavItem {

    private String typeCMS;
    private Long ide12;
    private String titre;
    private String participant;
    private String role;

    public CatalogueOctavItem() {

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

    private enum  TypeCMS {
        ANF("ANF"), FR("FR");

        private String code;

        TypeCMS(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }
}
