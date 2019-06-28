/*
package fr.sacem.priam.batch.fv.serviceimport.writer;

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

*/
/**
 * Created with IntelliJ IDEA.
 * @author Yegor Bugayenko (yegor@tpc2.com)
 * @version $Id$
 * @since 1.0
 *//*

@Configuration
public class WriterConfig {

    private DataSource dataSource;

    public WriterConfig(@Autowired DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Bean(name = "classifierCompositeItemWriter")
    @Scope("step")
    public ImportFvClassifierComopsiteItemWriter classifierCompositeItemWriter() {

        ImportFvClassifierComopsiteItemWriter classifierCompositeItemWriter = new ImportFvClassifierComopsiteItemWriter();
        ImportFvItemClassifier classifier = new ImportFvItemClassifier(null);

    //    classifier.setAyantDroitItemWriter(ayantDroitItemWriter());
        classifier.setCompositItemWriter(compositItemWriter());

        classifierCompositeItemWriter.setClassifier(classifier);

        return classifierCompositeItemWriter;
    }

    @Bean
    @Scope("step")
    public ImportFvJdbcItemWriter ayantDroitItemWriter() {
        ImportFvJdbcItemWriter writer = new ImportFvJdbcItemWriter();
        writer.setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO PRIAM_AYANT_DROIT (IDE12REPCOAD, CDETYPIDE12REPCOAD, ROLAD, COAD, IDSTEAD, CLEAD, CDETYPPROTEC, COADORIEDTR, IDSTEORIEDTR, NUMCATAL, NUMPERS) " +
            "VALUES (:ide12RepCoad, :cdeTypIde12RepCoad, :rolad, :coad, :idStead, :clead, :cdeTypProtec, :coadori, :idSteOri, :numCatal, :numPers) ");

        writer.setDataSource(dataSource);

        return writer;
    }

    @Bean
    @Scope("step")
    public ImportFvComopsiteItemWriter compositItemWriter() {
        List<ImportFvItemWriter> writers = new ArrayList<>(2);
        writers.add(ayantDroitPersWriter());
        writers.add(ayantDroitItemWriter());

        ImportFvComopsiteItemWriter itemWriter  = new ImportFvComopsiteItemWriter();
        itemWriter.setDelegates(writers);

        return itemWriter;
    }

    @Bean
    @Scope("step")
    public ImportFvJdbcItemWriter ayantDroitPersWriter() {
        ImportFvJdbcItemWriter writer = new ImportFvJdbcItemWriter();
        writer.setItemSqlParameterSourceProvider(
            new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO PRIAM_AYANT_DROIT_PERS (NUMPERS) VALUES (:numPers) ");

        writer.setDataSource(dataSource);

        return writer;
    }

}
*/
