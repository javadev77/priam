package fr.sacem.priam.model.domain.saref;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 *
 */
public class SareftjLibUtilPK implements Serializable {
    private String cdeLng;
    private String cdeUtil;
    
    @Column(name = "CDELNG")
    @Id
    public String getCdeLng() {
	  return cdeLng;
    }
    
    public void setCdeLng(String cdeLng) {
	  this.cdeLng = cdeLng;
    }
    
    @Column(name = "CDEUTIL")
    @Id
    public String getCdeUtil() {
	  return cdeUtil;
    }
    
    public void setCdeUtil(String cdeUtil) {
	  this.cdeUtil = cdeUtil;
    }
    
    @Override
    public boolean equals(Object o) {
	  if (this == o) return true;
	  if (o == null || getClass() != o.getClass()) return false;
	  
	  SareftjLibUtilPK that = (SareftjLibUtilPK) o;
	  
	  if (cdeLng != null ? !cdeLng.equals(that.cdeLng) : that.cdeLng != null) return false;
	  if (cdeUtil != null ? !cdeUtil.equals(that.cdeUtil) : that.cdeUtil != null) return false;
	  
	  return true;
    }
    
    @Override
    public int hashCode() {
	  int result = cdeLng != null ? cdeLng.hashCode() : 0;
	  result = 31 * result + (cdeUtil != null ? cdeUtil.hashCode() : 0);
	  return result;
    }
}
