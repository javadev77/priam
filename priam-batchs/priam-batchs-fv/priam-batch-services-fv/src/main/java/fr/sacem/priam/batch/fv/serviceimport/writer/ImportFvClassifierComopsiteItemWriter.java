/*
package fr.sacem.priam.batch.fv.serviceimport.writer;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.batch.item.ItemWriter;

*/
/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 *//*

public class ImportFvClassifierComopsiteItemWriter implements ItemWriter<ExportCsvDto> {
    private ImportFvItemClassifier classifier = new ImportFvItemClassifier( null);

    public ImportFvClassifierComopsiteItemWriter() {
    }

    public void setClassifier(final ImportFvItemClassifier classifier) {
        this.classifier = classifier;
    }

    public void write(List<? extends ExportCsvDto> items) throws Exception {
        Map<ItemWriter<? super ExportCsvDto>, List<ExportCsvDto>> map = new LinkedHashMap();

        Iterator i$;
        Object item;
        ItemWriter key;
        for (i$ = items.iterator(); i$.hasNext(); ((List) map.get(key)).add(item)) {
            item = i$.next();
            key = this.classifier.classify((ExportCsvDto) item);
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList());
            }
        }

        i$ = map.keySet().iterator();

        while (i$.hasNext()) {
            ItemWriter<ExportCsvDto> writer = (ItemWriter) i$.next();
            writer.write((List) map.get(writer));
        }

    }
}
*/
