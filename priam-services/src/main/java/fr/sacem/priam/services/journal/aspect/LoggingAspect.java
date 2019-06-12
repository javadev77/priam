package fr.sacem.priam.services.journal.aspect;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.*;
import fr.sacem.priam.model.dao.jpa.cms.LigneProgrammeCMSDao;
import fr.sacem.priam.model.dao.jpa.cp.LigneProgrammeCPDao;
import fr.sacem.priam.model.dao.jpa.cp.ProgrammeDao;
import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.domain.cms.LigneProgrammeCMS;
import fr.sacem.priam.model.domain.cp.LigneProgrammeCP;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import fr.sacem.priam.model.domain.dto.SelectionDto;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import fr.sacem.priam.model.domain.saref.SareftjLibfamiltyputil;
import fr.sacem.priam.model.domain.saref.SareftjLibter;
import fr.sacem.priam.model.domain.saref.SareftjLibtyputil;
import fr.sacem.priam.model.domain.saref.SareftrRion;
import fr.sacem.priam.model.util.EtatOeuvreEnum;
import fr.sacem.priam.model.util.FamillePriam;
import fr.sacem.priam.model.util.GlobalConstants;
import fr.sacem.priam.security.model.UserDTO;
import fr.sacem.priam.services.JournalService;
import fr.sacem.priam.services.dto.ValdierSelectionProgrammeInput;
import fr.sacem.priam.services.journal.annotation.*;
import org.apache.commons.lang.WordUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Aspect
@Configuration
public class LoggingAspect {
    private static final String OEUVRE_SELECTIONNEE = "Sélectionnée";
    private static final String OEUVRE_DESELECTIONNEE = "Désélectionnée";

    private static final Logger LOG = LoggerFactory.getLogger(LoggingAspect.class);

    private static final String PRG_EN_COURS = "En cours";
    private static final String PRG_VALIDE = "Validé";
    private static final String PRG_AFFECTE = "Affecté";
    private static final String PRG_MIS_EN_REPART = "Mis en répartition";
    private static final String SUPPRESSION_OEUVRE_MANUEL = "Suppression oeuvre avec état \"Manuel\"";
    private static final String SUPPRESSION_OEUVRE_CORRIGE = "Suppression oeuvre avec état \"Corrigé\"";

    @Autowired ProgrammeDao programmeDao;

    @Autowired FichierDao fichierDao;

    @Autowired JournalDao journalDao;

    @Autowired private LigneProgrammeCPDao ligneProgrammeCPDao;

    @Autowired private LigneProgrammeCMSDao ligneProgrammeCMSDao;

    @Autowired private LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired private SareftjLibterDao sareftjLibterDao;

    @Autowired private SareftrRionDao sareftrRionDao;


    @Autowired private SareftjLibfamiltyputilDao sareftjLibfamiltyputilDao;

    @Autowired private SareftjLibtyputilDao sareftjLibtyputilDao;

    @Autowired
    JournalService journalService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

  /*  @Autowired
    private JournalJdbcDao journalJdbcDao;*/


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

