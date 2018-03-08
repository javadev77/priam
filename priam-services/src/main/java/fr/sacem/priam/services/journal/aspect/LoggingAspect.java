package fr.sacem.priam.services.journal.aspect;

import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;
import fr.sacem.priam.model.domain.dto.FileDto;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.services.journal.annotation.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Configuration
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired ProgrammeDao programmeDao;

    @Autowired FichierDao fichierDao;

    @Autowired JournalDao journalDao;

    @Autowired private LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired private LigneProgrammeCMSDao ligneProgrammeCMSDao;


    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogProgramme * *(..)) && @annotation(logProgramme)")
    public void logProgramme(ProceedingJoinPoint joinPoint, LogProgramme logProgramme) throws Throwable {
        LOG.info("******");
        LOG.info("logProgramme() is running!");
        LOG.info("Method : " + joinPoint.getSignature().getName());
        LOG.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        LOG.info("Around before is running!");

        Journal journal = new Journal();

        String utilisateurMaj = "";
        ProgrammeDto programmeDto = (ProgrammeDto) joinPoint.getArgs()[0];
        TypeLog annotationValue = logProgramme.event();
        String evenement = annotationValue.getEvenement();

        journal.setEvenement(evenement);
        journal.setDate(new Date());

        List<SituationAvant> situationAvantList = new ArrayList<>();
        List<SituationApres> situationApresList = new ArrayList<>();
        SituationAvant situationAvant = new SituationAvant();
        SituationApres situationApres = new SituationApres();


        /*Journal journal = new Journal(programmeDto.getNumProg(),evenement, new Date(), utilisateurMaj);*/


        if (annotationValue.equals(TypeLog.MODIFICATION) || annotationValue.equals(TypeLog.SUPPRESSION)) {
            journal.setNumProg(programmeDto.getNumProg());
            utilisateurMaj = programmeDto.getUsermaj();
            journal.setUtilisateur(utilisateurMaj);

            Programme oldProgramme = programmeDao.findOne(programmeDto.getNumProg());
            situationAvant.setSituation(getInfoProgramme(oldProgramme));
            situationAvantList.add(situationAvant);

        } else if (annotationValue.equals(TypeLog.CREATION)){
            utilisateurMaj = programmeDto.getUsercre();
            journal.setUtilisateur(utilisateurMaj);
        }

        Object result = joinPoint.proceed();
        LOG.info("Around after is running!");

        if (annotationValue.equals(TypeLog.MODIFICATION) || annotationValue.equals(TypeLog.CREATION)) {
            if(result instanceof Programme) {
                Programme prog = (Programme) result;
                journal.setNumProg(prog.getNumProg());
            }
            situationApres.setSituation(getInfoProgrammeDto(programmeDto));
            situationApresList.add(situationApres);
        }

        journal.setListSituationAvant(situationAvantList);
        journal.setListSituationApres(situationApresList);
        journalDao.save(journal);
        LOG.info("******");
    }

    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogOeuvre * *(..)) && @annotation(logOeuvre)")
    public void logOeuvre(ProceedingJoinPoint joinPoint, LogOeuvre logOeuvre)  throws Throwable {
        TypeLog annotationValue = logOeuvre.event();
        joinPoint.proceed();

    }

    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogEtatProgramme * *(..)) && @annotation(logEtatProgramme)")
    public void logEtatProgramme(ProceedingJoinPoint joinPoint, LogEtatProgramme logEtatProgramme)  throws Throwable {
        TypeLog annotationValue = logEtatProgramme.event();
        joinPoint.proceed();
            ProgrammeDto programmeDto = (ProgrammeDto) joinPoint.getArgs()[0];
            String situationAvant = "";
            String situationApres = "";
            String utilisateur = "";
            if (annotationValue.equals(TypeLog.VALIDATION)){
                situationAvant = StatutProgramme.EN_COURS.toString();
                situationApres = StatutProgramme.VALIDE.toString();
                utilisateur = programmeDto.getUserValidation();
            } else if (annotationValue.equals(TypeLog.INVALIDATION)){
                situationAvant = StatutProgramme.VALIDE.toString();
                situationApres = StatutProgramme.EN_COURS.toString();
                utilisateur = programmeDto.getUsermaj();
            } else if (annotationValue.equals(TypeLog.ANNULATION)){
                situationAvant = StatutProgramme.EN_COURS.toString();
                situationApres = StatutProgramme.AFFECTE.toString();
                utilisateur = programmeDto.getUseraffecte();
            }else if (annotationValue.equals(TypeLog.REPARTITION)){
                situationAvant = StatutProgramme.VALIDE.toString();
                situationApres = StatutProgramme.MIS_EN_REPART.toString();
                utilisateur = programmeDto.getUsermaj();
            }
            /*Journal journal = new Journal(programmeDto.getNumProg(),annotationValue.getEvenement(), null, new Date(), utilisateur, situationAvant, situationApres);*/
        /*journalDao.save(journal);*/
    }

    private String getInfoProgramme(Programme programme) {
        return "Numprog : " + programme.getNumProg() + " Nom : " + programme.getNom()
                + " Famille : " + programme.getFamille().getCode() + " Type util : " + programme.getTypeUtilisation().getCode()
                + " Rion: " + programme.getRionTheorique().getRion() + " Code ter : " + programme.getCdeTer()
                + " Type repart : " + programme.getTypeRepart()
                + " Date début : " + programme.getDateDbtPrg() + " Date fin : " + programme.getDateFinPrg()
                + " Date maj : " + programme.getDatmaj() + " Utilisateur maj : " + programme.getUsermaj();
    }

    private String getInfoProgrammeDto(ProgrammeDto programmeDto) {
        return "Numprog : " + programmeDto.getNumProg() + " Nom : " + programmeDto.getNom()
                + " Famille : " + programmeDto.getFamille() + " Type util : " + programmeDto.getTypeUtilisation()
                + " Rion: " + programmeDto.getRionTheorique() + " Code ter : " + programmeDto.getCdeTer()
                + " Type repart : " + programmeDto.getTypeRepart()
                + " Date début : " + programmeDto.getDateDbtPrg() + " Date fin : " + programmeDto.getDateFinPrg()
                + " Date maj : " + new Date() + " Utilisateur maj : " + programmeDto.getUsermaj();
    }

}
