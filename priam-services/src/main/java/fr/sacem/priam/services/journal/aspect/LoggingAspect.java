package fr.sacem.priam.services.journal.aspect;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalDao;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.domain.*;

import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;

import fr.sacem.priam.services.journal.annotation.LogEtatProgramme;
import fr.sacem.priam.services.journal.annotation.LogOeuvre;
import fr.sacem.priam.services.journal.annotation.LogProgramme;
import fr.sacem.priam.services.journal.annotation.TypeLog;

import fr.sacem.priam.model.domain.dto.SelectionCMSDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.util.EtatOeuvreEnum;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.services.journal.annotation.*;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Aspect
@Configuration
public class LoggingAspect {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    private static final String PRG_EN_COURS = "Programme \"En cours\" ";
    private static final String PRG_VALIDE = "Programme \"Validé\" ";
    private static final String PRG_AFFECTE = "Programme \"Affecté\" ";
    private static final String PRG_MIS_EN_REPART = "Programme \"Mis en répartition\" ";
    private static final String OEUVRE_SELECTIONNEE = "Sélectionnée";
    private static final String OEUVRE_DESELECTIONNEE = "Désélectionnée";
    private static final String SUPPRESSION_OEUVRE_MANUEL = "Suppression oeuvre avec état \"Manuel\"";
    private static final String SUPPRESSION_OEUVRE_CORRIGE = "Suppression oeuvre avec état \"Corrigé\"";

    @Autowired ProgrammeDao programmeDao;

    @Autowired FichierDao fichierDao;

    @Autowired JournalDao journalDao;

