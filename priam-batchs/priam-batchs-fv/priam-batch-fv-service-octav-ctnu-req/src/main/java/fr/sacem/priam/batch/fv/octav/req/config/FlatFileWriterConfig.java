package fr.sacem.priam.batch.fv.octav.req.config;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.fv.writer.AbstractConfig;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import fr.sacem.priam.batch.common.domain.OctavCtnu;
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
public class FlatFileWriterConfig extends AbstractConfig<OctavCtnu> {

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
            "#cdeCisac;cdeTer;cdeFamilTypUtilOri;cdeTypUtilOri;cdeUtil;ide12;cdeTypIde12;datConslt;datSitu;rionStatut;rionCalc;cdeLng;indDoubSsTit;filler;ide12Ctnu;cdeTypIde12Ctnu;indCmplxCtnu;seqOeuvCtnu;seqOeuvPere;durDsOeuvPere;taxatDsOeuvPere;cdeGreDifDsOeuvPere;oeuvPreExist;pctDvaltn;numOrd;indDroitTotEnAttente;statut";
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
       return ligneProgrammeFVDao.countNbLignesOeuvreCtnuByIdFichier(idFichier);
    }

    @Override
    public FieldExtractor<OctavCtnu> createExtractor() {
        BeanWrapperFieldExtractor<OctavCtnu> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"cdeCisac", "cdeTer", "cdeFamilTypUtilOri","cdeTypUtilOri","cdeUtil","ide12",
                "cdeTypIde12","datConsult","datSitu","rionStatut","rionCalc","cdeLng","indDoubSsTit","ide12Ctnu",
                "cdeTypIde12Ctnu","indCmplxCtnu","seqOeuvCtnu","seqOeuvPere","durDsOeuvPere","taxatDsOeuvPere",
                "cdeGreDifDsOeuvPere","oeuvPreExist","pctDvaltn","numOrd","indDroitTotEnAttente","statut"});
        return extractor;
    }
}
