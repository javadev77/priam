package fr.sacem.priam.batch.felix.processor;

import fr.sacem.priam.batch.felix.domain.LignePreprepFV;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;

import static fr.sacem.priam.batch.felix.util.TypeRepartitionFVEnum.*;

public class TypeRepartProcessor implements ItemProcessor<LignePreprepFV, LignePreprepFV> {

    public static final String CDE_CISAC = "058";
    public static final String CDE_MOD_FAC = "FORFAI";
    public static final String PRINC = "PRINC";
    public static final String SANS = "SANS";
    public static final String CDE_UTIL = "XXX";

    private JobExecution jobExecution;

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.jobExecution = stepExecution.getJobExecution();
    }

    @Override
    public LignePreprepFV process(LignePreprepFV lignePreprepFV) throws Exception {
        String typeRepartFV = this.jobExecution.getJobParameters().getString("typeRepart");
        if(OEUVRE.getLibelle().equals(typeRepartFV)){
            lignePreprepFV.setTypRepart(OEUVRE.getCode());
        } else if (AYANT_DROIT.getLibelle().equals(typeRepartFV)) {
            lignePreprepFV.setTypRepart(AYANT_DROIT.getCode());
        } else if (OEUVRE_AD.getLibelle().equals(typeRepartFV)){
            lignePreprepFV.setTypRepart(OEUVRE_AD.getCode());
        }
        lignePreprepFV.setCdeCisac(CDE_CISAC);
        lignePreprepFV.setCdeModFac(CDE_MOD_FAC);
        lignePreprepFV.setCdeTypProg(PRINC);
        lignePreprepFV.setCdeCompl(SANS);
        lignePreprepFV.setCdeUtil(CDE_UTIL);

        return lignePreprepFV;
    }
}
