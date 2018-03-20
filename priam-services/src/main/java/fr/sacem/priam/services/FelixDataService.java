package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.domain.LignePreprep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by benmerzoukah on 18/08/2017.
 */
@Component
public class FelixDataService extends FelixDataServiceAbstract{
    

    @Autowired
    private LigneProgrammeCPDao ligneProgrammeCPDao;


    public List<LignePreprep> getListLignesSelectionnees(String numProg) {
       return ligneProgrammeCPDao.findLigneProgrammeSelectionnesForFelix(numProg);
    }
}

