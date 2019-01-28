package fr.sacem.priam.batch.fv.ad.info.req.domain;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;

import java.io.Serializable;

public class AyantDroit implements Serializable {
    private Long id;
    private Long ide12RepCoad;
    private String cdeTypIde12RepCoad;
    private String rolAd;
    private String idSteAd;
    private Double cleAd;
    private String cdeTypProtect;
    private Integer coadOriEdtr;
    private String idSteOriEdtr;
    private Long coad;
    private Long numPers;
    private Long numCatal;
    private Integer lineNumber;

    public AyantDroit() {
    }

    public AyantDroit(PriamValidationException e) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIde12RepCoad() {
        return ide12RepCoad;
    }

    public void setIde12RepCoad(Long ide12RepCoad) {
        this.ide12RepCoad = ide12RepCoad;
    }

    public String getCdeTypIde12RepCoad() {
        return cdeTypIde12RepCoad;
    }

    public void setCdeTypIde12RepCoad(String cdeTypIde12RepCoad) {
        this.cdeTypIde12RepCoad = cdeTypIde12RepCoad;
    }

    public String getRolAd() {
        return rolAd;
    }

    public void setRolAd(String rolAd) {
        this.rolAd = rolAd;
    }

    public String getIdSteAd() {
        return idSteAd;
    }

    public void setIdSteAd(String idSteAd) {
        this.idSteAd = idSteAd;
    }

    public Double getCleAd() {
        return cleAd;
    }

    public void setCleAd(Double cleAd) {
        this.cleAd = cleAd;
    }

    public String getCdeTypProtect() {
        return cdeTypProtect;
    }

    public void setCdeTypProtect(String cdeTypProtect) {
        this.cdeTypProtect = cdeTypProtect;
    }

    public Integer getCoadOriEdtr() {
        return coadOriEdtr;
    }

    public void setCoadOriEdtr(Integer coadOriEdtr) {
        this.coadOriEdtr = coadOriEdtr;
    }

    public String getIdSteOriEdtr() {
        return idSteOriEdtr;
    }

    public void setIdSteOriEdtr(String idSteOriEdtr) {
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

    public Long getCoad() {
        return coad;
    }

    public void setCoad(Long coad) {
        this.coad = coad;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }
}
