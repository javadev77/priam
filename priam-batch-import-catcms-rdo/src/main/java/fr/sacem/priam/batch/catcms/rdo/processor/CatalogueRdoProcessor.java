package fr.sacem.priam.batch.catcms.rdo.processor;

import fr.sacem.priam.batch.catcms.rdo.domain.CatalogueRdoCsv;
import fr.sacem.priam.batch.catcms.rdo.util.validator.CatalogueRdoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Date;
import java.util.Set;

/**
 * Created by benmerzoukah on 04/12/2017.
 */
public class CatalogueRdoProcessor implements ItemProcessor<CatalogueRdoCsv, CatalogueRdoCsv> {

    private static final Logger log = LoggerFactory.getLogger(CatalogueRdoProcessor.class);

    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n'a pas le bon format attendu";
    public static final String LIGNE_PROGRAMME_ERRORS = "ligne-programme-errors";

    private ExecutionContext executionContext;

    @Autowired
    CatalogueRdoValidator validator;

    @Override
    public CatalogueRdoCsv process(CatalogueRdoCsv catalogueRdoCsv) throws Exception {

        Set<String> errorSet = (Set<String>) executionContext.get(LIGNE_PROGRAMME_ERRORS);

        JobParameter parameter_nom_fichier = (JobParameter) executionContext.get("nomFichier");
        JobParameter parameter_id_fichier=(JobParameter) executionContext.get("idFichier");

        BindingResult errors = new BeanPropertyBindingResult(catalogueRdoCsv, "CatalogueRdoCsv-"+ catalogueRdoCsv.getLineNumber());
        /*validator.validate(catalogueRdoCsv, errors);*/

        if(catalogueRdoCsv.getPourcentageDP()==1){
            catalogueRdoCsv.setDateSortie(new Date());
            catalogueRdoCsv.setTypeSortie("Automatique");
        }

        if (!errors.hasErrors()) {
            return catalogueRdoCsv;
        }
        for(FieldError fe : errors.getFieldErrors()) {
            if(fe.getCode().startsWith("format.")){
                errorSet.add(String.format(MESSAGE_FORMAT, catalogueRdoCsv.getLineNumber(), fe.getField(), fe.getRejectedValue()));
            }
        }

        return catalogueRdoCsv;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {
        this.executionContext = stepExecution.getExecutionContext();
    }
}
