package fr.sacem.priam.model.domain.dto;


import java.math.BigDecimal;


public class FelixData {
    
    String cdeCisac = "";
    String cdeTer = "";
    Integer rionEffet = 0;
    String codeFamilyTypeUtil;
    String numProg;
    String dateDebutProg;
    String dateFinProg;
    String cdeTypeUtil;
    String cdeUtil = "";
    String dateSitu = "";
    String dateConsult = "";
    String ide12 = "";
    String codeTypeIde12 = "";
    String ide12Cmplx = "";
    String codeTypeIde12Cmplx = "";
    Integer dureeDif = 0;
    Integer nbDiff = 0;
    String signeMttOeuvre = "";
    BigDecimal mttOeuvre;
    String typeMtt = "";
    String typeProcess = "";
    String codeTypeDroitSacem = "";
    
    int seqAdHomer = 1;
    String ide12RepCoad = "";
    String codeTypeIde12RepCoad = "";
    String codeTypeProtect = "";
    Long ideSteAdPayer;
    Long coadPayer;
    String roleAd = "";
    String cleAd;
    BigDecimal mttAd;
    String idSteAdOriEdtr;
    Long coadOriEdtr;
    String roleAdOri = "";
    String keyligHomerAdBene = "";
    
    String idSteCoop = "";
    String numPers = "";
    String numCatal = "";

    
    private String indexId;

    public FelixData() {}


    public String getCdeCisac() {
        return cdeCisac;
    }

    public void setCdeCisac(String cdeCisac) {
        this.cdeCisac = cdeCisac;
    }

    public String getCdeTer() {
        return cdeTer;
    }

    public void setCdeTer(String cdeTer) {
        this.cdeTer = cdeTer;
    }
    
    public Integer getRionEffet() {
        return rionEffet;
    }

    public void setRionEffet(Integer rionEffet) {this.rionEffet = rionEffet;}

    public String getDateDebutProg() {
        return dateDebutProg;
    }

    public void setDateDebutProg(String dateDebutProg) {
        this.dateDebutProg = dateDebutProg;
    }

    public String getDateFinProg() {
        return dateFinProg;
    }

    public void setDateFinProg(String dateFinProg) {
        this.dateFinProg = dateFinProg;
    }

    public String getCodeFamilyTypeUtil() {
        return codeFamilyTypeUtil;
    }

    public void setCodeFamilyTypeUtil(String codeFamilyTypeUtil) {
        this.codeFamilyTypeUtil = codeFamilyTypeUtil;
    }
    
    public String getCdeTypeUtil() {
        return cdeTypeUtil;
    }

    public void setCdeTypeUtil(String cdeTypeUtil) {
        this.cdeTypeUtil = cdeTypeUtil;
    }

    public String getCdeUtil() {
        return cdeUtil;
    }

    public void setCdeUtil(String cdeUtil) {
        this.cdeUtil = cdeUtil;
    }

    public String getDateSitu() {
        return dateSitu;
    }

    public void setDateSitu(String dateSitu) {this.dateSitu = dateSitu;}

    public String getDateConsult() {
        return dateConsult;
    }

    public void setDateConsult(String dateConsult) {
        this.dateConsult = dateConsult;
    }

    public String getIde12() {return ide12;}

    public void setIde12(String ide12) {this.ide12 = ide12;}

    public String getCodeTypeIde12() {
        return codeTypeIde12;
    }

    public void setCodeTypeIde12(String codeTypeIde12) {
        this.codeTypeIde12 = codeTypeIde12;
    }

    public String getIde12Cmplx() {
        return ide12Cmplx;
    }

    public void setIde12Cmplx(String ide12Cmplx) {this.ide12Cmplx = ide12Cmplx;}

    public String getCodeTypeIde12Cmplx() {
        return codeTypeIde12Cmplx;
    }

    public void setCodeTypeIde12Cmplx(String codeTypeIde12Cmplx) {
        this.codeTypeIde12Cmplx = codeTypeIde12Cmplx;
    }

    public Integer getDureeDif() {
        return dureeDif;
    }

    public void setDureeDif(Integer dureeDif) {this.dureeDif = dureeDif;}

