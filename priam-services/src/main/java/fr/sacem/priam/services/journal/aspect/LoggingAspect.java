package fr.sacem.priam.services.journal.aspect;

import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.StatutProgramme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.services.journal.annotation.LogOeuvre;
import fr.sacem.priam.services.journal.annotation.LogProgramme;
import fr.sacem.priam.services.journal.annotation.LogEtatProgramme;
import fr.sacem.priam.services.journal.annotation.TypeLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;


import java.util.Arrays;
import java.util.Date;

@Aspect
@Configuration
public class LoggingAspect {

    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    @Autowired ProgrammeDao programmeDao;

    @Autowired JournalDao journalDao;

    @Autowired private LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired private LigneProgrammeCMSDao ligneProgrammeCMSDao;


    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogProgramme * *(..)) && @annotation(logProgramme)")
    public void logProgramme(ProceedingJoinPoint joinPoint, LogProgramme logProgramme) throws Throwable {
        System.out.println("******");
        System.out.println("logProgramme() is running!");
        System.out.println("Method : " + joinPoint.getSignature().getName());
        System.out.println("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("Around before is running!");
        String situationAvant = "";
        String utilisateurMaj = "";
        ProgrammeDto programmeDto = (ProgrammeDto) joinPoint.getArgs()[0];
        TypeLog annotationValue = logProgramme.event();
        if (annotationValue.equals(TypeLog.MODIFICATION) || annotationValue.equals(TypeLog.SUPPRESSION)) {
            Programme oldProgramme = programmeDao.findOne(programmeDto.getNumProg());
            situationAvant = getInfoProgramme(oldProgramme);
            utilisateurMaj = programmeDto.getUsermaj();
        } else if (annotationValue.equals(TypeLog.CREATION)){
            utilisateurMaj = programmeDto.getUsercre();
        }
        joinPoint.proceed();
        System.out.println("Around after is running!");
        String evenement = annotationValue.getEvenement();
        String situationApres = "";
        if (annotationValue.equals(TypeLog.MODIFICATION) || annotationValue.equals(TypeLog.CREATION)) {
            situationApres = getInfoProgrammeDto(programmeDto);
        }
        Journal journal = new Journal(evenement, null, new Date(), utilisateurMaj, situationAvant, situationApres);
        journalDao.save(journal);
        System.out.println("******");
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
            Journal journal = new Journal(annotationValue.getEvenement(), null, new Date(), utilisateur, situationAvant, situationApres);
        journalDao.save(journal);
    }

    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogOeuvre * *(..)) && @annotation(logOeuvre)")
    public void logOeuvre(ProceedingJoinPoint joinPoint, LogOeuvre logOeuvre)  throws Throwable {
        TypeLog annotationValue = logOeuvre.event();
        joinPoint.proceed();

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
