package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by benmerzoukah on 29/05/2017.
 */
@Entity
@Table(name = "PRIAM_LIGNE_PROGRAMME")
public class LigneProgramme  implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ID_FICHIER")
    private Fichier fichier;
    @Column(name = "cdeCisac")
    private String cdeCisac;
    @Column(name = "cdeTer")
    private Long cdeTer;
    @Column(name = "rionEffet")
    private Long rionEffet;
    @Column(name = "cdeFamilTypUtil")
    private String cdeFamilTypUtil;
    @Column(name = "numProg")
    private Long numProg;
    @Column(name = "keyLigPenel")
    private Long keyLigPenel;
    @Column(name = "cdeUtil")
    private String cdeUtil;
    @Column(name = "cdeTypUtil")
    private String cdeTypUtil;
    @Column(name = "cdeModFac")
    private String cdeModFac;
    @Column(name = "cdeTypProg")
    private String cdeTypProg;
    @Column(name = "cdeCompl")
    private String cdeCompl;
    @Column(name = "libProg")
    private String libProg;
    @Column(name = "compLibProg")
    private String compLibProg;
    @Column(name = "datDbtProg")
    private Date datDbtProg;
    @Column(name = "datFinProg")
    private Date datFinProg;
    @Column(name = "hrDbt")
    private Long hrDbt;
    @Column(name = "hrFin")
    private Long hrFin;
    @Column(name = "cdeGreDif")
    private String cdeGreDif;
    @Column(name = "cdeModDif")
    private String cdeModDif;
    @Column(name = "cdeTypIde12")
    private String cdeTypIde12;
    @Column(name = "ide12")
    private Long ide12;
    @Column(name = "datDif")
    private Date datDif;
    @Column(name = "hrDif")
    private Long hrDif;
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
    @Column(name = "typMt")
    private String typMt;

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }

    public LigneProgramme() {
    
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getCdeCisac() {
        return cdeCisac;
    }

    public void setCdeCisac(String cdeCisac) {
        this.cdeCisac = cdeCisac;
    }

    public Long getCdeTer() {
        return cdeTer;
    }

    public void setCdeTer(Long cdeTer) {
        this.cdeTer = cdeTer;
    }

    public Long getRionEffet() {
        return rionEffet;
    }

    public void setRionEffet(Long rionEffet) {
        this.rionEffet = rionEffet;
    }

    public String getCdeFamilTypUtil() {
        return cdeFamilTypUtil;
    }

    public void setCdeFamilTypUtil(String cdeFamilTypUtil) {
        this.cdeFamilTypUtil = cdeFamilTypUtil;
    }

    public Long getNumProg() {
        return numProg;
    }

    public void setNumProg(Long numProg) {
        this.numProg = numProg;
    }

    public Long getKeyLigPenel() {
        return keyLigPenel;
    }

    public void setKeyLigPenel(Long keyLigPenel) {
        this.keyLigPenel = keyLigPenel;
    }

    public String getCdeUtil() {
        return cdeUtil;
    }

    public void setCdeUtil(String cdeUtil) {
        this.cdeUtil = cdeUtil;
    }

    public String getCdeTypUtil() {
        return cdeTypUtil;
    }

    public void setCdeTypUtil(String cdeTypUtil) {
        this.cdeTypUtil = cdeTypUtil;
    }

    public String getCdeModFac() {
        return cdeModFac;
    }

    public void setCdeModFac(String cdeModFac) {
        this.cdeModFac = cdeModFac;
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

    public Long getHrDbt() {
        return hrDbt;
    }

    public void setHrDbt(Long hrDbt) {
        this.hrDbt = hrDbt;
    }

    public Long getHrFin() {
        return hrFin;
    }

    public void setHrFin(Long hrFin) {
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

    public Long getHrDif() {
        return hrDif;
    }

    public void setHrDif(Long hrDif) {
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

    public String getTypMt() {
        return typMt;
    }

    public void setTypMt(String typMt) {
        this.typMt = typMt;
    }
}
