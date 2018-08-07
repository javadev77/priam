package fr.sacem.priam.catcms.api.dto;

import java.io.Serializable;

/**
 * Created by jbelwidane on 24/07/2017.
 */
public class KeyValueDtoCatcms implements Serializable{

  private String code;
  private Long value;

  public KeyValueDtoCatcms(String code) {
    this.code = code;
  }

  public KeyValueDtoCatcms(Long value) {
    this.value = value;
  }

  public KeyValueDtoCatcms(String code, Long value) {
    this.code = code;
    this.value = value;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public Long getValue() {
    return value;
  }

  public void setValue(Long value) {
    this.value = value;
  }
}
