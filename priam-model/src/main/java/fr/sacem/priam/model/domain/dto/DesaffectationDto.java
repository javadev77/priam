package fr.sacem.priam.model.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class DesaffectationDto {

    private String numProg;
    private boolean allDesaffecte;
    private List fichersAvantDesaffectation;

    public String getNumProg() {
        return numProg;
    }

    public void setNumProg(String numProg) {
        this.numProg = numProg;
    }

    public boolean isAllDesaffecte() { return allDesaffecte; }

    public void setAllDesaffecte(boolean allDesaffecte) { this.allDesaffecte = allDesaffecte; }

    public List getFichersAvantDesaffectation() {
        return fichersAvantDesaffectation;
    }

    public void setFichersAvantDesaffectation(List fichersAvantDesaffectation) {
        this.fichersAvantDesaffectation = fichersAvantDesaffectation;
    }

    public DesaffectationDto(String numProg, boolean allDesaffecte, List fichersAvantDesaffectation)
    {
        this.fichersAvantDesaffectation = new ArrayList();
        this.numProg = numProg;
        this.allDesaffecte = allDesaffecte;
        this.fichersAvantDesaffectation = fichersAvantDesaffectation;
    }

    public DesaffectationDto() {
    }
}
