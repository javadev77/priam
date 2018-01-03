package fr.sacem.priam.model.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.domain.StatutEligibilite;
import fr.sacem.priam.model.domain.StatutFichierFelix;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.TypeRepart;
import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import fr.sacem.priam.model.util.DateRepartitionSerializer;
import fr.sacem.priam.model.util.SimpleDateSerializer;

import java.util.Date;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class ProgrammeDto {

	private String numProg;
	private String nom;
	private String famille;
	private String typeUtilisation;
	private Integer rionTheorique;
	
	@JsonSerialize(using = SimpleDateSerializer.class)
	private Date dateCreation;
	
	private TypeRepart typeRepart;
	private StatutProgramme statut;
	private Integer rionPaiement;
	private Long fichiers;
	private String usercre;
	private Date datmaj;
	private String usermaj;
	
	private Date dataffecte;
	private String useraffecte;

	@JsonSerialize(using = SimpleDateSerializer.class)
	private Date dateDbtPrg;
	
	@JsonSerialize(using = SimpleDateSerializer.class)
	private Date dateFinPrg;
	
	private Integer cdeTer;
 
	private String userValidation;
    	
      //@JsonSerialize(using = CustomDateSerializer.class)
	private Date dateValidation;
    	
	private StatutFichierFelix statutFichierFelix;
    
    @JsonSerialize(using = DateRepartitionSerializer.class)
    private Date dateRepartition;

	private StatutEligibilite statutEligibilite;
    	
	
	public ProgrammeDto(String numProg, String nom, SareftrFamiltyputil famille, SareftrTyputil typeUtilisation, SareftrRion rionTheorique,
						Date dateCreation, TypeRepart typeRepart, StatutProgramme statut, SareftrRion rionPaiement, Long nbFichiers,
						String usercre, Date datmaj, String usermaj, Date dataffecte, String useraffecte, Date dateValidation) {
		this.numProg = numProg;
		this.nom = nom;
		this.famille = famille != null ? famille.getCode() : "";
		this.typeUtilisation = typeUtilisation != null ? typeUtilisation.getCode() : "";
		this.rionTheorique = rionTheorique != null ? rionTheorique.getRion() : null;
		this.dateCreation = dateCreation;
		this.typeRepart = typeRepart;
		this.statut = statut;
		this.rionPaiement = rionPaiement != null ? rionPaiement.getRion() : null;
		this.fichiers = nbFichiers;
		this.usercre = usercre;
		this.datmaj = datmaj;
		this.usermaj = usermaj;
		this.dataffecte = dataffecte;
		this.useraffecte = useraffecte;
		this.dateValidation = dateValidation;
	}

	public ProgrammeDto(String numProg, String nom, String famille, String typeUtilisation, Integer rionTheorique, Date dateCreation, TypeRepart typeRepart,
	                    StatutProgramme statut, Integer rionPaiement, Long fichiers, String usercre, Date datmaj, String usermaj,
	                    Date dataffecte, String useraffecte, Date dateValidation, StatutFichierFelix statutFichierFelix, Date dateRepartition) {
		this.numProg = numProg;
		this.nom = nom;
		this.famille = famille;
		this.typeUtilisation = typeUtilisation;
		this.rionTheorique = rionTheorique;
		this.dateCreation = dateCreation;
		this.typeRepart = typeRepart;
		this.statut = statut;
		this.rionPaiement = rionPaiement;
		this.fichiers = fichiers;
		this.usercre = usercre;
		this.datmaj = datmaj;
		this.usermaj = usermaj;
		this.dataffecte = dataffecte;
		this.useraffecte = useraffecte;
		this.dateValidation = dateValidation;
	    this.statutFichierFelix = statutFichierFelix;
	    this.dateRepartition = dateRepartition;
	}

	public ProgrammeDto(String numProg, String nom, String famille, String typeUtilisation, Integer rionTheorique, Date dateCreation, TypeRepart typeRepart,
						StatutProgramme statut, Integer rionPaiement, Long fichiers, String usercre, Date datmaj, String usermaj,
						Date dataffecte, String useraffecte, Date dateDbtPrg,
						Date dateFinPrg, Integer cdeTer, String userValidation, Date dateValidation, StatutFichierFelix statutFichierFelix, Date dateRepartition, StatutEligibilite statutEligibilite) {

		this(numProg, nom, famille, typeUtilisation, rionTheorique, dateCreation, typeRepart,
				statut, rionPaiement, fichiers, usercre, datmaj, usermaj,
				dataffecte, useraffecte, dateDbtPrg, dateFinPrg, cdeTer, userValidation,
				dateValidation, statutFichierFelix, dateRepartition);

		this.statutEligibilite  = statutEligibilite;
	}

	public ProgrammeDto(String numProg, String nom, String famille, String typeUtilisation, Integer rionTheorique, Date dateCreation, TypeRepart typeRepart,
						StatutProgramme statut, Integer rionPaiement, Long fichiers, String usercre, Date datmaj, String usermaj,
						Date dataffecte, String useraffecte, Date dateDbtPrg,
						Date dateFinPrg, Integer cdeTer, String userValidation, Date dateValidation, StatutFichierFelix statutFichierFelix, Date dateRepartition) {
		this(numProg, nom, famille, typeUtilisation, rionTheorique, dateCreation, typeRepart,
				statut, rionPaiement, fichiers, usercre, datmaj, usermaj,
				dataffecte, useraffecte, dateValidation, statutFichierFelix, dateRepartition);

		this.dateDbtPrg = dateDbtPrg;
		this.dateFinPrg = dateFinPrg ;
		this.cdeTer = cdeTer;
		this.userValidation = userValidation;
		this.dateValidation = dateValidation;
		this.dateRepartition = dateRepartition;

	}

	public ProgrammeDto(String numProg, String nom, String famille, String typeUtilisation, Integer rionTheorique, Date dateCreation, TypeRepart typeRepart,
						StatutProgramme statut, Integer rionPaiement, Long fichiers, String usercre, Date datmaj, String usermaj,
						Date dataffecte, String useraffecte, Date dateValidation, StatutFichierFelix statutFichierFelix, Date dateRepartition, StatutEligibilite statutEligibilite) {

		this(numProg, nom, famille, typeUtilisation, rionTheorique, dateCreation, typeRepart,
				statut, rionPaiement, fichiers, usercre, datmaj, usermaj,
				dataffecte, useraffecte, dateValidation, statutFichierFelix, dateRepartition);

		this.statutEligibilite = statutEligibilite;

	}

		public String getNumProg() {
		return numProg;
	}
	
	public void setNumProg(String numProg) {
		this.numProg = numProg;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public String getFamille() {
		return famille;
	}
	
	public void setFamille(String famille) {
		this.famille = famille;
	}
	
	public String getTypeUtilisation() {
		return typeUtilisation;
	}
	
	public void setTypeUtilisation(String typeUtilisation) {
		this.typeUtilisation = typeUtilisation;
	}
	
	public Integer getRionTheorique() {
		return rionTheorique;
	}
	
	public void setRionTheorique(Integer rionTheorique) {
		this.rionTheorique = rionTheorique;
	}
	
	public Date getDateCreation() {
		return dateCreation;
	}
	
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	
	public TypeRepart getTypeRepart() {
		return typeRepart;
	}
	
	public void setTypeRepart(TypeRepart typeRepart) {
		this.typeRepart = typeRepart;
	}
	
	public StatutProgramme getStatut() {
		return statut;
	}
	
	public void setStatut(StatutProgramme statut) {
		this.statut = statut;
	}
	
	public Integer getRionPaiement() {
		return rionPaiement;
	}
	
	public void setRionPaiement(Integer rionPaiement) {
		this.rionPaiement = rionPaiement;
	}
	
	public Long getFichiers() {
		return fichiers;
	}
	
	public void setFichiers(Long fichiers) {
		this.fichiers = fichiers;
	}
	
	public String getUsercre() {
		return usercre;
	}
	
	public void setUsercre(String usercre) {
		this.usercre = usercre;
	}
	
	public Date getDatmaj() {
		return datmaj;
	}
	
	public void setDatmaj(Date datmaj) {
		this.datmaj = datmaj;
	}
	
	public String getUsermaj() {
		return usermaj;
	}
	
	public void setUsermaj(String usermaj) {
		this.usermaj = usermaj;
	}
	
	public Date getDataffecte() {
		return dataffecte;
	}
	
	public String getUseraffecte() {
		return useraffecte;
	}
	
	public void setDataffecte(Date dataffecte) {
		this.dataffecte = dataffecte;
	}
	
	public void setUseraffecte(String useraffecte) {
		this.useraffecte = useraffecte;
	}

	public Date getDateDbtPrg() { return dateDbtPrg; }

	public void setDateDbtPrg(Date dateDbtPrg) {this.dateDbtPrg = dateDbtPrg;}

	public Date getDateFinPrg() {return dateFinPrg;}

	public void setDateFinPrg(Date dateFinPrg) {this.dateFinPrg = dateFinPrg;}

	public Integer getCdeTer() {return cdeTer;}

	public void setCdeTer(Integer cdeTer) {this.cdeTer = cdeTer;}

	public String getUserValidation() {
		return userValidation;
	}

	public void setUserValidation(String userValidation) {
		this.userValidation = userValidation;
	}

	public Date getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}
    
    public StatutFichierFelix getStatutFichierFelix() {
	  return statutFichierFelix;
    }
    
    public void setStatutFichierFelix(StatutFichierFelix statutFichierFelix) {
	  this.statutFichierFelix = statutFichierFelix;
    }
    
    public Date getDateRepartition() {
	  return dateRepartition;
    }
    
    public void setDateRepartition(Date dateRepartition) {
	  this.dateRepartition = dateRepartition;
    }

	public StatutEligibilite getStatutEligibilite() {
		return statutEligibilite;
	}

	public void setStatutEligibilite(StatutEligibilite statutEligibilite) {
		this.statutEligibilite = statutEligibilite;
	}

	public ProgrammeDto() {}
}
