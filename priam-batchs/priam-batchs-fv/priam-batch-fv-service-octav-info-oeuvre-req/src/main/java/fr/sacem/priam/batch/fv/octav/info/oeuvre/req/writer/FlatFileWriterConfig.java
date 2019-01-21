package fr.sacem.priam.batch.fv.octav.info.oeuvre.req.writer;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.writer.AbstractConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by benmerzoukah on 13/06/2018.
 */

@Configuration
public class FlatFileWriterConfig extends AbstractConfig<LigneProgrammeFV>{

    public static final Logger LOGGER = LoggerFactory.getLogger(FlatFileWriterConfig.class);
    private LigneProgrammeFVDao ligneProgrammeFVDao;

    public FlatFileWriterConfig(@Autowired LigneProgrammeFVDao ligneProgrammeFVDao) {

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
                        "#IDE12;CDETYPIDE12;;;;;;;;;;;;;;;;;;";
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
        return "_INFOS_OEUVRES_REQ.csv";
    }

    @Override
    public Long countNbLignes(final Long idFichier) {
       return ligneProgrammeFVDao.countNbLignesInfoOeuvreReqByIdFichier(idFichier);
    }

    @Override
    public FieldExtractor<LigneProgrammeFV> createExtractor() {
        BeanWrapperFieldExtractor<LigneProgrammeFV> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"ide12", "cdeTypIde12"});
        return extractor;
    }



}
