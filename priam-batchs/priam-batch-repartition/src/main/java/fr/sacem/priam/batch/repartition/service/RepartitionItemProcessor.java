package fr.sacem.priam.batch.repartition.service;

import fr.sacem.priam.batch.common.dao.ProgrammeBatchDao;
import fr.sacem.priam.batch.common.domain.Programme;
import fr.sacem.priam.batch.common.domain.Repartition;
import fr.sacem.priam.batch.common.util.valdiator.repartition.RepartitionSpringValidator;
import fr.sacem.priam.batch.repartition.fv.dao.ReferentielJdbcDao;
import fr.sacem.priam.batch.repartition.fv.domain.ReferentielParticipation;
import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.dao.jpa.JournalBatchDao;
import fr.sacem.priam.model.domain.Journal;
import fr.sacem.priam.model.domain.SituationApres;
import fr.sacem.priam.model.domain.SituationAvant;
import fr.sacem.priam.model.journal.JournalBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by fandis on 04/05/2017.
 */

public class RepartitionItemProcessor implements ItemProcessor<Repartition, Repartition> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepartitionItemProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";
    public static final String REPARTITION_ERRORS = "repartition-errors";
    public static final String FAMILLE_VALORISATION = "FDSVAL";
    public static final String TYPE_REPART_AYANT_DROIT = "AYANT_DROIT";

    public static final String VALIDATION ="Validation programme";
    public static final String REPARTITION ="Répartition";
    private static final String PRG_VALIDE = "Validé";
    private static final String PRG_MIS_EN_REPART = "Mis en répartition";
    private static final String PRG_REPARTI = "Réparti";


    private ExecutionContext executionContext;

    @Autowired
    private RepartitionSpringValidator validator;

    @Autowired
    private ReferentielJdbcDao referentielJdbcDao;

    @Autowired
    ProgrammeBatchDao programmeBatchDao;

    @Autowired
    private JournalBatchDao journalBatchDao;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getJobExecution().getExecutionContext();

    }

    @Override
    public Repartition process(Repartition repartition) throws Exception {

        Set<String> errorSet = (Set<String>) executionContext.get(REPARTITION_ERRORS);

//        executionContext.put("numeroProgramme", repartition.getNumeroProgramme());

        BindingResult errors = new BeanPropertyBindingResult(repartition, "repartition-"+ repartition.getLineNumber());
        validator.validate(repartition, errors);
        //gestion des status de programme venant de FELIX
        if(repartition.getStatus()!=null && !repartition.getStatus().equals("")){
            Optional<String> optionalNumProg = Optional.ofNullable(repartition.getNumeroProgramme());
            if(repartition.getStatus().equalsIgnoreCase("OK")) {
                repartition.setStatus("REPARTI");
                if(optionalNumProg.isPresent()){
                    Programme programme = programmeBatchDao.findByNumProg(optionalNumProg.get());
                    if(FAMILLE_VALORISATION.equals(TypeUtilisationEnum.getValue(programme.getTypeUtilisation()).getCodeFamille())
                            && !TYPE_REPART_AYANT_DROIT.equals(programme.getTypeRepart())){
                        updateReferentiel(repartition.getNumeroProgramme());
                    }
                    journaliserRetourFelix(optionalNumProg.get(), REPARTITION);
                }
            }
            else if(repartition.getStatus().equalsIgnoreCase("KO")){
                repartition.setStatus("VALIDE");
                journaliserRetourFelix(optionalNumProg.get(), VALIDATION);
            }
        }
        if (!errors.hasErrors()) {
            return repartition;
        }
        for(FieldError fe : errors.getFieldErrors()) {

		if(fe.getCode().startsWith("format.")){
		    errorSet.add(String.format(MESSAGE_FORMAT, repartition.getLineNumber(), fe.getField(), fe.getRejectedValue()));
		} else {
		    errorSet.add(String.format(MESSAGE_CHAMPS_OBLIGATOIRE, repartition.getLineNumber(), fe.getField()));
		}

	  }
    
        return repartition;
    }

    private void journaliserRetourFelix(String numProg, String evenement) {
        JournalBuilder journalBuilder = new JournalBuilder(numProg,null, null);
        Journal journal = journalBuilder.addEvenement(evenement).build();
        SituationAvant situationAvant = new SituationAvant();
        situationAvant.setSituation(PRG_MIS_EN_REPART);
        journal.getListSituationAvant().add(situationAvant);
        if(REPARTITION.equals(evenement)){
            SituationApres situationApres = new SituationApres();
            situationApres.setSituation(PRG_REPARTI);
            journal.getListSituationApres().add(situationApres);
        } else {
            SituationApres situationApres = new SituationApres();
            situationApres.setSituation(PRG_VALIDE);
            journal.getListSituationApres().add(situationApres);
        }
        long idJournal = journalBatchDao.saveJournal(journal);
        journalBatchDao.saveSituationAvantJournal(journal.getListSituationAvant(), idJournal);
        journalBatchDao.saveSituationApresJournal(journal.getListSituationApres(), idJournal);
    }

    private void updateReferentiel(String numProg) {
        try {
            List<ReferentielParticipation> ide12RepartisByNumProg = referentielJdbcDao.getIde12RepartisByNumProg(numProg);
            ide12RepartisByNumProg.stream().forEach(ref -> {
                List<ReferentielParticipation> listReferentiel = referentielJdbcDao.getReferentielByIde12AndCdeTypUtil(ref.getIde12(),
                        ref.getCdeTypUtil());
                if(listReferentiel.isEmpty()){
                    referentielJdbcDao.enregistrerReferentielParticipation(ref);
                } else {
                    if(ref.getRionPaiementMax() > listReferentiel.stream().findFirst().get().getRionPaiementMax()){
                        referentielJdbcDao.updateReferentielParticipation(ref);
                    }
                }
            });
        } catch (Exception e) {
            //todo : stopper le batch si la maj du référentiel se passe mal ?
            LOGGER.error(String.format("Erreur lors de la mise à jour du référentiel participant pour les oeuvres du programme %s !!", numProg), e);
        }
    }
    
    
}