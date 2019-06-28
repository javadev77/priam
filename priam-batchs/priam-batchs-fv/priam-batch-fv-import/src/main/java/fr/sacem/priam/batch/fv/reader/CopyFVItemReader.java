package fr.sacem.priam.batch.fv.reader;

import fr.sacem.priam.batch.common.domain.LigneProgrammeFV;
import fr.sacem.priam.batch.common.util.mapper.importPenef.PriamLigneProgrammeFVSQLMapper;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * Created by embouazzar on 28/11/2018.
 */
@Component
@StepScope
public class CopyFVItemReader extends JdbcPagingItemReader<LigneProgrammeFV> {

    private Long idFichier;

    public CopyFVItemReader(@Value("#{jobExecutionContext['ID_FICHIER_GLOBAL']}") Long idFichier, @Autowired DataSource dataSource) {
        System.out.println("idFichier = " + idFichier);

        final SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(dataSource);
        sqlPagingQueryProviderFactoryBean.setSelectClause("SELECT id, id_fichier," +
                "                cdeCisac," +
                "                cdeFamilTypUtil," +
                "                numProg," +
                "                cdeUtil," +
                "                cdeTypUtil," +
                "                cdeGreDif," +
                "                cdeModDif," +
                "                cdeTypIde12," +
                "                ide12," +
                "                sum(durDif) durDif," +
                "                sum(nbrDif) nbrDif," +
                "                sum(mt) mt," +
                "                ctna," +
                "                paramCoefHor," +
                "                durDifCtna," +
                "                cdeLng," +
                "                indDoubSsTit," +
                "                tax," +
                "                typMt," +
                "                cdeGreIde12Cmplx," +
                "                cdeGreIde12," +
                "                titreOriCmplx," +
                "                titreAltPppalCmplx," +
                "                titreOriOeuvPereCmplx," +
                "                titreAltOeuvPereCmplx," +
                "                titreOeuvre," +
                "                cdePaysOriIso4NCmplx," +
                "                realisateurCmplx," +
                "                roleParticipant1," +
                "                nomParticipant1," +
                "                cdeTypUtilOri," +
                "                cdeFamilTypUtilOri," +
                "                libelleUtilisateur," +
                "                utilisateur," +
                "                date_insertion," +
                "                selection," +
                "                ajout," +
                "                rionEffet");
        sqlPagingQueryProviderFactoryBean.setFromClause("FROM PRIAM_LIGNE_PROGRAMME_TRAITEMENT_BATCH");
        sqlPagingQueryProviderFactoryBean.setWhereClause("WHERE ID_FICHIER = " + idFichier);
        sqlPagingQueryProviderFactoryBean.setGroupClause("GROUP BY ide12");
        sqlPagingQueryProviderFactoryBean.setSortKey("id");

        try {
            this.setQueryProvider(sqlPagingQueryProviderFactoryBean.getObject());
            this.setDataSource(dataSource);
            this.setPageSize(100);
            this.setRowMapper(new PriamLigneProgrammeFVSQLMapper());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setIdFichier(Long idFichier) {
        this.idFichier = idFichier;
    }

    public Long getIdFichier() {
        return idFichier;
    }
}
