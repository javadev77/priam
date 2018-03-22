package fr.sacem.priam.model.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.util.CustomDateSerializer;

import java.util.Date;

/**
 * Created by benmerzoukah on 28/04/2017.
 */
public class FileDto {
	private Long id;
	private String nomFichier;
	private String famille;
	private String typeUtilisation;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date dateDebutChargt;
	
	@JsonSerialize(using = CustomDateSerializer.class)
	private Date dateFinChargt;
	
	
	private Long nbLignes;
	private Status statut;
	
	public FileDto() {
	}
	
	public FileDto(Long id, String nomFichier, String famille, String typeUtilisation, Date dateDebutChgt, Date dateFinChgt, Long nbLignes, Status statut) {
		this.id = id;
		this.nomFichier = nomFichier;
		this.famille = famille;
		this.typeUtilisation = typeUtilisation;
		this.dateDebutChargt = dateDebutChgt;
		this.dateFinChargt = dateFinChgt;
		this.nbLignes = nbLignes;
		this.statut = statut;
	}

	public FileDto(Long id, String nomFichier, Date dateDebutChargt, Date dateFinChargt, Long nbLignes, Status statut) {
		this.id = id;
		this.nomFichier = nomFichier;
		this.dateDebutChargt = dateDebutChargt;
		this.dateFinChargt = dateFinChargt;
		this.nbLignes = nbLignes;
		this.statut = statut;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setNomFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}
	
	public void setFamille(String famille) {
		this.famille = famille;
	}
	
	public void setTypeUtilisation(String typeUtilisation) {
		this.typeUtilisation = typeUtilisation;
	}
	
	
	public void setDateDebutChargt(Date dateDebutChargt) {
		this.dateDebutChargt = dateDebutChargt;
	}
	
	public void setDateFinChargt(Date dateFinChargt) {
		this.dateFinChargt = dateFinChargt;
	}
	
	public void setNbLignes(Long nbLignes) {
		this.nbLignes = nbLignes;
	}
	
	public void setStatut(Status statut) {
		this.statut = statut;
	}
	
	public String getNomFichier() {
		
		return nomFichier;
	}
	
	public String getFamille() {
		return famille;
	}
	
	public String getTypeUtilisation() {
		return typeUtilisation;
	}
	
	public Date getDateDebutChargt() {
		return dateDebutChargt;
	}
	
	public Date getDateFinChargt() {
		return dateFinChargt;
	}
	
	public Long getNbLignes() {
		return nbLignes;
	}
	
	public Status getStatut() {
		return statut;
	}
}
