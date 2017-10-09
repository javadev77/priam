package fr.sacem.util.valdiator;

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


	public static final int IDE12_LENGTH = 10;

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

		if(ide12 != null && !ide12.isEmpty() && (ide12.length() < 6 ||  ide12.length() > 12)) {
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
		else if(!cdeTypUtil.equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_PHONO.getCode()) && !cdeTypUtil.equals(TypeUtilisationEnum.COPIE_PRIVEE_SONORE_RADIO.getCode())) {
			errors.rejectValue("cdeTypUtil", "format.error.cdeTypUtil");
		}

		try{

			if(cdeFamilTypUtil == null || cdeFamilTypUtil.isEmpty()) {
				errors.rejectValue("cdeFamilTypUtil", "error.cdeFamilTypUtil");
			}else if(!cdeTypUtil.isEmpty() && !cdeFamilTypUtil.equals(TypeUtilisationEnum.getValue(cdeTypUtil).getCodeFamille())) {
				errors.rejectValue("cdeFamilTypUtil", "format.error.cdeFamilTypUtil");
			}
		}catch (Exception e) {

		}

		validateNumericFields(errors, (LigneProgramme) o);
    }

	private void validateNumericFields(Errors errors, LigneProgramme o) {

		Double ide12 = validateNumericField(errors, o.getIde12(), "ide12");
		if(ide12 != null)
			o.setIde12(ide12.longValue()+"");
		else
			o.setIde12(null);

		Double durDif = validateNumericField(errors, o.getDurDif(), "durDif");
		if(durDif != null)
			o.setDurDif(durDif.longValue()+"");
		else
			o.setDurDif(null);

		Double nbrDif = validateNumericField(errors, o.getNbrDif(), "nbrDif");
		if(nbrDif != null)
			o.setNbrDif(nbrDif.longValue()+"");
		else
			o.setNbrDif(null);

		Double durDifCtna = validateNumericField(errors, o.getDurDifCtna(), "durDifCtna");
		if(durDifCtna != null)
			o.setDurDifCtna(durDifCtna.longValue()+"");
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


	public Double validateNumericField(Errors errors, String text, String field) {
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
