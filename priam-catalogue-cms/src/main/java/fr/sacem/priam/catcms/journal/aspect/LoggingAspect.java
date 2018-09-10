package fr.sacem.priam.catcms.journal.aspect;

import fr.sacem.priam.catcms.journal.annotation.LogCatalogueAjout;
import fr.sacem.priam.catcms.journal.annotation.LogCatalogueSuppression;
import fr.sacem.priam.catcms.journal.annotation.TypeLog;
import fr.sacem.priam.model.dao.jpa.catcms.CatalogueCmsDao;
import fr.sacem.priam.model.dao.jpa.catcms.JournalCatcmsDao;
import fr.sacem.priam.model.domain.catcms.CatalogueCms;
import fr.sacem.priam.model.domain.catcms.JournalCatcms;
import fr.sacem.priam.security.model.UserDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by embouazzar on 09/08/2018.
 */

@Aspect
@Configuration
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    JournalCatcmsDao journalCatcmsDao;

    @Autowired
    CatalogueCmsDao catalogueCmsDao;

    @Around("execution(@fr.sacem.priam.catcms.journal.annotation.LogCatalogueAjout * *(..)) && @annotation(logCatalogueAjout)")
    public Object logCatalogueAjout(ProceedingJoinPoint joinPoint, LogCatalogueAjout logCatalogueAjout) throws Throwable {
        LOG.info("******");
        LOG.info("logCatalogue() is running!");
        LOG.info("Method : " + joinPoint.getSignature().getName());
        LOG.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        LOG.info("Around before is running!");

        Object result = joinPoint.proceed();

        TypeLog annotationValue = logCatalogueAjout.event();

        JournalCatcms journalCatcms = new JournalCatcms();

        CatalogueCms catalogueCms = null;

        if(annotationValue.equals(TypeLog.AJOUT_OEUVRE)){
            journalCatcms.setEvenement("AJOUT_MANUEL_OEUVRE");
            catalogueCms = (CatalogueCms) joinPoint.getArgs()[0];
        } else if(annotationValue.equals(TypeLog.RENOUVELLEMENT_OEUVRE)){
            journalCatcms.setEvenement("RENOUVELLEMENT_MANUEL_OEUVRE");
            Long idCatalogueCms = (Long) joinPoint.getArgs()[0];
            catalogueCms = getCatalogueCms(idCatalogueCms);
        }

        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[1];

        journalCatcms.setIde12(catalogueCms.getIde12());

        journalCatcms.setUtilisateur(userDTO.getUserId());
        journalCatcms.setDate(new Date());
        journalCatcms.setTypeCMS(catalogueCms.getTypeCMS());
        journalCatcmsDao.save(journalCatcms);

        return result;

    }

    @Around("execution(@fr.sacem.priam.catcms.journal.annotation.LogCatalogueSuppression * *(..)) && @annotation(logCatalogueSuppression)")
    public Object logCatalogueSuppression(ProceedingJoinPoint joinPoint, LogCatalogueSuppression logCatalogueSuppression) throws Throwable {
        Object result = joinPoint.proceed();

        Long idCatalogueCms = (Long) joinPoint.getArgs()[0];
        CatalogueCms catalogueCms = getCatalogueCms(idCatalogueCms);

        JournalCatcms journalCatcms = new JournalCatcms();
        journalCatcms.setEvenement("SUPPRESSION_MANUELLE_OEUVRE");
        journalCatcms.setIde12(catalogueCms.getIde12());
        journalCatcms.setDate(new Date());
        journalCatcms.setTypeCMS(catalogueCms.getTypeCMS());

        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[2];
        journalCatcms.setUtilisateur(userDTO.getUserId());

        journalCatcmsDao.save(journalCatcms);
        return result;
    }

    private CatalogueCms getCatalogueCms(Long id){
        return catalogueCmsDao.findOne(id);
    }

}
