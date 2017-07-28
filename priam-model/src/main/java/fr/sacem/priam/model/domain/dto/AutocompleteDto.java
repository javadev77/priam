package fr.sacem.priam.model.domain.dto;

import java.io.Serializable;

/**
 * Created by jbelwidane on 24/07/2017.
 */
public class AutocompleteDto implements Serializable{

  private Long code;
  private String value;

  public AutocompleteDto(Long code) {
    this.code = code;
  }

  public AutocompleteDto(String value) {
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
