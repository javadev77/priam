package fr.sacem.priam.services.cms;

import java.util.Map;

/**
 * Created by benmerzoukah on 11/12/2017.
 */
public interface LigneProgrammeCMSService {
    Map<String,Object> calculerCompteurs(String numProg, String statut);
}
