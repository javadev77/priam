package fr.sacem.priam.batch.common.fv.writer;

import org.springframework.batch.item.file.FlatFileHeaderCallback;

import java.io.IOException;
import java.io.Writer;

/**
 * Created by benmerzoukah on 13/06/2018.
 */
public class StringHeaderWriter implements FlatFileHeaderCallback {

    private final String header;

    public StringHeaderWriter(String header) {
        this.header = header;
    }

    @Override
    public void writeHeader(Writer writer) throws IOException {
        writer.write(header);
    }
}