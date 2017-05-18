package fr.sacem.priam.model.domain;

import java.io.Serializable;

/**
 * Created by benmerzoukah on 15/05/2017.
 */
public class TypeUtilisationPK implements Serializable {
    private String code;
    
    private String lang;
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getLang() {return lang;}
    
    public void setLang(String lang) {this.lang = lang;}
    
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    
        TypeUtilisationPK that = (TypeUtilisationPK) o;
        
        if (code != null ? code.equals(that.code) : that.code != null) return false;
        return (lang != null ? lang.equals(that.lang) : that.lang == null);
    }
    
    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (lang != null ? lang.hashCode() : 0);
        return result;
    }
}
