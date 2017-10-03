package fr.sacem.priam.model.domain.saref;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Entity
@Table(name = "SAREFFR_TYPUTIL")
public class SareftrTyputil {
    private String code;
    private String codeFamille;
    private Date dateDebut;
    private Date dateFin;
    /*
    private String cdetyputil;
    private String cdefamiltyputil;
    private Long filtre;
    private Long ordaff;
    private Long flgulyss;
    private Long typdrt;
    private String com;
    private java.sql.Timestamp datcre;
    private String usercre;
    private java.sql.Timestamp datmaj;
    private String usermaj;
    private java.sql.Timestamp datdbtvld;
    private java.sql.Timestamp datfinvld;
    private Long cdedistrbcategtyp;
    private String txfrais;
    private Long flgsuidg;
    private Long flgignoredp;
    private Long flgtax;
    private String typmt;
    private String typutilrempl;
    */
    public SareftrTyputil() {
    
    }
    
    @Id
    @Column(name = "CDETYPUTIL")
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
    
    @Column(name = "CDEFAMILTYPUTIL")
    public String getCodeFamille() {
        return codeFamille;
    }
    
    public void setCodeFamille(String codeFamille) {
        this.codeFamille = codeFamille;
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
