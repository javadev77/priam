package fr.sacem.priam.batch.fv.export.dao;

import fr.sacem.priam.batch.common.util.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class ExportProgrammeFVDao {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    public long insertStatutExportProgramme(String numProg, String path, String statut){
        String sql = "INSERT INTO PRIAM_EXPORT_PROGRAMME_FV (NUMPROG, FILENAME,DATE_CREATION, STATUT) VALUES (?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {

            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, numProg);
            stmt.setString(2, path);
            stmt.setTimestamp(3, new DateTimeUtils().getCurrentTimeStamp());
            stmt.setString(4, statut);

            return stmt;
        }, keyHolder);

                return keyHolder.getKey().longValue();
    }

}
