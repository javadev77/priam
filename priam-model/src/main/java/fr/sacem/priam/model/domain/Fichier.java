package fr.sacem.priam.model.domain;

import javax.persistence.*;

/**
 * Created by benmerzoukah on 09/05/2017.
 */
@Entity
@Table(name = "PRIAM_RECEPTION_FILE")
public class Fichier {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    
    @Column("NOM")
    private String nom;
}
