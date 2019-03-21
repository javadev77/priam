package fr.sacem.priam.batch.fv.affectation.listener;

import fr.sacem.priam.batch.common.dao.LigneProgrammeBatchDao;
import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.domain.Programme;
import fr.sacem.priam.batch.common.service.importPenef.FichierBatchService;
import fr.sacem.priam.model.dao.jpa.FichierDao;
import fr.sacem.priam.model.dao.jpa.fv.AyantDroitFVDao;
import fr.sacem.priam.model.dao.jpa.fv.ExportProgrammeFVDao;
import fr.sacem.priam.model.dao.jpa.fv.ImportDataBatchFVJdbcDao;
import fr.sacem.priam.model.dao.jpa.fv.ImportProgrammeFVDao;
import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.Status;
import fr.sacem.priam.model.domain.dto.FileDto;
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
import org.springframework.stereotype.Component;

/**
 * Created by benmerzoukah on 02/01/2018.
 */
@Component
public class JobDesaffectationFvListener extends JobExecutionListenerSupport {


    private static final Logger LOG = LoggerFactory.getLogger(JobDesaffectationFvListener.class);

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    private FichierBatchService fichierBatchService;

    @Autowired
    private FichierDao fichierDao;

    @Autowired
    private AyantDroitFVDao ayantDroitFVDao;

    @Autowired
    LigneProgrammeBatchDao ligneProgrammeBatchDao;

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    @Autowired
    ImportDataBatchFVJdbcDao importDataBatchFVJdbcDao;

    @Autowired
    ExportProgrammeFVDao exportProgrammeFVDao;

    @Autowired
    ImportProgrammeFVDao importProgrammeFVDao;

    private static final String TYPE_REPART_OEUVRE = "OEUVRE";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");


    @Override
    public void beforeJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        programmeBatchDao.majStattutEligibilite(numProg, "EN_COURS_DESAFFECTATION");

        List<FileDto> files = fichierDao.findFichiersAffecteByIdProgramme(numProg, Status.AFFECTE);
        files.forEach(f -> ligneProgrammeBatchDao.deleteDonneesLigneFV(f.getId()));

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String numProg = jobExecution.getJobParameters().getString("numProg");
        boolean isAllDesaffecte = Boolean.parseBoolean(jobExecution.getJobParameters().getString("isAllDesaffecte"));
        boolean isFichiersAffectesVide = Boolean.parseBoolean(jobExecution.getJobParameters().getString("isFichiersAffectesVide"));

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {

            programmeBatchDao.majStattutEligibilite(numProg, "FIN_DESAFFECTATION");

          //  fichierRepository.deleteFichierLinkForAntille(numProg);
//            fichierDao.updateStatutEnrichissementFichiersAffectes(numProg);
            String user = jobExecution.getJobParameters().getString("username");
            if(!isAllDesaffecte && isFichiersAffectesVide) {
                fichierBatchService.clearSelectedFichiers(numProg, "CHARGEMENT_OK");
                programmeBatchDao.updateProgramme(numProg, user);
            }

            if(isAllDesaffecte) {
                programmeBatchDao.updateProgramme(numProg, user);

                Programme programme = programmeBatchDao.findByNumProg(numProg);
                if(!TYPE_REPART_OEUVRE.equals(programme.getTypeRepart())){
                    ayantDroitFVDao.deleteByNumProg(numProg);
                    Fichier fichierLink = fichierDao.findFichierLink(numProg);
                    if(fichierLink !=null) {
                        importDataBatchFVJdbcDao.deleteImportProgrammeByIdFichier(fichierLink.getId());
                        ligneProgrammeFVDao.deleteAllByFichierId(fichierLink.getId());
                    }

                    exportProgrammeFVDao.deleteByNumProg(numProg);
                    importProgrammeFVDao.deleteByNumProg(numProg);
                }
                fichierDao.updateStatutEnrichissementFichiersAffectes(numProg);


//                String userId = jobExecution.getJobParameters().getString("userId");
//                JournalBuilder journalBuilder = new JournalBuilder(numProg,null,userId);
//                Journal journal = journalBuilder.addEvenement(TypeLog.ALL_DESAFFECTATION.getEvenement()).build();
//
//                List<SituationAvant> situationAvantList = new ArrayList<>();
//
//                List<Fichier> listfichierAllDesaffecte = getListFichiersById(jobExecution.getJobParameters().getString("listIdFichiersAllDesaffectes"));
//
//
//                listfichierAllDesaffecte.forEach(fichier -> {
//                    SituationAvant situationAvant = new SituationAvant();
//                    situationAvant.setSituation(fichier.getNomFichier()+ " " + simpleDateFormat.format(fichier.getDateFinChargt()));
//                    situationAvantList.add(situationAvant);
//                });
//                journal.setListSituationAvant(situationAvantList);
//                Long idJournal = journalBatchDao.saveJournal(journal);
//                journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
            }


        } else if(jobExecution.getStatus() == BatchStatus.FAILED) {
            programmeBatchDao.majStattutEligibilite(numProg, "ERREUR_DESAFFECTATION");
        }
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
