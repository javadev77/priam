package fr.sacem.priam.batch.participants.rep.dao;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class ParticipantRepositoryForTest {

    private JdbcTemplate jdbcTemplate;
    private String nomTableFichier;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Long getNbParticipants(Long ide12){
        String sql = "SELECT COUNT(*) " +
                " FROM " + this.nomTableFichier +
                " WHERE IDE12=? ";
        return jdbcTemplate.queryForObject(sql, new Object[] {ide12}, Long.class);
    }

    public String getNomTableFichier() {
        return nomTableFichier;
    }

    public void setNomTableFichier(String nomTableFichier) {
        this.nomTableFichier = nomTableFichier;
    }
}
