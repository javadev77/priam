package fr.sacem.priam.batch.particpants.rep.mapper;

import fr.sacem.priam.batch.particpants.rep.domaine.Participant;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ParticipantRowMapper implements RowMapper<Participant> {

    @Override
    public Participant mapRow(ResultSet resultSet, int i) throws SQLException {
        Participant participant = new Participant();
        participant.setId(resultSet.getInt("ID"));
        participant.setIde12(resultSet.getLong("IDE12"));
        participant.setTypeCMS(resultSet.getString("TYPE_CMS"));
        participant.setRolPart(resultSet.getString("ROLE"));
        participant.setNomPart(resultSet.getString("PARTICIPANT"));
        participant.setStatut(resultSet.getInt("STATUT"));
        return participant;
    }
}
