package fr.sacem.priam.batch.affectation.mapper;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by fandis on 15/12/2017.
 */
public class PointsRowMapper implements RowMapper<PointsResult>  {

    private static  final Logger LOGGER = LoggerFactory.getLogger(PointsRowMapper.class);

    @Override
    public PointsResult mapRow(ResultSet resultSet, int i) throws SQLException {
        PointsResult pointsResult = new PointsResult();
        pointsResult.setIde12(resultSet.getLong("ide12"));
        pointsResult.setMt(resultSet.getDouble("mt"));

        LOGGER.info("pointsResult : ide12="  + pointsResult.getIde12() + ", mt = " + pointsResult.getMt());

        return pointsResult;
    }
}
