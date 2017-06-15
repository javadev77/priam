package fr.sacem.priam.model.util;

import fr.sacem.priam.model.domain.Famille;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.TypeUtilisation;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;

/**
 * Created by fandis on 14/06/2017.
 */
public class MapperConfiguration {
    public Programme convertProgrammeDtoToProgramme(ProgrammeDto programmeDto){
        ModelMapper modelMapper = new ModelMapper();
        Programme programme= modelMapper.map(programmeDto, Programme.class);
        Famille famille= new Famille();
        famille.setCode(programmeDto.getFamille());
        TypeUtilisation typeUtilisation =new TypeUtilisation();
        typeUtilisation.setCodeFamille(programmeDto.getFamille());
        typeUtilisation.setCode(programmeDto.getTypeUtilisation());
        programme.setFamille(famille);
        programme.setTypeUtilisation(typeUtilisation);
        return programme;
    }

    public MapperConfiguration() {
    }
}
