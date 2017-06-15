package fr.sacem.priam.model.util;

import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.modelmapper.ModelMapper;

/**
 * Created by fandis on 14/06/2017.
 */
public class MapperConfiguration {
    public Programme convertProgrammeDtoToProgramme(ProgrammeDto programmeDto){
        ModelMapper modelMapper = new ModelMapper();

        retrun modelMapper.map(programmeDto, Programme.class);
    }
}
