package fr.sacem.priam.batch.fv.export.domain;

import fr.sacem.priam.batch.common.util.exception.PriamValidationException;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import java.io.Serializable;

public class ExportCsvDto implements Serializable {
    private String cdeFamilTypUtil;
    private String cdeTypUtil;
    private Long numProg;
    private String rionEffet;
    private String ide12;
    private String cdeTypIde12;
    private Long ide12RepCoad;
    private String cdeTypIde12RepCoad;
    private String datsitu;
    private String datconslt;
    private Long coad;
    private Long numPers;
    private Long numCatal;
    private String idSteAd;
    private String rolAd;
    private String typeDroit;
    private Double cleAd;
    private String cdeTypProtect;
    private Integer coadOriEdtr;
    private String idSteOriEdtr;
    private Double points;
//    point
    private String nomProgramme;
    private String tax;
    private String durDif;
    private String nbrDif;
    private String typMt;
    private Double mt;
    private String genreOeuvre;
    private String titreOeuvre;
    private Integer dureeDeposee;
    private Double taxOri;
    private String labelValo;
    private String paysOri;
    private Integer indicRepart;
//    particpant
    private String nom;
    private String prenom;
    private Integer indicSacem;
    private String sousRole;
    private Integer anneeNaissance;
    private Integer anneeDeces;
    private Integer indicDrtPercus;

    private Integer lineNumber;
    private PriamValidationException exception;
    private Long idFichier;
    private boolean numpersExist;
    private boolean ayantDroitExist;
    private boolean oeuvreExist;
    private LigneProgrammeFV ligneProgrammeFV;
    private Long idOeuvreFv;

    public ExportCsvDto() {

    }

    public ExportCsvDto(final PriamValidationException e) {
        exception = e;
        lineNumber = e.getLineNumber();
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

    public Long getCoad() {
        return coad;
    }

    public void setCoad(Long coad) {
        this.coad = coad;
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

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getAnneeNaissance() {
        return anneeNaissance;
    }

    public void setAnneeNaissance(Integer anneeNaissance) {
        this.anneeNaissance = anneeNaissance;
    }

    public Integer getAnneeDeces() {
        return anneeDeces;
    }

    public void setAnneeDeces(Integer anneeDeces) {
        this.anneeDeces = anneeDeces;
    }

    public Integer getIndicSacem() {
        return indicSacem;
    }

    public void setIndicSacem(Integer indicSacem) {
        this.indicSacem = indicSacem;
    }

    public String getSousRole() {
        return sousRole;
    }

    public void setSousRole(String sousRole) {
        this.sousRole = sousRole;
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

    public Long getNumProg() {
        return numProg;
    }

    public void setNumProg(Long numProg) {
        this.numProg = numProg;
    }

    public String getRionEffet() {
        return rionEffet;
    }

    public void setRionEffet(String rionEffet) {
        this.rionEffet = rionEffet;
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

    public String getDatsitu() {
        return datsitu;
    }

    public void setDatsitu(String datsitu) {
        this.datsitu = datsitu;
    }

    public String getDatconslt() {
        return datconslt;
    }

    public void setDatconslt(String datconslt) {
        this.datconslt = datconslt;
    }

    public String getTypeDroit() {
        return typeDroit;
    }

    public void setTypeDroit(String typeDroit) {
        this.typeDroit = typeDroit;
    }

    public String getNomProgramme() {
        return nomProgramme;
    }

    public void setNomProgramme(String nomProgramme) {
        this.nomProgramme = nomProgramme;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDurDif() {
        return durDif;
    }

    public void setDurDif(String durDif) {
        this.durDif = durDif;
    }

    public String getNbrDif() {
        return nbrDif;
    }

    public void setNbrDif(String nbrDif) {
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

    public String getGenreOeuvre() {
        return genreOeuvre;
    }

    public void setGenreOeuvre(String genreOeuvre) {
        this.genreOeuvre = genreOeuvre;
    }

    public String getTitreOeuvre() {
        return titreOeuvre;
    }

    public void setTitreOeuvre(String titreOeuvre) {
        this.titreOeuvre = titreOeuvre;
    }

    public Integer getDureeDeposee() {
        return dureeDeposee;
    }

    public void setDureeDeposee(Integer dureeDeposee) {
        this.dureeDeposee = dureeDeposee;
    }

    public Double getTaxOri() {
        return taxOri;
    }

    public void setTaxOri(Double taxOri) {
        this.taxOri = taxOri;
    }

    public String getLabelValo() {
        return labelValo;
    }

    public void setLabelValo(String labelValo) {
        this.labelValo = labelValo;
    }

    public String getPaysOri() {
        return paysOri;
    }

    public void setPaysOri(String paysOri) {
        this.paysOri = paysOri;
    }

    public Integer getIndicRepart() {
        return indicRepart;
    }

    public void setIndicRepart(Integer indicRepart) {
        this.indicRepart = indicRepart;
    }

    public Integer getIndicDrtPercus() {
        return indicDrtPercus;
    }

    public void setIndicDrtPercus(Integer indicDrtPercus) {
        this.indicDrtPercus = indicDrtPercus;
    }

    public Long getIdFichier() {
        return idFichier;
    }

    public void setIdFichier(final Long idFichier) {
        this.idFichier = idFichier;
    }

    public Double getPoints() {
        return points;
    }

    public void setException(final PriamValidationException exception) {
        this.exception = exception;
    }

    public PriamValidationException getException() {
        return exception;
    }

    public void setPoints(final Double points) {
        this.points = points;
    }

    public void setNumpersExist(final boolean numpersExist) {
        this.numpersExist = numpersExist;
    }

    public boolean isNumpersExist() {
        return numpersExist;
    }

    public void setAyantDroitExist(final boolean ayantDroitExist) {
        this.ayantDroitExist = ayantDroitExist;
    }

    public boolean isAyantDroitExist() {
        return ayantDroitExist;
    }

    public void setOeuvreExist(final boolean oeuvreExist) {
        this.oeuvreExist = oeuvreExist;
    }

    public boolean isOeuvreExist() {
        return oeuvreExist;
    }

    public void setIdOeuvreFv(final Long idOeuvreFv) {
        this.idOeuvreFv = idOeuvreFv;
    }

    public Long getIdOeuvreFv() {
        return idOeuvreFv;
    }
}
