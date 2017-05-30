package fr.sacem.priam.model.domain;

import javax.persistence.*;

/**
 * Created by benmerzoukah on 29/05/2017.
 */
@Entity
@Table(name = "PRIAM_LIGNE_PROGRAMME")
public class LigneProgramme {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "ID_FICHIER")
    private Long fichierId;
    
    public LigneProgramme() {
    
    }
    
    public Long getId() {
        return id;
    }
    
    public Long getFichierId() {
        return fichierId;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setFichierId(Long fichierId) {
        this.fichierId = fichierId;
    }
}
