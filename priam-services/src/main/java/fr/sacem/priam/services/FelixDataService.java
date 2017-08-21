package fr.sacem.priam.services;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import fr.sacem.priam.common.util.csv.*;
import fr.sacem.priam.model.dao.jpa.LigneProgrammeDao;
import fr.sacem.priam.model.domain.dto.FelixData;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Arrays.asList;
import static org.apache.commons.io.IOUtils.LINE_SEPARATOR;

/**
 * Created by benmerzoukah on 18/08/2017.
 */
@Component
public class FelixDataService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ProgrammeService.class);
    
    @Autowired
    private LigneProgrammeDao ligneProgrammeDao;
    
    private final static CsvMapper csvMapper;
    private final static ObjectWriter writer;
    private final static Charset CHARSET;
    
    
    static {
        CHARSET = Charsets.toCharset(String.valueOf(""));
        csvMapper = new CsvMapper();
        csvMapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        SimpleModule module = new SimpleModule();
        module.addSerializer(Date.class, new DateSerializer("yyyyMMdd"));
        module.addDeserializer(Date.class, new DateDeserializer("yyyyMMdd"));
        module.addSerializer(Double.class, new NumberSerializer());
        module.addDeserializer(Double.class, new DoubleDeserializer());
        module.addSerializer(BigDecimal.class, new RepartBigDecimalSerializer());
        csvMapper.registerModule(module);
        
        CsvSchema schema = csvMapper.schemaFor(FelixData.class)
                               .withColumnSeparator(';')
                               .withoutQuoteChar();
        
        writer = csvMapper.writer(schema);
    }
    
    private OutputStream out;
    private File file;
    private long nbLine = 0;
    
    
    public void generateDonneesRepartition() {
    
    }
    
    private boolean writeFile() throws IOException {
        createFile();
        List<FelixData> data = new ArrayList<>();
        for (FelixData f : data) {
            writeLine(writer.writeValueAsString(f), false);
            nbLine++;
        }
        return finish(file, out, "");
    }
    
    private void createFile() throws IOException {
        
        LOGGER.debug("Création d'un fichier FELIX avec charset = " + CHARSET.displayName());
        file = File.createTempFile("inprogress", "tmp");
        out = new FileOutputStream(file);
        
        writeLines(head());
    }
    
    private List<String> head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return asList(
            "#--------------------------------------------------------------------------",
            "# Extraction à destination de FELIX pour les calculs de répartition",
            "#--------------------------------------------------------------------------",
            "# JJ/MM/AAAA HH:MM - Auteur - Objet",
            "#--------------------------------------------------------------------------",
            "# " + dateFormat.format(new Date()) + " - PRIAM - Creation",
            "#DEBUT=====================================================================",
            "#cdeCisac;cdeTer;rionEffet;cdeFamilTypUtil;numProg;keyLigPenel;cdeUtil;cdeTypUtil;cdeModFac;cdeTypProg;cdeCompl;libProg;compLibProg;datDbtProg;datFinProg;hrDbt;hrFin;cdeGreDif;cdeModDif;cdeTypIde12;ide12;datDif;hrDif;durDif;nbrDif;mt;ctna;paramCoefHor;durDifCtna;cdeLng;indDoubSsTit;tax");
    }
    
    private List<String> foot() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);
        return asList(
            "#FIN" + dateFormat.format(new Date()),
            "# Nbr lignes de donnees       = " + nbLine);
    }
    
    private void writeLine(String line, boolean withLineSeparator) throws IOException {
        if (line != null) {
            out.write(line.getBytes(CHARSET));
            if (withLineSeparator)
                out.write(LINE_SEPARATOR.getBytes(CHARSET));
        }
    }
    
    private void writeLines(Collection<String> lines) throws IOException {
        for (String line : lines) {
            writeLine(line, true);
        }
    }
    
    public boolean finish(File file, OutputStream out, String cisac) throws IOException {
        writeLines(foot());
        out.flush();
        IOUtils.closeQuietly(out);
        
        //String fileName = DOC_PREFIX + cisac + SPLITTER + DateUtil.format(new Date(), "yyyyMMddhhmmss") + DOC_SUFFIX;
        String fileName = null;
        boolean somethingToSend = this.nbLine > 0 ;
        
        if ( somethingToSend ) {
            LOGGER.debug("Envoi du fichier = " + file.getParent() + "/" + file.getName());
            //uploadFile(FELIX, file, fileName);
        } else {
            LOGGER.info("Annulation de l'envoi de "+file.getParent() + "/" + file.getName()+" - ne contient que du RRP sortant");
        }
        
        if (file.exists()) {
            FileUtils.forceDelete(file);
        }
        return somethingToSend;
    }
    
}

}

