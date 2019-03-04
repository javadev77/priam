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
        String type =  exportCsvDto.isNumpersExist() && exportCsvDto.isOeuvreExist() ?
                                "ayantDroit" :
                                 !exportCsvDto.isOeuvreExist() ? "createOeuvre" : "compisteAll";
        return type;
    }

}