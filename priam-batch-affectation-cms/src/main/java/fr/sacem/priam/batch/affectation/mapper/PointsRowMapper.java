package fr.sacem.priam.batch.affectation.mapper;


import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fandis on 15/12/2017.
 */
public class PointsRowMapper implements RowMapper<PointsResult>  {


    @Override
    public PointsResult mapRow(ResultSet resultSet, int i) throws SQLException {
        PointsResult pointsResult = new PointsResult();
        pointsResult.setId(resultSet.getLong("id"));
        pointsResult.setMt(resultSet.getDouble("mt"));

        return pointsResult;
    }
}
