package fr.sacem.priam.services.utils;

import fr.sacem.priam.common.TypeUtilisationEnum;
import fr.sacem.priam.model.domain.dto.FelixData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by benmerzoukah on 21/08/2017.
 */
@Component
public class FelixDataSpringValidator implements Validator {

    
    @Override
    public boolean supports(Class<?> clazz) {
	  return FelixData.class.equals(clazz);
    }
    
    @Override
    public void validate(Object o, Errors errors) {
	  String cdeTypUtil = ((FelixData)o).getCdeTypUtil();
   
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCisac", "error.cdeCisac");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTer", "error.cdeTer");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rionEffet", "error.rionEffet");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "numProg", "error.numProg");
	  //ValidationUtils.rejectIfEmptyOrWhitespace(errors, "keyLigPenel", "error.keyLigPenel");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeUtil", "error.cdeUtil");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeModFac", "error.cdeModFac");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypProg", "error.cdeTypProg");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCompl", "error.cdeCompl");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "libProg", "error.libProg");
	  
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datDbtProg", "error.datDbtProg");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "datFinProg", "error.datFinProg");
	  
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");
   
	  ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
	  
	  if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equalsIgnoreCase(cdeTypUtil))  {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durDif", "error.durDif");
	  
	  } else if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equalsIgnoreCase(cdeTypUtil)) {
	  
	  }
	  
	  
    }
}
