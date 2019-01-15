package fr.sacem.priam.services.api;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.dto.FileDto;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public abstract class AffectationResource {

    @Autowired
    FichierDao fichierDao;


    public String getListNomFichier(List<Fichier> fichiers){
        List<FileDto> list = new ArrayList<>();
        fichiers.forEach(fichier -> {
            list.add(fichierDao.findById(fichier.getId()));
        });
        return list.stream().map(e -> e.getNomFichier() + " " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(e.getDateFinChargt())).collect(Collectors.joining(","));
    }

    public List<Fichier> getListFichierByIdFichier(List<Long> listIdFichier){
        List<Fichier> result = new ArrayList<>();
        listIdFichier.forEach(id -> result.add(fichierDao.findOne(id)));

        return result;
    }

}
