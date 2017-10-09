package fr.sacem.priam.model.domain;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
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
    
    @Column(name = "cdeFamilTypUtil")
    private String cdeFamilTypUtil;
    
    //@Column(name = "numProg")
    private String numProg;
    
    @Column(name = "cdeUtil")
    private String cdeUtil;
    
    @Column(name = "cdeTypUtil")
    private String cdeTypUtil;
    
    @Column(name = "cdeGreDif")
    private String cdeGreDif;
    
    @Column(name = "cdeModDif")
    private String cdeModDif;
    
    @Column(name = "cdeTypIde12")
    private String cdeTypIde12;
    
    @Column(name = "ide12")
    private Long ide12;
    
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
    
    @Column(name = "cdeGreIde12Cmplx")
    private String cdeGreIde12Cmplx;
    
    @Column(name = "cdeGreIde12")
    private String cdeGreIde12;
    
    @Column(name = "titreOriCmplx")
    private String titreOriCmplx;
    
    @Column(name = "titreAltPppalCmplx")
    private String titreAltPppalCmplx;
    
    @Column(name = "titreOriOeuvPereCmplx")
    private String titreOriOeuvPereCmplx;
    
    @Column(name = "titreAltOeuvPereCmplx")
    private String titreAltOeuvPereCmplx;
    
    @Column(name = "titreOeuvre")
    private String titreOeuvre;
    
    @Column(name = "cdePaysOriIso4NCmplx")
    private String cdePaysOriIso4NCmplx;
    
    @Column(name = "realisateurCmplx")
    private String realisateurCmplx;
    
    @Column(name = "roleParticipant1")
    private String roleParticipant1;
    
    @Column(name = "nomParticipant1")
    private String nomParticipant1;
    
    @Column(name = "cdeTypUtilOri")
    private String cdeTypUtilOri;
    
    @Column(name = "cdeFamilTypUtilOri")
    private String cdeFamilTypUtilOri;
    
    @Column(name = "utilisateur")
    private String utilisateur;
    
    @Column(name = "date_insertion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInsertion;
    
    @Column(name = "ajout")
    private String ajout;
    
    @Column(name = "selection")
    private boolean selection;
    
    @Column(name = "SEL_EN_COURS")
    private boolean selectionEnCours;
    
    @Column(name = "libelleUtilisateur")
    private String libelleUtilisateur;
    
    
    @ManyToOne
    @JoinColumn(name = "idOeuvreManuel")
    private LigneProgramme oeuvreManuel;

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public void setDateInsertion(Date dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    public String getAjout() {
        return ajout;
    }

    public void setAjout(String ajout) {
        this.ajout = ajout;
    }

    public boolean getSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

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

    public String getCdeTypUtil() {
        return cdeTypUtil;
    }

    public void setCdeTypUtil(String cdeTypUtil) {
        this.cdeTypUtil = cdeTypUtil;
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

    public String getCdeGreIde12Cmplx() {
        return cdeGreIde12Cmplx;
    }

    public void setCdeGreIde12Cmplx(String cdeGreIde12Cmplx) {
        this.cdeGreIde12Cmplx = cdeGreIde12Cmplx;
    }

    public String getCdeGreIde12() {
        return cdeGreIde12;
    }

    public void setCdeGreIde12(String cdeGreIde12) {
        this.cdeGreIde12 = cdeGreIde12;
    }

    public String getTitreOriCmplx() {
        return titreOriCmplx;
    }

    public void setTitreOriCmplx(String titreOriCmplx) {
        this.titreOriCmplx = titreOriCmplx;
    }

    public String getTitreAltPppalCmplx() {
        return titreAltPppalCmplx;
    }

    public void setTitreAltPppalCmplx(String titreAltPppalCmplx) {
        this.titreAltPppalCmplx = titreAltPppalCmplx;
    }

    public String getTitreOriOeuvPereCmplx() {
        return titreOriOeuvPereCmplx;
    }

    public void setTitreOriOeuvPereCmplx(String titreOriOeuvPereCmplx) {
        this.titreOriOeuvPereCmplx = titreOriOeuvPereCmplx;
    }

    public String getTitreAltOeuvPereCmplx() {
        return titreAltOeuvPereCmplx;
    }

    public void setTitreAltOeuvPereCmplx(String titreAltOeuvPereCmplx) {
        this.titreAltOeuvPereCmplx = titreAltOeuvPereCmplx;
    }

    public String getTitreOeuvre() {
        return titreOeuvre;
    }

    public void setTitreOeuvre(String titreOeuvre) {
        this.titreOeuvre = titreOeuvre;
    }

    public String getCdePaysOriIso4NCmplx() {
        return cdePaysOriIso4NCmplx;
    }

    public void setCdePaysOriIso4NCmplx(String cdePaysOriIso4NCmplx) {
        this.cdePaysOriIso4NCmplx = cdePaysOriIso4NCmplx;
    }

    public String getRealisateurCmplx() {
        return realisateurCmplx;
    }

    public void setRealisateurCmplx(String realisateurCmplx) {
        this.realisateurCmplx = realisateurCmplx;
    }

    public String getRoleParticipant1() {
        return roleParticipant1;
    }

    public void setRoleParticipant1(String roleParticipant1) {
        this.roleParticipant1 = roleParticipant1;
    }

    public String getNomParticipant1() {
        return nomParticipant1;
    }

    public void setNomParticipant1(String nomParticipant1) {
        this.nomParticipant1 = nomParticipant1;
    }

    public String getCdeTypUtilOri() {
        return cdeTypUtilOri;
    }

    public void setCdeTypUtilOri(String cdeTypUtilOri) {
        this.cdeTypUtilOri = cdeTypUtilOri;
    }

    public String getCdeFamilTypUtilOri() {
        return cdeFamilTypUtilOri;
    }

    public void setCdeFamilTypUtilOri(String cdeFamilTypUtilOri) {
        this.cdeFamilTypUtilOri = cdeFamilTypUtilOri;
    }
    
    public LigneProgramme getOeuvreManuel() {
        return oeuvreManuel;
    }
    
    public void setOeuvreManuel(LigneProgramme oeuvreManuel) {
        this.oeuvreManuel = oeuvreManuel;
    }
    
    public Date getDateInsertion() {
        return dateInsertion;
    }
    
    public boolean isSelection() {
        return selection;
    }
    
    public boolean isSelectionEnCours() {
        return selectionEnCours;
    }
    
    public void setSelectionEnCours(boolean selectionEnCours) {
        this.selectionEnCours = selectionEnCours;
    }
    
    public String getLibelleUtilisateur() {
        return libelleUtilisateur;
    }
    
    public void setLibelleUtilisateur(String libelleUtilisateur) {
        this.libelleUtilisateur = libelleUtilisateur;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    
        LigneProgramme that = (LigneProgramme) o;
    
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        
    
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
