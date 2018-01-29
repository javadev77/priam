package fr.sacem.priam.model.domain.dto;

/**
 * Created by benmerzoukah on 08/12/2017.
 */
public class SelectionCMSDto extends SelectionDto {

    private Double pointsMontant;

    public SelectionCMSDto(Long ide12, String titreOeuvre, String roleParticipant1, String nomParticipant1,String ajout,
                        boolean selection,Double pointsMontant) {
        super(ide12, titreOeuvre, roleParticipant1, nomParticipant1, ajout, selection, null, null);
        this.pointsMontant = pointsMontant;
    }

    public SelectionCMSDto() {
    }

    public Double getPointsMontant() {
        return pointsMontant;
    }

    public void setPointsMontant(Double pointsMontant) {
        this.pointsMontant = pointsMontant;
    }
}
