package fr.sacem.priam.batch.fv.serviceimport.writer;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;
import fr.sacem.priam.model.domain.fv.LigneProgrammeFV;
import java.util.List;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class AyantDroitJdbcItemWriter extends JdbcBatchItemWriter<ExportCsvDto> implements ItemWriteListener<ExportCsvDto> {

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;

    public  AyantDroitJdbcItemWriter() {

    }


    @Override
    public void beforeWrite(final List<? extends ExportCsvDto> list) {

        list.forEach(dto ->  {
            final LigneProgrammeFV oeuvreByIde12 = ligneProgrammeFVDao.findOeuvreByIde12(Long.valueOf(dto.getIde12()), dto.getIdFichier());
            dto.setIdOeuvreFv(oeuvreByIde12.getId());
        });

    }

    @Override
    public void afterWrite(final List<? extends ExportCsvDto> list) {

    }

    @Override
    public void onWriteError(final Exception e, final List<? extends ExportCsvDto> list) {

    }
}
