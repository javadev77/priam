package fr.sacem.priam.rest.copieprivee.journal.aspect;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;

import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.rest.copieprivee.journal.annotation.*;

import fr.sacem.priam.security.model.UserDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Configuration
public class LoggingAspectRestCP {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspectRestCP.class);

    @Autowired ProgrammeDao programmeDao;

    @Autowired FichierDao fichierDao;

    @Autowired JournalDao journalDao;


    @Around("execution(@fr.sacem.priam.rest.copieprivee.journal.annotation.LogFichier * *(..)) && @annotation(logFichier)")
    public void logFichier(ProceedingJoinPoint joinPoint, LogFichier logFichier) throws Throwable{
        TypeLog annotationValue = logFichier.event();
        String situationAvant = "";
        String situationApres = "";
        String utilisateurMaj = "";
        /*ProgrammeDto programmeDto = (ProgrammeDto) joinPoint.getArgs()[0];*/
        AffectationDto affectationDto = (AffectationDto) joinPoint.getArgs()[0];
        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[1];
        String numProg = affectationDto.getNumProg();
        if (annotationValue.equals(fr.sacem.priam.rest.copieprivee.journal.annotation.TypeLog.AFFECTATION)) {
            /*Programme oldProgramme = programmeDao.findOne(programmeDto.getNumProg());*/
            situationAvant = getFichiersAffectes(numProg);
            utilisateurMaj = userDTO.getDisplayName();
            joinPoint.proceed();
            situationApres = getFichiersAffectes(numProg);
            /*Journal journal = new Journal(numProg,annotationValue.getEvenement(), null, new Date(), utilisateurMaj, situationAvant, situationApres);
            journalDao.save(journal);*/
        }

    }

    private String getFichiersAffectes(String numProg){
        List<Fichier> listFichiersAvantAffectation = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);

        return String.join(", ", listFichiersAvantAffectation.stream().map(mc -> mc.getNomFichier() + " " + mc.getDateFinChargt()).collect(Collectors.toList()));
    }
}
