package fr.sacem.priam.rest.copieprivee.journal.aspect;

import fr.sacem.priam.common.TypeLog;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;

import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.AffectationDto;
import fr.sacem.priam.rest.copieprivee.journal.annotation.LogFichier;

import fr.sacem.priam.model.domain.*;

import fr.sacem.priam.security.model.UserDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

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

        Object result = null;

        TypeLog annotationValue = logFichier.event();

        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[1];

        Journal journal = new Journal();

        String numProg = "";

        List<SituationAvant> situationAvantList = new ArrayList<>();
        List<SituationApres> situationApresList = new ArrayList<>();
        SituationAvant situationAvant = new SituationAvant();
        SituationApres situationApres = new SituationApres();

        String utilisateurMaj = userDTO.getUserId();
        journal.setUtilisateur(utilisateurMaj);

        journal.setDate(new Date());

        if (annotationValue.equals(TypeLog.AFFECTATION_DESAFFECTATION)) {
            AffectationDto affectationDto = (AffectationDto) joinPoint.getArgs()[0];

            numProg = affectationDto.getNumProg();
            journal.setNumProg(numProg);

            String evenement = TypeLog.AFFECTATION_DESAFFECTATION.getEvenement();
            journal.setEvenement(evenement);
            situationAvantList = getSituationAvantListByNumprog(numProg);
            result = joinPoint.proceed();
            situationApresList = getSituationApresListByNumprog(numProg);
            journal.setNumProg(numProg);
            journal.setUtilisateur(utilisateurMaj);
            journal.setDate(new Date());
            journal.setListSituationAvant(situationAvantList);
            journal.setListSituationApres(situationApresList);
            journalDao.save(journal);
            

        } else if (annotationValue.equals(TypeLog.ALL_DESAFFECTATION)){

            numProg = (String) joinPoint.getArgs()[0];
            journal.setNumProg(numProg);

            String evenement = annotationValue.getEvenement();
            journal.setEvenement(evenement);

            situationAvantList = getSituationAvantListByNumprog(numProg);
            journal.setListSituationAvant(situationAvantList);

            result = joinPoint.proceed();

        }

        journalDao.save(journal);

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

    /*private String getFichiersAffectes(String numProg){
        List<Fichier> listFichiersAvantAffectation = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);

        return String.join(", ", listFichiersAvantAffectation.stream().map(mc -> mc.getNomFichier() + " " + mc.getDateFinChargt()).collect(Collectors.toList()));
    }*/

    private List<SituationAvant> getSituationAvantListByNumprog(String numProg){
        List<Fichier> listFichiers = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);
        List<SituationAvant> result = new ArrayList<>();
        for (Fichier fichier : listFichiers) {
            SituationAvant situationAvant = new SituationAvant();
            situationAvant.setSituation(fichier.getNomFichier() + " " + fichier.getDateFinChargt());
            result.add(situationAvant);
        }
        return result;
    }

    private List<SituationApres> getSituationApresListByNumprog(String numProg){
        List<Fichier> listFichiers = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);
        List<SituationApres> result = new ArrayList<>();
        for (Fichier fichier : listFichiers) {
            SituationApres situationApres = new SituationApres();
            situationApres.setSituation(fichier.getNomFichier() + " " + fichier.getDateFinChargt());
            result.add(situationApres);
        }
        return result;
    }
}
