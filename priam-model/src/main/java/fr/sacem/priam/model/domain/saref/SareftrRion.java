package fr.sacem.priam.model.domain.saref;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by benmerzoukah on 06/06/2017.
 */

@Entity
@Table(name = "SAREFTR_RION")
public class SareftrRion implements Serializable {
    
    private Integer rion;
    private Date datcalc;
    private Date datrglmt;
    private BigDecimal filtre;
    private BigDecimal ordaff;
    private String com;
    private Date datcre;
    private String usercre;
    private Date datmaj;
    private String usermaj;
    
    public SareftrRion() {
    }
    
    @Id
    @Column(name = "RION", insertable = false, updatable = false)
    public Integer getRion() {
        return rion;
    }
    
    public void setRion(Integer rion) {
        this.rion = rion;
    }
    
    @Basic
    @Column(name = "DATCALC", insertable = false, updatable = false)
    public Date getDatcalc() {
        return datcalc;
    }
    
    public void setDatcalc(Date datcalc) {
        this.datcalc = datcalc;
    }
    
    @Basic
    @Column(name = "DATRGLMT", insertable = false, updatable = false)
    public Date getDatrglmt() {
        return datrglmt;
    }
    
    public void setDatrglmt(Date datrglmt) {
        this.datrglmt = datrglmt;
    }
    
    @Basic
    @Column(name = "FILTRE", insertable = false, updatable = false)
    public BigDecimal getFiltre() {
        return filtre;
    }
    
    public void setFiltre(BigDecimal filtre) {
        this.filtre = filtre;
    }
    
    @Basic
    @Column(name = "ORDAFF", insertable = false, updatable = false)
    public BigDecimal getOrdaff() {
        return ordaff;
    }
    
    public void setOrdaff(BigDecimal ordaff) {
        this.ordaff = ordaff;
    }
    
    @Basic
    @Column(name = "COM", insertable = false, updatable = false)
    public String getCom() {
        return com;
    }
    
    public void setCom(String com) {
        this.com = com;
    }
    
    @Basic
    @Column(name = "DATCRE", insertable = false, updatable = false)
    public Date getDatcre() {
        return datcre;
    }
    
    public void setDatcre(Date datcre) {
        this.datcre = datcre;
    }
    
    @Basic
    @Column(name = "USERCRE", insertable = false, updatable = false)
    public String getUsercre() {
        return usercre;
    }
    
    public void setUsercre(String usercre) {
        this.usercre = usercre;
    }
    
    @Basic
    @Column(name = "DATMAJ", insertable = false, updatable = false)
    public Date getDatmaj() {
        return datmaj;
    }
    
    public void setDatmaj(Date datmaj) {
        this.datmaj = datmaj;
    }
    
    @Basic
    @Column(name = "USERMAJ", insertable = false, updatable = false)
    public String getUsermaj() {
        return usermaj;
    }
    
    public void setUsermaj(String usermaj) {
        this.usermaj = usermaj;
    }
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    
        SareftrRion that = (SareftrRion) o;
        
        return rion == that.rion;
        
    }
    
    @Override
    public int hashCode() {
        return (int) (rion ^ (rion >>> 32));
    }
    
}

