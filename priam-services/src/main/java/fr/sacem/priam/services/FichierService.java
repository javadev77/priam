package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.cp.FichierCPDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeCPDao;
import fr.sacem.priam.model.domain.cp.FichierCP;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.StatutProgramme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by benmerzoukah on 29/05/2017.
 */
@Component
public class FichierService {
    
    public static final String GUEST = "GUEST";
    @Autowired
    private FichierCPDao fichierCPDao;
    
    @Autowired
    private LigneProgrammeCPDao ligneProgrammeCPDao;
    
    @Autowired
    private ProgrammeCPDao programmeCPDao;
    
    @Transactional
    public void deleteDonneesFichiers(Long fileId) {
        ligneProgrammeCPDao.deleteAllByFichierId(fileId);
        fichierCPDao.updateFichierStatus(fileId, Status.ABANDONNE);
    }
    
    
    @Transactional
    public void majFichiersAffectesAuProgramme(String numProg, List<FichierCP> nouveauxfichiersAffectes, String currentUserName){
        List<Long> idsNouveauxFichiersAffectes=new ArrayList<>();
        for(FichierCP fichier : nouveauxfichiersAffectes){
            idsNouveauxFichiersAffectes.add(fichier.getId());
        }

        fichierCPDao.clearSelectedFichiers(numProg, Status.CHARGEMENT_OK);
        if(!idsNouveauxFichiersAffectes.isEmpty()) {
            fichierCPDao.updateStatusFichiersAffectes(numProg, Status.AFFECTE, idsNouveauxFichiersAffectes);
        }
        
        
        //Mettre par defaut les oeuvre à  selectionne
        ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, true);
        ligneProgrammeCPDao.deselectAllByNumProgramme(numProg, false);
        
    
        Programme programme = programmeCPDao.findOne(numProg);
        if(idsNouveauxFichiersAffectes.isEmpty()) {
            programme.setStatut(StatutProgramme.CREE);
        } else {
            programme.setStatut(StatutProgramme.AFFECTE);
        }
        programme.setUsermaj(currentUserName);
        programme.setDatmaj(new Date());
        programme.setUseraffect(currentUserName);
        programme.setDataffect(new Date());
        
        programmeCPDao.saveAndFlush(programme);
    }

    public Set<String> getChargementLog(Long idFichier) {
        return fichierCPDao.getChargementLog(idFichier);
    }
}
