package fr.sacem.priam.services;

import fr.sacem.priam.model.dao.jpa.ParametrageDao;
import fr.sacem.priam.model.domain.Parametrage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jbelwidane on 25/07/2017.
 */
@Component
public class ParametrageService {


    
    @Autowired
    private ParametrageDao parametrageDao;

    private static final Logger LOG = LoggerFactory.getLogger(ParametrageService.class);

    @Transactional
    public Map<String,String> findByUserId(String userId) {

        List<Parametrage> parametrageByUserId = parametrageDao.findByUserId(userId);

        Map<String, String> result = new HashMap<>();
        for (Parametrage p: parametrageByUserId) {
            result.put(p.getKey(), p.getValue());
        }

        return result;
    }

    @Transactional
    public void save(Parametrage parametrage) {
        parametrageDao.saveAndFlush(parametrage);
    }
}
