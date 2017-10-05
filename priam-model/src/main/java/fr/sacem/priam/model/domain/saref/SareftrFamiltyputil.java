package fr.sacem.priam.model.domain.saref;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
@Entity
@Table(name = "SAREFTR_FAMILTYPUTIL")
public class SareftrFamiltyputil {
    
    private String code;
    private Date dateDebut;
    private Date dateFin;
    private List<SareftrTyputil> sareftrTyputils;
    /*private String cdefamiltyputil;
    private Long filtre;
    private Long ordaff;
    private String com;
    private String indlieudif;
    private String typrion;
    private String typdifaudv;
    private Date datcre;
    private String usercre;
    private Date datmaj;
    private String usermaj;
    //private Date datdbtvld;
    //private Date datfinvld;
    private String cdetypprocess;
    private Double poidsof;
    private String cdefamilpart;
    private String familrempl;
    */
    public SareftrFamiltyputil() {
    
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
    
    @OneToMany(mappedBy = "codeFamille", fetch = FetchType.LAZY)
    public List<SareftrTyputil> getSareftrTyputils() {
        return sareftrTyputils;
    }
    
    public void setSareftrTyputils(List<SareftrTyputil> sareftrTyputils) {
        this.sareftrTyputils = sareftrTyputils;
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
