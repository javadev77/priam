package fr.sacem.priam.batch.felix.processor;

import fr.sacem.priam.batch.felix.domain.LignePreprepFV;
import fr.sacem.priam.batch.felix.domain.LignePreprepFVCsv;
import fr.sacem.priam.batch.felix.validator.LignePreprepFVDataSpringValidator;
import fr.sacem.priam.model.dao.jpa.FichierFelixDao;
import fr.sacem.priam.model.dao.jpa.FichierFelixLogDao;
import fr.sacem.priam.model.domain.FichierFelix;
import fr.sacem.priam.model.domain.FichierFelixLog;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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

public class LignePreprepFVProcessor implements ItemProcessor<LignePreprepFV, LignePreprepFVCsv> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LignePreprepFVProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";
    public static final String LIGNE_PREPREP_ERRORS = "ligne-preprep-errors";

    private ExecutionContext executionContext;

    public Integer lineNumber = 1;

    @Autowired
    LignePreprepFVDataSpringValidator validator;

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
    public LignePreprepFVCsv process(final LignePreprepFV lignePreprepFV) throws Exception {
        LOGGER.info(String.format("Read count = %d", stepExecution.getReadCount()));
        lineNumber = stepExecution.getReadCount() +  1 + 8;

        LOGGER.info(String.format("Line number = %d", lineNumber));

        BindingResult errors = new BeanPropertyBindingResult(lignePreprepFV, "lignePreprepFV-"+ lineNumber);
        validator.validate(lignePreprepFV, errors);

        if (!errors.hasErrors()) {
            return mapper(lignePreprepFV);
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



        return mapper(lignePreprepFV);
    }

    private LignePreprepFVCsv mapper(LignePreprepFV lignePreprepFV){
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        DecimalFormat decimalFormat = new DecimalFormat("##0.00", DecimalFormatSymbols.getInstance(Locale.FRANCE));
        DecimalFormat decimalFormatPoints = new DecimalFormat("##0.0000", DecimalFormatSymbols.getInstance(Locale.FRANCE));
        DecimalFormat decimalFormatCleAd = new DecimalFormat("##0.00000000", DecimalFormatSymbols.getInstance(Locale.FRANCE)) ;

        LignePreprepFVCsv lignePreprepFVCsv = new LignePreprepFVCsv();

        lignePreprepFVCsv.setTypRepart(lignePreprepFV.getTypRepart());
        lignePreprepFVCsv.setCdeCisac(lignePreprepFV.getCdeCisac());
        lignePreprepFVCsv.setCdeTer(String.valueOf(lignePreprepFV.getCdeTer()));
        lignePreprepFVCsv.setRionEffet(String.valueOf(lignePreprepFV.getRionEffet()));
        lignePreprepFVCsv.setCdeFamilTypUtil(lignePreprepFV.getCdeFamilTypUtil());
        lignePreprepFVCsv.setNumProg(String.valueOf(lignePreprepFV.getNumProg()));
        lignePreprepFVCsv.setId(String.valueOf(lignePreprepFV.getId()));
        lignePreprepFVCsv.setCdeUtil(lignePreprepFV.getCdeUtil());
        lignePreprepFVCsv.setCdeTypUtil(lignePreprepFV.getCdeTypUtil());
        lignePreprepFVCsv.setCdeModFac(lignePreprepFV.getCdeModFac());
        lignePreprepFVCsv.setCdeTypProg(lignePreprepFV.getCdeTypProg());
        lignePreprepFVCsv.setCdeCompl(lignePreprepFV.getCdeCompl());
        lignePreprepFVCsv.setLibProg(lignePreprepFV.getLibProg());
        if(lignePreprepFV.getDatDbtProg()!= null) lignePreprepFVCsv.setDatDbtProg(dateFormat.format(lignePreprepFV.getDatDbtProg()));
        if(lignePreprepFV.getDatFinProg()!= null) lignePreprepFVCsv.setDatFinProg(dateFormat.format(lignePreprepFV.getDatFinProg()));
        lignePreprepFVCsv.setIde12(String.valueOf(lignePreprepFV.getIde12()));
        lignePreprepFVCsv.setCdeTypIde12(lignePreprepFV.getCdeTypIde12());
        if(lignePreprepFV.getDatSitu()!= null) lignePreprepFVCsv.setDatSitu(dateFormat.format(lignePreprepFV.getDatSitu()));
        if(lignePreprepFV.getDatConslt()!= null) lignePreprepFVCsv.setDatConslt(dateFormat.format(lignePreprepFV.getDatConslt()));
        if(lignePreprepFV.getDurDif()!= null) lignePreprepFVCsv.setDurDif(String.valueOf(lignePreprepFV.getDurDif()));
        lignePreprepFVCsv.setNbrDif(String.valueOf(lignePreprepFV.getNbrDif()));
        lignePreprepFVCsv.setTypMt(lignePreprepFV.getTypMt());
        lignePreprepFVCsv.setMt(decimalFormat.format(lignePreprepFV.getMt()));
        lignePreprepFVCsv.setCdeTypDrtSacem(lignePreprepFV.getCdeTypDrtSacem());
        lignePreprepFVCsv.setCoadPayer(String.valueOf(lignePreprepFV.getCoadPayer()));
        lignePreprepFVCsv.setIdSteAd(String.valueOf(lignePreprepFV.getIdSteAd()));
        lignePreprepFVCsv.setRolAd(lignePreprepFV.getRolAd());
        lignePreprepFVCsv.setCleAd(decimalFormatCleAd.format(lignePreprepFV.getCleAd()));
        lignePreprepFVCsv.setCdeTypProtec(lignePreprepFV.getCdeTypProtec());
        lignePreprepFVCsv.setCoadOriEdtr(String.valueOf(lignePreprepFV.getCoadOriEdtr()));
        lignePreprepFVCsv.setIdSteOriEdtr(String.valueOf(lignePreprepFV.getIdSteOriEdtr()));
        lignePreprepFVCsv.setNumPers(String.valueOf(lignePreprepFV.getNumPers()));
        lignePreprepFVCsv.setNumCatal(String.valueOf(lignePreprepFV.getNumCatal()));
        lignePreprepFVCsv.setPoints(decimalFormatPoints.format(lignePreprepFV.getPoints()));

        return lignePreprepFVCsv;
    }
}
