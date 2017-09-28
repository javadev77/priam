package fr.sacem.priam.model.domain.dto;

import java.util.List;

/**
 * Created by benmerzoukah on 22/08/2017.
 */
public class FichierFelixError {
    private String filename;
    private String tmpFilename;
    private List<String> errors;
    private byte[] content;
    
    public FichierFelixError() {
    
    }
    
    public String getFilename() {
	  return filename;
    }
    
    public void setFilename(String filename) {
	  this.filename = filename;
    }
    
    public List<String> getErrors() {
	  return errors;
    }
    
    public void setErrors(List<String> errors) {
	  this.errors = errors;
    }
    
    public String getTmpFilename() {
	  return tmpFilename;
    }
    
    public void setTmpFilename(String tmpFilename) {
	  this.tmpFilename = tmpFilename;
    }
    
    public byte[] getContent() {
	  return content;
    }
    
    public void setContent(byte[] content) {
	  this.content = content;
    }
}
