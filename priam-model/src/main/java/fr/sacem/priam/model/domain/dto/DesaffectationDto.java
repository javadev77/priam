package fr.sacem.priam.model.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class DesaffectationDto {

    private String numProg;
    private String isAllDesaffecte;
    private List fichersAvantDesaffectation;

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public String getIsAllDesaffecte() {
        return isAllDesaffecte;
    }

    public void setIsAllDesaffecte(String isAllDesaffecte) {
        this.isAllDesaffecte = isAllDesaffecte;
    }

    public List getFichersAvantDesaffectation() {
        return fichersAvantDesaffectation;
    }

    public void setFichersAvantDesaffectation(List fichersAvantDesaffectation) {
        this.fichersAvantDesaffectation = fichersAvantDesaffectation;
    }

    public DesaffectationDto(String numProg, String isAllDesaffecte, List fichersAvantDesaffectation)
    {
        this.fichersAvantDesaffectation = new ArrayList();
        this.numProg = numProg;
        this.isAllDesaffecte = isAllDesaffecte;
        this.fichersAvantDesaffectation = fichersAvantDesaffectation;
    }

    public DesaffectationDto() {
    }
}
