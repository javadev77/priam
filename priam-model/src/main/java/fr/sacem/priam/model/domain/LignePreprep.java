package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by benmerzoukah on 29/05/2017.
 */
@Entity
@Table(name = "PRIAM_LIGNE_PREPREP")
public class LignePreprep implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "keyLigPenel")
    private Long keyLigPenel;
    
    @Column(name = "cdeCisac")
    private String cdeCisac;

    @Column(name = "cdeTer")
    private Integer cdeTer;

    @Column(name = "rionEffet")
    private Integer rionEffet;
    
    /*@Column(name = "keyLigPenel")
    private Integer keyLigPenel;*/
    
    
    @Column(name = "cdeFamilTypUtil")
    private String cdeFamilTypUtil;
    
    @Column(name = "numProg")
    private String numProg;
    
    @Column(name = "cdeUtil")
    private String cdeUtil;
    
    @Column(name = "cdeModFac")
    private String cdeModFac;
    
    @Column(name = "cdeTypUtil")
    private String cdeTypUtil;
    
    @Column(name = "cdeTypProg")
    private String cdeTypProg;
    
    @Column(name = "cdeCompl")
    private String cdeCompl;
    
    @Column(name = "libProg")
    private String libProg;
    
    @Column(name = "compLibProg")
    private String compLibProg;
    
    @Column(name = "datDbtProg")
    @Temporal(TemporalType.DATE)
    private Date datDbtProg;
    
    @Column(name = "datFinProg")
    @Temporal(TemporalType.DATE)
    private Date datFinProg;
    
    @Column(name = "hrDbt")
    private Integer hrDbt;
    
    @Column(name = "hrFin")
    private Integer hrFin;
    
    @Column(name = "cdeGreDif")
    private String cdeGreDif;
    
    @Column(name = "cdeModDif")
    private String cdeModDif;
    
    @Column(name = "cdeTypIde12")
    private String cdeTypIde12;
    
    @Column(name = "ide12")
    private Long ide12;
    
    @Column(name = "datDif")
    @Temporal(TemporalType.DATE)
    private Date datDif;
    
    @Column(name = "hrDif")
    private String hrDif;
    
    @Column(name = "durDif")
    private Long durDif;
    
    @Column(name = "nbrDif")
    private Long nbrDif;
    
    @Column(name = "mt")
    private Double mt;
    
    @Column(name = "ctna")
    private String ctna;
    
    @Column(name = "paramCoefHor")
    private String paramCoefHor;
    
    @Column(name = "durDifCtna")
    private Long durDifCtna;
    
    @Column(name = "cdeLng")
    private String cdeLng;
    
    @Column(name = "indDoubSsTit")
    private String indDoubSsTit;
    
    @Column(name = "tax")
    private Double tax;
    
    public LignePreprep() {
    
    }
    
    /*public LignePreprep(String cdeCisac, String cdeUtil, String cdeGreDif, String cdeModDif, String cdeTypIde12, Long ide12, Long durDif, Long nbrDif, Double mt, String ctna, String paramCoefHor, Long durDifCtna, String cdeLng, String indDoubSsTit, Double tax) {
        this.cdeCisac = cdeCisac;
        
        this.cdeUtil = cdeUtil;
        this.cdeGreDif = cdeGreDif;
        this.cdeModDif = cdeModDif;
        this.cdeTypIde12 = cdeTypIde12;
        this.ide12 = ide12;
        this.durDif = durDif;
        this.nbrDif = nbrDif;
        this.mt = mt;
        this.ctna = ctna;
        this.paramCoefHor = paramCoefHor;
        this.durDifCtna = durDifCtna;
        this.cdeLng = cdeLng;
        this.indDoubSsTit = indDoubSsTit;
        this.tax = tax;
    }*/
    
    public LignePreprep(String cdeCisac, Integer cdeTer, Integer rionEffet, String cdeFamilTypUtil, String numProg, String cdeUtil, String cdeModFac, String cdeTypUtil, String cdeTypProg, String cdeCompl, String libProg, Date datDbtProg, Date datFinProg, String cdeGreDif, String cdeModDif, String cdeTypIde12, Long ide12, Long durDif, Long nbrDif, Double mt, String ctna, String paramCoefHor, Long durDifCtna, String cdeLng, String indDoubSsTit, Double tax) {
        this.cdeCisac = cdeCisac;
        this.cdeTer = cdeTer;
        this.rionEffet = rionEffet;
        this.cdeFamilTypUtil = cdeFamilTypUtil;
        this.numProg = numProg;
        this.cdeUtil = cdeUtil;
        this.cdeModFac = cdeModFac;
        this.cdeTypUtil = cdeTypUtil;
        this.cdeTypProg = cdeTypProg;
        this.cdeCompl = cdeCompl;
        this.libProg = libProg;
        this.datDbtProg = datDbtProg;
        this.datFinProg = datFinProg;
        this.cdeGreDif = cdeGreDif;
        this.cdeModDif = cdeModDif;
        this.cdeTypIde12 = cdeTypIde12;
        this.ide12 = ide12;
        /*if (durDif == 0L){
            this.durDif = null;
        } else {
            this.durDif = durDif;
        }*/
        this.durDif = durDif;
        this.nbrDif = nbrDif;
        this.mt = mt;
        this.ctna = ctna;
        this.paramCoefHor = paramCoefHor;
        this.durDifCtna = durDifCtna;
        this.cdeLng = cdeLng;
        this.indDoubSsTit = indDoubSsTit;
        this.tax = tax;
    }
    
   /* public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }*/
    
    public String getCdeCisac() {
        return cdeCisac;
    }
    
    public void setCdeCisac(String cdeCisac) {
        this.cdeCisac = cdeCisac;
    }
    
    public Integer getCdeTer() {
        return cdeTer;
    }
    
    public void setCdeTer(Integer cdeTer) {
        this.cdeTer = cdeTer;
    }
    
    public Integer getRionEffet() {
        return rionEffet;
    }
    
    public void setRionEffet(Integer rionEffet) {
        this.rionEffet = rionEffet;
    }
    
    public String getCdeFamilTypUtil() {
        return cdeFamilTypUtil;
    }
    
    public void setCdeFamilTypUtil(String cdeFamilTypUtil) {
        this.cdeFamilTypUtil = cdeFamilTypUtil;
    }
    
    public String getNumProg() {
        return numProg;
    }
    
    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }
    
    public String getCdeUtil() {
        return cdeUtil;
    }
    
    public void setCdeUtil(String cdeUtil) {
        this.cdeUtil = cdeUtil;
    }
    
    public String getCdeModFac() {
        return cdeModFac;
    }
    
    public void setCdeModFac(String cdeModFac) {
        this.cdeModFac = cdeModFac;
    }
    
    public String getCdeTypUtil() {
        return cdeTypUtil;
    }
    
    public void setCdeTypUtil(String cdeTypUtil) {
        this.cdeTypUtil = cdeTypUtil;
    }
    
    public String getCdeTypProg() {
        return cdeTypProg;
    }
    
    public void setCdeTypProg(String cdeTypProg) {
        this.cdeTypProg = cdeTypProg;
    }
    
    public String getCdeCompl() {
        return cdeCompl;
    }
    
    public void setCdeCompl(String cdeCompl) {
        this.cdeCompl = cdeCompl;
    }
    
    public String getLibProg() {
        return libProg;
    }
    
    public void setLibProg(String libProg) {
        this.libProg = libProg;
    }
    
    public String getCompLibProg() {
        return compLibProg;
    }
    
    public void setCompLibProg(String compLibProg) {
        this.compLibProg = compLibProg;
    }
    
    public Date getDatDbtProg() {
        return datDbtProg;
    }
    
    public void setDatDbtProg(Date datDbtProg) {
        this.datDbtProg = datDbtProg;
    }
    
    public Date getDatFinProg() {
        return datFinProg;
    }
    
    public void setDatFinProg(Date datFinProg) {
        this.datFinProg = datFinProg;
    }
    
    public Integer getHrDbt() {
        return hrDbt;
    }
    
    public void setHrDbt(Integer hrDbt) {
        this.hrDbt = hrDbt;
    }
    
    public Integer getHrFin() {
        return hrFin;
    }
    
    public void setHrFin(Integer hrFin) {
        this.hrFin = hrFin;
    }
    
    public String getCdeGreDif() {
        return cdeGreDif;
    }
    
    public void setCdeGreDif(String cdeGreDif) {
        this.cdeGreDif = cdeGreDif;
    }
    
    public String getCdeModDif() {
        return cdeModDif;
    }
    
    public void setCdeModDif(String cdeModDif) {
        this.cdeModDif = cdeModDif;
    }
    
    public String getCdeTypIde12() {
        return cdeTypIde12;
    }
    
    public void setCdeTypIde12(String cdeTypIde12) {
        this.cdeTypIde12 = cdeTypIde12;
    }
    
    public Long getIde12() {
        return ide12;
    }
    
    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }
    
    public Date getDatDif() {
        return datDif;
    }
    
    public void setDatDif(Date datDif) {
        this.datDif = datDif;
    }
    
    public String getHrDif() {
        return hrDif;
    }
    
    public void setHrDif(String hrDif) {
        this.hrDif = hrDif;
    }
    
    public Long getDurDif() {
        return durDif;
    }
    
    public void setDurDif(Long durDif) {
        this.durDif = durDif;
    }
    
    public Long getNbrDif() {
        return nbrDif;
    }
    
    public void setNbrDif(Long nbrDif) {
        this.nbrDif = nbrDif;
    }
    
    public Double getMt() {
        return mt;
    }
    
    public void setMt(Double mt) {
        this.mt = mt;
    }
    
    public String getCtna() {
        return ctna;
    }
    
    public void setCtna(String ctna) {
        this.ctna = ctna;
    }
    
    public String getParamCoefHor() {
        return paramCoefHor;
    }
    
    public void setParamCoefHor(String paramCoefHor) {
        this.paramCoefHor = paramCoefHor;
    }
    
    public Long getDurDifCtna() {
        return durDifCtna;
    }
    
    public void setDurDifCtna(Long durDifCtna) {
        this.durDifCtna = durDifCtna;
    }
    
    public String getCdeLng() {
        return cdeLng;
    }
    
    public void setCdeLng(String cdeLng) {
        this.cdeLng = cdeLng;
    }
    
    public String getIndDoubSsTit() {
        return indDoubSsTit;
    }
    
    public void setIndDoubSsTit(String indDoubSsTit) {
        this.indDoubSsTit = indDoubSsTit;
    }
    
    public Double getTax() {
        return tax;
    }
    
    public void setTax(Double tax) {
        this.tax = tax;
    }
    
    public Long getKeyLigPenel() {
        return keyLigPenel;
    }
    
    public void setKeyLigPenel(Long keyLigPenel) {
        this.keyLigPenel = keyLigPenel;
    }
}
