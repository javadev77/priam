package fr.sacem.priam.model.domain.dto;

import java.io.Serializable;

/**
 * Created by jbelwidane on 24/07/2017.
 */
public class KeyValueDto implements Serializable{

  private Long code;
  private String value;

  public KeyValueDto(Long code) {
    this.code = code;
  }

  public KeyValueDto(String value) {
    this.value = value;
  }

  public KeyValueDto(Long code, String value) {
    this.code = code;
    this.value = value;
  }

  public Long getCode() {
    return code;
  }

  public void setCode(Long code) {
    this.code = code;
  }

  public String getValue() { return value; }

  public void setValue(String value) { this.value = value; }
}
