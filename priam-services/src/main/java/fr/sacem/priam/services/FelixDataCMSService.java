package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.domain.LignePreprep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 */
@Component
public class FelixDataCMSService extends FelixDataServiceAbstract{
    
    @Autowired
    private LigneProgrammeCMSDao ligneProgrammeCMSDao;
    

    public List<LignePreprep> getListLignesSelectionnees(String numProg) {
        return ligneProgrammeCMSDao.findLigneProgrammeSelectionnesForFelix(numProg);
    }
}

