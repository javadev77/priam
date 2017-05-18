package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Entity
@Table(name = "PRIAM_FAMILTYPUTIL")
public class Famille {
    
    private String code;
    private Date dateDebut;
    private Date dateFin;
    
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
