package fr.sacem.priam.batch.fv.serviceimport.processor;

import fr.sacem.priam.batch.common.dao.FichierRepository;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.model.dao.jpa.fv.ImportProgrammeFVDao;
import fr.sacem.priam.model.domain.Fichier;
import fr.sacem.priam.model.domain.fv.ImportProgrammeFV;
import fr.sacem.priam.services.FichierService;
import java.util.HashSet;
import java.util.Set;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class ImportFvItemProcessor implements ItemProcessor<ExportCsvDto, ExportCsvDto> {
    public static final String MESSAGE_FORMAT = "Ligne '%s': Le champ \"%s\" avec la valeur \"%s\" n''a pas le bon format attendu";
    public static final String MESSAGE_CHAMPS_OBLIGATOIRE = "Ligne '%s': Le champ %s est obligatoire et non renseigné";

    private FichierService fichierService;
    private FichierRepository fichierRepository;
    private JobExecution jobExecution;


    @Autowired
    private ImportFvValidator validator;

    @Autowired
    ImportProgrammeFVDao importProgrammeFVDao;


    @Autowired
    public ImportFvItemProcessor(FichierService fichierService, @Qualifier("fichierRepositoryFV") FichierRepository fichierRepository) {
        this.fichierService = fichierService;
        this.fichierRepository = fichierRepository;
    }

    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        jobExecution = stepExecution.getJobExecution();
        jobExecution.getExecutionContext().put("IsError", "false");

    }

    @Override
    public ExportCsvDto process(final ExportCsvDto exportCsvDto) throws Exception {

        //Step 1 : validate les donnees
        BindingResult errors = new BeanPropertyBindingResult(exportCsvDto, "exportCsvDto-"+ exportCsvDto.getLineNumber());
        validator.validate(exportCsvDto, errors);

        if (!errors.hasErrors()) {
            //Step 2 : Stocker les donnes
            String numProg = jobExecution.getJobParameters().getString("numProg");
            Fichier fichierLink = fichierService.getOrCreateFichierLink(numProg);

            exportCsvDto.setIdFichier(fichierLink.getId());

            return exportCsvDto;
        }

        Set<String> errorSet = new HashSet<>();
        jobExecution.getExecutionContext().put("IsError", "true");
        for(FieldError fe : errors.getFieldErrors()) {

            if (fe.getCode().startsWith("format.")) {
                errorSet.add(String.format(MESSAGE_FORMAT, exportCsvDto.getLineNumber(), fe.getField(), fe.getRejectedValue()));
            } else {
                errorSet.add(String.format(MESSAGE_CHAMPS_OBLIGATOIRE, exportCsvDto.getLineNumber(), fe.getField()));
            }
        }

        String numProg = jobExecution.getJobParameters().getString("numProg");
        ImportProgrammeFV fichierImporte = importProgrammeFVDao.getFichierImporte(numProg);

        fichierRepository.enregistrerLog(fichierImporte.getId(), errorSet);

        return null;
    }
}
