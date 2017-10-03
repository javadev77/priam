package fr.sacem.priam.model.util;

import fr.sacem.priam.model.domain.saref.SareftrFamiltyputil;
import fr.sacem.priam.model.domain.Programme;
import fr.sacem.priam.model.domain.saref.SareftrTyputil;
import fr.sacem.priam.model.domain.dto.ProgrammeDto;
import org.modelmapper.ModelMapper;

/**
 * Created by fandis on 14/06/2017.
 */
public class MapperConfiguration {
    public Programme convertProgrammeDtoToProgramme(ProgrammeDto programmeDto){
        ModelMapper modelMapper = new ModelMapper();
        Programme programme= modelMapper.map(programmeDto, Programme.class);
        SareftrFamiltyputil sareftrFamiltyputil = new SareftrFamiltyputil();
        sareftrFamiltyputil.setCode(programmeDto.getFamille());
        SareftrTyputil sareftrTyputil =new SareftrTyputil();
        sareftrTyputil.setCodeFamille(programmeDto.getFamille());
        sareftrTyputil.setCode(programmeDto.getTypeUtilisation());
        programme.setSareftrFamiltyputil(sareftrFamiltyputil);
        programme.setSareftrTyputil(sareftrTyputil);
        return programme;
    }

    public MapperConfiguration() {
    }
}
