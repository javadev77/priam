package fr.sacem.priam.batch.fv.ad.info.req.writer;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.fv.writer.AbstractConfig;
import fr.sacem.priam.batch.fv.ad.info.req.domain.AyantDroit;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Configuration
public class FlatFileWriterConfig extends AbstractConfig<AyantDroit> {

    private LigneProgrammeFVDao ligneProgrammeFVDao;

    public FlatFileWriterConfig(@Autowired LigneProgrammeFVDao ligneProgrammeFVDao) {

        this.ligneProgrammeFVDao = ligneProgrammeFVDao;
    }

    @Override
    public String head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n" +
                "# Fichier a destination de OSCAR\n"+
                "# en provenance de PRIAM\n"+
                "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
                "# JJ/MM/AAAA HH:MM - Auteur - Objet\n"+
                "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
                "# " + dateFormat.format(new Date()) + " - PRIAM - Creation\n"+
                "#DEBUT\n"+
                "#NUMPERS";
    }

    @Override
    public FieldExtractor<AyantDroit> createExtractor() {
        BeanWrapperFieldExtractor<AyantDroit> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"numPers"});
        return extractor;
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
        return "_INFOS_AD_REQ.csv";
    }

    @Override
    public Long countNbLignes(Long idFichier) {
        return ligneProgrammeFVDao.countNbLignesInfosADByIdFichier(idFichier);
    }
}
