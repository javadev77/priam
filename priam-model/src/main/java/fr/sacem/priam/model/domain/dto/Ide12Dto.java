package fr.sacem.priam.model.domain.dto;

import java.io.Serializable;

/**
 * Created by jbelwidane on 24/07/2017.
 */
public class Ide12Dto implements Serializable{
  private Long code;


  public Ide12Dto(Long code) {
    this.code = code;
  }


  public Long getCode() {
    return code;
  }

  public void setCode(Long code) {
    this.code = code;
  }
}
