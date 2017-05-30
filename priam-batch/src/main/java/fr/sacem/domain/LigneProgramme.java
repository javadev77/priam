package fr.sacem.domain;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by fandis on 03/05/2017.
 */

public class LigneProgramme implements Serializable {
    private Long idFichier;
    private String cdeCisac;
    private Long cdeTer;
    private Long rionEffet;
    private String cdeFamilTypUtil;
    private Long numProg;
    private Long keyLigPenel;
    private String cdeUtil;
    private String cdeTypUtil;
    private String cdeModFac;
    private String cdeTypProg;
    private String cdeCompl;
    private String libProg;
    private String compLibProg;
    private LocalDate datDbtProg;
    private LocalDate datFinProg;
    private Long hrDbt;
    private Long hrFin;
    private String cdeGreDif;
    private String cdeModDif;
    private String cdeTypIde12;
    private Long ide12;
    private LocalDate datDif;
    private Long hrDif;
    private Long durDif;
    private Long nbrDif;
    private Double mt;
    private String ctna;
    private String paramCoefHor;
    private Long durDifCtna;
    private String cdeLng;
    private String indDoubSsTit;
    private Double tax;
    private String typMt;


    public LigneProgramme(String nomFichier, String cdeCisac, Long cdeTer, Long rionEffet, String cdeFamilTypUtil, Long numProg, Long keyLigPenel, String cdeUtil, String cdeTypUtil, String cdeModFac, String cdeTypProg, String cdeCompl, String libProg, String compLibProg, LocalDate datDbtProg, LocalDate datFinProg, Long hrDbt, Long hrFin, String cdeGreDif, String cdeModDif, String cdeTypIde12, Long ide12, LocalDate datDif, Long hrDif, Long durDif, Long nbrDif, Double mt, String ctna, String paramCoefHor, Long durDifCtna, String cdeLng, String indDoubSsTit, Double tax, String typMt) {
        this.cdeCisac = cdeCisac;
        this.cdeTer = cdeTer;
        this.rionEffet = rionEffet;
        this.cdeFamilTypUtil = cdeFamilTypUtil;
        this.numProg = numProg;
        this.keyLigPenel = keyLigPenel;
        this.cdeUtil = cdeUtil;
        this.cdeTypUtil = cdeTypUtil;
        this.cdeModFac = cdeModFac;
        this.cdeTypProg = cdeTypProg;
        this.cdeCompl = cdeCompl;
        this.libProg = libProg;
        this.compLibProg = compLibProg;
        this.datDbtProg = datDbtProg;
        this.datFinProg = datFinProg;
        this.hrDbt = hrDbt;
        this.hrFin = hrFin;
        this.cdeGreDif = cdeGreDif;
        this.cdeModDif = cdeModDif;
        this.cdeTypIde12 = cdeTypIde12;
        this.ide12 = ide12;
        this.datDif = datDif;
        this.hrDif = hrDif;
        this.durDif = durDif;
        this.nbrDif = nbrDif;
        this.mt = mt;
        this.ctna = ctna;
        this.paramCoefHor = paramCoefHor;
        this.durDifCtna = durDifCtna;
        this.cdeLng = cdeLng;
        this.indDoubSsTit = indDoubSsTit;
        this.tax = tax;
        this.typMt = typMt;
    }

    public LigneProgramme() {
    }

    public Long getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(Long nomFichier) {
        this.idFichier = nomFichier;
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

    public LocalDate getDatDbtProg() {
        return datDbtProg;
    }

    public void setDatDbtProg(LocalDate datDbtProg) {
        this.datDbtProg = datDbtProg;
    }

    public LocalDate getDatFinProg() {
        return datFinProg;
    }

    public void setDatFinProg(LocalDate datFinProg) {
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

    public LocalDate getDatDif() {
        return datDif;
    }

    public void setDatDif(LocalDate datDif) {
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
