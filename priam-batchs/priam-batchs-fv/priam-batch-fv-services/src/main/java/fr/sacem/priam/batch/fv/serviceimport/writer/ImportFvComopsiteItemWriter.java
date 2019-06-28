package fr.sacem.priam.batch.fv.serviceimport.writer;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import java.util.Iterator;
import java.util.List;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 */
public class ImportFvComopsiteItemWriter implements ItemStream, ImportFvItemWriter<ExportCsvDto>, InitializingBean {
    private List<ImportFvItemWriter> delegates;
    private boolean ignoreItemStream = false;

    public ImportFvComopsiteItemWriter() {
    }

    public void setIgnoreItemStream(boolean ignoreItemStream) {
        this.ignoreItemStream = ignoreItemStream;
    }

    public void write(List<? extends ExportCsvDto> item) throws Exception {
        Iterator i$ = this.delegates.iterator();

        while(i$.hasNext()) {
            ImportFvItemWriter writer = (ImportFvItemWriter)i$.next();
            writer.write(item);
        }

    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.delegates, "The 'delegates' may not be null");
        Assert.notEmpty(this.delegates, "The 'delegates' may not be empty");
    }

    public void setDelegates(List<ImportFvItemWriter> delegates) {
        this.delegates = delegates;
    }

    public void close() throws ItemStreamException {
        Iterator i$ = this.delegates.iterator();

        while(i$.hasNext()) {
            ImportFvItemWriter writer = (ImportFvItemWriter)i$.next();
            if(!this.ignoreItemStream && writer instanceof ItemStream) {
                ((ItemStream)writer).close();
            }
        }

    }

    public void open(ExecutionContext executionContext) throws ItemStreamException {
        Iterator i$ = this.delegates.iterator();

        while(i$.hasNext()) {
            ImportFvItemWriter writer = (ImportFvItemWriter)i$.next();
            if(!this.ignoreItemStream && writer instanceof ItemStream) {
                ((ItemStream)writer).open(executionContext);
            }
        }

    }

    public void update(ExecutionContext executionContext) throws ItemStreamException {
        Iterator i$ = this.delegates.iterator();

        while(i$.hasNext()) {
            ImportFvItemWriter writer = (ImportFvItemWriter)i$.next();
            if(!this.ignoreItemStream && writer instanceof ItemStream) {
                ((ItemStream)writer).update(executionContext);
            }
        }

    }
}
