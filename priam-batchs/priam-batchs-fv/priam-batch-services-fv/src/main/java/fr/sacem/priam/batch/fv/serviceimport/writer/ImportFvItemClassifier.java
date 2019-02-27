package fr.sacem.priam.batch.fv.serviceimport.writer;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.ClassifierSupport;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class ImportFvItemClassifier extends ClassifierSupport<ExportCsvDto, ItemWriter<ExportCsvDto>> {

    private ImportFvJdbcItemWriter ayantDroitItemWriter;
    private ImportFvComopsiteItemWriter compositItemWriter;

    public ImportFvItemClassifier(final ImportFvItemWriter<ExportCsvDto> defaultValue) {
        super(defaultValue);
    }

    @Override
    public ImportFvItemWriter<ExportCsvDto> classify(final ExportCsvDto exportCsvDto) {
        if(exportCsvDto.isNumpersExist()) {
            return this.ayantDroitItemWriter;
        } else {
            return compositItemWriter;
        }
    }

    @Autowired
    public void setAyantDroitItemWriter(final ImportFvJdbcItemWriter ayantDroitItemWriter) {
        this.ayantDroitItemWriter = ayantDroitItemWriter;
    }

    @Autowired
    public void setCompositItemWriter(final ImportFvComopsiteItemWriter compositItemWriter) {
        this.compositItemWriter = compositItemWriter;
    }
}
