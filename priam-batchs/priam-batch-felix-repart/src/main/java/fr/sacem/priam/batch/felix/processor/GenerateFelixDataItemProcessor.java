package fr.sacem.priam.batch.felix.processor;

import fr.sacem.priam.batch.felix.domain.FelixData;
import fr.sacem.priam.batch.felix.validator.FelixDataValidator;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.dao.jpa.FichierFelixLogDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.FichierFelixLog;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class GenerateFelixDataItemProcessor implements ItemProcessor<FelixData, FelixData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenerateFelixDataItemProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";

    private ExecutionContext executionContext;

    public Integer lineNumber = 1;

    @Autowired
    FelixDataValidator validator;

    @Autowired
    FichierFelixDao fichierFelixDao;

    private JobExecution jobExecution;
    private StepExecution stepExecution;

    @Autowired
    private FichierFelixLogDao fichierFelixLogDao;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        this.executionContext = stepExecution.getExecutionContext();
        jobExecution = stepExecution.getJobExecution();
        this.stepExecution = stepExecution;
        jobExecution.getExecutionContext().put("IsError", "false");
    }

    @Override
    public FelixData process(final FelixData felixData) throws Exception {
        LOGGER.info(String.format("Read count = %d", stepExecution.getReadCount()));
        lineNumber = stepExecution.getReadCount() +  1 + 8;

        LOGGER.info(String.format("Line number = %d", lineNumber));

        BindingResult errors = new BeanPropertyBindingResult(felixData, "felixData-"+ lineNumber);
        validator.validate(felixData, errors);

        if (!errors.hasErrors()) {
            return felixData;
        }

        jobExecution.getExecutionContext().put("IsError", "true");
        String numProg = jobExecution.getJobParameters().getString("numProg");
        FichierFelix ff = fichierFelixDao.findByNumprog(numProg);
        String error = null;
        for(FieldError fe : errors.getFieldErrors()) {

            if (fe.getCode().startsWith("format.")) {
                error = String.format(MESSAGE_FORMAT, lineNumber, fe.getField(), fe.getRejectedValue());
            } else {
                error = String.format(MESSAGE_CHAMPS_OBLIGATOIRE, lineNumber, fe.getField());
            }
            FichierFelixLog felixLog = new FichierFelixLog();
            felixLog.setLog(error);
            felixLog.setDateCreation(new Date());
            fichierFelixLogDao.save(felixLog);
            ff.getLogs().add(felixLog);
        }
        fichierFelixDao.saveAndFlush(ff);



        return felixData;
    }

}
