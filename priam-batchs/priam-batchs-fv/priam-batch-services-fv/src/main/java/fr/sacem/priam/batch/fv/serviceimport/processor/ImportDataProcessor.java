package fr.sacem.priam.batch.fv.serviceimport.processor;

import fr.sacem.priam.batch.common.dao.AyanrtDroitPersDao;
import fr.sacem.priam.batch.common.dao.AyantDroitDao;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
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

    @Autowired
    AyanrtDroitPersDao ayanrtDroitPersDao;

    @Autowired
    AyantDroitDao ayantDroitDao;



    @BeforeStep
    public void beforeStep(StepExecution stepExecution) {

        jobExecution = stepExecution.getJobExecution();

    }

    @Override
    public ExportCsvDto process(final ExportCsvDto exportCsvDto) throws Exception {
        boolean numpersExist = ayanrtDroitPersDao.isNumpersExist(String.valueOf(exportCsvDto.getNumPers()));
        exportCsvDto.setNumpersExist(numpersExist);

        boolean ayantDroitExist = ayantDroitDao.isAyantDroitExist(exportCsvDto.getCoad());
        exportCsvDto.setAyantDroitExist(ayantDroitExist);

        Long ide12 = Long.valueOf(exportCsvDto.getIde12());
        LigneProgrammeFV ligneProgrammeFV = ligneProgrammeFVDao.findOeuvreByIde12(ide12, exportCsvDto.getIdFichier());

        exportCsvDto.setOeuvreExist(ligneProgrammeFV != null);
        if(exportCsvDto.isOeuvreExist()) {
            exportCsvDto.setIdOeuvreFv(ligneProgrammeFV.getId());
        }



        return exportCsvDto;
    }
}
