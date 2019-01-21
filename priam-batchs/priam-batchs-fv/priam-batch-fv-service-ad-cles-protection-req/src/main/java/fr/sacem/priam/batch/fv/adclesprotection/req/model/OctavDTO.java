package fr.sacem.priam.batch.fv.adclesprotection.req.model;

import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Guillaume on 13/05/2015.
 */
public class OctavDTO {

    // in :
    private String cdeCisac ;
    private Integer cdeTer ;
    private String cdeFamilTypUtil ;
    private String cdeTypUtil ;
    private String cdeTypDrtSacem ;
    private String ide12 ;
    private String cdeTypIde12 ;
    private String datConsult ;
    private String datSitu ;
    private String rionStatut ;
    private String rionCalc ;
    private String idRevend ;
    private String filler ;

    // out :
    private String ide12RepCoad ;
    private String cdeTypIde12RepCoad ;
    private String rolad ;                      // rôle
    private String coad ;                       // identifiant Ayant Droit
    private String idStead ;
    private BigDecimal clead ;
    private String cdeTypProtec ;
    private String roladori ;
    private String coadori ;
    private String idSteOri ;
    private String statut ;

    //other
    private Boolean isPayed = null;

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

    public String getCdeFamilTypUtil() {
        return cdeFamilTypUtil;
    }

    public void setCdeFamilTypUtil(String cdeFamilTypUtil) {
        this.cdeFamilTypUtil = cdeFamilTypUtil;
    }

    public String getCdeTypUtil() {
        return cdeTypUtil;
    }

    public void setCdeTypUtil(String cdeTypUtil) {
        this.cdeTypUtil = cdeTypUtil;
    }

    public String getCdeTypDrtSacem() {
        return cdeTypDrtSacem;
    }

    public void setCdeTypDrtSacem(String cdeTypDrtSacem) {
        this.cdeTypDrtSacem = cdeTypDrtSacem;
    }

    public String getIde12() {
        return ide12;
    }

    public void setIde12(String ide12) {
        this.ide12 = ide12;
    }

    public String getCdeTypIde12() {
        return cdeTypIde12;
    }

    public void setCdeTypIde12(String cdeTypIde12) {
        this.cdeTypIde12 = cdeTypIde12;
    }

    public String getDatConsult() {
        return datConsult;
    }

    public void setDatConsult(String datConsult) {
        this.datConsult = datConsult;
    }

    public String getDatSitu() {
        return datSitu;
    }

    public void setDatSitu(String datSitu) {
        this.datSitu = datSitu;
    }

    public String getRionStatut() {
        return rionStatut;
    }

    public void setRionStatut(String rionStatut) {
        this.rionStatut = rionStatut;
    }

    public String getRionCalc() {
        return rionCalc;
    }

    public void setRionCalc(String rionCalc) {
        this.rionCalc = rionCalc;
    }

    public String getIdRevend() {
        return idRevend;
    }

    public void setIdRevend(String idRevend) {
        this.idRevend = idRevend;
    }

    public String getFiller() {
        return filler;
    }

    public void setFiller(String filler) {
        this.filler = filler;
    }

    public String getIde12RepCoad() {
        return ide12RepCoad;
    }

    public void setIde12RepCoad(String ide12RepCoad) {
        this.ide12RepCoad = ide12RepCoad;
    }

    public String getCdeTypIde12RepCoad() {
        return cdeTypIde12RepCoad;
    }

    public void setCdeTypIde12RepCoad(String cdeTypIde12RepCoad) {
        this.cdeTypIde12RepCoad = cdeTypIde12RepCoad;
    }

    public String getRolad() {
        return rolad;
    }

    public void setRolad(String rolad) {
        this.rolad = rolad;
    }

    public String getCoad() {
        return coad;
    }

    public void setCoad(String coad) {
        this.coad = coad;
    }

    public String getIdStead() {
        return idStead;
    }

    public void setIdStead(String idStead) {
        this.idStead = idStead;
    }

    public BigDecimal getClead() {
        return clead;
    }

    //json mapper
    public void setClead(Double clead) {
        this.clead = (clead == null || Double.isNaN(clead)) ? null : new BigDecimal(clead, MathContext.DECIMAL64);
    }

    public String getCdeTypProtec() {
        return cdeTypProtec;
    }

    public void setCdeTypProtec(String cdeTypProtec) {
        this.cdeTypProtec = cdeTypProtec;
    }

    public String getRoladori() {
        return roladori;
    }

    public void setRoladori(String roladori) {
        this.roladori = roladori;
    }

    public String getCoadori() {
        return coadori;
    }

    public void setCoadori(String coadori) {
        this.coadori = coadori;
    }

    public String getIdSteOri() {
        return idSteOri;
    }

    public void setIdSteOri(String idSteOri) {
        this.idSteOri = idSteOri;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Boolean getPayed() {
        return isPayed;
    }

    public void setPayed(Boolean isPayed) {this.isPayed = isPayed;}

    public static enum FondsCategory {
        FD01("FD01"), FD02("FD02"), FD03("FD03");

        String value;

        FondsCategory(String value){
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static FondsCategory toEnum(final String value) {
            for (final FondsCategory cat : values() ) {
                if(StringUtils.equalsIgnoreCase(value, cat.getValue())) {
                    return cat;
                }
            }
            return FondsCategory.FD01;
        }

    }

}