        if (annotationValue.equals(TypeLog.MODIFICATION) || annotationValue.equals(TypeLog.SUPPRESSION)) {
            journal.setNumProg(programmeDto.getNumProg());
            journal.setUtilisateur(utilisateurMaj);

            Programme oldProgramme = programmeDao.findOne(programmeDto.getNumProg());
            situationAvant.setSituation(getInfoProgramme(oldProgramme));
            situationAvantList.add(situationAvant);

        } else if (annotationValue.equals(TypeLog.CREATION)){
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
        journalService.logJournalOeuvre(selectionProgrammeInput, userDTO, annotationValue);
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
            } else if(programme.getFamille().getCode().equals(FamillePriam.VALORISATION.getCode())){
                LigneProgrammeFV oeuvreCorrigeeFV = ligneProgrammeFVDao.findOeuvreCorrigeByIde12(numProg, ide12);
                List<LigneProgrammeFV> listLigneProgrammeFVLiee = ligneProgrammeFVDao.findOeuvresAutoByIdOeuvreManuel(oeuvreCorrigeeFV.getId());

                if(oeuvreCorrigeeFV.getCdeTypUtil().equals(TypeUtilisationEnum.FV_FONDS_06.getCode())){
                    situationAvant.setSituation(String.valueOf(oeuvreCorrigeeFV.getMt()));
                    situationAvantList.add(situationAvant);
                    Double mt = listLigneProgrammeFVLiee.stream().mapToDouble(LigneProgrammeFV::getMt).sum();
                    situationApres.setSituation(String.valueOf(mt));
                    situationApresList.add(situationApres);
                } else if(oeuvreCorrigeeFV.getCdeTypUtil().equals(TypeUtilisationEnum.FV_FONDS_12.getCode())){
                    situationAvant.setSituation(String.valueOf(oeuvreCorrigeeFV.getNbrDif()));
                    situationAvantList.add(situationAvant);

                    Long nbrDif = listLigneProgrammeFVLiee.stream().mapToLong(LigneProgrammeFV::getNbrDif).sum();
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
                    situationAvant.setSituation(String.valueOf(oeuvreManuelCMS.getNbrDif()));
                    situationAvantList.add(situationAvant);
                    situationApres.setSituation("0");
                    situationApresList.add(situationApres);
                }
            } else if(programme.getFamille().getCode().equals(FamillePriam.VALORISATION.getCode())){
                LigneProgrammeFV oeuvreManuelFV = ligneProgrammeFVDao.findOeuvreManuelByIde12(numProg, ide12);
                if(oeuvreManuelFV.getCdeTypUtil().equals(TypeUtilisationEnum.FV_FONDS_06.getCode())){
                    situationAvant.setSituation(String.valueOf(oeuvreManuelFV.getMt()));
                    situationAvantList.add(situationAvant);
                    situationApres.setSituation("0");
                    situationApresList.add(situationApres);
                }else if(oeuvreManuelFV.getCdeTypUtil().equals(TypeUtilisationEnum.FV_FONDS_12.getCode())){
                    situationAvant.setSituation(String.valueOf(oeuvreManuelFV.getNbrDif()));
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
        return " Nom programme : " + programme.getNom() + "<br/>"
                + " Rion cible : " + getLibelleRion(programme.getRionTheorique().getRion()) + "<br/>"
                + " Famille : " + getLibelleFamille(programme.getFamille().getCode(), GlobalConstants.FR_LANG) + "<br/>"
                + " Type d'utilisation : " + getLibelleTypeUtil(programme.getTypeUtilisation().getCode(), GlobalConstants.FR_LANG) + "<br/>"

                + " Date de début : " + simpleDateFormat.format(programme.getDateDbtPrg()) + "<br/>"
                + " Date de fin : " + simpleDateFormat.format(programme.getDateFinPrg()) + "<br/>"
                + " Territoire : " + getLibelleTerritoire(programme.getCdeTer(), GlobalConstants.FR_LANG) + "<br/>"
                + " Mode de répartition : " + WordUtils.capitalize(programme.getTypeRepart().toString())  + "<br/>"
                + ("FDSVAL".equalsIgnoreCase(programme.getFamille().getCode()) ? "Type de droit : " + programme.getTypeDroit().getValue() : "");
    }

    private String getInfoProgrammeDto(ProgrammeDto programmeDto) {
        return "Nom programme : " + programmeDto.getNom()+ "<br/>"
                + "Rion cible : " + getLibelleRion(programmeDto.getRionTheorique()) + "<br/>"
                + "Famille : " + getLibelleFamille(programmeDto.getFamille(), GlobalConstants.FR_LANG) + "<br/>"
                + "Type d'utilisation : " + getLibelleTypeUtil(programmeDto.getTypeUtilisation(), GlobalConstants.FR_LANG) + "<br/>"
                + "Date de début : " + simpleDateFormat.format(programmeDto.getDateDbtPrg()) + "<br/>"
                + "Date de fin : " + simpleDateFormat.format(programmeDto.getDateFinPrg()) + "<br/>"
                + "Territoire : " + getLibelleTerritoire(programmeDto.getCdeTer(), GlobalConstants.FR_LANG) + "<br/>"
                + "Mode de répartition : " + WordUtils.capitalize(programmeDto.getTypeRepart().toString()) +  "<br/>"
                + ("FDSVAL".equalsIgnoreCase(programmeDto.getFamille()) ? "Type de droit : " + programmeDto.getTypeDroit().getValue() : "");
    }

    private String getLibelleTerritoire(Integer code, String lang){
        SareftjLibter sareftjLibter = sareftjLibterDao.findByCodeAndLang(code, lang);
        String libelleTerritoire = String.valueOf(sareftjLibter.getCdePaysIso4N()) + " - " + sareftjLibter.getNomPays();
        return libelleTerritoire;
    }

    private String getLibelleRion(Integer rion){
        SareftrRion sareftrRion = sareftrRionDao.findOne(rion);
        String libelleRion = sareftrRion.getRion() + " - " + new SimpleDateFormat("MMMM yyyy").format(sareftrRion.getDatrglmt());
        return libelleRion;
    }

    private String getLibelleFamille(String code, String lang){
        SareftjLibfamiltyputil sareftjLibfamiltyputil = sareftjLibfamiltyputilDao.findByCodeAndLang(code, lang);
        return sareftjLibfamiltyputil.getLibelle();
    }

    private String getLibelleTypeUtil(String code, String lang){
        SareftjLibtyputil sareftjLibtyputil = sareftjLibtyputilDao.findByCodeAndLang(code , lang);
        return sareftjLibtyputil.getLibelle();

    }

}