    public Integer getNbDiff() {
        return nbDiff;
    }

    public void setNbDiff(Integer nbDiff) {this.nbDiff = nbDiff;}

    public String getSigneMttOeuvre() {
        return signeMttOeuvre;
    }

    public void setSigneMttOeuvre(String signeMttOeuvre) {
        this.signeMttOeuvre = signeMttOeuvre;
    }

    public BigDecimal getMttOeuvre() {
        return mttOeuvre;
    }

    public void setMttOeuvre(BigDecimal mttOeuvre) {
        this.mttOeuvre = mttOeuvre;
    }

    public String getTypeMtt() {
        return typeMtt;
    }

    public void setTypeMtt(String typeMtt) {
        this.typeMtt = typeMtt;
    }

    public String getTypeProcess() {
        return typeProcess;
    }

    public void setTypeProcess(String typeProcess) {
        this.typeProcess = typeProcess;
    }

    public String getCodeTypeDroitSacem() {
        return codeTypeDroitSacem;
    }

    public void setCodeTypeDroitSacem(String codeTypeDroitSacem) {
        this.codeTypeDroitSacem = codeTypeDroitSacem;
    }

    public int getSeqAdHomer() {
        return seqAdHomer;
    }

    public void setSeqAdHomer(int seqAdHomer) {this.seqAdHomer = seqAdHomer;}

    public String getIde12RepCoad() {
        return ide12RepCoad;
    }

    public void setIde12RepCoad(String ide12RepCoad) {
        this.ide12RepCoad = ide12RepCoad;
    }

    public String getCodeTypeIde12RepCoad() {
        return codeTypeIde12RepCoad;
    }

    public void setCodeTypeIde12RepCoad(String codeTypeIde12RepCoad) {
        this.codeTypeIde12RepCoad = codeTypeIde12RepCoad;
    }

    public String getCodeTypeProtect() {
        return codeTypeProtect;
    }

    public void setCodeTypeProtect(String codeTypeProtect) {
        this.codeTypeProtect = codeTypeProtect;
    }

    public Long getIdeSteAdPayer() {
        return ideSteAdPayer;
    }

    public void setIdeSteAdPayer(Long ideSteAdPayer) {
        this.ideSteAdPayer = ideSteAdPayer;
    }

    public Long getCoadPayer() {
        return coadPayer;
    }

    public void setCoadPayer(Long coadPayer) {
        this.coadPayer = coadPayer;
    }

    public String getRoleAd() {
        return roleAd;
    }

    public void setRoleAd(String roleAd) {this.roleAd = roleAd;}

    public String getCleAd() {
        return cleAd;
    }

    public void setCleAd(String cleAd) {this.cleAd = cleAd;}

    public BigDecimal getMttAd() {return mttAd;}

    public void setMttAd(BigDecimal mttAd) {this.mttAd = mttAd;}

    public String getIdSteAdOriEdtr() {
        return idSteAdOriEdtr;
    }

    public void setIdSteAdOriEdtr(String idSteAdOriEdtr) {this.idSteAdOriEdtr = idSteAdOriEdtr;}

    public Long getCoadOriEdtr() {
        return coadOriEdtr;
    }

    public void setCoadOriEdtr(Long coadOriEdtr) {
        this.coadOriEdtr = coadOriEdtr;
    }

    public String getRoleAdOri() {
        return roleAdOri;
    }

    public void setRoleAdOri(String roleAdOri) {
        this.roleAdOri = roleAdOri;
    }

    public String getKeyligHomerAdBene() {
        return keyligHomerAdBene;
    }

    public void setKeyligHomerAdBene(String keyligHomerAdBene) {
        this.keyligHomerAdBene = keyligHomerAdBene;
    }

    public String getIdSteCoop() {return idSteCoop;}
    public void setIdSteCoop(String idSteCoop) {this.idSteCoop = idSteCoop;}

    public String getNumPers() {return numPers;}
    public void setNumPers(String numPers) {this.numPers = numPers;}

    public String getNumCatal() {return numCatal;}
    public void setNumCatal(String numCatal) {this.numCatal = numCatal;}

    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }
    
    public String getNumProg() {
        return numProg;
    }
    
    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }
    
}
