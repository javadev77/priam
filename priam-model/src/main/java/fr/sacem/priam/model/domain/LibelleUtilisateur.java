package fr.sacem.priam.model.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fandis on 04/08/2017.
 */
@Entity
@Table(name="SAREFTJ_LIBUTIL")
public class LibelleUtilisateur {

    @Column(name = "CDELNG")
    private String cdeLng;
    @Column(name = "CDEUTIL")
    private String cdeUtil;
    @Column(name = "LIBUTIL")
    private String libUtil;
    @Column(name = "LIBABRGUTIL")
    private String libAbrgUtil;
    @Column(name = "DATECRE")
    private Date dateCre;
    @Column(name = "USERCRE")
    private String userCre;
    @Column(name = "DATEMAJ")
    private Date dateMaj;
    @Column(name = "USERMAJ")
    private String userMaj;
    @Column(name = "LIBFEUILLET")
    private String libFeuillet;

}
