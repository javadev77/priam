package fr.sacem.priam.model.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by benmerzoukah on 25/09/2017.
 */
@Entity
@Table(name = "PRIAM_FICHIER_FELIX_LOG")
public class FichierFelixLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;
    
    @Column(name = "LOG")
    private String log;
    
    public FichierFelixLog() {
    
    }
    
    public Date getDateCreation() {
	  return dateCreation;
    }
    
    public void setDateCreation(Date dateCreation) {
	  this.dateCreation = dateCreation;
    }
    
    public String getLog() {
	  return log;
    }
    
    public void setLog(String log) {
	  this.log = log;
    }
    
    public Long getId() {
    
	  return id;
    }
    
    public void setId(Long id) {
	  this.id = id;
    }
}
