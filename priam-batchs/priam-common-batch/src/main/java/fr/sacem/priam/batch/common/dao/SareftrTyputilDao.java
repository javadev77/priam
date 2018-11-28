package fr.sacem.priam.batch.common.dao;

import fr.sacem.priam.batch.common.domain.SareftrTyputil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by embouazzar on 27/11/2018.
 */

@Repository
public class SareftrTyputilDao {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Transactional(readOnly = true)
    @Cacheable("SareftrTyputil")
    public List<SareftrTyputil> findByCdeFamilTypUtilOri(String cdeFamilTypUtilOri){
        String sql = "SELECT s.CDETYPUTIL, s.CDEFAMILTYPUTIL, s.DATDBTVLD, s.DATFINVLD " +
                "FROM SAREFTR_TYPUTIL s " +
                "WHERE s.DATDBTVLD IS NOT NULL " +
                "AND (s.DATFINVLD IS NULL OR " +
                "DATE_FORMAT(s.DATFINVLD, '%Y-%d-%m') = '0000-00-00' OR " +
                "s.DATFINVLD >= CURRENT_DATE) " +
                "AND s.CDEFAMILTYPUTIL =? ";

        return jdbcTemplate.query(sql, new ResultSetExtractor<List<SareftrTyputil>>(){
            @Override
            public List<SareftrTyputil> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<SareftrTyputil> list = new ArrayList<>();
                while(rs.next()){
                    SareftrTyputil sareftrTyputil = new SareftrTyputil();
                    sareftrTyputil.setCode(rs.getString("CDETYPUTIL"));
                    sareftrTyputil.setCodeFamille(rs.getString("CDEFAMILTYPUTIL"));
                    sareftrTyputil.setDateDebut(rs.getDate("DATDBTVLD"));
                    sareftrTyputil.setDateFin(rs.getDate("DATFINVLD"));
                    list.add(sareftrTyputil);
                }
                return list;
            }
        }, cdeFamilTypUtilOri);

    }

}
