package fr.sacem.priam.batch.fv.repartition.domain;

import java.io.Serializable;
import java.util.Date;

public class LignePreprepFV implements Serializable {

    private Long id;
    private String typRepart;
    private String cdeCisac;
    private Integer cdeTer;
    private Integer rionEffet;
    private String cdeFamilTypUtil;
    private Integer numProg;
    private String cdeUtil;
    private String cdeTypUtil;
    private String cdeModFac;
    private String cdeTypProg;
    private String cdeCompl;
    private String libProg;
    private Date datDbtProg;
    private Date datFinProg;
    private Long ide12;
    private String cdeTypIde12;
    private Date datSitu;
    private Date datConslt;
    private Long durDif;
    private Long nbrDif;
    private String typMt;
    private Double mt;
    private String cdeTypDrtSacem;
    private Long coadPayer;
    private Long idSteAd;
    private String rolAd;
    private Double cleAd;
    private String cdeTypProtec;
    private Long coadOriEdtr;
    private Long idSteOriEdtr;
    private Long numPers;
    private Long numCatal;
    private Double points;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTypRepart() {
        return typRepart;
    }

    public void setTypRepart(String typRepart) {
        this.typRepart = typRepart;
    }

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

    public Integer getNumProg() {
        return numProg;
    }

    public void setNumProg(Integer numProg) {
        this.numProg = numProg;
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

    public Long getIde12() {
        return ide12;
    }

    public void setIde12(Long ide12) {
        this.ide12 = ide12;
    }

    public String getCdeTypIde12() {
        return cdeTypIde12;
    }

    public void setCdeTypIde12(String cdeTypIde12) {
        this.cdeTypIde12 = cdeTypIde12;
    }

    public Date getDatSitu() {
        return datSitu;
    }

    public void setDatSitu(Date datSitu) {
        this.datSitu = datSitu;
    }

    public Date getDatConslt() {
        return datConslt;
    }

    public void setDatConslt(Date datConslt) {
        this.datConslt = datConslt;
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

    public String getTypMt() {
        return typMt;
    }

    public void setTypMt(String typMt) {
        this.typMt = typMt;
    }

    public Double getMt() {
        return mt;
    }

    public void setMt(Double mt) {
        this.mt = mt;
    }

    public String getCdeTypDrtSacem() {
        return cdeTypDrtSacem;
    }

    public void setCdeTypDrtSacem(String cdeTypDrtSacem) {
        this.cdeTypDrtSacem = cdeTypDrtSacem;
    }

    public Long getCoadPayer() {
        return coadPayer;
    }

    public void setCoadPayer(Long coadPayer) {
        this.coadPayer = coadPayer;
    }

    public Long getIdSteAd() {
        return idSteAd;
    }

    public void setIdSteAd(Long idSteAd) {
        this.idSteAd = idSteAd;
    }

    public String getRolAd() {
        return rolAd;
    }

    public void setRolAd(String rolAd) {
        this.rolAd = rolAd;
    }

    public Double getCleAd() {
        return cleAd;
    }

    public void setCleAd(Double cleAd) {
        this.cleAd = cleAd;
    }

    public String getCdeTypProtec() {
        return cdeTypProtec;
    }

    public void setCdeTypProtec(String cdeTypProtec) {
        this.cdeTypProtec = cdeTypProtec;
    }

    public Long getCoadOriEdtr() {
        return coadOriEdtr;
    }

    public void setCoadOriEdtr(Long coadOriEdtr) {
        this.coadOriEdtr = coadOriEdtr;
    }

    public Long getIdSteOriEdtr() {
        return idSteOriEdtr;
    }

    public void setIdSteOriEdtr(Long idSteOriEdtr) {
        this.idSteOriEdtr = idSteOriEdtr;
    }

    public Long getNumPers() {
        return numPers;
    }

    public void setNumPers(Long numPers) {
        this.numPers = numPers;
    }

    public Long getNumCatal() {
        return numCatal;
    }

    public void setNumCatal(Long numCatal) {
        this.numCatal = numCatal;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public LignePreprepFV() {
    }
}
