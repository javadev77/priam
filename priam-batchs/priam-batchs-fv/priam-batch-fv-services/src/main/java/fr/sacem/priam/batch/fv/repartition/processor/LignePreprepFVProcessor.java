package fr.sacem.priam.batch.fv.repartition.processor;

import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFV;
import fr.sacem.priam.batch.fv.repartition.domain.LignePreprepFVCsv;
import fr.sacem.priam.batch.fv.repartition.validator.LignePreprepFVDataSpringValidator;
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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LignePreprepFVProcessor implements ItemProcessor<LignePreprepFV, LignePreprepFVCsv> {

    private static final Logger LOGGER = LoggerFactory.getLogger(LignePreprepFVProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";
    public static final String LIGNE_PREPREP_ERRORS = "ligne-preprep-errors";

    private ExecutionContext executionContext;

    public Integer lineNumber = 1;

    @Autowired
    LignePreprepFVDataSpringValidator validator;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getExecutionContext();
    }

    @Override
    public LignePreprepFVCsv process(final LignePreprepFV lignePreprepFV) throws Exception {

        List<String> errorList = (ArrayList<String>) executionContext.get(LIGNE_PREPREP_ERRORS);
        BindingResult errors = new BeanPropertyBindingResult(lignePreprepFV, "lignePreprepFV-"+ lineNumber);
        validator.validate(lignePreprepFV, errors);

        if (!errors.hasErrors()) {
            return mapper(lignePreprepFV);
        }
        for(FieldError fe : errors.getFieldErrors()) {

            if (fe.getCode().startsWith("format.")) {
                errorList.add(String.format(MESSAGE_FORMAT, lineNumber, fe.getField(), fe.getRejectedValue()));
            } else {
                errorList.add(String.format(MESSAGE_CHAMPS_OBLIGATOIRE, lineNumber, fe.getField()));
            }

        }

        lineNumber++;

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
