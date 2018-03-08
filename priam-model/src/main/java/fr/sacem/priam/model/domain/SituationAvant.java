package fr.sacem.priam.model.domain;

import javax.persistence.*;


@Entity
@Table(name = "PRIAM_SITUATION_AVANT")
public class SituationAvant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "SITUATION")
    private String situation;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "ID_EVENEMENT")
//    private Journal journal;

    public SituationAvant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

//    public Journal getJournal() {
//        return journal;
//    }
//
//    public void setJournal(Journal journal) {
//        this.journal = journal;
//    }
}
