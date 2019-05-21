package fr.sacem.priam.batch.participants.rep.mapper;

import fr.sacem.priam.batch.participants.rep.domain.Participant;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class SaveParticipantRowMapper implements RowMapper<Participant> {

    @Override
    public Participant mapRow(ResultSet resultSet, int i) throws SQLException {
        Participant participant = new Participant();

        participant.setId(resultSet.getInt("ID"));
        participant.setIde12(resultSet.getLong("IDE12"));
        participant.setTypeCMS(resultSet.getString("TYPE_CMS"));
        participant.setRolPart(resultSet.getString("ROLE"));
        participant.setNomPart(resultSet.getString("PARTICIPANT"));
        participant.setOeuvreSortie(resultSet.getInt("OEUVRE_SORTIE"));

        return participant;
    }
}
