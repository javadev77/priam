package fr.sacem.priam.catcms.journal.aspect;

import fr.sacem.priam.catcms.journal.annotation.LogCatalogue;
import fr.sacem.priam.model.domain.catcms.JournalCatcms;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Created by embouazzar on 09/08/2018.
 */

@Aspect
@Configuration
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired
    JournalCatcms journalCatcms;

    @Around("execution(@fr.sacem.priam.catcms.journal.annotation.LogCatalogue * *(..)) && @annotation(logCatalogue)")
    public Object logCatalogue(ProceedingJoinPoint joinPoint, LogCatalogue logCatalogue) throws Throwable {
        LOG.info("******");
        LOG.info("logCatalogue() is running!");
        LOG.info("Method : " + joinPoint.getSignature().getName());
        LOG.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        LOG.info("Around before is running!");

        Object result = joinPoint.proceed();

        return result;

    }
}
