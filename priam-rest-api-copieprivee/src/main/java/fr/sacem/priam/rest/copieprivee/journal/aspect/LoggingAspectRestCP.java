package fr.sacem.priam.rest.copieprivee.journal.aspect;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.rest.copieprivee.journal.annotation.LogFichier;
import fr.sacem.priam.rest.copieprivee.journal.annotation.TypeLog;
import fr.sacem.priam.security.model.UserDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Configuration
public class LoggingAspectRestCP {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspectRestCP.class);

    @Autowired ProgrammeDao programmeDao;

    @Autowired FichierDao fichierDao;

    @Autowired JournalDao journalDao;

    @Autowired
    LigneProgrammeCPDao ligneProgrammeCPDao;


    @Around("execution(@fr.sacem.priam.rest.copieprivee.journal.annotation.LogFichier * *(..)) && @annotation(logFichier)")
    public Object logFichier(ProceedingJoinPoint joinPoint, LogFichier logFichier) throws Throwable{
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



            situationApres = getFichiersAffectes(numProg);
            /*Journal journal = new Journal(numProg,annotationValue.getEvenement(), null, new Date(), utilisateurMaj, situationAvant, situationApres);
            journalDao.save(journal);*/
        }

        Object result = joinPoint.proceed();
        LOG.info("Result  :  " + result);

        return result;
    }

    /*@Around("execution(@fr.sacem.priam.rest.copieprivee.journal.annotation.LogOeuvre * *(..)) && @annotation(logOeuvre)")
    public  Object logAjouterOeuvre(ProceedingJoinPoint joinPoint, LogOeuvre logOeuvre) throws Throwable {

        TypeLog annotationValue = logOeuvre.event();

        LigneProgrammeCP input = (LigneProgrammeCP) joinPoint.getArgs()[0];
        UserDTO userDTO = (UserDTO)joinPoint.getArgs()[1];




        Object result = joinPoint.proceed();


        Journal journal = new Journal();
        String numProg = input.getNumProg();
        journal.setNumProg(numProg);
        journal.setDate(new Date());
        journal.setEvenement(annotationValue.getEvenement());
        journal.setIde12(input.getIde12());
        journal.setUtilisateur(userDTO.getUserId());

        SituationAvant situationAvant = new SituationAvant();
        situationAvant.setSituation("0");

        journal.getListSituationAvant().add(situationAvant);
        SituationApres situationApres = new SituationApres();

        Programme prog = programmeDao.findByNumProg(numProg);
        if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(prog.getTypeUtilisation().getCode())) {
            situationApres.setSituation(String.valueOf(input.getNbrDif()));
        } else if(TypeUtilisationPriam.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(prog.getTypeUtilisation().getCode())) {
            situationApres.setSituation(String.valueOf(input.getDurDif()));
        }

        journal.getListSituationApres().add(situationApres);

        journalDao.save(journal);

        return result;

    }*/

    private String getFichiersAffectes(String numProg){
        List<Fichier> listFichiersAvantAffectation = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);

        return String.join(", ", listFichiersAvantAffectation.stream().map(mc -> mc.getNomFichier() + " " + mc.getDateFinChargt()).collect(Collectors.toList()));
    }
}
