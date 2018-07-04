package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCopyCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.util.FamillePriam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

;

/**
 * Created by benmerzoukah on 29/05/2017.
 */
@Service
public class FichierService {
    @Autowired
    private FichierDao fichierDao;
    
    @Autowired
    private LigneProgrammeCPDao ligneProgrammeCPDao;
    
    @Autowired
    private ProgrammeDao programmeDao;

    @Autowired
    private LigneProgrammeCMSDao ligneProgrammeCMSDao;

    @Autowired
    private LigneProgrammeCopyCMSDao ligneProgrammeCopyCMSDao;

    @Transactional
    public void deleteDonneesFichiers(Long fileId) {
        Fichier fichier = fichierDao.findOne(fileId);

        if(FamillePriam.COPIE_PRIVEE.getCode().equals(fichier.getFamille().getCode())) {
            ligneProgrammeCPDao.deleteAllByFichierId(fileId);
        } else if(FamillePriam.CMS.getCode().equals(fichier.getFamille().getCode())) {
            ligneProgrammeCMSDao.deleteAllByFichierId(fileId);
            ligneProgrammeCopyCMSDao.deleteAllCopyByFichierId(fileId);
        }

        fichierDao.updateFichierStatus(fileId, Status.ABANDONNE);
    }
    
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void majFichiersAffectesAuProgramme(String numProg, List<Fichier> nouveauxfichiersAffectes, String currentUserName){
        List<Long> idsNouveauxFichiersAffectes=new ArrayList<>();
        for(Fichier fichier : nouveauxfichiersAffectes){
            idsNouveauxFichiersAffectes.add(fichier.getId());
        }

        fichierDao.clearSelectedFichiers(numProg, Status.CHARGEMENT_OK);
        if(!idsNouveauxFichiersAffectes.isEmpty()) {
            fichierDao.updateStatusFichiersAffectes(numProg, Status.AFFECTE, idsNouveauxFichiersAffectes);
        }

        Programme programme = programmeDao.findOne(numProg);

        if(programme.getFamille().getCode().equals(FamillePriam.CMS.getCode())) {
            //Mettre par defaut les oeuvre à  selectionne
            ligneProgrammeCMSDao.updateSelectionTemporaireByNumProgramme(numProg, true);
            ligneProgrammeCMSDao.deselectAllByNumProgramme(numProg, true);
            programme.setStatutEligibilite(StatutEligibilite.EN_ATTENTE_ELIGIBILITE);
        } else if(programme.getFamille().getCode().equals(FamillePriam.COPIE_PRIVEE.getCode())) {
            //Mettre par defaut les oeuvre à  selectionne
            ligneProgrammeCPDao.updateSelectionTemporaireByNumProgramme(numProg, true);
            ligneProgrammeCPDao.deselectAllByNumProgramme(numProg, true);

        }


        if(idsNouveauxFichiersAffectes.isEmpty()) {
            programme.setStatut(StatutProgramme.CREE);
        } else {
            if(programme.getFamille().getCode().equals(FamillePriam.COPIE_PRIVEE.getCode())) {
                programme.setStatut(StatutProgramme.AFFECTE);
            }
        }
        programme.setUsermaj(currentUserName);
        programme.setDatmaj(new Date());
        programme.setUseraffect(currentUserName);
        programme.setDataffect(new Date());
        
        programmeDao.saveAndFlush(programme);
    }

    public Fichier getOrCreateFichierLink(String numProg) {

        Fichier fichierLink = fichierDao.findFichierLink(numProg);
        if(fichierLink == null) {
            fichierLink = new Fichier();

            Programme programme = programmeDao.findByNumProg(numProg);
            fichierLink.setProgramme(programme);
            fichierLink.setAutomatique(false);

            fichierLink = fichierDao.saveAndFlush(fichierLink);
        }

        return fichierLink;
    }

    public Set<String> getChargementLog(Long idFichier) {
        return fichierDao.getChargementLog(idFichier);
    }
}
