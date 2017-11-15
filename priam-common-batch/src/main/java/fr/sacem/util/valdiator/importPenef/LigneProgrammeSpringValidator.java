package fr.sacem.util.valdiator.importPenef;

import fr.sacem.domain.LigneProgramme;
import fr.sacem.priam.common.TypeUtilisationEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by belwidanej on 28/08/2017.
 */
@Component
public class LigneProgrammeSpringValidator implements Validator {


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
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "nbrDif", "error. nbrDif");
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

	protected void validateNumericFields(Errors errors, LigneProgramme o) {

		Double ide12 = validateNumericField(errors, o.getIde12(), "ide12");
		if(ide12 != null)
			o.setIde12(Long.toString(ide12.longValue()));
		else
			o.setIde12(null);

		Double durDif = validateNumericField(errors, o.getDurDif(), "durDif");
		if(durDif != null)
			o.setDurDif(Long.toString(durDif.longValue()));
		else
			o.setDurDif(null);

		Double nbrDif = validateNumericField(errors, o.getNbrDif(), "nbrDif");
		if(nbrDif != null)
			o.setNbrDif(Long.toString(nbrDif.longValue()));
		else
			o.setNbrDif(null);

		Double durDifCtna = validateNumericField(errors, o.getDurDifCtna(), "durDifCtna");
		if(durDifCtna != null)
			o.setDurDifCtna(Long.toString(durDifCtna.longValue()));
		else
			o.setDurDifCtna(null);

		Double tax = validateNumericField(errors, o.getTax(), "tax");
		if(tax != null)
			o.setTax(tax.toString());
		else
			o.setTax(null);

		Double mt = validateNumericField(errors, o.getMt(), "mt");
		if(mt != null)
			o.setMt(mt.toString());
		else
			o.setMt(null);
	}


	protected Double validateNumericField(Errors errors, String text, String field) {
		if (StringUtils.hasText(text)) {
			NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
			try {
				return nf.parse(text).doubleValue();
			} catch (ParseException e) {
				errors.rejectValue(field, "format.error."+field);
			}
		}

		return null;
	}
}
