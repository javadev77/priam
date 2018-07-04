package fr.sacem.priam.model.domain;


import java.io.Serializable;

/**
 * Created by belwidanej on 22/09/2017.
 */
public class ParametragePK implements Serializable {

    private String key;
    private String userId;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    
        ParametragePK that = (ParametragePK) o;
    
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
    
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
