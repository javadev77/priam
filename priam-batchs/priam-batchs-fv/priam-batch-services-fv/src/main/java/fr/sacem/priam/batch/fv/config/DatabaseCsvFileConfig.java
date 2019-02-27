package fr.sacem.priam.batch.fv.config;

import fr.sacem.priam.batch.common.domain.Admap;
import fr.sacem.priam.batch.common.fv.writer.CsvFileItemWriter;
import fr.sacem.priam.batch.common.fv.writer.StringHeaderWriter;
import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.model.dao.jpa.fv.LigneProgrammeFVDao;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.FileSystemResource;

@Configuration
@ComponentScan(basePackages = {"fr.sacem.priam.batch.fv", "fr.sacem.priam.model"})
public class DatabaseCsvFileConfig {

    @Autowired
    LigneProgrammeFVDao ligneProgrammeFVDao;
    //LigneProgrammeFVDao ligneProgrammeFVDao;

    private String head() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n" +
                "# Extraction"+
                "# en provenance de PRIAM\n"+
                "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
                "# JJ/MM/AAAA HH:MM - Auteur - Objet\n"+
                "#-------------------------------------------------------------------------------------------------------------------;;;;;;;;;;;;;;;;;;;\n"+
                "# " + dateFormat.format(new Date()) + " - PRIAM - Creation\n"+
                "#DEBUT\n"+
                "#cdeFamilTypUtil;cdeTypUtil;numProg;rionEffet;ide12;cdeTypIde12;ide12RepCoad;cdeTypIde12RepCoad;datsitu;datconslt;coad;numPers;numCatal;idSteAd;rolAd;typeDroit;cleAd;cdeTypProtect;coadOriEdtr;idSteOriEdtr;nomProgramme;tax;durDif;nbrDif;typMt;mt;genreOeuvre;titreOeuvre;dureeDeposee;taxOri;labelValo;paysOri;indicRepart;nom;prenom;indicSacem;sousRole;anneeNaissance;anneeDeces;indicDrtPercus";
    }

    private String foot(Long lignes) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.FRANCE);
        return
                "#FIN;;\n" +
                        "#" + dateFormat.format(new Date())  + "\n" +
                        "# Nbr lignes de donnees       = " + lignes;
    }

    @Bean(name = "csvExportFileWriter")
    @Scope(value = "step")
    CsvFileItemWriter<ExportCsvDto> databaseCsvItemWriter(@Value("#{jobParameters['numProg']}")  Long numProg, @Autowired
            Admap admap) {
        CsvFileItemWriter<ExportCsvDto> csvFileWriter = new CsvFileItemWriter<>();
        StringHeaderWriter headerWriter = new StringHeaderWriter(head());

        csvFileWriter.setFooterCallback(writer -> writer.write(foot(countNbLignes(numProg))));
        csvFileWriter.setHeaderCallback(headerWriter);

        String fileName = "FF_PRIAM_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + "_" + numProg + ".csv";
        fileName = admap.getOutputFile() + fileName;

        csvFileWriter.setResource(new FileSystemResource(fileName));

        LineAggregator<ExportCsvDto> lineAggregator = createLineAggregator();
        csvFileWriter.setLineAggregator(lineAggregator);

        return csvFileWriter;
    }

    public DelimitedLineAggregator<ExportCsvDto> createLineAggregator() {
        DelimitedLineAggregator<ExportCsvDto> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(";");


        FieldExtractor<ExportCsvDto> fieldExtractor = createExtractor();
        lineAggregator.setFieldExtractor(fieldExtractor);

        return lineAggregator;
    }


    public FieldExtractor<ExportCsvDto> createExtractor() {
        BeanWrapperFieldExtractor<ExportCsvDto> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"cdeFamilTypUtil", "cdeTypUtil", "numProg", "rionEffet", "ide12", "cdeTypIde12", "ide12RepCoad", "cdeTypIde12RepCoad", "datsitu", "datconslt",
                "coad", "numPers", "numCatal", "idSteAd", "rolAd", "typeDroit", "cleAd", "cdeTypProtect", "coadOriEdtr", "idSteOriEdtr",
                "nomProgramme", "tax", "durDif", "nbrDif", "typMt", "mt", "genreOeuvre", "titreOeuvre", "dureeDeposee", "taxOri",
                "labelValo", "paysOri", "indicRepart", "nom", "prenom", "indicSacem", "sousRole", "anneeNaissance", "anneeDeces", "indicDrtPercus"});
        return extractor;
    }

    public Long countNbLignes(Long numProg) {
        return ligneProgrammeFVDao.countNbLignesForExport(numProg);
    }

}
