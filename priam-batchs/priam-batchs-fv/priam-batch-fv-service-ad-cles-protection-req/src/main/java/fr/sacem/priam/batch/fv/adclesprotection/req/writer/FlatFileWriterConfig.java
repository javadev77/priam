package fr.sacem.priam.batch.fv.adclesprotection.req.writer;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.fv.util.OctavDTO;
import fr.sacem.priam.batch.common.fv.writer.AbstractConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by benmerzoukah on 13/06/2018.
 */
@Configuration
public class FlatFileWriterConfig extends AbstractConfig<OctavDTO>{

    public static final Logger LOGGER = LoggerFactory.getLogger(FlatFileWriterConfig.class);

    private LigneProgrammeFVDao ligneProgrammeFVDao;

    public FlatFileWriterConfig(LigneProgrammeFVDao ligneProgrammeFVDao) {

        this.ligneProgrammeFVDao = ligneProgrammeFVDao;
    }

    @Override
    public String head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return
                "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n" +
                        "# Fichier a destination de OCTAV;;;;;;;;;;;;;;;;;;;\n"+
                        "# en provenance de PRIAM pour service INFO OEUVRE;;;;;;;;;;;;;;;;;;;\n"+
                        "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
                        "# JJ/MM/AAAA HH:MM - Auteur - Objet;;;;;;;;;;;;;;;;;;;\n"+
                        "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
                        "# " + dateFormat.format(new Date()) + " - PRIAM - Creation\n"+
                        "#DEBUT;;;;;;;;;;;;;;;;;;;\n"+
                        "#cdeCisac;cdeTer;cdeFamilTypUtil;cdeTypUtil;cdeTypDrtSacem;ide12;cdeTypIde12;datConsult;datSitu;rionStatut;rionCalc;idRevend;filler;ide12RepCoad;cdeTypIde12RepCoad;rolad;coad;idSteAd;cleAd;cdeTypProtec;coadOriEdtr;idSteOriEdtr;statut;numPers;numCatal";
    }

    @Override
    public String foot(Long lignes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);

        return
                "#FIN;;\n" +
                        "#" + dateFormat.format(new Date())  + "\n" +
                        "# Nbr lignes de donnees       = " + lignes;
    }


    @Override
    public String suffixNomFicher() {
        return "_ADClesProtections_REQ.csv";
    }

    @Override
    public Long countNbLignes(final Long idFichier) {
        return ligneProgrammeFVDao.countNbLignesAdclesByIdFichier(idFichier);
    }

    @Override
    public FieldExtractor<OctavDTO> createExtractor() {
        BeanWrapperFieldExtractor<OctavDTO> extractor = new BeanWrapperFieldExtractor<>();


        extractor.setNames(new String[] {"cdeCisac", "cdeTer", "cdeFamilTypUtil","cdeTypUtil", "cdeTypDrtSacem",
            "ide12", "cdeTypIde12", "datConsult", "datSitu", "rionStatut", "rionCalc", "idRevend"
        ,"filler", "ide12RepCoad", "cdeTypIde12RepCoad", "rolad", "coad", "idStead", "clead", "cdeTypProtec",
                "coadori", "idSteOri", "statut", "numPers", "numCatal"});
        return extractor;
    }



}
