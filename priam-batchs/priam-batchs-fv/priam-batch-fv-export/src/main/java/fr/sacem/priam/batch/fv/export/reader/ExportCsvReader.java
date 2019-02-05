package fr.sacem.priam.batch.fv.export.reader;

import fr.sacem.priam.batch.fv.export.domain.ExportCsvDto;
import fr.sacem.priam.batch.fv.export.mapper.ExportCsvMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@StepScope
public class ExportCsvReader extends JdbcPagingItemReader<ExportCsvDto> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExportCsvReader.class);

    public ExportCsvReader(@Value("#{jobParameters['numProg']}") Long numProg, @Autowired DataSource dataSource) {

        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("SELECT FV.cdeFamilTypUtil, FV.cdeTypUtil, FV.numProg, FV.rionEffet, FV.ide12, FV.cdeTypIde12, PAD.IDE12REPCOAD, PAD.CDETYPIDE12REPCOAD, PAD.COAD," +
                "PAD.NUMPERS, PAD.NUMCATAL, PAD.IDSTEAD, PAD.ROLAD, PAD.CLEAD, PAD.CDETYPPROTEC, PAD.COADORIEDTR, PAD.IDSTEORIEDTR, FV.tax, FV.durDif, FV.nbrDif," +
                "FV.typMt, FV.mt, FV.genreOeuvre, FV.titreOeuvre, FV.dureeDeposee, FV.taxOri, FV.paysOri, FV.indicRepart, PP.NOM, PERS.NOM, PERS.PRENOM, PERS.INDICSACEM," +
                "PERS.SOUS_ROLE, PERS.ANNEE_NAISSANCE, PERS.ANNEE_DECES, PERS.INDICDRTPERCUS");
        sqlPagingQueryProviderFactoryBean.setFromClause("PRIAM_LIGNE_PROGRAMME_FV FV " +
                "INNER JOIN PRIAM_FICHIER PF on FV.ID_FICHIER = PF.ID " +
                "INNER JOIN PRIAM_PROGRAMME PP on PP.NUMPROG = PF.NUMPROG " +
                "INNER JOIN PRIAM_AYANT_DROIT PAD on FV.id = PAD.ID_FV " +
                "INNER JOIN PRIAM_AYANT_DROIT_PERS PERS on PAD.NUMPERS = PERS.NUMPERS ");
        sqlPagingQueryProviderFactoryBean.setWhereClause("WHERE PP.NUMPROG ="+ numProg + " AND FV.isOeuvreComplex = 0 ");
        sqlPagingQueryProviderFactoryBean.setSortKey("PAD.COAD");

        try {
            this.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
            this.setDataSource(dataSource);
            this.setPageSize(2000);
            this.setRowMapper(new ExportCsvMapper());
        } catch (Exception e) {
            LOGGER.error(String.format("Exception %s", e));
        }

    }
}
