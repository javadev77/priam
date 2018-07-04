package fr.sacem.util.valdiator.importPenef;

import fr.sacem.domain.LigneProgramme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import static fr.sacem.priam.common.TypeUtilisationEnum.CMS_ANT;
import static fr.sacem.priam.common.TypeUtilisationEnum.CMS_FRA;

/**
 * Created by belwidanej on 28/08/2017.
 */
@Component
public class LigneProgrammeCMSSpringValidator extends CommonValidator implements Validator {


	public static final int IDE12_LENGTH_MAX = 12;
	public static final int IDE12_LENGTH_MIN = 6;

	@Value("${type.fichier}")
	String typeFichier;

	@Override
    public boolean supports(Class<?> clazz) {
	  return LigneProgramme.class.equals(clazz);
    }


    @Override
    public void validate(Object o, Errors errors) {
		String ide12 = ((LigneProgramme) o).getIde12();
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvre", "error.titreOeuvre");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtilOri", "error.cdeTypUtilOri");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtilOri", "error.cdeFamilTypUtilOri");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtil", "error.cdeFamilTypUtil");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeUtil", "error.cdeUtil");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCisac", "error.cdeCisac");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtil", "error.cdeTypUtil");


		if(CMS_ANT.getCode().equals(typeFichier)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
		}
		if(CMS_FRA.getCode().equals(typeFichier)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mt", "error.mt");
		}

		if (ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
				errors.rejectValue("ide12", "format.error.ide12");
		}

		validateNumericFields(errors, (LigneProgramme) o);
    }

}
