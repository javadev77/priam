package fr.sacem.priam.batch.fv.octav.req.config;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.fv.writer.AbstractConfig;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by benmerzoukah on 13/06/2018.
 */

@Configuration
public class FlatFileWriterConfig extends AbstractConfig<LigneProgrammeFV> {

    private static final Logger LOG = LoggerFactory.getLogger(FlatFileWriterConfig.class);
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
            "# en provenance de PRIAM pour service ŒUVRE CTNU;;;;;;;;;;;;;;;;;;;\n"+
            "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
            "# JJ/MM/AAAA HH:MM - Auteur - Objet;;;;;;;;;;;;;;;;;;;\n"+
            "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
            "# " + dateFormat.format(new Date()) + " - PRIAM - Creation\n"+
            "#DEBUT;;;;;;;;;;;;;;;;;;;\n"+
            "#IDE12CMPLX;CDETYPIDE12CMPLX;;;;;;;;;;;;;;;;;;";
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
        return  "_OeuvreCTNU_REQ.csv";
    }

    @Override
    public Long countNbLignes(final Long idFichier) {
       return ligneProgrammeFVDao.countNbLignesByIdFichier(idFichier);
    }

    @Override
    public FieldExtractor<LigneProgrammeFV> createExtractor() {
        BeanWrapperFieldExtractor<LigneProgrammeFV> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"ide12", "cdeTypIde12"});
        return extractor;
    }
}
