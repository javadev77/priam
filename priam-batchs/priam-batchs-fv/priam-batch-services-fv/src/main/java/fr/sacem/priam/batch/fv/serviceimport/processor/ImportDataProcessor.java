package fr.sacem.priam.batch.fv.serviceimport.processor;

import fr.sacem.priam.batch.common.dao.LigneProgrammeFVDao;
import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class ImportDataProcessor implements ItemProcessor<ExportCsvDto, ExportCsvDto> {
    private JobExecution jobExecution;

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;


    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        jobExecution = stepExecution.getJobExecution();

    }

    @Override
    public ExportCsvDto process(final ExportCsvDto exportCsvDto) throws Exception {
        Long ide12 = Long.valueOf(exportCsvDto.getIde12());
        LigneProgrammeFV oeuvreByIde12 = ligneProgrammeFVDao.findOeuvreByIde12(ide12, exportCsvDto.getIdFichier());

        return null;
    }
}
