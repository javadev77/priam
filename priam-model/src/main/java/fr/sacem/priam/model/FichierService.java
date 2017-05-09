package fr.sacem.priam.model;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.domain.Fichier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by benmerzoukah on 10/05/2017.
 */
@Component
@Transactional
public class FichierService {
    
    @Autowired
    FichierDao fichierDao;
    
    public List<Fichier> findAllFichiers() {
        return fichierDao.rechercher();
    }
}
