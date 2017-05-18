package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Entity
@Table(name = "PRIAM_FAMILTYPUTIL")
public class Famille {
    
    private String code;
    private Date dateDebut;
    private Date dateFin;
    private List<TypeUtilisation> typeUtilisations;
    
    public Famille() {
    
    }
    
    @Id
    @Column(name = "CDEFAMILTYPUTIL")
    public String getCode() {
        return code;
    }
    
    @Column(name = "DATDBTVLD")
    @Temporal(TemporalType.DATE)
    public Date getDateDebut() {
        return dateDebut;
    }
    
    @Column(name = "DATFINVLD")
    @Temporal(TemporalType.DATE)
    public Date getDateFin() {
        return dateFin;
    }
    
    @OneToMany(mappedBy = "codeFamille", fetch = FetchType.EAGER)
    public List<TypeUtilisation> getTypeUtilisations() {
        return typeUtilisations;
    }
    
    public void setTypeUtilisations(List<TypeUtilisation> typeUtilisations) {
        this.typeUtilisations = typeUtilisations;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
}
