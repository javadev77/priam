package fr.sacem.priam.batch.affectation.listener;

import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.common.TypeLog;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.JournalBatchDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.journal.JournalBuilder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * Created by benmerzoukah on 02/01/2018.
 */
public class JobDesaffectationListener extends JobExecutionListenerSupport {


    private static final Logger LOG = LoggerFactory.getLogger(JobDesaffectationListener.class);

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    FichierRepository fichierRepository;

    @Autowired
    private FichierBatchService fichierBatchService;

    @Autowired
    private FichierDao fichierDao;

    @Autowired
    JournalBatchDao journalBatchDao;

/*    @Autowired
    private ProgrammeViewDao programmeViewDao;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;*/

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    @Override
    public void beforeJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        programmeBatchDao.majStattutEligibilite(numProg, "EN_COURS_DESAFFECTATION");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        boolean isAllDesaffecte = Boolean.parseBoolean(jobExecution.getJobParameters().getString("isAllDesaffecte"));
        String userId = jobExecution.getJobParameters().getString("userId");

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

            programmeBatchDao.majStattutEligibilite(numProg, "FIN_DESAFFECTATION");

            fichierRepository.deleteFichierLinkForAntille(numProg);

            fichierBatchService.clearSelectedFichiers(numProg, "CHARGEMENT_OK");

            String user = jobExecution.getJobParameters().getString("username");

            programmeBatchDao.updateProgramme(numProg, user);

            if(isAllDesaffecte) {

                JournalBuilder journalBuilder = new JournalBuilder(numProg,null,userId);
                Journal journal = journalBuilder.addEvenement(TypeLog.ALL_DESAFFECTATION.getEvenement()).build();

                List<SituationAvant> situationAvantList = new ArrayList<>();

                List<Fichier> listfichierAllDesaffecte = getListFichiersById(jobExecution.getJobParameters().getString("listIdFichiersAllDesaffectes"));


                listfichierAllDesaffecte.forEach(fichier -> {
                    SituationAvant situationAvant = new SituationAvant();
                    situationAvant.setSituation(fichier.getNomFichier()+ " " + simpleDateFormat.format(fichier.getDateFinChargt()));
                    situationAvantList.add(situationAvant);
                });
                journal.setListSituationAvant(situationAvantList);
                Long idJournal = journalBatchDao.saveJournal(journal);
                journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
            }


        } else if(jobExecution.getStatus() == BatchStatus.FAILED) {
            programmeBatchDao.majStattutEligibilite(numProg, "ERREUR_DESAFFECTATION");
        }

        /*final ProgrammeDto payload = programmeViewDao.findByNumProg(numProg);
        simpMessagingTemplate.convertAndSend("/global-message/affectation", payload);*/

    }

    private List<Fichier> getListFichiersById(String listIdFichiers) {
        List<Fichier> result = new ArrayList<>();
        List<Long> listId = Stream.of(listIdFichiers.split(",")).map(Long::parseLong).collect(Collectors.toList());
        listId.forEach(id -> {
            result.add(fichierDao.findOne(id));
        });
        return result;
    }


    public void setFichierBatchService(FichierBatchService fichierBatchService) {
        this.fichierBatchService = fichierBatchService;
    }

    public FichierBatchService getFichierBatchService() {
        return fichierBatchService;
    }
}
