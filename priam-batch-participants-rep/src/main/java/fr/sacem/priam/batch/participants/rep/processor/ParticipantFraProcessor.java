package fr.sacem.priam.batch.participants.rep.processor;

import fr.sacem.priam.batch.participants.rep.domain.Participant;
import fr.sacem.priam.batch.participants.rep.domain.StatutRoleParticipant;
import fr.sacem.priam.common.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

public class ParticipantFraProcessor implements ItemProcessor<Participant, Participant> {

    private static final Logger log = LoggerFactory.getLogger(ParticipantFraProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";
    public static final String PARTICIPANT_ERRORS = "repartition-errors";

    private ExecutionContext executionContext;

    @Value("#{jobParameters['typeCMS']}")
    private String typeCMS;

    @Override
    public Participant process(Participant participant) throws Exception {
//        participant.setTypeCMS("FR");

        if(typeCMS.equals(FileUtils.CATALOGUE_TYPE_CMS_ANF)){
            participant.setTypeCMS(typeCMS);
        } else {
            participant.setTypeCMS(FileUtils.CATALOGUE_TYPE_CMS_FR);
        }

        if(participant.getRolPart().equals(StatutRoleParticipant.getValue("ROLE_A").getCodeStatutRole())
                || participant.getRolPart().equals(StatutRoleParticipant.getValue("ROLE_AR").getCodeStatutRole())
                || participant.getRolPart().equals(StatutRoleParticipant.getValue("ROLE_C").getCodeStatutRole())
                || participant.getRolPart().equals(StatutRoleParticipant.getValue("ROLE_CA").getCodeStatutRole())
                || participant.getRolPart().equals(StatutRoleParticipant.getValue("ROLE_SA").getCodeStatutRole())){

                if(participant.getStatut() < 0){
                    participant.setStatut(0);
                } else {
                    participant.setStatut(1);
                }

            log.info(
                String.format("IDE12=[%s], Participant=[%s], Role=[%s], Statut=[%s]", participant.getIde12(), participant.getNomPart(), participant.getRolPart(), participant.getStatut())
            );


            return participant;
        }


        return null;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getExecutionContext();
    }

    public void setTypeCMS(String typeCMS) {
        this.typeCMS = typeCMS;
    }
}