    @Autowired private LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired private LigneProgrammeCMSDao ligneProgrammeCMSDao;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogProgramme * *(..)) && @annotation(logProgramme)")
    public Object logProgramme(ProceedingJoinPoint joinPoint, LogProgramme logProgramme) throws Throwable {
        LOG.info("******");
        LOG.info("logProgramme() is running!");
        LOG.info("Method : " + joinPoint.getSignature().getName());
        LOG.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
        LOG.info("Around before is running!");

        Journal journal = new Journal();


        ProgrammeDto programmeDto = (ProgrammeDto) joinPoint.getArgs()[0];
        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[1];
        String utilisateurMaj = userDTO.getUserId();
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
            /*utilisateurMaj = programmeDto.getUsermaj();*/
            journal.setUtilisateur(utilisateurMaj);

            Programme oldProgramme = programmeDao.findOne(programmeDto.getNumProg());
            situationAvant.setSituation(getInfoProgramme(oldProgramme));
            situationAvantList.add(situationAvant);

        } else if (annotationValue.equals(TypeLog.CREATION)){
            /*utilisateurMaj = programmeDto.getUsercre();*/
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

        return result;
    }

    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogOeuvre * *(..)) && @annotation(logOeuvre)")
    public Object logOeuvre(ProceedingJoinPoint joinPoint, LogOeuvre logOeuvre)  throws Throwable {
        TypeLog annotationValue = logOeuvre.event();
        ValdierSelectionProgrammeInput selectionProgrammeInput = (ValdierSelectionProgrammeInput)joinPoint.getArgs()[0];

        UserDTO userDTO = (UserDTO)joinPoint.getArgs()[1];
        String numProg = selectionProgrammeInput.getNumProg();

        Programme programme = programmeDao.findByNumProg(numProg);

        List<SelectionDto> listLigneProgrammePreselectionnees = new ArrayList<>();
        List<SelectionDto> listLigneProgrammePredeselectionnees = new ArrayList<>();
        List<SelectionCMSDto> listLigneProgrammePreselectionneesCMS = new ArrayList<>();
        List<SelectionCMSDto> listLigneProgrammePredeselectionneesCMS = new ArrayList<>();

        if(selectionProgrammeInput.isFromSelection() && programme.getFamille().getCode().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCodeFamille())) {
            listLigneProgrammePreselectionnees = ligneProgrammeCPDao.findLigneProgrammePreselected(numProg, null, null, null, null, true, false);
            listLigneProgrammePredeselectionnees = ligneProgrammeCPDao.findLigneProgrammePreselected(numProg, null, null, null, null, false, true);

            for (SelectionDto ligneSelectionnee : listLigneProgrammePreselectionnees) {
                Journal journal = new Journal();
                journal.setEvenement(annotationValue.getEvenement());
                journal.setNumProg(selectionProgrammeInput.getNumProg());
                journal.setIde12(ligneSelectionnee.getIde12());
                journal.setDate(new Date());
                journal.setUtilisateur(userDTO.getUserId());

                List<SituationAvant> situationAvantList = new ArrayList<>();
                List<SituationApres> situationApresList = new ArrayList<>();

                SituationAvant situationAvant = new SituationAvant();
                SituationApres situationApres = new SituationApres();

                situationAvant.setSituation(OEUVRE_DESELECTIONNEE);
                situationApres.setSituation(OEUVRE_SELECTIONNEE);

                situationAvantList.add(situationAvant);
                situationApresList.add(situationApres);

                journal.setListSituationAvant(situationAvantList);
                journal.setListSituationApres(situationApresList);

                journalDao.save(journal);
            }


            for (SelectionDto ligneDeselectionnee : listLigneProgrammePredeselectionnees) {
                Journal journal = new Journal();
                journal.setEvenement(TypeLog.DESELECTION.getEvenement());
                journal.setNumProg(selectionProgrammeInput.getNumProg());
                journal.setIde12(ligneDeselectionnee.getIde12());
                journal.setDate(new Date());
                journal.setUtilisateur(userDTO.getUserId());

                List<SituationAvant> situationAvantList = new ArrayList<>();
                List<SituationApres> situationApresList = new ArrayList<>();

                SituationAvant situationAvant = new SituationAvant();
                SituationApres situationApres = new SituationApres();

                situationAvant.setSituation(OEUVRE_SELECTIONNEE);
                situationApres.setSituation(OEUVRE_DESELECTIONNEE);

                situationAvantList.add(situationAvant);
                situationApresList.add(situationApres);

                journal.setListSituationAvant(situationAvantList);
                journal.setListSituationApres(situationApresList);

                journalDao.save(journal);
            }

        } else if(selectionProgrammeInput.isFromSelection() && programme.getFamille().getCode().equals(TypeUtilisationEnum.CMS_FRA.getCodeFamille())){
            listLigneProgrammePreselectionneesCMS = ligneProgrammeCMSDao.findLigneProgrammePreselected(numProg, true, false);
            listLigneProgrammePredeselectionneesCMS = ligneProgrammeCMSDao.findLigneProgrammePreselected(numProg, false, true);

            for (SelectionCMSDto ligneSelectionnee : listLigneProgrammePreselectionneesCMS) {
                Journal journal = new Journal();
                journal.setEvenement(annotationValue.getEvenement());
                journal.setNumProg(selectionProgrammeInput.getNumProg());
                journal.setIde12(ligneSelectionnee.getIde12());
                journal.setDate(new Date());
                journal.setUtilisateur(userDTO.getUserId());

                List<SituationAvant> situationAvantList = new ArrayList<>();
                List<SituationApres> situationApresList = new ArrayList<>();

                SituationAvant situationAvant = new SituationAvant();
                SituationApres situationApres = new SituationApres();

                situationAvant.setSituation(OEUVRE_DESELECTIONNEE);
                situationApres.setSituation(OEUVRE_SELECTIONNEE);

                situationAvantList.add(situationAvant);
                situationApresList.add(situationApres);

                journal.setListSituationAvant(situationAvantList);
                journal.setListSituationApres(situationApresList);

                journalDao.save(journal);
            }


            for (SelectionCMSDto ligneDeselectionnee : listLigneProgrammePredeselectionneesCMS) {
                Journal journal = new Journal();
                journal.setEvenement(TypeLog.DESELECTION.getEvenement());
                journal.setNumProg(selectionProgrammeInput.getNumProg());
                journal.setIde12(ligneDeselectionnee.getIde12());
                journal.setDate(new Date());
                journal.setUtilisateur(userDTO.getUserId());

                List<SituationAvant> situationAvantList = new ArrayList<>();
                List<SituationApres> situationApresList = new ArrayList<>();

                SituationAvant situationAvant = new SituationAvant();
                SituationApres situationApres = new SituationApres();

                situationAvant.setSituation(OEUVRE_SELECTIONNEE);
                situationApres.setSituation(OEUVRE_DESELECTIONNEE);

                situationAvantList.add(situationAvant);
                situationApresList.add(situationApres);

                journal.setListSituationAvant(situationAvantList);
                journal.setListSituationApres(situationApresList);

                journalDao.save(journal);
            }
        }



        Object result = joinPoint.proceed();

        return result;
    }

    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogEtatProgramme * *(..)) && @annotation(logEtatProgramme)")
    public Object logEtatProgramme(ProceedingJoinPoint joinPoint, LogEtatProgramme logEtatProgramme)  throws Throwable {
        TypeLog annotationValue = logEtatProgramme.event();

        Object result = joinPoint.proceed();

        ProgrammeDto programmeDto = (ProgrammeDto) joinPoint.getArgs()[0];
        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[1];

        List<SituationAvant> situationAvantList = new ArrayList<>();
        List<SituationApres> situationApresList = new ArrayList<>();
        SituationAvant situationAvant = new SituationAvant();
        SituationApres situationApres = new SituationApres();

        String utilisateur = userDTO.getUserId();

        if (annotationValue.equals(TypeLog.VALIDATION)){

            situationAvant.setSituation(PRG_EN_COURS);
            situationAvantList.add(situationAvant);

            situationApres.setSituation(PRG_VALIDE);
            situationApresList.add(situationApres);

        } else if (annotationValue.equals(TypeLog.INVALIDATION)){
            situationAvant.setSituation(PRG_VALIDE);
            situationAvantList.add(situationAvant);

            situationApres.setSituation(PRG_EN_COURS);
            situationApresList.add(situationApres);

        } else if (annotationValue.equals(TypeLog.ANNULATION)){

            situationAvant.setSituation(PRG_EN_COURS);
            situationAvantList.add(situationAvant);

            situationApres.setSituation(PRG_AFFECTE);
            situationApresList.add(situationApres);

        }else if (annotationValue.equals(TypeLog.REPARTITION)){

            situationAvant.setSituation(PRG_VALIDE);
            situationAvantList.add(situationAvant);

            situationApres.setSituation(PRG_MIS_EN_REPART);
            situationApresList.add(situationApres);
        }
        Journal journal = new Journal(programmeDto.getNumProg(),annotationValue.getEvenement(), null, new Date(), utilisateur, situationAvantList, situationApresList);
        journalDao.save(journal);
        return result;
    }

    @Around("execution(@fr.sacem.priam.services.journal.annotation.LogSuppressionOeuvre * *(..)) && @annotation(logSuppressionOeuvre)")
    public Object logSuppressionOeuvre(ProceedingJoinPoint joinPoint, LogSuppressionOeuvre logSuppressionOeuvre)  throws Throwable {

        TypeLog annotationValue = logSuppressionOeuvre.event();
        String numProg = (String) joinPoint.getArgs()[0];
        Long ide12 = (Long) joinPoint.getArgs()[1];
        SelectionDto selectionDto = (SelectionDto) joinPoint.getArgs()[2];
        UserDTO userDTO = (UserDTO) joinPoint.getArgs()[3];

        List<SituationAvant> situationAvantList = new ArrayList<>();
        List<SituationApres> situationApresList = new ArrayList<>();
        SituationAvant situationAvant = new SituationAvant();
        SituationApres situationApres = new SituationApres();

        Programme programme = programmeDao.findByNumProg(numProg);

        if(selectionDto.getAjout().equals(EtatOeuvreEnum.CORRIGE.getCode())){

            if(programme.getFamille().getCode().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCodeFamille())){

                LigneProgrammeCP oeuvreCorrigeeCP = ligneProgrammeCPDao.findOeuvreCorrigeByIde12AndCdeUtil(numProg, ide12, selectionDto.getCdeUtil());
                List<LigneProgrammeCP> listLigneProgrammeCPLiee = ligneProgrammeCPDao.findOeuvresAutoByIdOeuvreManuel(oeuvreCorrigeeCP.getId());

                if(oeuvreCorrigeeCP.getCdeTypUtil().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode())){
                    situationAvant.setSituation(String.valueOf(selectionDto.getNbrDif()));
                    situationAvantList.add(situationAvant);

                    Long nbrDif = listLigneProgrammeCPLiee.stream().mapToLong(LigneProgrammeCP::getNbrDif).sum();
                    situationApres.setSituation(String.valueOf(nbrDif));
                    situationApresList.add(situationApres);

                } else if(oeuvreCorrigeeCP.getCdeTypUtil().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode())){
                    situationAvant.setSituation(String.valueOf(selectionDto.getDurDif()));
                    situationAvantList.add(situationAvant);

                    Long durDif = listLigneProgrammeCPLiee.stream().mapToLong(LigneProgrammeCP::getDurDif).sum();
                    situationApres.setSituation(String.valueOf(durDif));
                    situationApresList.add(situationApres);

                }
            } else if(programme.getFamille().getCode().equals(TypeUtilisationEnum.CMS_FRA.getCodeFamille())) {
                LigneProgrammeCMS oeuvreCorrigeeCMS = ligneProgrammeCMSDao.findOeuvreCorrigeByIde12(numProg, ide12);
                List<LigneProgrammeCMS> listLigneProgrammeCMSLiee = ligneProgrammeCMSDao.findOeuvresAutoByIdOeuvreManuel(oeuvreCorrigeeCMS.getId());

                if(oeuvreCorrigeeCMS.getCdeTypUtil().equals(TypeUtilisationEnum.CMS_FRA.getCode())){
                    situationAvant.setSituation(String.valueOf(oeuvreCorrigeeCMS.getMt()));
                    situationAvantList.add(situationAvant);
                    Double mt = listLigneProgrammeCMSLiee.stream().mapToDouble(LigneProgrammeCMS::getMt).sum();
                    situationApres.setSituation(String.valueOf(mt));
                    situationApresList.add(situationApres);
                } else if(oeuvreCorrigeeCMS.getCdeTypUtil().equals(TypeUtilisationEnum.CMS_ANT.getCode())){
                    situationAvant.setSituation(String.valueOf(oeuvreCorrigeeCMS.getNbrDif()));
                    situationAvantList.add(situationAvant);

                    Long nbrDif = listLigneProgrammeCMSLiee.stream().mapToLong(LigneProgrammeCMS::getNbrDif).sum();
                    situationApres.setSituation(String.valueOf(nbrDif));
                    situationApresList.add(situationApres);
                }
            }


            Journal journal = new Journal(numProg, SUPPRESSION_OEUVRE_CORRIGE, ide12, new Date(), userDTO.getUserId(), situationAvantList, situationApresList);
            journalDao.save(journal);

        } else if(selectionDto.getAjout().equals(EtatOeuvreEnum.MANUEL.getCode())){

            if(programme.getFamille().getCode().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCodeFamille())){
                LigneProgrammeCP oeuvreManuelCP = ligneProgrammeCPDao.findOeuvreManuelByIde12AndCdeUtil(numProg, ide12, selectionDto.getCdeUtil());
                if(oeuvreManuelCP.getCdeTypUtil().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode())) {
                    situationAvant.setSituation(String.valueOf(selectionDto.getNbrDif()));
                    situationAvantList.add(situationAvant);
                    situationApres.setSituation("0");
                    situationApresList.add(situationApres);
                } else if(oeuvreManuelCP.getCdeTypUtil().equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode())){
                    situationAvant.setSituation(String.valueOf(selectionDto.getDurDif()));
                    situationAvantList.add(situationAvant);
                    situationApres.setSituation("0");
                    situationApresList.add(situationApres);
                }
            } else if(programme.getFamille().getCode().equals(TypeUtilisationEnum.CMS_FRA.getCodeFamille())){
                LigneProgrammeCMS oeuvreManuelCMS = ligneProgrammeCMSDao.findOeuvreManuelByIde12(numProg, ide12);
                if(oeuvreManuelCMS.getCdeTypUtil().equals(TypeUtilisationEnum.CMS_FRA.getCode())){
                    situationAvant.setSituation(String.valueOf(oeuvreManuelCMS.getMt()));
                    situationAvantList.add(situationAvant);
                    situationApres.setSituation("0");
                    situationApresList.add(situationApres);
                }else if(oeuvreManuelCMS.getCdeTypUtil().equals(TypeUtilisationEnum.CMS_ANT.getCode())){
                    situationAvant.setSituation(String.valueOf(selectionDto.getNbrDif()));
                    situationAvantList.add(situationAvant);
                    situationApres.setSituation("0");
                    situationApresList.add(situationApres);
                }
            }



            Journal journal = new Journal(numProg, SUPPRESSION_OEUVRE_MANUEL, ide12, new Date(), userDTO.getUserId(), situationAvantList, situationApresList);
            journalDao.save(journal);
        }

        Object result = joinPoint.proceed();


        return result;
    }

    private String getInfoProgramme(Programme programme) {
        return "Numprog : " + programme.getNumProg() + " Nom : " + programme.getNom() + "<br/>"
                + " Famille : " + programme.getFamille().getCode() + " Type util : " + programme.getTypeUtilisation().getCode() + "<br/>"
                + " Rion: " + programme.getRionTheorique().getRion() + " Code ter : " + programme.getCdeTer() + "<br/>"
                + " Type repart : " + programme.getTypeRepart() + "<br/>"
                + " Date début : " + simpleDateFormat.format(programme.getDateDbtPrg()) + "<br/>"
                + " Date fin : " + programme.getDateFinPrg() + "<br/>"
                + " Date maj : " + programme.getDatmaj();
                /*+ " Utilisateur maj : " + programme.getUsermaj();*/
    }

    private String getInfoProgrammeDto(ProgrammeDto programmeDto) {
        return "Numprog : " + programmeDto.getNumProg() + "<br/>"
                + "Nom : " + programmeDto.getNom()+ "<br/>"
                + "Famille : " + programmeDto.getFamille() + "<br/>"
                + "Type util : " + programmeDto.getTypeUtilisation() + "<br/>"
                + "Rion: " + programmeDto.getRionTheorique() + "<br/>"
                + "Code ter : " + programmeDto.getCdeTer() + "<br/>"
                + "Type repart : " + programmeDto.getTypeRepart() +"<br/>"
                + "Date début : " + simpleDateFormat.format(programmeDto.getDateDbtPrg()) + "<br/>"
                + "Date fin : " + simpleDateFormat.format(programmeDto.getDateFinPrg()) + "<br/>"
                + "Date maj : " + simpleDateFormat.format(new Date()); /*+ "<br/>"
                + "Utilisateur maj : " + programmeDto.getUsermaj();*/
    }

}
