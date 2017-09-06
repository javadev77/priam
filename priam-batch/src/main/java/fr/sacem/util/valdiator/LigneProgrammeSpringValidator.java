package fr.sacem.util.valdiator;

import fr.sacem.domain.LigneProgramme;
import fr.sacem.priam.common.TypeUtilisationEnum;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by belwidanej on 28/08/2017.
 */
@Component
public class LigneProgrammeSpringValidator implements Validator {


	public static final int IDE12_LENGTH = 10;

	@Override
    public boolean supports(Class<?> clazz) {
	  return LigneProgramme.class.equals(clazz);
    }
    
    @Override
    public void validate(Object o, Errors errors) {

		String cdeTypUtil = ((LigneProgramme)o).getCdeTypUtil();

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCisac", "error.cdeCisac");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeUtil", "error.cdeUtil");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");

		Long ide12 = ((LigneProgramme) o).getIde12();
		if(ide12 != null && ide12.toString().length() != IDE12_LENGTH) {
			errors.rejectValue("ide12", "length.error.ide12");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvre", "error.titreOeuvre");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleParticipant1", "error.roleParticipant1");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nomParticipant1", "error.nomParticipant1");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtilOri", "error.cdeTypUtilOri");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtilOri", "error.cdeFamilTypUtilOri");

		if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(cdeTypUtil)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durDif", "error.durDif");
		}

		if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(cdeTypUtil)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error. nbrDif");
		}
    }
}
