package fr.sacem.priam.model.domain;

import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@Entity
@Table(name = "PRIAM_PROGRAMME")
public class Programme implements Serializable {
	private String numProg;
	private String nom;
	private SareftrRion sareftrRionTheorique;
	private SareftrFamiltyputil sareftrFamiltyputil;
	private SareftrTyputil sareftrTyputil;
	private TypeRepart typeRepart;
	private Date dateCreation;
	private StatutProgramme statut;
	private SareftrRion sareftrRionPaiement;
	//private List<Fichier> fichiers;
	private String usercre;
	private Date datmaj;
	private String usermaj;
	private String useraffect;
	private Date dataffect;

	private String userValidation;
	private Date dateValidation;

	private Date dateDbtPrg;
	private Date dateFinPrg;
	private Integer cdeTer;
	
	public Programme() {
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
	
	@ManyToOne
	@JoinColumn(name = "RION_THEORIQUE")
	public SareftrRion getSareftrRionTheorique() {
		return sareftrRionTheorique;
	}
	
	@ManyToOne
	@JoinColumn(name = "CDEFAMILTYPUTIL")
	public SareftrFamiltyputil getSareftrFamiltyputil() {
		return sareftrFamiltyputil;
	}
	
	@ManyToOne
	@JoinColumn(name = "CDETYPUTIL")
	public SareftrTyputil getSareftrTyputil() {
		return sareftrTyputil;
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
	
	@ManyToOne
	@JoinColumn(name = "RION_PAIEMENT")
	public SareftrRion getSareftrRionPaiement() {
		return sareftrRionPaiement;
	}

	@Column(name = "USERCRE")
	public String getUsercre() {
		return usercre;
	}
	
	@Column(name = "DATMAJ")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDatmaj() {
		return datmaj;
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

	@Column(name = "USER_VALIDATION")
	public String getUserValidation() { return userValidation; }

	@Column(name = "DATE_VALIDATION")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDateValidation() { return dateValidation; }

	@Column(name = "DATE_DBT_PRG")
	@Temporal(TemporalType.DATE)
	public Date getDateDbtPrg() { return dateDbtPrg; }

	@Column(name = "DATE_FIN_PRG")
	@Temporal(TemporalType.DATE)
	public Date getDateFinPrg() { return dateFinPrg; }

	@Column(name = "CDE_TER")
	public Integer getCdeTer() { return cdeTer; }
	
	public void setUsercre(String usercre) {
		this.usercre = usercre;
	}
	
	public void setNumProg(String numProg) {
		this.numProg = numProg;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setSareftrRionTheorique(SareftrRion sareftrRionTheorique) {
		this.sareftrRionTheorique = sareftrRionTheorique;
	}
	
	public void setSareftrFamiltyputil(SareftrFamiltyputil sareftrFamiltyputil) {
		this.sareftrFamiltyputil = sareftrFamiltyputil;
	}
	
	public void setSareftrTyputil(SareftrTyputil sareftrTyputil) {
		this.sareftrTyputil = sareftrTyputil;
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
	
	public void setSareftrRionPaiement(SareftrRion sareftrRionPaiement) {
		this.sareftrRionPaiement = sareftrRionPaiement;
	}

	public void setUsermaj(String usermaj) {
		this.usermaj = usermaj;
	}
	
	public void setDatmaj(Date datmaj) {
		this.datmaj = datmaj;
	}
	
	public void setUseraffect(String useraffect) {
		this.useraffect = useraffect;
	}
	
	public void setDataffect(Date dataffect) {
		this.dataffect = dataffect;
	}

    public void setUserValidation(String userValidation) { this.userValidation = userValidation; }

	public void setDateValidation(Date dateValidation) { this.dateValidation = dateValidation; }

	public void setDateDbtPrg(Date dateDbtPrg) { this.dateDbtPrg = dateDbtPrg; }

	public void setDateFinPrg(Date dateFinPrg) { this.dateFinPrg = dateFinPrg; }

	public void setCdeTer(Integer cdeTer) { this.cdeTer = cdeTer; }
}
