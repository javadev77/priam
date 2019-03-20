package fr.sacem.priam.model.dao.jpa.fv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

@Component
public class ImportDataBatchFVJdbcDao {

    @Autowired
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void postConstruct() {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void deleteImportProgrammeByIdFichier(Long idFichier){
        String sql = "DELETE FROM PRIAM_IMPORT_PROGRAMME_FV_DATA_BATCH WHERE ID_FICHIER=?";
        jdbcTemplate.update(sql, idFichier);
    }

}
