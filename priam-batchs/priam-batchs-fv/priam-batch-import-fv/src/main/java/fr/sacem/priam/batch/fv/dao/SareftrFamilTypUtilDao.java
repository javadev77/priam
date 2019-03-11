package fr.sacem.priam.batch.fv.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by embouazzar on 13/12/2018.
 */
@Repository
public class SareftrFamilTypUtilDao {

    @Autowired
    DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        this.jdbcTemplate = new JdbcTemplate(this.dataSource);
    }

    @Transactional(readOnly = true)
    @Cacheable("SareftrFamilTyputil")
    public Map<String, List<String>> findAll(){
        String sql = "SELECT CDEFAMILTYPUTIL, CDETYPUTIL FROM SAREFTR_TYPUTIL ORDER BY CDEFAMILTYPUTIL ";
        return jdbcTemplate.query(sql, resultSet -> {
        Map<String, List<String>> sareftrFamiltyputilListMap = new HashMap<>();

        while (resultSet.next()){

            String sareftrFamiltyputil = resultSet.getString("CDEFAMILTYPUTIL");
            String sareftrTyputil = resultSet.getString("CDETYPUTIL");

            List<String> sareftrTyputilList = sareftrFamiltyputilListMap.get(sareftrFamiltyputil);
            if (sareftrTyputilList == null) {
                List<String> sareftrTyputilNewList = new ArrayList<>();
                sareftrTyputilNewList.add(sareftrTyputil);
                sareftrFamiltyputilListMap.put(sareftrFamiltyputil, sareftrTyputilNewList);
            } else {
                sareftrTyputilList.add(sareftrTyputil);
            }
        }
        return sareftrFamiltyputilListMap;
    });
    }

}
