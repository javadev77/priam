package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by benmerzoukah on 29/05/2017.
 */
@Component
public class FichierService {
    
    @Autowired
    private FichierDao fichierDao;
    
    @Autowired
    LigneProgrammeDao ligneProgrammeDao;
    
    @Transactional
    public void deleteDonneesFichiers(Long fileId) {
        ligneProgrammeDao.deleteAllByFichierId(fileId);
        fichierDao.updateFichierStatus(fileId, Status.ABANDONNE);
    }
    @Transactional
    public void majFichiersAffectesAuProgramme(String numProg, ArrayList<Fichier> nouveauxfichiersAffectes){
        ArrayList<Long> idsNouveauxFichiersAffectes=new ArrayList<>();
        for(Fichier fichier : nouveauxfichiersAffectes){
            idsNouveauxFichiersAffectes.add(fichier.getId());
        }

        fichierDao.clearSelectedFichiers(numProg,Status.CHARGEMENT_OK);
        fichierDao.updateStatusFichiersAffectes(numProg,Status.AFFECTE,idsNouveauxFichiersAffectes);

    }
}
