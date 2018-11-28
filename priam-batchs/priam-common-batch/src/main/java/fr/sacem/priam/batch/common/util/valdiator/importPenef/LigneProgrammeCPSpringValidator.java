package fr.sacem.priam.batch.common.util.valdiator.importPenef;

import fr.sacem.priam.batch.common.domain.LigneProgramme;
import fr.sacem.priam.common.TypeUtilisationEnum;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by belwidanej on 28/08/2017.
 */
@Component
public class LigneProgrammeCPSpringValidator extends CommonValidator implements Validator {


	public static final int IDE12_LENGTH_MAX = 12;
	public static final int IDE12_LENGTH_MIN = 6;
	public static final String NOMPARTICIPANT_VIDE = "COMPTE DE RESERVE";
	public static final String ROLEPARTICIPANT_VIDE = "CR";
	public static final String ERROR_DECIMAL_BINDING = "PARSIN-ERROR-";

	@Override
    public boolean supports(Class<?> clazz) {
	  return LigneProgramme.class.equals(clazz);
    }


    @Override
    public void validate(Object o, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeCisac", "error.cdeCisac");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeUtil", "error.cdeUtil");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypIde12", "error.cdeTypIde12");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "ide12", "error.ide12");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "titreOeuvre", "error.titreOeuvre");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeTypUtilOri", "error.cdeTypUtilOri");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cdeFamilTypUtilOri", "error.cdeFamilTypUtilOri");
		//COPIEPRIV
		String cdeTypUtil = ((LigneProgramme)o).getCdeTypUtil();
		String cdeFamilTypUtil = ((LigneProgramme)o).getCdeFamilTypUtil();
		String ide12 = ((LigneProgramme) o).getIde12();
		String nomParticipant1 = ((LigneProgramme) o).getNomParticipant1();
		String roleParticipant1 = ((LigneProgramme) o).getRoleParticipant1();

		if(nomParticipant1 == null || nomParticipant1.isEmpty()){
			((LigneProgramme) o).setNomParticipant1(NOMPARTICIPANT_VIDE);
		}
		if(roleParticipant1 == null || roleParticipant1.isEmpty()){
			((LigneProgramme) o).setRoleParticipant1(ROLEPARTICIPANT_VIDE);
		}
		if(ide12 != null && !ide12.isEmpty() && (ide12.length() < IDE12_LENGTH_MIN || ide12.length() > IDE12_LENGTH_MAX)) {
			errors.rejectValue("ide12", "format.error.ide12");
		}
		if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(cdeTypUtil)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "durDif", "error.durDif");
		}

		if(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(cdeTypUtil)) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error.nbrDif");
		}

		if(cdeTypUtil == null || cdeTypUtil.isEmpty()) {
			errors.rejectValue("cdeTypUtil", "error.cdeTypUtil");
		}
		else if(!TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode().equals(cdeTypUtil)
				&& !TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode().equals(cdeTypUtil)) {
			errors.rejectValue("cdeTypUtil", "format.error.cdeTypUtil");
		}


		if(cdeFamilTypUtil == null || cdeFamilTypUtil.isEmpty()) {
			errors.rejectValue("cdeFamilTypUtil", "error.cdeFamilTypUtil");
		}else if(cdeTypUtil != null && !cdeTypUtil.isEmpty() &&
				!cdeFamilTypUtil.equals(TypeUtilisationEnum.getValue(cdeTypUtil).getCodeFamille())) {
			errors.rejectValue("cdeFamilTypUtil", "format.error.cdeFamilTypUtil");
		}


		validateNumericFields(errors, (LigneProgramme) o);
    }

}
