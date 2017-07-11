package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benmerzoukah on 06/06/2017.
 */
@Entity
@Table(name = "PRIAM_PROGRAMME")
public class Programme {
	private String numProg;
	private String nom;
	private Rion rionTheorique;
	private Famille famille;
	private TypeUtilisation typeUtilisation;
	private TypeRepart typeRepart;
	private Date dateCreation;
	private StatutProgramme statut;
	private Rion rionPaiement;
	//private List<Fichier> fichiers;
	private String usercre;
	private Date datmaj;
	private String usermaj;
	private String useraffect;
	private Date dataffect;
	
	
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
	public Rion getRionTheorique() {
		return rionTheorique;
	}
	
	@ManyToOne
	@JoinColumn(name = "CDEFAMILTYPUTIL")
	public Famille getFamille() {
		return famille;
	}
	
	@ManyToOne
	@JoinColumn(name = "CDETYPUTIL")
	public TypeUtilisation getTypeUtilisation() {
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
	
	@ManyToOne
	@JoinColumn(name = "RION_PAIEMENT")
	public Rion getRionPaiement() {
		return rionPaiement;
	}
	
	//@OneToMany(mappedBy = "programme")
	//public List<Fichier> getFichiers() {
	//	return fichiers;
	//}
	
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
	
	public void setUsercre(String usercre) {
		this.usercre = usercre;
	}
	
	public void setNumProg(String numProg) {
		this.numProg = numProg;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setRionTheorique(Rion rionTheorique) {
		this.rionTheorique = rionTheorique;
	}
	
	public void setFamille(Famille famille) {
		this.famille = famille;
	}
	
	public void setTypeUtilisation(TypeUtilisation typeUtilisation) {
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
	
	public void setRionPaiement(Rion rionPaiement) {
		this.rionPaiement = rionPaiement;
	}
	
	/*public void setFichiers(List<Fichier> fichiers) {
		this.fichiers = fichiers;
	}*/
	
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
}
