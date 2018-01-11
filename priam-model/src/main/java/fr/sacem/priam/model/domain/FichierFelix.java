package fr.sacem.priam.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by benmerzoukah on 22/09/2017.
 */
@Entity
@Table(name = "PRIAM_FICHIER_FELIX")
public class FichierFelix implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "FILENAME")
    private String nomFichier;
    
    @Column(name = "DATE_CREATION")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    
    @Column(name = "ERROR_LIST")
    private String errors;
    
    @Column(name = "NUMPROG")
    private String numProg;
    
    @Column(name = "STATUT")
    @Enumerated(EnumType.STRING)
    private StatutFichierFelix statut;
    
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="ID_FICHIER_FELIX")
    private List<FichierFelixLog> logs = new ArrayList<>();
    
    @Column(name = "CONTENT")
    @Lob
    @JsonIgnore
    private byte[] content;
    
    public FichierFelix() {
    
    }
    
    public Long getId() {
	  return id;
    }
    
    public void setId(Long id) {
	  this.id = id;
    }
    
    public String getNomFichier() {
	  return nomFichier;
    }
    
    public void setNomFichier(String nomFichier) {
	  this.nomFichier = nomFichier;
    }
    
    public Date getDateCreation() {
	  return dateCreation;
    }
    
    public void setDateCreation(Date dateCreation) {
	  this.dateCreation = dateCreation;
    }
    
    public String getErrors() {
	  return errors;
    }
    
    public void setErrors(String errors) {
	  this.errors = errors;
    }
    
    public String getNumProg() {
	  return numProg;
    }
    
    public void setNumProg(String numProg) {
	  this.numProg = numProg;
    }
    
    public StatutFichierFelix getStatut() {
	  return statut;
    }
    
    
    public void setStatut(StatutFichierFelix statut) {
	  this.statut = statut;
    }
    
    public List<FichierFelixLog> getLogs() {
	  return logs;
    }
    
    public void setLogs(List<FichierFelixLog> logs) {
	  this.logs = logs;
    }
    
    public byte[] getContent() {
	  return content;
    }
    
    public void setContent(byte[] content) {
	  this.content = content;
    }
}
