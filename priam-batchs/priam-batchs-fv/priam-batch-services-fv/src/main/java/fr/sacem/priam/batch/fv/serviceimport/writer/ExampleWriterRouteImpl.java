package fr.sacem.priam.batch.fv.serviceimport.writer;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import org.springframework.batch.support.annotation.Classifier;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class ExampleWriterRouteImpl {

    @Classifier
    public String classify(ExportCsvDto exportCsvDto) {

        if (!exportCsvDto.isImportAD()) {
            if (!exportCsvDto.isNumpersExist()) {
                if (!exportCsvDto.isOeuvreExist()) {
                    return "O+AD+PERS";
                } else {
                    return "AD+PERS";
                }
            } else if (!exportCsvDto.isOeuvreExist()) {
                return "O+AD";
            } else {
                return "AD";
            }

        } else if (!exportCsvDto.isNumpersExist()) {
            return "FULL_AD+PERS";
        } else {
            return "FULL_AD";
        }

    }

}