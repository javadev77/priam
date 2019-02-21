package fr.sacem.priam.batch.fv.affectation.listener;

import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.batch.common.util.UtilFile;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.services.FichierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JobAffectationFVListener extends JobExecutionListenerSupport {
    public static final String ERREUR_ELIGIBILITE = "ERREUR_ELIGIBILITE";
    public static final String CHARGEMENT_OK = "CHARGEMENT_OK";
    private ExecutionContext executionContext;

    @Autowired
    private FichierBatchService fichierBatchService;

    private UtilFile utilFile;
    private static final Logger LOG = LoggerFactory.getLogger(JobAffectationFVListener.class);

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    FichierService fichierService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Autowired
    public JobAffectationFVListener() {

        utilFile = new UtilFile();
    }

    @Override
    public void beforeJob(JobExecution jobExecution) {

        String numProg = jobExecution.getJobParameters().getString("numProg");
        String[] fichiersAffectes = jobExecution.getJobParameters().getString("fichiersAffectes").split(",");
        List<Long> fichiersAffectesIds = Stream.of(fichiersAffectes).map(Long::parseLong).collect(Collectors.toList());
        List<Fichier> listFichiersByIds = fichierService.findListFichiersByIds(fichiersAffectesIds);
        String userId = jobExecution.getJobParameters().getString("userId");
        fichierService.majFichiersAffectesAuProgramme(numProg, listFichiersByIds, userId);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

        String numProg = jobExecution.getJobParameters().getString("numProg");
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {


            String userId = jobExecution.getJobParameters().getString("userId");
            String listNomFichier = jobExecution.getJobParameters().getString("listNomFichier");


            programmeBatchDao.majStattutEligibilite(numProg, "FIN_ELIGIBILITE");
            programmeBatchDao.majStattutProgramme(numProg, "AFFECTE");


//            List<SituationAvant> situationAvantList = new ArrayList<>();
//            List<SituationApres> situationApresList = new ArrayList<>();
//
//            if(!listNomFichier.isEmpty()) {
//                List<String> listNomFichierAvantAffecte = Arrays.asList(listNomFichier.split("\\s*,\\s*"));
//                listNomFichierAvantAffecte.forEach(NomFichierAvantAffecte -> {
//                    SituationAvant situationAvant = new SituationAvant();
//                    situationAvant.setSituation(NomFichierAvantAffecte);
//                    situationAvantList.add(situationAvant);
//                });
//            }
//
//            List<Fichier> listFichierAffecte = fichierDao.findFichiersByIdProgramme(numProg, Status.AFFECTE);
//            listFichierAffecte.forEach(fichierAffecte ->{
//                SituationApres situationApres = new SituationApres();
//                situationApres.setSituation(fichierAffecte.getNomFichier() + " " + simpleDateFormat.format(fichierAffecte.getDateFinChargt()));
//                situationApresList.add(situationApres);
//            });
//
//            JournalBuilder journalBuilder = new JournalBuilder(numProg,null,userId);
//            Journal journal = journalBuilder.addEvenement(TypeLog.AFFECTATION_DESAFFECTATION.getEvenement()).build();
//            journal.setListSituationAvant(situationAvantList);
//            journal.setListSituationApres(situationApresList);
//
//            Long idJournal = journalBatchDao.saveJournal(journal);
//            journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
//            journalBatchDao.saveSituationApresJournal(journal.getListSituationApres(), idJournal);
        } else {

            programmeBatchDao.majStattutEligibilite(numProg, ERREUR_ELIGIBILITE);
            fichierBatchService.clearSelectedFichiers(numProg, CHARGEMENT_OK);
        }
    }

    public FichierBatchService getFichierBatchService() {
        return fichierBatchService;
    }

    public void setFichierBatchService(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }
}

