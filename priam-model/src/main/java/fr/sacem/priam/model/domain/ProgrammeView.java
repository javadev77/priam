package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@Entity
@Table(name = "PRIAM_PROG_VIEW")
public class ProgrammeView {
    private String numProg;
    private String nom;
    private Integer rionTheorique;
    private String famille;
    private String typeUtilisation;
    private TypeRepart typeRepart;
    private Date dateCreation;
    private StatutProgramme statut;
    private Integer rionPaiement;
    private Long fichiers;
    private String usercre;
    private Date datmaj;
    private String usermaj;
    private String useraffect;
    private Date dataffect;

    private Date dateDbtPrg;
    private Date dateFinPrg;
    private Integer cdeTer;

    private String userValidation;
    private Date dateValidation;
    
    private StatutFichierFelix statutFichierFelix;
    
    private Date dateRepartition;

    
    public ProgrammeView() {
    }
    
    @Id
    @Column(name = "NUMPROG")
    public String getNumProg() {
        return numProg;
    }
    
    @Column(name = "NOM")
    public String getNom() {
        return nom;
    }
    
    @Column(name = "RION_THEORIQUE")
    public Integer getRionTheorique() {
        return rionTheorique;
    }
    
    @Column(name = "CDEFAMILTYPUTIL")
    public String getFamille() {
        return famille;
    }
    
    @Column(name = "CDETYPUTIL")
    public String getTypeUtilisation() {
        return typeUtilisation;
    }
    
    @Column(name = "TYPE_REPART")
    @Enumerated(EnumType.STRING)
    public TypeRepart getTypeRepart() {
        return typeRepart;
    }
    
    @Column(name = "DATE_CREATION")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateCreation() {
        return dateCreation;
    }
    
    @Column(name = "STATUT_PROG_CODE")
    @Enumerated(EnumType.STRING)
    public StatutProgramme getStatut() {
        return statut;
    }
    
    @Column(name = "RION_PAIEMENT")
    public Integer getRionPaiement() {
        return rionPaiement;
    }
    
    @Column(name = "fichiers")
    public Long getFichiers() {
        return fichiers;
    }
    
    @Column(name = "DATMAJ")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDatmaj() {
        return datmaj;
    }
    
    @Column(name = "USERCRE")
    public String getUsercre() {
        return usercre;
    }
    
    @Column(name = "USERMAJ")
    public String getUsermaj() {
        return usermaj;
    }
    
    @Column(name = "USERAFFECT")
    public String getUseraffect() {
        return useraffect;
    }
    
    @Column(name = "DATAFFECT")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDataffect() {
        return dataffect;
    }

    @Column(name = "DATEDBTPRG")
    @Temporal(TemporalType.DATE)
    public Date getDateDbtPrg() { return dateDbtPrg; }

    @Column(name = "DATEFINPRG")
    @Temporal(TemporalType.DATE)
    public Date getDateFinPrg() { return dateFinPrg; }

    @Column(name = "CDETER")
    public Integer getCdeTer() { return cdeTer; }

    @Column(name = "DATEVALIDATION")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateValidation() { return dateValidation; }

    @Column(name = "USERVALIDATION")
    public String getUserValidation() {
        return userValidation;
    }
    
    @Column(name = "STATUT_FICHIER_FELIX")
    @Enumerated(EnumType.STRING)
    public StatutFichierFelix getStatutFichierFelix() {
        
        return statutFichierFelix;
    }
    
    @Column(name = "DATE_REPARTITION")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateRepartition() {
        return dateRepartition;
    }
    
    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public void setRionTheorique(Integer rionTheorique) {
        this.rionTheorique = rionTheorique;
    }
    
    public void setFamille(String famille) {
        this.famille = famille;
    }
    
    public void setTypeUtilisation(String typeUtilisation) {
        this.typeUtilisation = typeUtilisation;
    }
    
    public void setTypeRepart(TypeRepart typeRepart) {
        this.typeRepart = typeRepart;
    }
    
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }
    
    public void setStatut(StatutProgramme statut) {
        this.statut = statut;
    }
    
    public void setRionPaiement(Integer rionPaiement) {
        this.rionPaiement = rionPaiement;
    }
    
    public void setFichiers(Long fichiers) {
        this.fichiers = fichiers;
    }
    
    public void setDatmaj(Date datmaj) {
        this.datmaj = datmaj;
    }
    
    public void setUsercre(String usercre) {
        this.usercre = usercre;
    }
    
    public void setUsermaj(String usermaj) {
        this.usermaj = usermaj;
    }
    
    public void setDataffect(Date dataffect) {
        this.dataffect = dataffect;
    }
    
    public void setUseraffect(String useraffect) {
        this.useraffect = useraffect;
    }

    public void setDateDbtPrg(Date dateDbtPrg) { this.dateDbtPrg = dateDbtPrg; }

    public void setDateFinPrg(Date dateFinPrg) { this.dateFinPrg = dateFinPrg; }

    public void setCdeTer(Integer cdeTer) { this.cdeTer = cdeTer; }

    public void setUserValidation(String userValidation) { this.userValidation = userValidation; }

    public void setDateValidation(Date dateValidation) { this.dateValidation = dateValidation; }
    
    public void setStatutFichierFelix(StatutFichierFelix statutFichierFelix) {
        this.statutFichierFelix = statutFichierFelix;
    }
    
    public void setDateRepartition(Date dateRepartition) {
        this.dateRepartition = dateRepartition;
    }
}
