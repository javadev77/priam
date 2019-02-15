package fr.sacem.priam.model.domain.fv;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Created by benmerzoukah on 25/09/2017.
 */
@Entity
@Table(name = "PRIAM_IMPORT_PROGRAMME_LOG")
public class ImportFichierProgrammeFvLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreation;

    @Column(name = "LOG")
    private String log;

    public ImportFichierProgrammeFvLog() {
    
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
